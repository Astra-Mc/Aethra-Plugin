package astra.mongodb;

import astra.plugin;
import astra.config;
import com.mongodb.client.MongoClient;
import com.mongodb.client.MongoClients;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoDatabase;
import org.bson.Document;

public class MongoDB {

    private static MongoDB instance;
    private static MongoClient client;
    private static MongoDatabase mongoDatabase;
    private static MongoCollection<Document> playerCollection;

    public static MongoDB getInstance() {
        return instance;
    }

    public static MongoClient getClient() {
        return client;
    }

    public static MongoDatabase getMongoDatabase() {
        return mongoDatabase;
    }

    public static MongoCollection<Document> getPlayerCollection() {
        return playerCollection;
    }

    public void Start() {
        try {
            MongoDB.instance = this;

            MongoDB.client = MongoClients.create(config.MONGODB_LOGIN_INFO);

            MongoDB.mongoDatabase = client.getDatabase(config.MONGODB_DATABASE);
            MongoDB.playerCollection = mongoDatabase.getCollection(config.MONGODB_COLLECTION_PLAYER);

            new PlayerDB().Start();

        } catch (Exception e) {
            plugin.getInstance().getLogger().error("Caught MongoDB Exception (while Enabling): " + e + "\n" + e.getMessage());
        }
    }

    public void Stop() {
        try {
            getClient().close();
        } catch (Exception e) {
            plugin.getInstance().getLogger().error("Caught MongoDB Exception (while Disabling): " + e + "\n" + e.getMessage());
        }
    }
}
