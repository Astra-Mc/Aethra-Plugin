package astra.listener;

import astra.mongodb.PlayerDB;
import astra.Plugin;
import astra.playerquest.PlayerQuest;
import astra.plotisland.PlotIsland;
import astra.sidepanel.SidePanel;
import cn.nukkit.Player;
import cn.nukkit.event.EventHandler;
import cn.nukkit.event.Listener;
import cn.nukkit.event.player.PlayerJoinEvent;

import astra.Config;
import cn.nukkit.scheduler.Task;

import java.util.List;

public class JoinAction implements Listener {

    @EventHandler
    public void onJoin(PlayerJoinEvent event) {
        Player player = event.getPlayer();
        event.setJoinMessage("");

        player.teleport(Config.PLUGIN_SPAWN_VECTOR);

        if (!PlayerDB.isInPlayerDB(player)) {
            PlayerDB.addPlayer(
                    player.getLoginChainData().getXUID(),
                    player.getLoginChainData().getClientUUID(),
                    player.getLoginChainData().getUsername(),
                    Config.Languages.ENGLISH_GB
            );

            player.sendTitle("§bWelcome!", "§dWe hope you have a great Time on here!", 20, 80, 20);
        }
        else {
            player.sendPopupJukebox("Welcome back...");
        }

        Plugin.getInstance().getServer().getScheduler().scheduleDelayedTask(new Task() {
            @Override
            public void onRun(int currentTick) {
                SidePanel.sendPlayerName(player);
            }
        }, 35, true);

        Plugin.getInstance().getServer().getScheduler().scheduleDelayedTask(new Task() {
            @Override
            public void onRun(int currentTick) {
                SidePanel.sendPlayerCoins(player);
            }
        }, 60, true);

        Plugin.getInstance().getServer().getScheduler().scheduleDelayedTask(new Task() {
            @Override
            public void onRun(int currentTick) {
                SidePanel.sendPlayerSelectedRank(player);
            }
        }, 75, true);

        Plugin.getInstance().getServer().getScheduler().scheduleDelayedTask(new Task() {
            @Override
            public void onRun(int currentTick) {
                List<PlayerQuest> quests = PlayerDB.getPlayerAethraQuests(player);
                for (PlayerQuest quest : quests) {
                    quest.display(player);
                }
            }
        }, 90, true);

        PlotIsland.load(player);
    }
}
