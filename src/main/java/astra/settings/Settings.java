package astra.settings;

import astra.Config;
import astra.form.FormConfig;
import astra.lang.LangConfig;
import astra.lang.LangManager;
import astra.mongodb.PlayerDB;
import astra.ranks.RankConfig;
import cn.nukkit.Player;
import cn.nukkit.Server;
import cn.nukkit.form.element.*;
import cn.nukkit.form.window.FormWindowCustom;
import cn.nukkit.scheduler.Task;

import java.util.*;
import java.util.stream.Stream;

public class Settings {
    public static void Show(Player player){

        List<String> languages = Stream.of(LangConfig.Languages.values())
                .map(Enum::name)
                .toList();

        List<String> ranks = PlayerDB.getPlayerRanks(player).stream()
                .map(Enum::name)
                .toList();


        FormWindowCustom form = new FormWindowCustom(
                FormConfig.SEND_SETTINGS_FORM_SYMBOL+LangManager.getString(player, "ui.form.settings.title"),
                List.of(
                        new ElementLabel(LangManager.getString(player, "ui.form.settings.description")),
                        new ElementInput(LangManager.getString(player, "ui.form.settings.display_name_input.title"), LangManager.getString(player, "ui.form.settings.display_name_input.placeholder"), PlayerDB.getPlayName(player)),
                        new ElementDropdown(LangManager.getString(player, "ui.form.settings.language_dropdown.title"), languages, languages.indexOf(PlayerDB.getPlayerLanguage(player).name())),
                        new ElementDropdown(LangManager.getString(player, "ui.form.settings.selected_rank_dropdown.title"), ranks,  ranks.indexOf(PlayerDB.getSelectedPlayerRank(player).name()))
                ),
                new ElementButtonImageData("url", "https://www.powernukkitx.com/assets/image/PNX_LOGO_sm")
        );

        player.showFormWindow(form);

        final int[] timeOutStatus = {0};
        Server.getInstance().getScheduler().scheduleRepeatingTask(new Task() {
            @Override
            public void onRun(int currentTick) {
                if (form.getResponse() != null){
                    this.cancel();

                    int i = 0;
                    int count_tasks = 0;

                    for (Object response: form.getResponse().getResponses().values()){

                        switch (i){
                            case 1 -> {
                                if (!(response.toString().isBlank()) && (!Objects.equals(response.toString(), PlayerDB.getPlayName(player)))){
                                    Server.getInstance().getScheduler().scheduleDelayedTask(new Task() {
                                        @Override
                                        public void onRun(int i) {
                                            PlayerDB.setPLayerName(player, response.toString());
                                        }
                                    }, count_tasks*20, true);
                                    count_tasks++;
                                }
                            }
                            case 2 -> {
                                if (!(Objects.equals(response.toString(), PlayerDB.getPlayerLanguage(player).name()))) {
                                    PlayerDB.setPlayerLanguage(player, LangConfig.Languages.valueOf(response.toString()));
                                }

                            }
                            case 3 -> {
                                if (!(Objects.equals(response.toString(), PlayerDB.getSelectedPlayerRank(player).name()))) {
                                    Server.getInstance().getScheduler().scheduleDelayedTask(new Task() {
                                        @Override
                                        public void onRun(int i) {
                                            PlayerDB.setSelectedPlayerRank(player, RankConfig.Ranks.valueOf(response.toString()));
                                        }
                                    }, count_tasks*20, true);
                                    count_tasks++;
                                }
                            }
                        }

                        i++;
                    }
                }
                else {
                    timeOutStatus[0]++;
                    if (timeOutStatus[0] > Config.MAX_FORM_TIME_OUT_PERIOD){
                        this.cancel();
                    }
                }
            }
        }, 1, true);
    }
}
