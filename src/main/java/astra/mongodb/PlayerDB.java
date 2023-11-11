package astra.mongodb;

import astra.events.coins.CoinsUpdateEvent;
import astra.lang.LangConfig;
import astra.mongodb.codec.quests.PlayerQuestCodec;
import astra.plugin;
import astra.playerquestsystem.PlayerQuest;
import astra.ranks.RankConfig;
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

    public static void addPlayer(String xuid, UUID uuid, String name, LangConfig.Languages language) {

        Document playerDocument = new Document()
                .append("xuid", xuid)
                .append("uuid", uuid.toString())
                .append("name", name)
                .append("lang", language)
                .append("coins", 0L)
                .append("ranks", List.of(RankConfig.Ranks.MEMBER))
                .append("selected_rank", RankConfig.Ranks.MEMBER)
                .append("skill_points", 0)
                .append("skills", new HashMap<String, Integer>())
                .append("quests", List.of());

        MongoDB.getPlayerCollection().insertOne(playerDocument);

        plugin.getInstance().getLogger().info(String.format(
                "Added Player to PlayerDB\nXUID: %s\nUUID: %s\nNAME: %s\nLANG: %s",
                xuid,
                uuid,
                name,
                language
        ));
    }

    public static boolean isPlayerInDB(Player player) {
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
    public static String getPlayName(Player player) {
        Document result = MongoDB.getPlayerCollection().find(Filters.eq("xuid", player.getLoginChainData().getXUID())).first();
        if (result != null) {
            return result.get("name", String.class);
        } else {
            return null;
        }
    }
    public static LangConfig.Languages getPlayerLanguage(Player player) {
        Document result = MongoDB.getPlayerCollection().find(Filters.eq("xuid", player.getLoginChainData().getXUID())).first();
        if (result != null) {
            return LangConfig.Languages.valueOf(result.get("lang", String.class));
        } else {
            return null;
        }
    }
    public static long getPlayerCoins(Player player) {
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
    public static List<RankConfig.Ranks> getPlayerRanks(Player player) {
        Document result = MongoDB.getPlayerCollection().find(Filters.eq("xuid", player.getLoginChainData().getXUID())).first();
        if (result != null) {
            List<String> data = result.get("ranks", List.class);
            List<RankConfig.Ranks> returnData = List.of();
            for (String rank: data) {
                returnData.add(RankConfig.Ranks.valueOf(rank));
            }
            return returnData;
        } else {
            return null;
        }
    }
    public static RankConfig.Ranks getSelectedPlayerRank(Player player) {
        Document result = MongoDB.getPlayerCollection().find(Filters.eq("xuid", player.getLoginChainData().getXUID())).first();
        if (result != null) {
            return RankConfig.Ranks.valueOf(result.get("selected_rank", String.class));
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
    public static void setPLayerName(Player player, String name) {
        Bson filter = Filters.eq("xuid", player.getLoginChainData().getXUID());
        Bson update = Updates.set("name", name);
        MongoDB.getPlayerCollection().updateOne(filter, update);

        SidePanel.sendPlayerName(player);
    }
    public static void setPlayerLanguage(Player player, LangConfig.Languages language) {
        Bson filter = Filters.eq("xuid", player.getLoginChainData().getXUID());
        Bson update = Updates.set("lang", language);
        MongoDB.getPlayerCollection().updateOne(filter, update);
    }
    public static void setPlayerCoins(Player player, long coins) {
        Bson filter = Filters.eq("xuid", player.getLoginChainData().getXUID());
        Bson update = Updates.set("coins", coins);
        MongoDB.getPlayerCollection().updateOne(filter, update);

        Server.getInstance().getPluginManager().callEvent(new CoinsUpdateEvent(player, PlayerDB.getPlayerCoins(player)));
        SidePanel.sendPlayerCoins(player);
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
    public static void setAllPlayerQuests(Player player, List<PlayerQuest> playerQuests){
        Bson filter = Filters.eq("xuid", player.getLoginChainData().getXUID());
        Bson update = Updates.set("quests", playerQuests);
        MongoDB.getPlayerCollection().updateOne(filter, update);
    }
    public static void setOnePlayerQuest(Player player, PlayerQuest playerQuest){
        List<PlayerQuest> playerQuests = getPlayerQuests(player);
        for (PlayerQuest quest : playerQuests) {
            if (Objects.equals(quest.getID(), playerQuest.getID())){
                playerQuests.remove(quest);
                playerQuests.add(playerQuest);
                PlayerDB.setAllPlayerQuests(player, playerQuests);
                break;
            }
        }
    }
    public static void setPlayerRanks(Player player, List<RankConfig.Ranks> ranks) {
        Bson filter = Filters.eq("xuid", player.getLoginChainData().getXUID());
        Bson update = Updates.set("ranks", ranks);
        MongoDB.getPlayerCollection().updateOne(filter, update);
    }
    public static void setSelectedPlayerRank(Player player, RankConfig.Ranks rank){
        Bson filter = Filters.eq("xuid", player.getLoginChainData().getXUID());
        Bson update = Updates.set("rank", rank);
        MongoDB.getPlayerCollection().updateOne(filter, update);

        SidePanel.sendPlayerSelectedRank(player);
    }


    // ADDERS & REMOVERS

    public static void addPlayerQuest(Player player, PlayerQuest playerQuest){
        List<PlayerQuest> playerQuests = getPlayerQuests(player);
        playerQuests.add(playerQuest);
        setAllPlayerQuests(player, playerQuests);
    }
    public static void removePlayerQuest(Player player, PlayerQuest playerQuest){
        List<PlayerQuest> playerQuests = getPlayerQuests(player);
        for (PlayerQuest quest : playerQuests) {
            if (Objects.equals(quest.getID(), playerQuest.getID())){
                playerQuests.remove(quest);
                break;
            }
        }
        setAllPlayerQuests(player, playerQuests);
    }
    public static void addPlayerRank(Player player, RankConfig.Ranks rank) {
        List<RankConfig.Ranks> ranks = getPlayerRanks(player);
        ranks.add(rank);
        setPlayerRanks(player, ranks);
    }
    public static void removePlayerRank(Player player, RankConfig.Ranks rank) {
        List<RankConfig.Ranks> ranks = getPlayerRanks(player);
        ranks.remove(rank);
        setPlayerRanks(player, ranks);
    }
}
