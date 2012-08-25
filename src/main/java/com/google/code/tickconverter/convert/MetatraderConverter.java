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

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashSet;

import org.joda.time.DateTime;
import org.joda.time.MutableInterval;
import org.joda.time.ReadWritableInterval;
import org.joda.time.ReadableInstant;
import org.joda.time.ReadableInterval;
import org.joda.time.ReadablePeriod;

import com.google.code.tickconverter.bean.IDukascopyRO;
import com.google.code.tickconverter.bean.IMetatraderRO;
import com.google.code.tickconverter.util.DukascopyAskBidComparator;
import com.google.code.tickconverter.util.DukascopyDateComparator;

/**
 * This class represent the converter between {@link IDukascopyRO} objects and {@link IMetatraderRO}. All
 * {@link IDukascopyRO} objects holds in a {@link HashSet}. The {@link #addDukascopy(IDukascopyRO)} method adds objects
 * only if the timestamp contains in the assigned {@link ReadWritableInterval}. The {@link #incrementInterval()} method
 * remove all objects of the {@link HashSet} and increment the interval.
 * 
 * @author Karsten Schulz <a href="mailto:lennylinux.ks@googlmail.com">(lennylinux.ks@googlmail.com)</a>
 */
public class MetatraderConverter
    implements IMetatraderRO
{
    private final HashSet<IDukascopyRO> validTickSet;

    private final ReadWritableInterval currentInterval;

    private final ReadablePeriod period;

    /**
     * Standard constructor to initalize the {@link MetatraderConverter} object. It's only one object necessary.
     * 
     * @param interval
     */
    public MetatraderConverter( final ReadableInstant start, final ReadablePeriod period )
    {
        validTickSet = new HashSet<>();
        this.period = period;
        currentInterval = new MutableInterval( start, period );
    }

    @Override
    public DateTime getTimeStamp()
    {
        return currentInterval.getStart();
    }

    @Override
    public double getOpen()
    {
        if ( validTickSet.size() == 0 )
        {
            return 0;
        }

        ArrayList<IDukascopyRO> tmpList = new ArrayList<>( validTickSet );
        Collections.sort( tmpList, new DukascopyDateComparator() );
        IDukascopyRO firstTick = tmpList.get( 0 );
        return ( firstTick.getAsk() + firstTick.getBid() ) / 2;
    }

    @Override
    public double getMax()
    {
        if ( validTickSet.size() == 0 )
        {
            return 0;
        }

        IDukascopyRO maxValue = Collections.max( validTickSet, new DukascopyAskBidComparator() );
        return ( maxValue.getAsk() + maxValue.getBid() ) / 2;
    }

    @Override
    public double getMin()
    {
        if ( validTickSet.size() == 0 )
        {
            return 0;
        }

        IDukascopyRO minValue = Collections.min( validTickSet, new DukascopyAskBidComparator() );
        return ( minValue.getAsk() + minValue.getBid() ) / 2;
    }

    @Override
    public double getClose()
    {
        if ( validTickSet.size() == 0 )
        {
            return 0;
        }

        ArrayList<IDukascopyRO> tmpList = new ArrayList<>( validTickSet );
        Collections.sort( tmpList, Collections.reverseOrder( new DukascopyDateComparator() ) );
        IDukascopyRO firstTick = tmpList.get( 0 );
        return ( firstTick.getAsk() + firstTick.getBid() ) / 2;
    }

    @Override
    public double getVolume()
    {
        double totalVolume = 0;
        for ( IDukascopyRO dukasObject : validTickSet )
        {
            totalVolume = totalVolume + dukasObject.getAskVolume() + dukasObject.getBidVolume();
        }
        return totalVolume;
    }

    /**
     * Try to add a new {@link IDukascopyRO}. It's only possible if the timestamp is in range of the
     * {@link #currentInterval}.
     * 
     * @param object
     * @throws InvalidTimeException throws if the object has a invalid time range and don't contains the current
     *             interval
     * @see ReadableInterval#contains(org.joda.time.ReadableInstant)
     */
    public void addDukascopy( final IDukascopyRO object )
        throws InvalidTimeException
    {
        if ( currentInterval.contains( object.getTimeStamp() ) )
        {
            validTickSet.add( object );
        }
        else
        {
            throw new InvalidTimeException( object.getTimeStamp() + " is not in range of the current interval: "
                + currentInterval );
        }
    }

    /**
     * Returns a boolean value to query if this object has elements in his {@link HashSet}
     * 
     * @return
     */
    public boolean hasElements()
    {
        return 0 < validTickSet.size();
    }

    /**
     * Remove all entries of the current set
     */
    public void clearTicks()
    {
        validTickSet.clear();
    }

    /**
     * Remove all entries like {@link #clearTicks()} and set the end time to the new start time.
     */
    public void incrementInterval()
    {
        clearTicks();
        currentInterval.setStart( currentInterval.getEnd() );
        currentInterval.setPeriodAfterStart( period );
    }
}
