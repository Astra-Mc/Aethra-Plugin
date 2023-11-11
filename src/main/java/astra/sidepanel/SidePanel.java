package astra.sidepanel;

import astra.mongodb.PlayerDB;
import cn.nukkit.Player;

public class SidePanel {
    public static void sendPlayerCoins(Player player) {
        player.sendTitle(SidePanelConfig.UPDATE_COINS_SYMBOL+" §g$"+ PlayerDB.getPlayerCoins(player), "", 0, 1, 0);
    }

    public static void sendPlayerSelectedRank(Player player) {
        player.sendTitle(SidePanelConfig.UPDATE_RANK_SYMBOL+" "+PlayerDB.getSelectedPlayerRank(player), "", 0, 1, 0);
    }

    public static void sendPlayerName(Player player) {
        player.sendTitle(SidePanelConfig.UPDATE_NAME_SYMBOL+" "+PlayerDB.getPlayName(player), "", 0, 1, 0);
    }
}
