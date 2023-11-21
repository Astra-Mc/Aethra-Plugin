package astra.events.quests;

import astra.playerquest.PlayerQuest;
import cn.nukkit.Player;
import cn.nukkit.event.Event;
import cn.nukkit.event.HandlerList;

public class QuestDestroyEvent extends Event {
    private static final HandlerList handlerList = new HandlerList();
    private final Player player;
    private final PlayerQuest playerQuest;

    public QuestDestroyEvent(Player player, PlayerQuest playerQuest) {
        this.player = player;
        this.playerQuest = playerQuest;
    }

    public static HandlerList getHandlers() {
        return handlerList;
    }

    public Player getPlayer() {
        return player;
    }

    public PlayerQuest getQuest() {
        return playerQuest;
    }
}
