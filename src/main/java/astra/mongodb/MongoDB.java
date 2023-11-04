package astra.mongodb;

import astra.mongodb.codec.CodecRegisterer;
import astra.plugin;
import astra.config;
import com.mongodb.client.MongoClient;
import com.mongodb.client.MongoClients;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoDatabase;
import org.bson.Document;

public class MongoDB {

    private static MongoClient client;
    private static MongoDatabase mongoDatabase;
    private static MongoCollection<Document> playerCollection;

    public static MongoClient getClient() {
        return client;
    }
    public static MongoCollection<Document> getPlayerCollection() {
        return playerCollection;
    }
    public static MongoDatabase getMongoDatabase(){
        return mongoDatabase;
    }

    public static void setPlayerCollection(MongoCollection<Document> playerCollection) {
        MongoDB.playerCollection = playerCollection;
    }
    public static void setClient(MongoClient client) {
        MongoDB.client = client;
    }
    public static void setMongoDatabase(MongoDatabase mongoDatabase) {
        MongoDB.mongoDatabase = mongoDatabase;
    }

    public static void Start() {
        try {

            MongoDB.client = MongoClients.create(config.MONGODB_LOGIN_INFO);

            MongoDB.mongoDatabase = client.getDatabase(config.MONGODB_DATABASE);
            MongoDB.playerCollection = mongoDatabase.getCollection(config.MONGODB_COLLECTION_PLAYER);

            CodecRegisterer.RegisterCodecs();
            PlayerDB.Start();

        } catch (Exception e) {
            plugin.getInstance().getLogger().error("Caught MongoDB Exception (while Enabling): " + e + "\n" + e.getMessage());
        }
    }

    public static void Stop() {
        try {
            getClient().close();
        } catch (Exception e) {
            plugin.getInstance().getLogger().error("Caught MongoDB Exception (while Disabling): " + e + "\n" + e.getMessage());
        }
    }
}
