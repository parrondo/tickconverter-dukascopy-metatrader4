/**
 * Copyright (C) 2012 Karsten Schulz <lennylinux.ks@googlemail.com>
 *
 * Permission is hereby granted, free of charge, to any person obtaining a copy of this software and
 * associated documentation files (the "Software"), to deal in the Software without restriction,
 * including without limitation the rights to use, copy, modify, merge, publish, distribute,
 * sublicense, and/or sell copies of the Software, and to permit persons to whom the Software is
 * furnished to do so, subject to the following conditions:
 *
 * The above copyright notice and this permission notice shall be included in all copies or
 * substantial portions of the Software.
 *
 * THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR IMPLIED, INCLUDING BUT
 * NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY, FITNESS FOR A PARTICULAR PURPOSE AND
 * NONINFRINGEMENT. IN NO EVENT SHALL THE AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM,
 * DAMAGES OR OTHER LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM, OUT
 * OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN THE SOFTWARE.
 */
package com.google.code.tickconverter.io;

import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.GregorianCalendar;
import java.util.concurrent.BlockingQueue;

import org.apache.commons.lang.StringUtils;
import org.joda.time.DateTime;

import au.com.bytecode.opencsv.CSVReader;

import com.google.code.tickconverter.bean.DukascopyBean;
import com.google.code.tickconverter.bean.IDukascopyRO;
import com.google.code.tickconverter.util.LoggerUtils;

/**
 * This class works like a standard reader class. The {@link #read()}-method of {@link DukascopyCsvReader} read line by
 * line the csv file, which is specify in {@link #filename} attribute. Afterwards the line is scanned by the
 * {@link CSVReader} and the tokens will save in a new {@link DukascopyBean} object. This class implements the
 * {@link Runnable} interface to run in a {@link Thread}.
 * 
 * @author Karsten Schulz <a href="mailto:lennylinux.ks@googlmail.com">(lennylinux.ks@googlmail.com)</a>
 */
public class DukascopyCsvReader
    implements Runnable
{
    private final BlockingQueue<IDukascopyRO> dukaQueue;

    private final String filename;

    /**
     * Standard constructor of class {@link DukascopyCsvReader}. This constructor needs an {@link BlockingQueue} to put
     * the reading {@link DukascopyBean} objects to process. The filename define the location of the csv file.
     * 
     * @param dukaQueue <br>
     *            the {@link BlockingQueue} of {@link IDukascopyRO} to process
     * @param filename <br>
     *            the full filename of the csv file to parse
     */
    public DukascopyCsvReader( final BlockingQueue<IDukascopyRO> dukaQueue, final String filename )
    {
        this.dukaQueue = dukaQueue;
        this.filename = filename;
    }

    /**
     * Process method to read the csv file ({@link #filename}) and put the information as {@link DukascopyBean} objects
     * into the {@link BlockingQueue} of {@link IDukascopyRO}
     * 
     * @throws FileNotFoundException will throws if the file wheres not found
     * @throws IOException will throws if any other I/O errors where occur of the process
     * @throws ParseException will throws if the timestamp isn't in a valid format. The expected format of the timestamp
     *             is "dd.MM.yyyy HH:mm:ss.SSS"
     * @see SimpleDateFormat
     * @see CSVReader
     */
    public void read()
        throws FileNotFoundException, IOException, ParseException
    {
        LoggerUtils.createInfoLog( "read from file: " + filename );
        SimpleDateFormat dateFormat = new SimpleDateFormat( "dd.MM.yyyy HH:mm:ss.SSS" );
        GregorianCalendar cal = new GregorianCalendar();
        try (CSVReader reader = new CSVReader( new FileReader( filename ) ))
        {
            String[] line;
            while ( ( line = reader.readNext() ) != null )
            {
                String withoutSep = StringUtils.remove( line[1], "." );
                LoggerUtils.createDebugLog( "second token without separator: " + withoutSep );
                if ( StringUtils.isNumeric( withoutSep ) )
                {
                    cal.setTime( dateFormat.parse( line[0] ) );
                    DateTime dateTime = new DateTime( cal );
                    IDukascopyRO bean =
                        new DukascopyBean( dateTime, Double.parseDouble( line[1] ), Double.parseDouble( line[2] ),
                                           Double.parseDouble( line[3] ), Double.parseDouble( line[4] ) );
                    dukaQueue.offer( bean );
                    LoggerUtils.createDebugLog( "offer new element in queue:" + bean );
                }
            }
        }
    }

    @Override
    public void run()
    {
        try
        {
            LoggerUtils.createInfoLog( "start thread to read the csv file" );
            read();
        }
        catch ( IOException | ParseException e )
        {
            LoggerUtils.createErrorLog( "there is an error by parsing the csv file: " + filename, e );
        }
    }
}
