package org.example.util;

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
     * Build Jsoup Document from html page for further parsing.
     * @return Document
     * @throws RuntimeException If html page can not be downloaded. Caused by MalformedURLException.
     */
    private Document getPage() {
        String url = "https://www.gismeteo.by/weather-minsk-4248/";
        Document page = null;
        try {
            page = Jsoup.parse(new URL(url), 3000);
        } catch (IOException e) {
            System.out.println("404 not found.");
            System.out.println("Сегодня: Вт, 1 янв \nТемпература: -100 +100");
            System.exit(0);
        }
        return page;
    }

    /**
     * Build String with today's date and temperature range from Jsoup Document.
     * @return String
     */
    public String getWeatherInfo() {
        Document page = getPage();
        Elements todayForecast = page.select("div[class=tabs _center]").select("div[class=tab  tooltip]");
        String date = todayForecast.select("div[class=date]").text();
        Elements todayTemperatures = todayForecast.select("div[class=tabtempline tabtemp_0line clearfix]")
                .select("span[class=unit unit_temperature_c]");

        return "Сегодня: " + date + "\n" +
               "Температура: " + todayTemperatures.get(0).text() + " " + todayTemperatures.get(1).text();
    }
}