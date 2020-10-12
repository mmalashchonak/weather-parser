package org.example.db.impl;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.dataformat.yaml.YAMLFactory;
import com.mongodb.BasicDBObject;
import com.mongodb.MongoClient;
import com.mongodb.MongoClientURI;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoDatabase;
import org.bson.Document;

import java.io.File;
import java.io.IOException;


public class MongoDB implements Database {

    private String Mongo_URI;
    private static Database mongoDB = new MongoDB();
    private MongoClient client;
    private MongoCollection collection;

    /**
     * Read MongoDB settings from .properties and initialise fields.
     */ {
        try {
            ObjectMapper om = new ObjectMapper(new YAMLFactory());
            om.findAndRegisterModules();
            Mongo_URI = om.readValue(new File("properties.yaml"), Properties.class).Mongo_URI;
            client = new MongoClient(new MongoClientURI(Mongo_URI));
            MongoDatabase database = client.getDatabase("test");
            collection = database.getCollection("test");
        } catch (IOException ex) {
            if (client != null) {client.close();}
            ex.printStackTrace();
        }
    }


    public MongoDB() {
    }

    public static Database getDatabase() {
        return mongoDB;
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

    static class Properties {
        @JsonProperty("Mongo_URI")
        String Mongo_URI;
        }
    }
