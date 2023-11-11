package astra.playerquestsystem;

import astra.events.quests.*;
import astra.mongodb.PlayerDB;
import cn.nukkit.Player;
import cn.nukkit.Server;
import cn.nukkit.utils.BossBarColor;
import cn.nukkit.utils.DummyBossBar;

public class PlayerQuest {

    private Long bossbar_ID = 0L;

    private String title;
    private BossBarColor color;
    private Float length;
    private String ID;

    public PlayerQuest(String title, BossBarColor color, Float length, String ID){
        this.title = title;
        this.color = color;
        this.length = length;
        this.ID = ID;
    }

    public void start(Player player){
        PlayerDB.addPlayerQuest(player, this);

        Server.getInstance().getPluginManager().callEvent(new QuestStartEvent(player, this));
    }

    public void destroy(Player player){
        PlayerDB.removePlayerQuest(player, this);

        Server.getInstance().getPluginManager().callEvent(new QuestDestroyEvent(player, this));
    }

    public void complete(Player player){
        PlayerDB.removePlayerQuest(player, this);

        Server.getInstance().getPluginManager().callEvent(new QuestCompleteEvent(player, this));
    }

    public void fail(Player player){
        PlayerDB.removePlayerQuest(player, this);

        Server.getInstance().getPluginManager().callEvent(new QuestFailEvent(player, this));
    }

    public void update(Player player){
        Server.getInstance().getPluginManager().callEvent(new QuestFailEvent(player, this));
    }

    public void display(Player player){
        DummyBossBar bossBar = new DummyBossBar
                .Builder(player)
                .text(this.title)
                .color(this.color)
                .length(this.length)
                .build();

        this.bossbar_ID = bossBar.getBossBarId();
        player.createBossBar(bossBar);

        PlayerDB.setOnePlayerQuest(player, this);

        Server.getInstance().getPluginManager().callEvent(new QuestDisplayEvent(player, this));
    }

    public void hide(Player player){
        if (this.bossbar_ID != 0L) {
            player.removeBossBar(this.bossbar_ID);
            this.bossbar_ID = 0L;
            PlayerDB.setOnePlayerQuest(player, this);
        }

        Server.getInstance().getPluginManager().callEvent(new QuestHideEvent(player, this));
    }

    public String getTitle() {
        return this.title;
    }
    public BossBarColor getColor() {
        return this.color;
    }
    public Long getBossbar_ID() {
        return bossbar_ID;
    }
    public Float getProgress(){
        return this.length / 100;
    }
    public Float getLength() {
        return this.length;
    }
    public String getID() {
        return ID;
    }

    public void setTitle(String title) {
        this.title = title;
    }
    public void setColor(BossBarColor color) {
        this.color = color;
    }
    public void setBossbar_ID(Long bossbar_ID) {
        this.bossbar_ID = bossbar_ID;
    }
    public void setProgress(Float progress) {
        this.length = progress * 100;
    }
    public void setLength(Float length) {
        this.length = length;
    }
    public void setID(String ID) {
        this.ID = ID;
    }
}