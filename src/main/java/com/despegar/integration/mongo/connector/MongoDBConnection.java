package com.despegar.integration.mongo.connector;

import java.net.UnknownHostException;
import java.util.ArrayList;
import java.util.List;

import com.mongodb.DB;
import com.mongodb.MongoClient;
import com.mongodb.MongoClientOptions;
import com.mongodb.ServerAddress;

public class MongoDBConnection {

    private String dbName;
    private String replicaSet;
    private MongoClientOptions mongoOptions;

    public MongoDBConnection(String dbName, String replicaSet) throws UnknownHostException {
        this.dbName = dbName;
        this.replicaSet = replicaSet;
        this.instanceDB();
    }

    public MongoDBConnection(String dbName, String replicaSet, MongoClientOptions mongoOptions) throws UnknownHostException {
        this.dbName = dbName;
        this.replicaSet = replicaSet;
        this.mongoOptions = mongoOptions;
        this.instanceDB();
    }

    private DB db;

    @SuppressWarnings("deprecation")
    private void instanceDB() throws UnknownHostException {
        if (this.mongoOptions == null) {
            this.mongoOptions = MongoClientOptions.builder().build();
        }

        String[] addresses = this.replicaSet.split(",");
        List<ServerAddress> serverAddresses = new ArrayList<ServerAddress>();
        ServerAddress serverAddress;
        for (String address : addresses) {
            String[] split = address.split(":");
            serverAddress = new ServerAddress(split[0], new Integer(split[1]));
            serverAddresses.add(serverAddress);
        }

        MongoClient mongo = new MongoClient(serverAddresses, this.mongoOptions);
        this.db = mongo.getDB(this.dbName);

    }

    DB getDB() {
        return this.db;
    }
}
