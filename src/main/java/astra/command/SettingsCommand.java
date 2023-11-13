package astra.command;

import astra.Plugin;
import astra.settings.Settings;
import cn.nukkit.Player;
import cn.nukkit.command.Command;
import cn.nukkit.command.CommandSender;
import cn.nukkit.command.data.CommandParamType;
import cn.nukkit.command.data.CommandParameter;
import cn.nukkit.command.tree.ParamList;
import cn.nukkit.command.utils.CommandLogger;
import cn.nukkit.permission.Permission;

import java.util.ArrayList;
import java.util.Map;

public class SettingsCommand extends Command {
    public SettingsCommand() {
        super("settings", "Shows your Settings", "usage");
        this.setPermission(Permission.DEFAULT_OP);
        this.commandParameters.clear();
        this.commandParameters.put("default", new CommandParameter[]{CommandParameter.newType("player", true, CommandParamType.TARGET)});
        this.enableParamTree();
    }

    @Override
    public int execute(CommandSender sender, String commandLabel, Map.Entry<String, ParamList> result, CommandLogger log) {
        ParamList list = result.getValue();
        ArrayList<Player> players = list.getResult(0);


        if (players != null) {
            if (!players.isEmpty()) {
                for (Player player: players) {
                    if (player != null) {
                        Settings.Show(player);
                    }
                }
            }
            else {
                sender.sendMessage("Target not found");
                return 0;
            }
        }
        else {
            Settings.Show(sender.asPlayer());
        }

        return 1;
    }
}
