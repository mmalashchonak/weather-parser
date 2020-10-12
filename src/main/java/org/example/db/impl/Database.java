package org.example.db.impl;

public interface Database {

    /**
     * Put date and temperature as single weather entry into database.
     *
     * @param day
     * @param weather
     */
    void putWeatherIntoDB(String day, String weather);

    /**
     * Print last weather from database in console.
     */
    void printLastWeatherFromDB();

    /**
     * Close connection to database.
     */
    void closeConnection();

}
