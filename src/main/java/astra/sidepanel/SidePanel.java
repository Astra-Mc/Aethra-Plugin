package astra.sidepanel;

import astra.Config;
import astra.mongodb.PlayerDB;
import cn.nukkit.Player;

public class SidePanel {
    public static void sendPlayerCoins(Player player) {
        player.sendTitle(Config.SIDEPANEL_SEND_COINS_SYMBOL+" §g$"+ PlayerDB.getPlayerAethraCoins(player), "", 0, 1, 0);
    }

    public static void sendPlayerSelectedRank(Player player) {
        player.sendTitle(Config.SIDEPANEL_SEND_RANK_SYMBOL+" "+PlayerDB.getSelectedPlayerRank(player), "", 0, 1, 0);
    }

    public static void sendPlayerName(Player player) {
        player.sendTitle(Config.SIDEPANEL_SEND_NAME_SYMBOL+" "+PlayerDB.getPlayerName(player), "", 0, 1, 0);
    }
}
