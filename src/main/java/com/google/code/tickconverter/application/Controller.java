package com.google.code.tickconverter.application;

import java.nio.file.Files;
import java.nio.file.LinkOption;
import java.nio.file.Paths;
import java.util.concurrent.LinkedBlockingQueue;

import org.apache.commons.lang.StringUtils;
import org.apache.log4j.Appender;
import org.apache.log4j.BasicConfigurator;
import org.apache.log4j.ConsoleAppender;
import org.apache.log4j.Level;
import org.apache.log4j.Logger;
import org.apache.log4j.PatternLayout;
import org.apache.log4j.PropertyConfigurator;

import com.google.code.tickconverter.bean.IDukascopyRO;
import com.google.code.tickconverter.bean.IMetatraderRO;
import com.google.code.tickconverter.convert.ConvertAdapter;
import com.google.code.tickconverter.io.DukascopyCsvReader;
import com.google.code.tickconverter.io.MetatraderCsvWriter;
import com.google.code.tickconverter.util.AppProperties;

/**
 * @author Karsten Schulz <a href="mailto:lennylinux.ks@googlmail.com">(lennylinux.ks@googlmail.com)</a>
 */
public final class Controller
{

    /**
     * Default constructor and configure the logging framework for the application
     */
    public Controller()
    {
        configureLogging();
    }

    private void configureLogging()
    {
        String loggingLocation = AppProperties.getLoggingConfig();
        if ( StringUtils.isNotBlank( loggingLocation )
            && Files.exists( Paths.get( loggingLocation ), LinkOption.NOFOLLOW_LINKS ) )
        {
            PropertyConfigurator.configureAndWatch( loggingLocation );
        }
        else
        {
            Appender appender =
                new ConsoleAppender( new PatternLayout( "%d{yyyy-MM-dd/HH:mm:ss.SSS/zzz} [%c] %-5p %m%n" ) );
            BasicConfigurator.configure( appender );
            Logger.getRootLogger().setLevel( Level.toLevel( AppProperties.getLogLevel() ) );
        }
    }

    /**
     * Method to start the convert process in three indipendent different threads.
     * 
     * @see DukascopyCsvReader#read()
     * @see ConvertAdapter#convertProcess()
     * @see MetatraderCsvWriter#write()
     */
    public void process()
    {
        LinkedBlockingQueue<IDukascopyRO> dukasQueue = new LinkedBlockingQueue<>();
        LinkedBlockingQueue<IMetatraderRO> metatraderQueue = new LinkedBlockingQueue<>();
        Thread reader = new Thread( new DukascopyCsvReader( dukasQueue, AppProperties.getInputFile() ) );
        Thread convert = new Thread( new ConvertAdapter( dukasQueue, metatraderQueue ) );
        Thread writer = new Thread( new MetatraderCsvWriter( metatraderQueue, AppProperties.getOutputFile() ) );

        reader.start();
        convert.start();
        writer.start();
    }
}
