package org.example;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.example.util.WeatherInfo;
import org.example.util.WeatherParser;

/**
 * Main class of weather html parser program using Jsoup and MySQL.
 *
 * @author mmalashchonak
 * @version 1.1 MySQL database storage added.
 */
public class Main {
    /**
     * Get instance of Log4j2 logger.
     */
    private static final Logger logger = LogManager.getLogger();

    /**
     * String with current date and temperature;
     */
    private static String weather;

    /**
     * Program entry point.
     */
    public static void main(String... args) throws ClassNotFoundException {

        logger.info("Log4j2 started.");
        WeatherInfo weatherInfo = new WeatherParser();
        logger.info("WeatherParser created.");
        weather = weatherInfo.getWeatherInfo();
        System.out.println(weather);
        logger.info("Weather received: " + weather);
    }
}
