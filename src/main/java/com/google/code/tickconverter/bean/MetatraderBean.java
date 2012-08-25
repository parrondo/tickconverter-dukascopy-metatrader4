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
 * This class represent the output bean for the csv file. It includes a timestamp as class {@link DateTime} and many
 * number attributes with the primativ datatype <code>double</code>.
 * 
 * @author Karsten Schulz <a href="mailto:lennylinux.ks@googlmail.com">(lennylinux.ks@googlmail.com)</a>
 */
public class MetatraderBean
    implements IMetatraderRO
{
    private DateTime timeStamp;

    private double open;

    private double max;

    private double min;

    private double close;

    private double volume;

    /**
     * Standard constructor to instance a new {@link MetatraderBean} object with the current time and zero values.
     */
    public MetatraderBean()
    {
        timeStamp = new DateTime();
        open = 0;
        max = 0;
        min = 0;
        close = 0;
        volume = 0;
    }

    /**
     * Create a new {@link MetatraderBean} with an existing {@link IMetatraderRO} object and copy the information to the
     * owned attributes
     * 
     * @param traderObject <br>
     *            an object who implements the {@link IMetatraderRO} to copy the information
     */
    public MetatraderBean( final IMetatraderRO traderObject )
    {
        timeStamp = traderObject.getTimeStamp();
        open = traderObject.getOpen();
        max = traderObject.getMax();
        min = traderObject.getMin();
        close = traderObject.getClose();
        volume = traderObject.getVolume();
    }

    /*
     * (non-Javadoc)
     * @see de.hhberlin.beans.IMetatraiderBeanRO#getTimeStamp()
     */
    @Override
    public DateTime getTimeStamp()
    {
        return timeStamp;
    }

    /**
     * Set the timestamp of this object.
     * 
     * @param timeStamp the timeStamp to set
     */
    public void setTimeStamp( final DateTime timeStamp )
    {
        this.timeStamp = timeStamp;
    }

    /*
     * (non-Javadoc)
     * @see de.hhberlin.beans.IMetatraiderBeanRO#getOpen()
     */
    @Override
    public double getOpen()
    {
        return open;
    }

    /**
     * Set the open value.
     * 
     * @param open the open to set
     */
    public void setOpen( final double open )
    {
        this.open = open;
    }

    /*
     * (non-Javadoc)
     * @see de.hhberlin.beans.IMetatraiderBeanRO#getMax()
     */
    @Override
    public double getMax()
    {
        return max;
    }

    /**
     * Set the max value of this object.
     * 
     * @param max the max to set
     */
    public void setMax( final double max )
    {
        this.max = max;
    }

    /*
     * (non-Javadoc)
     * @see de.hhberlin.beans.IMetatraiderBeanRO#getMin()
     */
    @Override
    public double getMin()
    {
        return min;
    }

    /**
     * Set the min value of this object.
     * 
     * @param min the min to set
     */
    public void setMin( final double min )
    {
        this.min = min;
    }

    /*
     * (non-Javadoc)
     * @see de.hhberlin.beans.IMetatraiderBeanRO#getClose()
     */
    @Override
    public double getClose()
    {
        return close;
    }

    /**
     * Set the close value of this object.
     * 
     * @param close the close to set
     */
    public void setClose( final double close )
    {
        this.close = close;
    }

    /*
     * (non-Javadoc)
     * @see de.hhberlin.beans.IMetatraiderBeanRO#getVolume()
     */
    @Override
    public double getVolume()
    {
        return volume;
    }

    /**
     * Set the total volume of this object.
     * 
     * @param volume the volume to set
     */
    public void setVolume( final double volume )
    {
        this.volume = volume;
    }

    @Override
    public boolean equals( final Object obj )
    {
        if ( obj instanceof MetatraderBean )
        {
            MetatraderBean refObject = (MetatraderBean) obj;
            EqualsBuilder builder = new EqualsBuilder();
            builder.append( this.timeStamp, refObject.timeStamp );
            builder.append( this.open, refObject.open );
            builder.append( this.max, refObject.max );
            builder.append( this.min, refObject.min );
            builder.append( this.close, refObject.close );
            builder.append( this.volume, refObject.volume );
            return builder.isEquals();
        }
        return false;
    }

    @Override
    public int hashCode()
    {
        HashCodeBuilder builder = new HashCodeBuilder();
        builder.append( timeStamp );
        builder.append( open );
        builder.append( max );
        builder.append( min );
        builder.append( close );
        builder.append( volume );
        return builder.toHashCode();
    }

    @Override
    public String toString()
    {
        ToStringBuilder builder = new ToStringBuilder( this, ToStringStyle.SHORT_PREFIX_STYLE );
        builder.append( "timestamp", timeStamp );
        builder.append( "open", open );
        builder.append( "max", max );
        builder.append( "min", min );
        builder.append( "close", close );
        builder.append( "volume", volume );
        return builder.toString();
    }
}
