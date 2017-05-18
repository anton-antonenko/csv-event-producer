package com.griddynamics.gridu;

import org.apache.commons.cli.CommandLine;
import org.apache.commons.cli.CommandLineParser;
import org.apache.commons.cli.DefaultParser;
import org.apache.commons.cli.HelpFormatter;
import org.apache.commons.cli.MissingOptionException;
import org.apache.commons.cli.Option;
import org.apache.commons.cli.Options;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.util.Date;

import static com.griddynamics.gridu.CsvEventProducer.DATE_FORMAT;

public class App {
    private static final long DEFAULT_EVENTS_PER_SEC = 100;
    private static final int DEFAULT_PORT = 9999;
    private static final String DEFAULT_HOST = "127.0.0.1";

    private static final Logger logger = LogManager.getLogger(App.class);

    public static void main(String[] args) throws InterruptedException {
        CommandLineParser parser = new DefaultParser();
        HelpFormatter formatter = new HelpFormatter();
        Options options = getOptions();

        final long eventsPerSec;
        final String host;
        final int port;
        final Date from;
        final Date to;
        final int totalEvents;

        try {
            CommandLine line = parser.parse(options, args);
            if (line.hasOption("help")) {
                formatter.printHelp("csv-event-producer", options, true);
            } else {
                host = line.hasOption("h") ? line.getOptionValue("h") : DEFAULT_HOST;
                port = line.hasOption("p") ? Integer.parseInt(line.getOptionValue("p")) : DEFAULT_PORT;
                eventsPerSec = line.hasOption("e") ? Long.parseLong(line.getOptionValue("e")) : DEFAULT_EVENTS_PER_SEC;
                from = CsvEventProducer.DATE_FORMAT.parse(line.getOptionValue("f"));
                to = CsvEventProducer.DATE_FORMAT.parse(line.getOptionValue("t"));
                totalEvents = line.hasOption("T") ? Integer.parseInt(line.getOptionValue("T")) : -1;
                new CsvEventProducer(eventsPerSec, host, port, from, to, totalEvents).startProducingEvents();
            }
        } catch (MissingOptionException me) {
            logger.error(me.getMessage());
            formatter.printHelp("csv-event-producer", options, true);
        } catch (Exception e) {
            logger.error("Unexpected exception: " + e.getMessage(), e);
        }
    }

    private static Options getOptions() {
        Options options = new Options();

        Option eventsPerSec = Option.builder("e")
                .longOpt("events-per-sec")
                .hasArg()
                .argName("events-num")
                .desc("How many events per second must be produced. Default is " + DEFAULT_EVENTS_PER_SEC).build();
        options.addOption(eventsPerSec);

        Option port = Option.builder("p")
                .longOpt("port")
                .hasArg()
                .argName("port")
                .desc("Port to which events must be sent. Default is " + DEFAULT_PORT).build();
        options.addOption(port);

        Option host = Option.builder("h")
                .longOpt("host")
                .hasArg()
                .argName("host")
                .desc("Host to which events must be sent. Default is " + DEFAULT_HOST).build();
        options.addOption(host);

        Option totalEvents = Option.builder("T")
                .longOpt("total-events")
                .hasArg()
                .argName("total-events-num")
                .desc("Total events number which must be generated. If not specified, events will be produced endlessly.").build();
        options.addOption(totalEvents);

        Option dateFrom = Option.builder("f")
                .longOpt("date-from")
                .required()
                .hasArg()
                .argName("date-from")
                .desc("REQUIRED. The start date in the range in which the events will be created. Format of date: " + DATE_FORMAT.toPattern()).build();
        options.addOption(dateFrom);

        Option dateTo = Option.builder("t")
                .longOpt("date-to")
                .required()
                .hasArg()
                .argName("date-to")
                .desc("REQUIRED. The end date in the range in which the events will be created. Format of date: " + DATE_FORMAT.toPattern()).build();
        options.addOption(dateTo);

        Option help = Option.builder()
                .longOpt("help")
                .desc("Prints this message").build();
        options.addOption(help);

        return options;
    }
}
