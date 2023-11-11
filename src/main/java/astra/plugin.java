package astra;

import astra.block.BlockOreMagicGen;
import astra.block.BlockOreMagicGenCooldown;
import astra.block.BlockWheatGen;
import astra.command.SkillCommand;
import astra.lang.LangManager;
import astra.listener.BlockBreakAction;
import astra.listener.CoinsUpdateAction;
import astra.listener.JoinAction;
import astra.mongodb.MongoDB;
import cn.nukkit.block.Block;
import cn.nukkit.level.Level;
import cn.nukkit.plugin.PluginBase;

import java.util.List;

public class plugin extends PluginBase {

    private static plugin instance;

    public static plugin getInstance() {
        return instance;
    }

    @Override
    public void onLoad() {
        Block.registerCustomBlock(List.of(
                BlockOreMagicGen.class,
                BlockOreMagicGenCooldown.class,
                BlockWheatGen.class
        )); // register Blocks

    }

    @Override
    public void onEnable() {
        this.getLogger().info("Astra plugin started!");
        plugin.instance = this;

        MongoDB.Start();
        LangManager.Start();

        Level.setCanRandomTick(new BlockWheatGen().getId(), true);

        getServer().getPluginManager().registerEvents(new BlockBreakAction(), this); // register on block break event
        getServer().getPluginManager().registerEvents(new JoinAction(), this); // register on player join event
        getServer().getPluginManager().registerEvents(new CoinsUpdateAction(), this); // register Event for Coins update

        getServer().getCommandMap().register("skill", new SkillCommand());
    }

    @Override
    public void onDisable() {
        this.getLogger().info("Astra plugin shut down!");

        MongoDB.Stop();
        LangManager.Stop();
    }
}
