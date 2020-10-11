package org.example.db.impl;

public interface Database {

    void putWeatherIntoDB(String day, String weather);

    void printLastWeatherFromDB();

    void closeConnection();

}
