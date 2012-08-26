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
package com.google.code.tickconverter.bean;

import org.apache.commons.lang.builder.EqualsBuilder;
import org.apache.commons.lang.builder.HashCodeBuilder;
import org.apache.commons.lang.builder.ToStringBuilder;
import org.apache.commons.lang.builder.ToStringStyle;
import org.joda.time.DateTime;

/**
 * This class represent one tick of the input csv file. The attributes are {@link DateTime} for the timestamp of the
 * tick and the number attributes {@link #ask}, {@link #bid}, {@link #askVolume} and {@link #bidVolume}
 * 
 * @author Karsten Schulz <a href="mailto:lennylinux.ks@googlmail.com">(lennylinux.ks@googlmail.com)</a>
 */
public class DukascopyBean implements IDukascopyRO
{
    private DateTime timeStamp;

    private double ask;

    private double bid;

    private double askVolume;

    private double bidVolume;

    /**
     * Simple constructor to instance a bean.
     */
    public DukascopyBean()
    {
        timeStamp = new DateTime();
        ask = 0;
        bid = 0;
        askVolume = 0;
        bidVolume = 0;
    }

    /**
     * This constructor instance an object with all attributes.
     * 
     * @param timeStamp <br>
     *            this parameter represent the timestamp of the tick.
     * @param ask
     * @param bid
     * @param askVolume
     * @param bidVolume
     */
    public DukascopyBean( final DateTime timeStamp, final double ask, final double bid, final double askVolume,
                          final double bidVolume )
    {
        this.timeStamp = timeStamp;
        this.ask = ask;
        this.bid = bid;
        this.askVolume = askVolume;
        this.bidVolume = bidVolume;
    }

    /* (non-Javadoc)
     * @see de.hhberlin.beans.IDukascopy#getTimeStamp()
     */
    @Override
    public DateTime getTimeStamp()
    {
        return timeStamp;
    }

    /**
     * Set the timestamp of this object.
     * 
     * @param timeStamp
     */
    public void setTimeStamp( final DateTime timeStamp )
    {
        this.timeStamp = timeStamp;
    }

    /* (non-Javadoc)
     * @see de.hhberlin.beans.IDukascopy#getAsk()
     */
    @Override
    public double getAsk()
    {
        return ask;
    }

    /**
     * Set the ask value.
     * 
     * @param ask
     */
    public void setAsk( final double ask )
    {
        this.ask = ask;
    }

    /* (non-Javadoc)
     * @see de.hhberlin.beans.IDukascopy#getBid()
     */
    @Override
    public double getBid()
    {
        return bid;
    }

    /**
     * Set the bid value of this object.
     * 
     * @param bid
     */
    public void setBid( final double bid )
    {
        this.bid = bid;
    }

    /* (non-Javadoc)
     * @see de.hhberlin.beans.IDukascopy#getAskVolume()
     */
    @Override
    public double getAskVolume()
    {
        return askVolume;
    }

    /**
     * Set the askvolume.
     * 
     * @param askVolume
     */
    public void setAskVolume( final double askVolume )
    {
        this.askVolume = askVolume;
    }

    /* (non-Javadoc)
     * @see de.hhberlin.beans.IDukascopy#getBidVolume()
     */
    @Override
    public double getBidVolume()
    {
        return bidVolume;
    }

    /**
     * Set the bidvolume
     * 
     * @param bidVolume
     */
    public void setBidVolume( final double bidVolume )
    {
        this.bidVolume = bidVolume;
    }

    @Override
    public boolean equals( final Object obj )
    {
        if ( obj instanceof DukascopyBean )
        {
            DukascopyBean refObject = (DukascopyBean) obj;
            EqualsBuilder builder = new EqualsBuilder();
            builder.append( this.timeStamp, refObject.timeStamp );
            builder.append( this.ask, refObject.ask );
            builder.append( this.bid, refObject.bid );
            builder.append( this.askVolume, refObject.askVolume );
            builder.append( this.bidVolume, refObject.bidVolume );
            return builder.isEquals();
        }
        return false;
    }

    @Override
    public int hashCode()
    {
        HashCodeBuilder builder = new HashCodeBuilder();
        builder.append( timeStamp );
        builder.append( ask );
        builder.append( bid );
        builder.append( askVolume );
        builder.append( bidVolume );
        return builder.toHashCode();
    }

    @Override
    public String toString()
    {
        ToStringBuilder builder = new ToStringBuilder( this, ToStringStyle.SHORT_PREFIX_STYLE );
        builder.append( "timestamp", timeStamp );
        builder.append( "ask", ask );
        builder.append( "bid", bid );
        builder.append( "askVolume", askVolume );
        builder.append( "bidVolume", bidVolume );
        return builder.toString();
    }
}
