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
package com.google.code.tickconverter.util;

import java.util.Properties;

/**
 * This class is a final class with static method to get the config values of the command line.
 * 
 * @author Karsten Schulz <a href="mailto:lennylinux.ks@googlmail.com">(lennylinux.ks@googlmail.com)</a>
 */
public final class AppProperties
{

    protected static Properties properties = new Properties();

    /**
     * Returns the inputfile to parse the dukascopy object.
     * 
     * @return
     */
    public static String getInputFile()
    {
        return properties.getProperty( ConfigProperties.INPUTFILE.getConfigName() );
    }

    /**
     * Returns the outputfile to write the metatrader objects.
     * 
     * @return
     */
    public static String getOutputFile()
    {
        return properties.getProperty( ConfigProperties.OUTPUTFILE.getConfigName() );
    }

    /**
     * Returns the location of the logging configuration file. It this was not set the return value is a blank string.
     * 
     * @return
     */
    public static String getLoggingConfig()
    {
        return properties.getProperty( ConfigProperties.LOGGING.getConfigName(),
                                       ConfigProperties.LOGGING.getDefaultValue() );
    }

    /**
     * Returns the log level, if the configuration file was not set.
     * 
     * @return
     */
    public static String getLogLevel()
    {
        return properties.getProperty( ConfigProperties.LOGLEVEL.getConfigName(),
                                       ConfigProperties.LOGLEVEL.getDefaultValue() );
    }
}
