package astra.skillsystem;

import astra.config;
import astra.mongodb.PlayerDB;
import astra.playerquestsystem.PlayerQuest;
import astra.playerquestsystem.PlayerQuestBuilder;
import astra.skillsystem.skills.SkillFarming;
import astra.skillsystem.skills.SkillMining;
import cn.nukkit.Player;
import cn.nukkit.Server;
import cn.nukkit.form.element.ElementButton;
import cn.nukkit.form.element.ElementButtonImageData;
import cn.nukkit.form.window.FormWindowSimple;
import cn.nukkit.scheduler.Task;
import cn.nukkit.utils.BossBarColor;

import java.util.List;
import java.util.Random;

public class SkillSystem {
    public void Start(){}

    public void Stop(){}

    public static void Show(Player player){
        FormWindowSimple form = new FormWindowSimple("Skills", "");
        form.addButton(new ElementButton("Mining", new ElementButtonImageData("url", "https://www.powernukkitx.com/assets/image/PNX_LOGO_sm")));
        form.addButton(new ElementButton("Farming"));
        form.addButton(new ElementButton("ADD QUEST"));
        form.addButton(new ElementButton("REMOVE ALL QUESTS"));
        player.showFormWindow(form);

        final int[] timeOutStatus = {0};

        Server.getInstance().getScheduler().scheduleRepeatingTask(new Task() {
            @Override
            public void onRun(int currentTick) {
                if (form.getResponse() != null){
                    this.cancel();

                    switch (form.getResponse().getClickedButton().getText()){

                        case "Mining" -> {
                            SkillMining.Show(player);
                        }

                        case "Farming" -> {
                            SkillFarming.Show(player);
                        }

                        case "ADD QUEST" -> {
                            Random rand = new Random ();
                            PlayerQuest quest = new PlayerQuestBuilder("Title", ""+rand.nextLong())
                                    .setColor(BossBarColor.RED)
                                    .setProgress(1f)
                                    .build();

                            quest.start(player);
                            quest.display(player);
                        }

                        case "REMOVE ALL QUESTS" -> {
                            List<PlayerQuest> quests = PlayerDB.getPlayerQuests(player);
                            for (PlayerQuest quest1 : quests) {
                                quest1.hide(player);
                                quest1.destroy(player);
                            }
                        }

                    }
                }
                else {
                    timeOutStatus[0]++;
                    if (timeOutStatus[0] > config.MAX_FORM_TIME_OUT_PERIOD){
                        this.cancel();
                    }
                }
            }
        }, 1, true);

    }
}
