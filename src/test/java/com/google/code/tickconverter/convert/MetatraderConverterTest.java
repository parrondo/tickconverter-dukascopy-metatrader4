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
