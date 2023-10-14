package astra.listener;

import astra.events.CoinsUpdateEvent;
import astra.mongodb.PlayerDB;
import cn.nukkit.Player;
import cn.nukkit.event.EventHandler;
import cn.nukkit.event.Listener;

public class CoinsUpdateAction implements Listener {
    @EventHandler
    public void onCoinsUpdate(CoinsUpdateEvent event) {
        Player player = event.getPlayer();

        player.getDummyBossBar(player.getDataPropertyLong(999999)).setText("Coins: " + PlayerDB.getInstance().getPlayerCoinsFromDB(player.getLoginChainData().getXUID()));
    }
}
