package org.example.db.impl;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.sql.*;
import java.util.Properties;

import static java.sql.DriverManager.getConnection;

/**
 * MySQL database managing class.
 */
public class MySQLDB implements Database {
    /**
     * Get instance of Log4j2 logger.
     */
    private static final Logger logger = LogManager.getLogger();

    private String userName;
    private String password;
    private String connectionUrl;
    private Connection connection;

    /**
     * MySQL database static instance.
     */
    private static Database database = new MySQLDB();


    /**
     * Read MySQL settings from .properties and initialise fields.
     */
    {
        Properties prop = new Properties();
        InputStream input = null;

        try {
            input = new FileInputStream("config.properties");
            prop.load(input);
            userName = prop.getProperty("mysql_user");
            password = prop.getProperty("mysql_pass");
            connectionUrl = prop.getProperty("mysql_url");
        } catch (IOException ex) {
            ex.printStackTrace();
        } finally {
            if (input != null) {
                try {
                    input.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
    }


    /**
     * Create MySQL database table if it was not created yet.
     */ {
        logger.info("Create new MySQL weather table if is not found.");
        try {
            Class.forName("com.mysql.cj.jdbc.Driver");
            connection = getConnection(connectionUrl, userName, password);
            Statement statement = connection.createStatement();
            statement.executeUpdate("CREATE TABLE IF Not EXISTS Weather " +
                    "(id MEDIUMINT NOT NULL AUTO_INCREMENT, " +
                    "DAY CHAR(20) NOT NULL, " +
                    "WEATHER CHAR(30) NOT NULL, " +
                    "PRIMARY KEY (id))");
        } catch (Exception e) {
            e.printStackTrace();
            logger.error("New MySQL weather table creation failed.");
        }
    }

    private MySQLDB() {
    }

    /**
     * Return MySQL database instance.
     */
    public static Database getDatabase() {
        return database;
    }

    /**
     * Put weather into MySQL database.
     */
    public void putWeatherIntoDB(String day, String weather) {
        logger.info("Trying to put weather into MySQL database.");
        try {
            String sql = "insert into Weather (DAY, WEATHER) values (?, ?)";
            PreparedStatement preparedStatement = connection.prepareStatement(sql);
            preparedStatement.setString(1, day);
            preparedStatement.setString(2, weather);
            preparedStatement.executeUpdate();
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
    public void printLastWeatherFromDB() {
        logger.info("Trying to print weather from MySQL database in console.");
        try {
            String sql = "select DAY, WEATHER, id from Weather ORDER BY id DESC LIMIT 1";
            PreparedStatement preparedStatement = connection.prepareStatement(sql);
            ResultSet resultSet = preparedStatement.executeQuery();
            if (resultSet.next()) {
                System.out.println("Current date: " + resultSet.getString("DAY") +
                        "\nTemperature: " + resultSet.getString("WEATHER"));
            }
        } catch (
                SQLException throwables) {
            throwables.printStackTrace();
            logger.error("Printing weather from MySQL database in console was failed.");
        }
        logger.info("Weather from MySQL database successfully printed in console.");
    }

    public void closeConnection() {
        if (connection != null) {
            try {
                connection.close();
            } catch (SQLException throwables) {
                throwables.printStackTrace();
            }
        }
    }
}
