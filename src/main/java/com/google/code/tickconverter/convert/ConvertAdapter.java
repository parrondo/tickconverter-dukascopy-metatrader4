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
package com.google.code.tickconverter.convert;

import java.util.concurrent.BlockingQueue;
import java.util.concurrent.TimeUnit;

import org.joda.time.DateTime;
import org.joda.time.DateTimeFieldType;
import org.joda.time.Period;

import com.google.code.tickconverter.bean.IDukascopyRO;
import com.google.code.tickconverter.bean.IMetatraderRO;
import com.google.code.tickconverter.bean.MetatraderBean;

/**
 * This class convert the {@link IDukascopyRO} object of the reference from a {@link BlockingQueue} and create a
 * {@link IMetatraderRO} object.
 * 
 * @author Karsten Schulz <a href="mailto:lennylinux.ks@googlmail.com">(lennylinux.ks@googlmail.com)</a>
 */
public class ConvertAdapter
    implements Runnable
{

    private final BlockingQueue<IDukascopyRO> dukaQueue;

    private final BlockingQueue<IMetatraderRO> traderQueue;

    private MetatraderConverter converter;

    /**
     * Create an object of the class {@link ConvertAdapter} and save the references of {@link BlockingQueue}s of
     * {@link IDukascopyRO} and {@link IMetatraderRO}
     * 
     * @param dukaQueue <br>
     *            reference from a {@link BlockingQueue} of {@link IDukascopyRO} to poll the objects
     * @param traderQueue <br>
     *            reference from a {@link BlockingQueue} of {@link IMetatraderRO} to put the converted objects
     */
    public ConvertAdapter( final BlockingQueue<IDukascopyRO> dukaQueue, final BlockingQueue<IMetatraderRO> traderQueue )
    {
        this.dukaQueue = dukaQueue;
        this.traderQueue = traderQueue;
    }

    /**
     * This method is the main method of the convert process. While the {@link BlockingQueue} of {@link IDukascopyRO}
     * have for 10 seconds no objects in the {@link BlockingQueue} add this method this object into the
     * {@link MetatraderConverter}. If an {@link InvalidTimeException} will threw the converter put a new
     * {@link MetatraderBean} into the {@link BlockingQueue} of {@link IMetatraderRO} and get the information of the
     * values from the {@link MetatraderConverter} object.
     * 
     * @throws InterruptedException will threw if {@link Thread#interrupt()} is called in the poll phase
     * @throws InvalidTimeException will threw if {@link MetatraderConverter#incrementInterval()} have a low range for
     *             the new object after the creation of a new {@link MetatraderBean}
     */
    public void convertProcess()
        throws InterruptedException, InvalidTimeException
    {
        while ( true )
        {
            IDukascopyRO object = dukaQueue.poll( 2, TimeUnit.SECONDS );
            if ( null == object )
            {
                if ( null != converter && converter.hasElements() )
                {
                    traderQueue.put( new MetatraderBean( converter ) );
                }

                break;
            }

            if ( null == converter )
            {
                DateTime timestamp = object.getTimeStamp();
                DateTime start =
                    new DateTime( timestamp.get( DateTimeFieldType.year() ),
                                  timestamp.get( DateTimeFieldType.monthOfYear() ),
                                  timestamp.get( DateTimeFieldType.dayOfMonth() ),
                                  timestamp.get( DateTimeFieldType.hourOfDay() ),
                                  timestamp.get( DateTimeFieldType.minuteOfHour() ) );
                converter = new MetatraderConverter( start, Period.minutes( 1 ) );
            }
            try
            {
                converter.addDukascopy( object );
            }
            catch ( InvalidTimeException e )
            {
                traderQueue.put( new MetatraderBean( converter ) );
                converter.incrementInterval();
                converter.addDukascopy( object );
            }
        }
    }

    /*
     * (non-Javadoc)
     * @see java.lang.Runnable#run()
     */
    @Override
    public void run()
    {
        try
        {
            convertProcess();
        }
        catch ( InterruptedException | InvalidTimeException e )
        {
            e.printStackTrace();
        }
    }
}
