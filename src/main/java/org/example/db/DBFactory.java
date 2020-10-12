package org.example.db;

import org.example.db.impl.Database;
import org.example.db.impl.MongoDB;
import org.example.db.impl.MySQLDB;

public class DBFactory {
    /**
     * Generate instance of required database managing class.
     *
     * @param type enum of available databases.
     * @return instance of database managing class.
     */
    public static Database getDB(DBTypes type) {
        switch (type) {
            case MONGO_DB: {
                return MongoDB.getDatabase();
            }

            case MYSQL_DB: {
                return MySQLDB.getDatabase();
            }

            default:
                throw new RuntimeException("Unknown database type");
        }
    }
}

