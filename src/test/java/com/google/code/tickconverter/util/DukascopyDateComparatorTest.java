package com.google.code.tickconverter.util;

import java.util.ArrayList;
import java.util.Collections;

import junit.framework.Assert;

import org.joda.time.DateTime;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;

import com.google.code.tickconverter.bean.DukascopyBean;

public class DukascopyDateComparatorTest
{

    private static DukascopyDateComparator comperator;

    private DukascopyBean first;

    private DukascopyBean second;

    @BeforeClass
    public static void setUpBefore()
        throws Exception
    {
        comperator = new DukascopyDateComparator();
    }

    @Before
    public void setUp()
        throws Exception
    {
        first = new DukascopyBean();
        second = new DukascopyBean();
    }

    @Test
    public void testCompareLess()
    {
        first.setTimeStamp( new DateTime( 2012, 8, 22, 0, 0, 0 ) );
        second.setTimeStamp( new DateTime( 2012, 8, 22, 0, 0, 1 ) );

        Assert.assertEquals( -1, comperator.compare( first, second ) );
    }

    @Test
    public void testCompareGreater()
    {
        first.setTimeStamp( new DateTime( 2012, 8, 22, 0, 0, 0 ) );
        second.setTimeStamp( new DateTime( 2012, 8, 22, 0, 0, 1 ) );

        Assert.assertEquals( 1, comperator.compare( second, first ) );
    }

    @Test
    public void testCompareEquals()
    {
        first.setTimeStamp( new DateTime( 2012, 8, 22, 0, 0, 0 ) );
        second.setTimeStamp( first.getTimeStamp() );
        Assert.assertEquals( 0, comperator.compare( second, first ) );
    }

    @Test
    public void testCompareList()
    {
        ArrayList<DukascopyBean> list = new ArrayList<>();
        first.setTimeStamp( new DateTime( 2012, 8, 22, 0, 0, 0 ) );
        second.setTimeStamp( new DateTime( 2012, 8, 22, 0, 0, 1 ) );

        list.add( second );
        list.add( first );
        Collections.sort( list, comperator );

        Assert.assertEquals( first, list.get( 0 ) );
    }
}
