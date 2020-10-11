package org.example.db.impl;

import com.mongodb.BasicDBObject;
import com.mongodb.MongoClient;
import com.mongodb.MongoClientURI;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoDatabase;
import org.bson.Document;


public class MongoDB implements Database {

    private static final String Mongo_URI = "mongodb+srv://root:root@cluster0.zhvgt.mongodb.net/test";
    private static Database mongoDB = new MongoDB();
    private MongoClient client;
    private MongoCollection collection;

    public MongoDB() {
    }

    public static Database getDatabase() {
        return mongoDB;
    }

    {
        client = new MongoClient(new MongoClientURI(Mongo_URI));
        MongoDatabase database = client.getDatabase("test");
        collection = database.getCollection("test");
    }

    @Override
    public void putWeatherIntoDB(String day, String weather) {
        Document document = new Document("day", day);
        document.put("weather", weather);
        collection.insertOne(document);
    }

    @Override
    public void printLastWeatherFromDB() {
        Document myDoc = (Document) collection.find().sort(new BasicDBObject("_id", -1)).first();
        System.out.println("Current date: " + myDoc.get("day") + "\nTemperature: " + myDoc.get("weather"));
    }

    @Override
    public void closeConnection() {
        if (client != null) {
            client.close();
        }
    }
}
