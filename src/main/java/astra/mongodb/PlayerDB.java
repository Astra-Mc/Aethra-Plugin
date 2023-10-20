package astra.mongodb;

import astra.config;
import astra.plugin;
import com.mongodb.client.model.Filters;
import com.mongodb.client.model.Updates;
import org.bson.Document;
import org.bson.conversions.Bson;

import java.util.UUID;

public class PlayerDB {


    private static PlayerDB instance;

    public static PlayerDB getInstance() {
        return instance;
    }

    void Start() {
        PlayerDB.instance = this;
    }

    public void addPlayerToDB(String xuid, UUID uuid, String name, config.PlayerLanguage language) {

        Document playerDocument = new Document();

        long zero = 0;

        playerDocument.append("xuid", xuid);
        playerDocument.append("uuid", uuid.toString());
        playerDocument.append("name", name);
        playerDocument.append("lang", language);
        playerDocument.append("coins", zero);

        MongoDB.getPlayerCollection().insertOne(playerDocument);

        plugin.getInstance().getLogger().info("Added a new Player to the DB!");

    }

    public boolean isPlayerInDB(String xuid) {
        Document result = MongoDB.getPlayerCollection().find(Filters.eq("xuid", xuid)).first();
        return result != null;
    }

    public UUID getPlayerUUIDFromDB(String xuid) {
        Document result = MongoDB.getPlayerCollection().find(Filters.eq("xuid", xuid)).first();
        if (result != null) {
            return UUID.fromString(result.get("uuid", String.class));
        } else {
            return null;
        }
    }

    public String getPlayNameFromDB(String xuid) {
        Document result = MongoDB.getPlayerCollection().find(Filters.eq("xuid", xuid)).first();
        if (result != null) {
            return result.get("name", String.class);
        } else {
            return null;
        }
    }

    public config.PlayerLanguage getPlayerLanguageFromDB(String xuid) {
        Document result = MongoDB.getPlayerCollection().find(Filters.eq("xuid", xuid)).first();
        if (result != null) {
            return result.get("lang", config.PlayerLanguage.class);
        } else {
            return null;
        }
    }

    public long getPlayerCoinsFromDB(String xuid) {
        Document result = MongoDB.getPlayerCollection().find(Filters.eq("xuid", xuid)).first();
        if (result != null) {
            return (result.get("coins", Long.class));
        } else {
            return 0;
        }
    }

    public void updatePlayerUUIDInDB(String xuid, UUID uuid) {
        Bson filter = Filters.eq("xuid", xuid);
        Bson update = Updates.set("uuid", uuid.toString());
        MongoDB.getPlayerCollection().updateOne(filter, update);
    }

    public void updatePLayerNameInDB(String xuid, String name) {
        Bson filter = Filters.eq("xuid", xuid);
        Bson update = Updates.set("name", name);
        MongoDB.getPlayerCollection().updateOne(filter, update);
    }

    public void updatePlayerLanguageInDB(String xuid, config.PlayerLanguage language) {
        Bson filter = Filters.eq("xuid", xuid);
        Bson update = Updates.set("lang", language);
        MongoDB.getPlayerCollection().updateOne(filter, update);
    }

    public void updatePlayerCoinsInDB(String xuid, long coins) {
        Bson filter = Filters.eq("xuid", xuid);
        Bson update = Updates.set("coins", coins);
        MongoDB.getPlayerCollection().updateOne(filter, update);
    }
}
