package astra.block;

import astra.Config;

import cn.nukkit.Player;
import cn.nukkit.block.Block;
import cn.nukkit.block.customblock.CustomBlock;
import cn.nukkit.block.customblock.CustomBlockDefinition;
import cn.nukkit.item.Item;
import cn.nukkit.item.ItemTool;
import org.jetbrains.annotations.NotNull;

public class BlockOreMagicGen extends Block implements CustomBlock {

    @NotNull
    @Override
    public String getNamespaceId() {
        return Config.PLUGIN_BLOCK_PREFIX + "magic_ore_gen";
    }

    @Override
    public CustomBlockDefinition getDefinition() {
        return CustomBlockDefinition
                .builder(this, "magic_ore_gen") // set block's texture to "magic_ore"
                .build();
    }

    @Override
    public String getName() {
        return CustomBlock.super.getName();
    }

    @Override
    public int getId() {
        return CustomBlock.super.getId();
    }

    @Override
    public int getToolTier() {
        return ItemTool.TIER_NETHERITE;
    }

    @Override
    public int getToolType() {
        return ItemTool.TYPE_PICKAXE;
    }

    @Override
    public double breakTime(Item item, Player player) {
        return (CustomBlock.super.breakTime(item, player) / 2);
    }
}
