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

import org.apache.log4j.Logger;

/**
 * Util class for simplfy the logging mechanism
 * 
 * @author Karsten Schulz <a href="mailto:lennylinux.ks@googlmail.com">(lennylinux.ks@googlmail.com)</a>
 */
public final class LoggerUtils
{

    /**
     * Return a specify logging reference with the called class.
     * 
     * @return
     */
    public static Logger getLogger()
    {
        final Throwable t = new Throwable();
        final StackTraceElement methodCaller = t.getStackTrace()[2];
        final Logger logger = Logger.getLogger( methodCaller.getClassName() );
        return logger;
    }

    /**
     * The logging reference for errors
     */
    private static final Logger errorLogger = Logger.getLogger( "Error" );

    /**
     * Create a new logging entry for info.
     * 
     * @param message
     * @see {@link LoggerUtils#createInfoLog(Object) }
     */
    public static void createInfoLog( final Object message )
    {
        Logger defaultLogger = getLogger();
        defaultLogger.info( message );
    }

    /**
     * Create a new logging entry for info message with throwable.
     * 
     * @param message
     * @param throwable
     * @see {@link LoggerUtils#createInfoLog(Object, Throwable) }
     */
    public static void createInfoLog( final Object message, final Throwable throwable )
    {
        Logger defaultLogger = getLogger();
        defaultLogger.info( message, throwable );
    }

    /**
     * Create a new logging entry for warning message.
     * 
     * @param message
     * @see {@link LoggerUtils#createWarningLog(Object) }
     */
    public static void createWarningLog( final Object message )
    {
        Logger defaultLogger = getLogger();
        defaultLogger.warn( message );
    }

    /**
     * Create a new logging entry for warning message with throwable.
     * 
     * @param message
     * @param t
     * @see {@link LoggerUtils#createWarningLog(Object, Throwable) }
     */
    public static void createWarningLog( final Object message, final Throwable t )
    {
        Logger defaultLogger = getLogger();
        defaultLogger.warn( message, t );
    }

    /**
     * Create a new logging entry for debug message
     * 
     * @param message
     * @see {@link LoggerUtils#createDebugLog(Object) }
     */
    public static void createDebugLog( final Object message )
    {
        Logger defaultLogger = getLogger();
        defaultLogger.debug( message );
    }

    /**
     * Create a new logging entry for debug message and throwable
     * 
     * @param message
     * @param t
     * @see {@link LoggerUtils#createDebugLog(Object, Throwable) }
     */
    public static void createDebugLog( final Object message, final Throwable t )
    {
        Logger defaultLogger = getLogger();
        defaultLogger.debug( message, t );
    }

    /**
     * Create a new logging entry for error message and throwable
     * 
     * @param message
     * @param t
     * @see {@link LoggerUtils#createErrorLog(Object, Throwable) }
     */
    public static void createErrorLog( final Object message, final Throwable t )
    {
        errorLogger.error( message, t );
    }
}
