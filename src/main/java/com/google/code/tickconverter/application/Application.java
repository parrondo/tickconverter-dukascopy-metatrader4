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
package com.google.code.tickconverter.application;

import java.util.concurrent.LinkedBlockingQueue;

import com.google.code.tickconverter.bean.IDukascopyRO;
import com.google.code.tickconverter.bean.IMetatraderRO;
import com.google.code.tickconverter.convert.ConvertAdapter;
import com.google.code.tickconverter.io.DukascopyCsvReader;
import com.google.code.tickconverter.io.MetatraderCsvWriter;

/**
 * @author Karsten Schulz <a href="mailto:lennylinux.ks@googlmail.com">(lennylinux.ks@googlmail.com)</a>
 */
public final class Application
{

    public static void main( final String[] args )
    {
        LinkedBlockingQueue<IDukascopyRO> dukasQueue = new LinkedBlockingQueue<>();
        LinkedBlockingQueue<IMetatraderRO> metatraderQueue = new LinkedBlockingQueue<>();
        Thread reader = new Thread( new DukascopyCsvReader( dukasQueue, "CADJPY_tick.csv" ) );
        Thread convert = new Thread( new ConvertAdapter( dukasQueue, metatraderQueue ) );
        Thread writer = new Thread( new MetatraderCsvWriter( metatraderQueue, "minute.csv" ) );

        reader.start();
        convert.start();
        writer.start();
    }
}
