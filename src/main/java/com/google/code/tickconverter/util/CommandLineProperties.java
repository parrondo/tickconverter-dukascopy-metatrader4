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

import java.nio.file.Files;
import java.nio.file.LinkOption;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Properties;

import javax.xml.bind.PropertyException;

import org.apache.commons.cli.BasicParser;
import org.apache.commons.cli.CommandLine;
import org.apache.commons.cli.CommandLineParser;
import org.apache.commons.cli.HelpFormatter;
import org.apache.commons.cli.OptionGroup;
import org.apache.commons.cli.Options;
import org.apache.commons.cli.ParseException;
import org.apache.commons.lang.ObjectUtils;

/**
 * This final class parse the command line arguments to valid properties and set the information into the
 * {@link AppProperties#properties}
 * 
 * @author Karsten Schulz <a href="mailto:lennylinux.ks@googlmail.com">(lennylinux.ks@googlmail.com)</a>
 */
public final class CommandLineProperties
{

    private CommandLine cmdLine;

    private Options options;

    /**
     * Default constructor to parse the array list of string arguments
     * 
     * @param args <br>
     *            a Array of {@link String}
     * @throws ParseException will threw if the parser has occupied an parsing error
     */
    public CommandLineProperties( final String[] args )
        throws ParseException
    {
        createCommandLineOptions();
        parseCommandLine( args );
    }

    private void createCommandLineOptions()
    {
        options = new Options();
        OptionGroup group = new OptionGroup();

        for ( ConfigProperties configEnum : ConfigProperties.values() )
        {
            switch ( configEnum )
            {
                case LOGGING:
                case LOGLEVEL:
                    group.addOption( configEnum.getOption() );
                    break;
                default:
                    options.addOption( configEnum.getOption() );

            }
        }
        options.addOptionGroup( group );
    }

    private void parseCommandLine( final String[] args )
        throws ParseException
    {
        try
        {
            CommandLineParser parser = new BasicParser();
            cmdLine = parser.parse( options, args );
            if ( cmdLine.hasOption( 'h' ) )
            {
                printHelp();
            }
        }
        catch ( ParseException pe )
        {
            printHelp();
            System.out.println( pe.getMessage() );
            throw pe;
        }
    }

    private void printHelp()
    {
        HelpFormatter format = new HelpFormatter();
        format.printHelp( "java -jar tradeconverter-1.0.jar [options]", options );
    }

    /**
     * Load all options from the command line. The properties will saved in {@link AppProperties#properties}
     * 
     * @throws PropertyException will threw if the parameter of the commandline has invalid state
     */
    public void loadConfigProperties()
        throws PropertyException
    {
        try
        {
            checkConfigProperties();
        }
        catch ( PropertyException pe )
        {
            printHelp();
            throw pe;
        }

        Properties properties = AppProperties.properties;

        for ( ConfigProperties configProperties : ConfigProperties.values() )
        {
            properties.setProperty( configProperties.getConfigName(),
                                    cmdLine.getOptionValue( configProperties.getLongName(),
                                                            configProperties.getDefaultValue() ) );
        }
    }

    private void checkConfigProperties()
        throws PropertyException
    {
        if ( ObjectUtils.equals( null, cmdLine ) )
        {
            throw new PropertyException( "error of parsing was occupied" );
        }

        Path inputfile = Paths.get( cmdLine.getOptionValue( ConfigProperties.INPUTFILE.getLongName() ) );
        if ( !Files.exists( inputfile, LinkOption.NOFOLLOW_LINKS ) )
        {
            throw new PropertyException( "input file don't exist" );
        }

        if ( !inputfile.toString().endsWith( "csv" ) )
        {
            throw new PropertyException( "input file isn't a csv file" );
        }
    }
}
