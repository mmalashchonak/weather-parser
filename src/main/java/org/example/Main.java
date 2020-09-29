package org.example;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.example.util.WeatherInfo;
import org.example.util.WeatherParser;

/**
 * Main class of weather html parser program using Jsoup.
 *
 * @author mmalashchonak
 * @version 1.0
 */
public class Main {
    /**
     * Log4j2 logger.
     */
    private static final Logger logger = LogManager.getLogger();

    /**
     * String with current date and temperature;
     */
    private static String weather;

    /**
     * Program entry point.
     */
    public static void main(String... args) {
        logger.info("Log4j2 started.");
        WeatherInfo weatherInfo = new WeatherParser();
        logger.info("WeatherParser created.");
        weather = weatherInfo.getWeatherInfo();
        System.out.println(weather);
        logger.info("Weather received: " + weather);
    }
}
