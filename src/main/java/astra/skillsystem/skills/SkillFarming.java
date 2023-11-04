package astra.skillsystem.skills;

import cn.nukkit.Player;
import cn.nukkit.Server;
import cn.nukkit.form.element.ElementButton;
import cn.nukkit.form.window.FormWindowSimple;
import cn.nukkit.scheduler.Task;

public class SkillFarming {
    public static void Show(Player player){
        FormWindowSimple form = new FormWindowSimple("Farming", "");
        form.addButton(new ElementButton("Hope of the Plants"));
        form.addButton(new ElementButton("Knowledge of the plants"));
        form.addButton(new ElementButton("Make the plants great again!"));
        player.showFormWindow(form);

        Server.getInstance().getScheduler().scheduleRepeatingTask(new Task() {
            @Override
            public void onRun(int currentTick) {
                if (form.getResponse() != null){
                    this.cancel();

                    switch (form.getResponse().getClickedButton().getText()){

                        case "Hope of the Plants":
                            SkillFarming.Show(player);
                            break;

                        case "Knowledge of the plants":
                            SkillMining.Show(player);
                            break;

                        case "Make the plants great again!":
                            SkillFarming.Show(player);
                            break;
                    }
                }
            }
        }, 1, true);
    }
}
