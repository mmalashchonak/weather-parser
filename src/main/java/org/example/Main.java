package org.example;

import org.example.util.WeatherInfo;
import org.example.util.WeatherParser;

/**Main class of weather html parser program using Jsoup.
 * @author mmalashchonak
 * @version 1.0
 */
public class Main {

  /**
   * Program entry point.
   */
  public static void main(String... args) {
    WeatherInfo weatherInfo = new WeatherParser();
    System.out.println(weatherInfo.getWeatherInfo());
  }
}
