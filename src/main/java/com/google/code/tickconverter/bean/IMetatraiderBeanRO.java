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

import org.joda.time.DateTime;

/**
 * This interface is an immutable (read-only) interface of a metatrader object.
 * 
 * @author Karsten Schulz <a href="mailto:lennylinux.ks@googlmail.com">(lennylinux.ks@googlmail.com)</a>
 */
public interface IMetatraiderBeanRO
{

    /**
     * Get the timestamp of this object.
     * 
     * @return the timeStamp
     */
    DateTime getTimeStamp();

    /**
     * Return the open value.
     * 
     * @return the open
     */
    double getOpen();

    /**
     * Return the max value.
     * 
     * @return the max
     */
    double getMax();

    /**
     * Return the min value of this object.
     * 
     * @return the min
     */
    double getMin();

    /**
     * Return the close value of this object.
     * 
     * @return the close
     */
    double getClose();

    /**
     * Return the total volume of this object.
     * 
     * @return the volume
     */
    double getVolume();

}