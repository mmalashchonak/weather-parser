package org.example.util;

/**
 * Build String with today's date and temperature range.
 * @since 1.0
 */
public interface WeatherInfo {

  /**
   * Return String with today's date and temperature range.
   * Any weather parsing methods should be realised by implementing this method.
   * @return String today's date and temperature range
   */
  String getWeatherInfo();
}
