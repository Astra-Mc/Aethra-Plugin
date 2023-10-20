package astra.listener;

import astra.mongodb.PlayerDB;
import cn.nukkit.Player;
import cn.nukkit.entity.data.LongEntityData;
import cn.nukkit.event.EventHandler;
import cn.nukkit.event.Listener;
import cn.nukkit.event.player.PlayerJoinEvent;

import astra.config;
import cn.nukkit.utils.BossBarColor;
import cn.nukkit.utils.DummyBossBar;

public class JoinAction implements Listener {

    @EventHandler
    public void onJoin(PlayerJoinEvent event) {
        Player player = event.getPlayer();
        event.setJoinMessage("");

        player.teleport(config.PLUGIN_SPAWN_VECTOR);

        if (!PlayerDB.getInstance().isPlayerInDB(player.getLoginChainData().getXUID())) {
            PlayerDB.getInstance().addPlayerToDB(
                    player.getLoginChainData().getXUID(),
                    player.getLoginChainData().getClientUUID(),
                    player.getLoginChainData().getUsername(),
                    config.PlayerLanguage.ENGLISH
            );

            player.sendTitle("§bWelcome", "§dWe hope you have a great Time on here!", 20, 80, 20);
        } else {
            player.sendPopupJukebox("Welcome back!");
        }

        DummyBossBar bossBar = new DummyBossBar
                .Builder(player)
                .text("Coins: " + PlayerDB.getInstance().getPlayerCoinsFromDB(player.getLoginChainData().getXUID()))
                .color(BossBarColor.BLUE)
                .build();

        player.createBossBar(bossBar);
        player.setDataProperty(new LongEntityData(999999, bossBar.getBossBarId()));
    }
}
