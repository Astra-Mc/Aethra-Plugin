package astra.skillsystem.skills;

import cn.nukkit.Player;
import cn.nukkit.Server;
import cn.nukkit.form.element.ElementButton;
import cn.nukkit.form.window.FormWindowSimple;
import cn.nukkit.scheduler.Task;

public class SkillMining {
    public static void Show(Player player){
        FormWindowSimple form = new FormWindowSimple("Mining", "");
        form.addButton(new ElementButton("The Power of Ore"));
        form.addButton(new ElementButton("Expert of the Underneath"));
        form.addButton(new ElementButton("Harder than Myrtle-Viola"));
        form.addButton(new ElementButton("Let's it up"));
        player.showFormWindow(form);

        Server.getInstance().getScheduler().scheduleRepeatingTask(new Task() {
            @Override
            public void onRun(int currentTick) {
                if (form.getResponse() != null){
                    this.cancel();

                    switch (form.getResponse().getClickedButton().getText()){

                        case "The Power of Ore":
                            SkillMining.Show(player);
                            break;

                        case "Expert of the Underneath":
                            SkillFarming.Show(player);
                            break;

                        case "Harder than Myrtle-Viola":
                            SkillMining.Show(player);
                            break;

                        case "Let's it up":
                            SkillFarming.Show(player);
                            break;
                    }
                }
            }
        }, 1, true);
    }
}
