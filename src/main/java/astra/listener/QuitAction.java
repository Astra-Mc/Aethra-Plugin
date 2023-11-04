package astra.listener;

import astra.mongodb.PlayerDB;
import astra.playerquestsystem.PlayerQuest;
import cn.nukkit.Player;
import cn.nukkit.event.EventHandler;
import cn.nukkit.event.Listener;
import cn.nukkit.event.player.PlayerQuitEvent;

import java.util.List;

public class QuitAction implements Listener {

    @EventHandler
    public void onQuit(PlayerQuitEvent event){
        event.setQuitMessage("");

        Player player = event.getPlayer();

        List<PlayerQuest> quests = PlayerDB.getPlayerQuests(player);
        for (PlayerQuest quest : quests) {
            quest.hide(player);
        }
    }
}
