package org.example.util;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.example.db.DBFactory;
import org.example.db.DBTypes;
import org.example.db.impl.Database;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;

import java.io.IOException;
import java.net.URL;
import java.util.regex.Matcher;
import java.util.regex.Pattern;


/**
 * Parser of html page which extract today's date and temperature range.
 */
public class WeatherParser implements WeatherInfo {

    /**
     * Get instance of Database.
     */
    private Database db = DBFactory.getDB(DBTypes.MYSQL_DB);

    /**
     * Creating log4j2 logger.
     */
    private static final Logger logger = LogManager.getLogger();

    /**
     * Build Jsoup Document from html page for further parsing.
     * If parsing failed - the last weather from database will be printed in console.
     *
     * @return Document
     * @throws RuntimeException If html page can not be downloaded. Caused by MalformedURLException.
     */
    private Document getPage() {
        String url = "http://www.intermeteo.com/europe/belarus/minsk/";
        logger.info("Url string created.");
        Document page = null;
        logger.info("Null Jsoup document created.");
        try {
            logger.info("Trying to parse html page.");
            page = Jsoup.parse(new URL(url), 8000);
        } catch (IOException e) {
            logger.error("Html page parsing failed. Printing the last weather from database.");
            db.printLastWeatherFromDB();
            db.closeConnection();
            System.exit(0);
        }
        logger.info("Html page successfully parsed.");
        return page;
    }

    /**
     * Build String with today's date and temperature range from Jsoup Document.
     * Put the resulted String into MySQL database.
     *
     * @return String
     */
    public String getWeatherInfo() {
        logger.info("Building Jsoup Document from html page.");
        Document page = getPage();
        logger.info("Searching for weather tags in Jsoup Document.");
        Element currentDate = page.select("th[id=dt]").first();
        String date = dateStringCutter(currentDate.text());
        Element currentTemperature = page.select("a[href=http://www.intermeteo.com/]").get(3);
        String temp = currentTemperature.text();
        logger.info("Today's weather successfully found.");
        db.putWeatherIntoDB(date, temp);
        logger.info("Weather was put into the databases. Printing in console. Close database.");
        db.closeConnection();
        return "Current date: " + date + "\n" +
                "Temperature: " + temp;
    }


    /**
     * Cut extra words from today's date String.
     *
     * @return String
     */
    private String dateStringCutter(String dateString) {
        Pattern pattern = Pattern.compile("\\d{2}\\.\\d{2}");
        Matcher matcher = pattern.matcher(dateString);
        if (matcher.find()) {
            return matcher.group();
        }
        throw new RuntimeException("Can not process date!");
    }
}