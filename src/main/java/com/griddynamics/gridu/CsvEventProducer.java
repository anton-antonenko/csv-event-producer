package com.griddynamics.gridu;

import com.opencsv.CSVWriter;
import org.apache.commons.lang3.tuple.Pair;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.io.IOException;
import java.io.OutputStreamWriter;
import java.io.Writer;
import java.net.Socket;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicLong;

public class CsvEventProducer {
    private final long eventsPerSec;
    private final String host;
    private final int port;
    private final Date dateFrom;
    private final Date dateTo;
    private final long dateFromLong;
    private final long dateToLong;
    private final long totalEvents;
    private final AtomicLong produced;

    private static final Logger logger = LogManager.getLogger(CsvEventProducer.class);
    public static final SimpleDateFormat DATE_FORMAT = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");

    public CsvEventProducer(long eventsPerSec, String host, int port, Date from, Date to, int totalEvents) throws ParseException {
        this.eventsPerSec = eventsPerSec;
        this.host = host;
        this.port = port;
        this.dateFrom = from;
        this.dateTo = to;
        this.dateFromLong = from.getTime();
        this.dateToLong = to.getTime();
        this.totalEvents = totalEvents;
        produced = new AtomicLong(0);
    }

    public void startProducingEvents() throws InterruptedException, IOException {
        logger.info("CsvEventProducer started with params: Events per sec={} | port={} | date from={} | date to={} | total events={}",
                eventsPerSec, port, DATE_FORMAT.format(dateFrom), DATE_FORMAT.format(dateTo), totalEvents > 0 ? totalEvents : "infinity");

        Socket socket = null;
        Writer writer;
        CSVWriter csvWriter = null;
        try {
            socket = new Socket(host, port);

            writer = new OutputStreamWriter(socket.getOutputStream());
            csvWriter = new CSVWriter(writer, '\t');

            logger.info("Producing events started.");

            createLoggingThread().start();

            if (totalEvents > 0) {
                while (produced.get() < totalEvents) {
                    produceEvent(csvWriter);
                    produced.incrementAndGet();
                }
                logger.info("Produced {} events. Finishing events producing.", produced.get());
            } else {
                while (true) {
                    produceEvent(csvWriter);
                    produced.incrementAndGet();
                }
            }

        } catch (Exception e) {
            logger.error("Error occurred sending event to socket.", e);
        } finally {
            if (csvWriter != null) {
                csvWriter.close();
            }
            if (socket != null) {
                socket.close();
            }
        }
    }

    private void produceEvent(CSVWriter csvWriter) throws InterruptedException, ParseException {
        TimeUnit.MICROSECONDS.sleep(1000000 / eventsPerSec);
        String[] event = createEvent();
        csvWriter.writeNext(event, false);

        try {
            csvWriter.flush();
        } catch (IOException ex) {
            logger.error("Error occurred flushing event to socket. Seems that the client stopped reading events.");
            logger.info("Event producing has stopped! Produced {} events.", produced.get());
            System.exit(1);
        }
    }

    private String[] createEvent() throws ParseException {
        Pair<String, Product> randomProduct = RandomEventValuesGenerator.getRandomProduct();
        String productCategory = randomProduct.getLeft();
        String productName = randomProduct.getRight().name;
        float productPrice = randomProduct.getRight().price;
        String purchaseDate = RandomEventValuesGenerator.getRandomDate(dateFromLong, dateToLong, DATE_FORMAT);
        String clientIp = RandomEventValuesGenerator.getRandomIP();

        return new String[]{productName, String.valueOf(productPrice), purchaseDate, productCategory, clientIp};
    }

    private Thread createLoggingThread() {
        Thread loggerThread = new Thread(new Runnable() {
            @Override
            public void run() {
                try {
                    while (true) {
                        TimeUnit.SECONDS.sleep(5);
                        CsvEventProducer.logger.info("Produced {} events", produced.get());
                    }
                } catch (InterruptedException e) {
                    CsvEventProducer.logger.error("Error occurred on logging.", e);
                }
            }
        }, "logger");
        loggerThread.setDaemon(true);
        return loggerThread;
    }
}
