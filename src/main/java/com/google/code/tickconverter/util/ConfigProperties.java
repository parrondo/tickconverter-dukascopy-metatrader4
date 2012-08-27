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

import org.apache.commons.cli.Option;
import org.apache.commons.cli.OptionBuilder;

/**
 * This enum class specify the available properties of this application
 * 
 * @author Karsten Schulz <a href="mailto:lennylinux.ks@googlmail.com">(lennylinux.ks@googlmail.com)</a>
 */
public enum ConfigProperties
{
    /**
     * Inputfile to read the dukascopy objects into the application.
     */
    @SuppressWarnings( "static-access" )
    INPUTFILE(
        "app.input",
        OptionBuilder.withLongOpt( "inputfile" ).withDescription( "inputfile to read the dukascopy objects" ).withArgName( "FILE" ).isRequired( true ).hasArg().create( 'i' ),
        "" ),

    /**
     * Outputfile to write the parsed metatrader objects to this file.
     */
    @SuppressWarnings( "static-access" )
    OUTPUTFILE(
        "app.output",
        OptionBuilder.withLongOpt( "outputfile" ).withDescription( "outputfile to write the metatrader object" ).withArgName( "FILE" ).isRequired( true ).hasArg().create( 'o' ),
        "" ),

    /**
     * This config entry define the log4j config file. If this haven't set the logging output to the console.
     */
    @SuppressWarnings( "static-access" )
    LOGGING(
        "app.logging",
        OptionBuilder.withLongOpt( "logging" ).withDescription( "path to the config file for log4j" ).withArgName( "FILE" ).hasArg().create( 'c' ),
        "" ),

    /**
     * The loglevel specify the level to logging output.
     */
    @SuppressWarnings( "static-access" )
    LOGLEVEL(
        "app.loglevel",
        OptionBuilder.withLongOpt( "loglevel" ).withDescription( "loglevel for the console logging" ).withArgName( "LEVEL" ).hasArg().create( 'l' ),
        "info" ),

    /**
     * The input file date pattern.
     */
    @SuppressWarnings( "static-access" )
    DATEPATTERN(
        "app.datepattern",
        OptionBuilder.withLongOpt( "datepattern" ).withDescription( "date patter to specify the date format from input file like dd.MM.yyyy HH:mm:ss.SSS" ).withArgName( "PATTER" ).hasArg().create( 'p' ),
        "dd.MM.yyyy HH:mm:ss.SSS" );

    private final String longName;

    private final String configName;

    private final Option option;

    private final String defaultValue;

    private ConfigProperties( final String configName, final Option option, final String defaulValue )
    {
        this.longName = option.getLongOpt();
        this.configName = configName;
        this.option = option;
        this.defaultValue = defaulValue;
    }

    /**
     * Returns the configname like <code>app.path</code>.
     * 
     * @return
     */
    public String getConfigName()
    {
        return configName;
    }

    /**
     * Returns the total name of this propertie
     * 
     * @return
     */
    public String getLongName()
    {
        return longName;
    }

    /**
     * Returns the {@link Option} object to add this to the command line
     * 
     * @return
     */
    public Option getOption()
    {
        return option;
    }

    /**
     * Returns the default value of the enum
     * 
     * @return
     */
    public String getDefaultValue()
    {
        return defaultValue;
    }
}
