package astra.mongodb;

import astra.Config;
import astra.events.coins.CoinsUpdateEvent;
import astra.mongodb.codec.quests.PlayerQuestCodec;
import astra.Plugin;
import astra.playerquest.PlayerQuest;
import astra.sidepanel.SidePanel;
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

    public static void addPlayer(String xuid, UUID uuid, String name, Config.Languages language) {

        Document playerDocument = new Document()
                .append("xuid", xuid)
                .append("uuid", uuid.toString())
                .append("name", name)
                .append("lang", language)
                .append("ranks", List.of(Config.Ranks.MEMBER))
                .append("selected_rank", Config.Ranks.MEMBER)
                .append("aethra_level", 0)
                .append("aethra_coins", (long) 0)
                .append("aethra_plot_island_id", UUID.randomUUID().toString())
                .append("aethra_skills", new HashMap<String, Integer>())
                .append("aethra_skill_points", 0)
                .append("aethra_quests", List.of());

        MongoDB.getPlayerCollection().insertOne(playerDocument);

        Plugin.getInstance().getLogger().info(String.format(
                "Added Player to PlayerDB\nXUID: %s\nUUID: %s\nNAME: %s\nLANG: %s",
                xuid,
                uuid,
                name,
                language
        ));
    }

    public static boolean isInPlayerDB(Player player) {
        Document result = MongoDB.getPlayerCollection().find(Filters.eq("xuid", player.getLoginChainData().getXUID())).first();
        return result != null;
    }

    // GETTERS

    public static UUID getPlayerUUID(Player player) {
        Document result = MongoDB.getPlayerCollection().find(Filters.eq("xuid", player.getLoginChainData().getXUID())).first();
        if (result != null) {
            return UUID.fromString(result.get("uuid", String.class));
        } else {
            return null;
        }
    }
    public static String getPlayerName(Player player) {
        Document result = MongoDB.getPlayerCollection().find(Filters.eq("xuid", player.getLoginChainData().getXUID())).first();
        if (result != null) {
            return result.get("name", String.class);
        } else {
            return null;
        }
    }
    public static Config.Languages getPlayerLanguage(Player player) {
        Document result = MongoDB.getPlayerCollection().find(Filters.eq("xuid", player.getLoginChainData().getXUID())).first();
        if (result != null) {
            return Config.Languages.valueOf(result.get("lang", String.class));
        } else {
            return null;
        }
    }
    public static List<Config.Ranks> getPlayerRanks(Player player) {
        Document result = MongoDB.getPlayerCollection().find(Filters.eq("xuid", player.getLoginChainData().getXUID())).first();
        if (result != null) {
            List<String> data = result.get("ranks", List.class);
            List<Config.Ranks> returnData = new ArrayList<>();
            for (String rank: data) {
                returnData.add(Config.Ranks.valueOf(rank));
            }
            return returnData;
        } else {
            return null;
        }
    }
    public static Config.Ranks getSelectedPlayerRank(Player player) {
        Document result = MongoDB.getPlayerCollection().find(Filters.eq("xuid", player.getLoginChainData().getXUID())).first();
        if (result != null) {
            return Config.Ranks.valueOf(result.get("selected_rank", String.class));
        } else {
            return null;
        }
    }

    public static int getPlayerAethraLevel(Player player) {
        Document result = MongoDB.getPlayerCollection().find(Filters.eq("xuid", player.getLoginChainData().getXUID())).first();
        if (result != null) {
            return (result.get("aethra_level", int.class));
        } else {
            return 0;
        }
    }
    public static long getPlayerAethraCoins(Player player) {
        Document result = MongoDB.getPlayerCollection().find(Filters.eq("xuid", player.getLoginChainData().getXUID())).first();
        if (result != null) {
            return (result.get("aethra_coins", Long.class));
        } else {
            return 0;
        }
    }
    public static UUID getPlayerAethraPlotIslandID(Player player) {
        Document result = MongoDB.getPlayerCollection().find(Filters.eq("xuid", player.getLoginChainData().getXUID())).first();
        if (result != null) {
            return UUID.fromString(result.get("aethra_plot_island_id", String.class));
        } else {
            return null;
        }
    }
    public static int getPlayerAethraSkillPoints(Player player) {
        Document result = MongoDB.getPlayerCollection().find(Filters.eq("xuid", player.getLoginChainData().getXUID())).first();
        if (result != null) {
            return (result.get("aethra_skill_points", int.class));
        } else {
            return  0;
        }
    }
    public static HashMap<String, Integer> getPlayerAethraSkills(Player player) {
        Document result = MongoDB.getPlayerCollection().find(Filters.eq("xuid", player.getLoginChainData().getXUID())).first();
        if (result != null) {
            return result.get("aethra_skills", HashMap.class);
        } else {
            return null;
        }
    }
    public static List<PlayerQuest> getPlayerAethraQuests(Player player) {
        Document result = MongoDB.getPlayerCollection().find(Filters.eq("xuid", player.getLoginChainData().getXUID())).first();
        if (result != null) {
            List<PlayerQuest> data = new ArrayList<>();
            List<Document> questDocuments = result.getList("aethra_quests", Document.class);
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

    public static void setPlayerUUID(Player player, UUID uuid) {
        Bson filter = Filters.eq("xuid", player.getLoginChainData().getXUID());
        Bson update = Updates.set("uuid", uuid.toString());
        MongoDB.getPlayerCollection().updateOne(filter, update);
    }
    public static void setPlayerName(Player player, String name) {
        Bson filter = Filters.eq("xuid", player.getLoginChainData().getXUID());
        Bson update = Updates.set("name", name);
        MongoDB.getPlayerCollection().updateOne(filter, update);

        SidePanel.sendPlayerName(player);
    }
    public static void setPlayerLanguage(Player player, Config.Languages language) {
        Bson filter = Filters.eq("xuid", player.getLoginChainData().getXUID());
        Bson update = Updates.set("lang", language);
        MongoDB.getPlayerCollection().updateOne(filter, update);
    }
    public static void setPlayerRanks(Player player, List<Config.Ranks> ranks) {
        Bson filter = Filters.eq("xuid", player.getLoginChainData().getXUID());
        Bson update = Updates.set("ranks", ranks);
        MongoDB.getPlayerCollection().updateOne(filter, update);
    }
    public static void setSelectedPlayerRank(Player player, Config.Ranks rank){
        if (getPlayerRanks(player).contains(rank)) {
            Bson filter = Filters.eq("xuid", player.getLoginChainData().getXUID());
            Bson update = Updates.set("selected_rank", rank);
            MongoDB.getPlayerCollection().updateOne(filter, update);

            SidePanel.sendPlayerSelectedRank(player);
        }
    }

    public static void setPlayerAethraLevel(Player player, int level) {
        Bson filter = Filters.eq("xuid", player.getLoginChainData().getXUID());
        Bson update = Updates.set("aethra_level", level);
        MongoDB.getPlayerCollection().updateOne(filter, update);
    }
    public static void setPlayerAethraCoins(Player player, long coins) {
        Bson filter = Filters.eq("xuid", player.getLoginChainData().getXUID());
        Bson update = Updates.set("aethra_coins", coins);
        MongoDB.getPlayerCollection().updateOne(filter, update);

        Server.getInstance().getPluginManager().callEvent(new CoinsUpdateEvent(player, PlayerDB.getPlayerAethraCoins(player)));
        SidePanel.sendPlayerCoins(player);
    }
    public static void setPlayerAethraPlotIslandID(Player player, UUID id) {
        Bson filter = Filters.eq("xuid", player.getLoginChainData().getXUID());
        Bson update = Updates.set("aethra_plot_island_id", id);
        MongoDB.getPlayerCollection().updateOne(filter, update);
    }
    public static void setPlayerAethraSkillPoints(Player player, int points) {
        Bson filter = Filters.eq("xuid", player.getLoginChainData().getXUID());
        Bson update = Updates.set("aethra_skill_points", points);
        MongoDB.getPlayerCollection().updateOne(filter, update);
    }
    public static void setPlayerAethraSkills(Player player, HashMap<String, Integer> skills){
        Bson filter = Filters.eq("xuid", player.getLoginChainData().getXUID());
        Bson update = Updates.set("aethra_skills", skills);
        MongoDB.getPlayerCollection().updateOne(filter, update);
    }
    public static void setPlayerAethraQuests(Player player, List<PlayerQuest> playerQuests){
        Bson filter = Filters.eq("xuid", player.getLoginChainData().getXUID());
        Bson update = Updates.set("aethra_quests", playerQuests);
        MongoDB.getPlayerCollection().updateOne(filter, update);
    }
    public static void setPlayerAethraQuest(Player player, PlayerQuest playerQuest){
        List<PlayerQuest> playerQuests = getPlayerAethraQuests(player);
        for (PlayerQuest quest : playerQuests) {
            if (Objects.equals(quest.getID(), playerQuest.getID())){
                playerQuests.remove(quest);
                playerQuests.add(playerQuest);
                PlayerDB.setPlayerAethraQuests(player, playerQuests);
                break;
            }
        }
    }

    // ADDERS & REMOVERS

    public static void addPlayerRank(Player player, Config.Ranks rank) {
        List<Config.Ranks> ranks = getPlayerRanks(player);
        ranks.add(rank);
        setPlayerRanks(player, ranks);
    }
    public static void removePlayerRank(Player player, Config.Ranks rank) {
        List<Config.Ranks> ranks = getPlayerRanks(player);
        ranks.remove(rank);
        setPlayerRanks(player, ranks);
    }

    public static void addPlayerAethraLevel(Player player, int level){
        setPlayerAethraLevel(player, getPlayerAethraLevel(player) + level);
    }

    public static void removePlayerAethraLevel(Player player, int level){
        setPlayerAethraLevel(player, getPlayerAethraLevel(player) - level);
    }
    public static void addPlayerAethraQuest(Player player, PlayerQuest playerQuest){
        List<PlayerQuest> playerQuests = getPlayerAethraQuests(player);
        playerQuests.add(playerQuest);
        setPlayerAethraQuests(player, playerQuests);
    }
    public static void removePlayerAethraQuest(Player player, PlayerQuest playerQuest){
        List<PlayerQuest> playerQuests = getPlayerAethraQuests(player);
        for (PlayerQuest quest : playerQuests) {
            if (Objects.equals(quest.getID(), playerQuest.getID())){
                playerQuests.remove(quest);
                break;
            }
        }
        setPlayerAethraQuests(player, playerQuests);
    }
    public static void removePlayerAethraQuest(Player player, String ID){
        List<PlayerQuest> playerQuests = getPlayerAethraQuests(player);
        for (PlayerQuest quest : playerQuests) {
            if (Objects.equals(quest.getID(), ID)){
                playerQuests.remove(quest);
                break;
            }
        }
        setPlayerAethraQuests(player, playerQuests);
    }
}
