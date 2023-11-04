package astra.listener;

import astra.events.coins.CoinsUpdateEvent;
import cn.nukkit.Player;
import cn.nukkit.event.EventHandler;
import cn.nukkit.event.Listener;

public class CoinsUpdateAction implements Listener {
    @EventHandler
    public void onCoinsUpdate(CoinsUpdateEvent event) {
        Player player = event.getPlayer();
    }
}
