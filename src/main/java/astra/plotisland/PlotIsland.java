package astra.plotisland;

import astra.Config;
import astra.mongodb.PlayerDB;
import cn.nukkit.Player;
import cn.nukkit.Server;

import java.io.File;
import java.util.UUID;

public class PlotIsland {
    public static void load(UUID id) {
        if (Server.getInstance().getLevelByName(id.toString()) == null) {
            File fileDat = new File(Config.PLOT_ISLAND_WORLD_FOLDER + id + Config.PLOT_ISLAND_LEVEL_FILE);

            if (fileDat.exists()){
                Server.getInstance().loadLevel(Config.PLOT_ISLAND_WORLD_FOLDER + id + Config.PLOT_ISLAND_LEVEL_FILE);
            }
            else {
                long SEED = 0;
                Server.getInstance().generateLevel(id.toString(), SEED, PlotIslandGenerator.class);
            }
        }
    }

    public static void load(Player player) {
        load(PlayerDB.getPlayerAethraPlotIslandID(player));
    }

    public static void unload(UUID id) {
        Server.getInstance().unloadLevel(Server.getInstance().getLevelByName(id.toString()));
    }

    public static void unload(Player player) {
        unload(PlayerDB.getPlayerAethraPlotIslandID(player));
    }
}
