package astra.mongodb;

import astra.events.coins.CoinsUpdateEvent;
import astra.lang.LangConfig;
import astra.mongodb.codec.quests.PlayerQuestCodec;
import astra.plugin;
import astra.playerquestsystem.PlayerQuest;
import cn.nukkit.Player;
import cn.nukkit.Server;
import com.mongodb.client.model.Filters;
import com.mongodb.client.model.Updates;
import org.bson.Document;
import org.bson.codecs.DecoderContext;
import org.bson.conversions.Bson;

import java.util.*;

public class PlayerDB {

    public static void Start() {
    }

    public static void addPlayerToDB(String xuid, UUID uuid, String name, LangConfig.Languages language) {

        Document playerDocument = new Document()
                .append("xuid", xuid)
                .append("uuid", uuid.toString())
                .append("name", name)
                .append("lang", language)
                .append("coins", 0L)
                .append("skill_points", 0)
                .append("skills", new HashMap<String, Integer>())
                .append("quests", List.of());

        MongoDB.getPlayerCollection().insertOne(playerDocument);

        plugin.getInstance().getLogger().info(String.format(
                "Added Player to PlayerDB\nXUID: %s\nUUID: %s\nNAME: %s\nLANG: %s",
                xuid,
                uuid.toString(),
                name,
                language
        ));
    }

    public static boolean isPlayerInDB(Player player) {
        Document result = MongoDB.getPlayerCollection().find(Filters.eq("xuid", player.getLoginChainData().getXUID())).first();
        return result != null;
    }

    // GETTERS

    public static UUID getPlayerUUIDFromDB(Player player) {
        Document result = MongoDB.getPlayerCollection().find(Filters.eq("xuid", player.getLoginChainData().getXUID())).first();
        if (result != null) {
            return UUID.fromString(result.get("uuid", String.class));
        } else {
            return null;
        }
    }
    public static String getPlayNameFromDB(Player player) {
        Document result = MongoDB.getPlayerCollection().find(Filters.eq("xuid", player.getLoginChainData().getXUID())).first();
        if (result != null) {
            return result.get("name", String.class);
        } else {
            return null;
        }
    }
    public static LangConfig.Languages getPlayerLanguageFromDB(Player player) {
        Document result = MongoDB.getPlayerCollection().find(Filters.eq("xuid", player.getLoginChainData().getXUID())).first();
        if (result != null) {
            return result.get("lang", LangConfig.Languages.class);
        } else {
            return null;
        }
    }
    public static long getPlayerCoinsFromDB(Player player) {
        Document result = MongoDB.getPlayerCollection().find(Filters.eq("xuid", player.getLoginChainData().getXUID())).first();
        if (result != null) {
            return (result.get("coins", Long.class));
        } else {
            return 0;
        }
    }
    public static int getPlayerSkillPoints(Player player) {
        Document result = MongoDB.getPlayerCollection().find(Filters.eq("xuid", player.getLoginChainData().getXUID())).first();
        if (result != null) {
            return (result.get("skill_points", int.class));
        } else {
            return  0;
        }
    }
    public static HashMap<String, Integer> getPlayPlayerSkills(Player player) {
        Document result = MongoDB.getPlayerCollection().find(Filters.eq("xuid", player.getLoginChainData().getXUID())).first();
        if (result != null) {
            return result.get("skill_points", HashMap.class);
        } else {
            return null;
        }
    }
    public static List<PlayerQuest> getPlayerQuests(Player player) {
        Document result = MongoDB.getPlayerCollection().find(Filters.eq("xuid", player.getLoginChainData().getXUID())).first();
        if (result != null) {
            List<PlayerQuest> data = new ArrayList<>();
            List<Document> questDocuments = result.getList("quests", Document.class);
            for (Document questDocument : questDocuments) {
                PlayerQuest quest = new PlayerQuestCodec(MongoDB.getPlayerCollection().getCodecRegistry()).decode(questDocument.toBsonDocument(null, MongoDB.getPlayerCollection().getCodecRegistry()).asBsonReader(), DecoderContext.builder().build());
                data.add(quest);
            }

            return data;
        } else {
            return null;
        }
    }

    // SETTERS

    public static void setPlayerUUIDInDB(Player player, UUID uuid) {
        Bson filter = Filters.eq("xuid", player.getLoginChainData().getXUID());
        Bson update = Updates.set("uuid", uuid.toString());
        MongoDB.getPlayerCollection().updateOne(filter, update);
    }
    public static void setPLayerNameInDB(Player player, String name) {
        Bson filter = Filters.eq("xuid", player.getLoginChainData().getXUID());
        Bson update = Updates.set("name", name);
        MongoDB.getPlayerCollection().updateOne(filter, update);
    }
    public static void setPlayerLanguageInDB(Player player, LangConfig.Languages language) {
        Bson filter = Filters.eq("xuid", player.getLoginChainData().getXUID());
        Bson update = Updates.set("lang", language);
        MongoDB.getPlayerCollection().updateOne(filter, update);
    }
    public static void setPlayerCoinsInDB(Player player, long coins) {
        Bson filter = Filters.eq("xuid", player.getLoginChainData().getXUID());
        Bson update = Updates.set("coins", coins);
        MongoDB.getPlayerCollection().updateOne(filter, update);

        Server.getInstance().getPluginManager().callEvent(new CoinsUpdateEvent(player, PlayerDB.getPlayerCoinsFromDB(player)));
    }
    public static void setPlayerSkillPoints(Player player, int points) {
        Bson filter = Filters.eq("xuid", player.getLoginChainData().getXUID());
        Bson update = Updates.set("skill_points", points);
        MongoDB.getPlayerCollection().updateOne(filter, update);
    }
    public static void setPlayerSkills(Player player, HashMap<String, Integer> skills){
        Bson filter = Filters.eq("xuid", player.getLoginChainData().getXUID());
        Bson update = Updates.set("skill_points", skills);
        MongoDB.getPlayerCollection().updateOne(filter, update);
    }
    public static void setPlayerQuests(Player player, List<PlayerQuest> playerQuests){
        Bson filter = Filters.eq("xuid", player.getLoginChainData().getXUID());
        Bson update = Updates.set("quests", playerQuests);
        MongoDB.getPlayerCollection().updateOne(filter, update);
    }

    public static void setPlayerQuest(Player player, PlayerQuest playerQuest){
        List<PlayerQuest> playerQuests = getPlayerQuests(player);
        for (PlayerQuest quest : playerQuests) {
            if (Objects.equals(quest.getID(), playerQuest.getID())){
                playerQuests.remove(quest);
                playerQuests.add(playerQuest);
                PlayerDB.setPlayerQuests(player, playerQuests);
                break;
            }
        }
    }

    // ADDERS & REMOVERS

    public static void addPlayerQuest(Player player, PlayerQuest playerQuest){
        List<PlayerQuest> playerQuests = getPlayerQuests(player);
        playerQuests.add(playerQuest);
        setPlayerQuests(player, playerQuests);
    }

    public static void removePlayerQuest(Player player, PlayerQuest playerQuest){
        List<PlayerQuest> playerQuests = getPlayerQuests(player);
        for (PlayerQuest quest : playerQuests) {
            if (Objects.equals(quest.getID(), playerQuest.getID())){
                playerQuests.remove(quest);
                break;
            }
        }
        setPlayerQuests(player, playerQuests);
    }
}
