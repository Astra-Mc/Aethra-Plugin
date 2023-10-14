package astra;

import astra.block.BlockMagicGen;
import astra.block.BlockMagicGenCooldown;
import astra.listener.BlockBreakAction;
import astra.listener.CoinsUpdateAction;
import astra.listener.JoinAction;
import astra.mongodb.MongoDB;
import cn.nukkit.block.Block;
import cn.nukkit.plugin.PluginBase;

import java.util.List;

public class plugin extends PluginBase {

    private static plugin instance;
    private static MongoDB mongodb;

    public static plugin getInstance() {
        return instance;
    }

    @Override
    public void onLoad() {
        Block.registerCustomBlock(List.of(BlockMagicGen.class, BlockMagicGenCooldown.class)); // register Blocks
    }

    @Override
    public void onEnable() {
        this.getLogger().info("Astra plugin started");
        plugin.instance = this;

        plugin.mongodb = new MongoDB();
        plugin.mongodb.Start();

        getServer().getPluginManager().registerEvents(new BlockBreakAction(), this); // register on block break event
        getServer().getPluginManager().registerEvents(new JoinAction(), this); // register on player join event
        getServer().getPluginManager().registerEvents(new CoinsUpdateAction(), this); // register Event for Coins update
    }

    @Override
    public void onDisable() {
        this.getLogger().info("Astra plugin shut down");

        plugin.mongodb.Stop();
    }
}
