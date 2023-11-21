package astra.listener;

import astra.plotisland.PlotIsland;
import cn.nukkit.Player;
import cn.nukkit.event.EventHandler;
import cn.nukkit.event.Listener;
import cn.nukkit.event.player.PlayerQuitEvent;

public class QuitAction implements Listener {

    @EventHandler
    public void onQuit(PlayerQuitEvent event){
        event.setQuitMessage("");

        Player player = event.getPlayer();

        PlotIsland.unload(player);
    }
}
