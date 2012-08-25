package com.google.code.tickconverter.convert;

import junit.framework.Assert;

import org.joda.time.DateTime;
import org.joda.time.Period;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;

import com.google.code.tickconverter.bean.DukascopyBean;

public class MetatraderConverterTest
{
    private static MetatraderConverter converter;

    @BeforeClass
    public static void setUpBeforeClass()
        throws Exception
    {
        converter = new MetatraderConverter( new DateTime( 2012, 8, 22, 0, 0 ), Period.minutes( 1 ) );
    }

    @Before
    public void setUp()
        throws Exception
    {

    }

    @Test
    public void testAddDukascopy()
    {
        for ( int i = 0; i < 60; i++ )
        {
            DukascopyBean bean = new DukascopyBean( new DateTime( 2012, 8, 22, 0, 0, i ), 10, 10, 10, 10 );
            try
            {
                converter.addDukascopy( bean );
            }
            catch ( InvalidTimeException e )
            {
                Assert.fail( "throw unexpected exception: " + e );
            }
        }
    }

    @Test
    public void testAddDukascopyException()
    {
        DukascopyBean bean = new DukascopyBean( new DateTime( 2012, 8, 23, 0, 0, 0 ), 10, 10, 10, 10 );
        try
        {
            converter.addDukascopy( bean );
            Assert.fail( "don't throw expected exception" );
        }
        catch ( InvalidTimeException e )
        {
            Assert.assertTrue( true );
        }
    }

    @Test
    public void testIncrement()
    {
        DukascopyBean bean = new DukascopyBean( new DateTime( 2012, 8, 22, 0, 1, 0 ), 10, 10, 10, 10 );
        converter.incrementInterval();
        try
        {
            converter.addDukascopy( bean );
        }
        catch ( InvalidTimeException e )
        {
            Assert.fail( "don't throw expected exception" );
        }
    }
}
