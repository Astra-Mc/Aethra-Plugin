package astra.block.instances;

import astra.Config;

import astra.ability.mining.Efficiency;
import astra.block.BlockAethra;
import astra.item.instances.ItemOreMagicRaw;
import cn.nukkit.Player;
import cn.nukkit.block.customblock.CustomBlock;
import cn.nukkit.block.customblock.CustomBlockDefinition;
import cn.nukkit.block.customblock.data.CraftingTable;
import cn.nukkit.item.Item;
import cn.nukkit.item.ItemTool;
import org.jetbrains.annotations.NotNull;

import java.util.List;

public class BlockOreMagicGen extends BlockAethra implements CustomBlock {

    CraftingTable craftingTable = new CraftingTable("table", List.of("dunno"));

    @Override
    public int getAbilityBreakTimeFactor(){
        return 5;
    };
    @Override
    public int getAbilityDropFactor(){
        return 5;
    };

    @NotNull
    @Override
    public String getNamespaceId() {
        return Config.BLOCK_NAMESPACE_PREFIX + "magic_ore_gen";
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
        double value = ((CustomBlock.super.breakTime(item, player) * getAbilityBreakTimeFactor() * 10) - (int) Efficiency.getData(player)) / 10;
        return Math.max(value, 0.11);
    }

    @Override
    public boolean onBreak(Item item) {
        return super.onBreak(item);
    }

    @Override
    public Item[] getDrops(Item item) {
        return new Item[]{
                new ItemOreMagicRaw()
        };
    }
}
