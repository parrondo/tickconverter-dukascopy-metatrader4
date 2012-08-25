package com.google.code.tickconverter.util;

import java.util.Comparator;

import com.google.code.tickconverter.bean.IDukascopyRO;

/**
 * This class represent a {@link Comparator} of the Interface {@link IDukascopyRO} and compare the
 * {@link IDukascopyRO#getTimeStamp()} values.
 * 
 * @author Karsten Schulz <a href="mailto:lennylinux.ks@googlmail.com">(lennylinux.ks@googlmail.com)</a>
 */
public final class DukascopyDateComperator
    implements Comparator<IDukascopyRO>
{

    /*
     * (non-Javadoc)
     * @see java.util.Comparator#compare(java.lang.Object, java.lang.Object)
     */
    @Override
    public int compare( final IDukascopyRO o1, final IDukascopyRO o2 )
    {
        return o1.getTimeStamp().compareTo( o2.getTimeStamp() );
    }
}
