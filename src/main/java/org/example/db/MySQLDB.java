package org.example.db;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.sql.*;
import java.sql.SQLException;
import java.sql.Statement;

import static java.sql.DriverManager.getConnection;

/**
 * MySQL database managing class.
 */
public class MySQLDB {
    /**
     * Get instance of Log4j2 logger.
     */
    private static final Logger logger = LogManager.getLogger();

    private String userName = "root";
    private String password = "1234";
    private String connectionUrl = "jdbc:mysql://localhost:3306/weather_db";

    /**
     * MySQL database static instance.
     */
    private static MySQLDB database = new MySQLDB();

    /**
     * Create MySQL database table if it was not created yet.
     */ {
         logger.info("Create new MySQL weather table if is not found.");
        try {
            Class.forName("com.mysql.cj.jdbc.Driver");
            Connection connection = getConnection(connectionUrl, userName, password);
            Statement statement = connection.createStatement();
            statement.executeUpdate("CREATE TABLE IF Not EXISTS Weather " +
                    "(id MEDIUMINT NOT NULL AUTO_INCREMENT, " +
                    "DAY CHAR(20) NOT NULL, " +
                    "WEATHER CHAR(30) NOT NULL, " +
                    "PRIMARY KEY (id))");
        } catch (
                Exception e) {
            e.printStackTrace();
            logger.error("New MySQL weather table creation failed.");
        }
    }

    private MySQLDB() {
    }

    /**
     * Return MySQL database instance.
     */
    public static MySQLDB getDatabase() {
        return database;
    }

    /**
     * Put weather into MySQL database.
     */
    public void putWeatherIntoDatabase(String day, String weather) {
        logger.info("Trying to put weather into MySQL database.");
        try (Connection connection = getConnection(connectionUrl, userName, password);
             Statement statement = connection.createStatement()) {
            statement.executeUpdate("insert into Weather (DAY, WEATHER) values ('" + day + "', '" + weather +
                    "')");
        } catch (
                SQLException throwables) {
            throwables.printStackTrace();
            logger.error("Putting weather into MySQL database failed.");
        }
        logger.info("Weather was successfully put into MySQL database.");
    }

    /**
     * Print weather from MySQL database in console.
     */
    public void printLastWeatherFromDatabase() {
        logger.info("Trying to print weather from MySQL database in console.");
        try (Connection connection = getConnection(connectionUrl, userName, password);
             Statement statement = connection.createStatement()) {
            ResultSet resultSet = statement.executeQuery("select * from Weather ORDER BY id DESC LIMIT 1");
            if (resultSet.next()) {
                System.out.println("Сегодня: " + resultSet.getString("DAY") + " \nТемпература: " + resultSet.getString("WEATHER"));
            }
        } catch (
                SQLException throwables) {
            throwables.printStackTrace();
            logger.error("Printing weather from MySQL database in console was failed.");
        }
        logger.info("Weather from MySQL database successfully printed in console.");
    }
}
