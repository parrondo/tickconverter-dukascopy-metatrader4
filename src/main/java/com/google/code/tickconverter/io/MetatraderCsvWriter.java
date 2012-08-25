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

import java.io.FileWriter;
import java.io.IOException;
import java.text.DecimalFormat;
import java.text.DecimalFormatSymbols;
import java.util.Locale;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.TimeUnit;

import au.com.bytecode.opencsv.CSVWriter;

import com.google.code.tickconverter.bean.IMetatraderRO;

/**
 * This class take the {@link IMetatraderRO} out of the {@link BlockingQueue} and write this object to another csv file.
 * The class {@link MetatraderCsvWriter} implements additional the interface {@link Runnable} to write line by line the
 * information in a {@link Thread}.
 * 
 * @author Karsten Schulz <a href="mailto:lennylinux.ks@googlmail.com">(lennylinux.ks@googlmail.com)</a>
 */
public class MetatraderCsvWriter
    implements Runnable
{

    private final BlockingQueue<IMetatraderRO> traderQueue;

    private final String filename;

    /**
     * Standard constructor to instance an object of this class.
     * 
     * @param traderQueue <br>
     *            the {@link BlockingQueue} to take the {@link IMetatraderRO} objects and write down into the define csv
     *            file.
     * @param filename <br>
     *            the full path of the output file location.
     */
    public MetatraderCsvWriter( final BlockingQueue<IMetatraderRO> traderQueue, final String filename )
    {
        this.traderQueue = traderQueue;
        this.filename = filename;
    }

    /**
     * Process method to write the {@link IMetatraderRO} objects into the csv file ({@link #filename}).
     * 
     * @throws IOException will throws if any other I/O errors where occur of the process
     * @throws InterruptedException will throws if the method will interrupt in the poll phase
     */
    public void write()
        throws IOException, InterruptedException
    {
        try (CSVWriter writer = new CSVWriter( new FileWriter( filename ), ',', CSVWriter.NO_QUOTE_CHARACTER ))
        {
            DecimalFormat format = new DecimalFormat( "#####0.00000", new DecimalFormatSymbols( Locale.US ) );
            while ( true )
            {
                IMetatraderRO trader = traderQueue.poll( 5, TimeUnit.SECONDS );

                if ( null != trader )
                {
                    String[] line = new String[7];
                    line[0] = trader.getTimeStamp().toString( "yyyy.MM.dd" );
                    line[1] = trader.getTimeStamp().toString( "HH:mm" );
                    line[2] = format.format( trader.getOpen() );
                    line[3] = format.format( trader.getMax() );
                    line[4] = format.format( trader.getMin() );
                    line[5] = format.format( trader.getClose() );
                    line[6] = format.format( trader.getVolume() );

                    writer.writeNext( line );
                }
                else
                {
                    break;
                }
            }
        }
    }

    @Override
    public void run()
    {
        try
        {
            write();
        }
        catch ( IOException | InterruptedException e )
        {
            e.printStackTrace();
        }
    }
}
