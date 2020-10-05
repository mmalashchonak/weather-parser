package org.example.util;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.select.Elements;

import java.io.IOException;
import java.net.URL;


/**
 * Parser of html page which extract today's date and temperature range.
 */
public class WeatherParser implements WeatherInfo {

    /**
     * Creating log4j2 logger.
     */
    private static final Logger logger = LogManager.getLogger();

    /**
     * Build Jsoup Document from html page for further parsing.
     * @return Document
     * @throws RuntimeException If html page can not be downloaded. Caused by MalformedURLException.
     */
    private Document getPage() {
        String url = "https://www.gismeteo.by/weather-minsk-4248/";
        logger.info("Url string created.");
        Document page = null;
        logger.info("Null Jsoup document created.");
        try {
            logger.info("Trying to parse html page.");
            page = Jsoup.parse(new URL(url), 3000);
        } catch (IOException e) {
            logger.error("Html page parsing failed.");
            System.out.println("404 not found.");
            System.out.println("Сегодня: Вт, 1 янв \nТемпература: -100 +100");
            System.exit(0);
        }
        logger.info("Html page successfully parsed..");
        return page;
    }

    /**
     * Build String with today's date and temperature range from Jsoup Document.
     * @return String
     */
    public String getWeatherInfo() {
        logger.info("Building Jsoup Document from html page.");
        Document page = getPage();
        logger.info("Searching for weather tags in Jsoup Document.");
        Elements todayForecast = page.select("div[class=tabs _center]").select("div[class=tab  tooltip]");
        String date = todayForecast.select("div[class=date]").text();
        Elements todayTemperatures = todayForecast.select("div[class=tabtempline tabtemp_0line clearfix]")
                .select("span[class=unit unit_temperature_c]");
        logger.info("Today'w weather successfully found. Printing in console.");
        return "Сегодня: " + date + "\n" +
               "Температура: " + todayTemperatures.get(0).text() + " " + todayTemperatures.get(1).text();
    }
}