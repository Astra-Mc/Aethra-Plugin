package astra.events.coins;

import cn.nukkit.Player;
import cn.nukkit.event.Event;
import cn.nukkit.event.HandlerList;

public class CoinsUpdateEvent extends Event {
    private static final HandlerList handlerList = new HandlerList();
    private final Player player;
    private final long coins;

    public CoinsUpdateEvent(Player player, long coins) {
        this.player = player;
        this.coins = coins;
    }

    public static HandlerList getHandlers() {
        return handlerList;
    }

    public Player getPlayer() {
        return player;
    }

    public long getCoins() {
        return coins;
    }
}
