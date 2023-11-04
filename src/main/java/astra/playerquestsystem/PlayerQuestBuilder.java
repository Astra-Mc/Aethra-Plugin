package astra.playerquestsystem;

import cn.nukkit.utils.BossBarColor;

public class PlayerQuestBuilder {

    private final String title;
    private final String ID;
    private BossBarColor color = BossBarColor.BLUE;
    private Float progress = 1f;

    public PlayerQuestBuilder(String title, String ID){
        this.title = title;
        this.ID = ID;
    }

    public PlayerQuestBuilder setColor(BossBarColor color){
        this.color = color;
        return this;
    }

    public PlayerQuestBuilder setProgress(Float progress){
        this.progress = progress;
        return this;
    }

    public PlayerQuest build(){
        return new PlayerQuest(
                title,
                this.color,
                this.progress * 100,
                this.ID
        );
    }
}
