package astra.block;

import astra.config;
import cn.nukkit.Player;
import cn.nukkit.block.Block;
import cn.nukkit.block.customblock.CustomBlock;
import cn.nukkit.block.customblock.CustomBlockDefinition;
import cn.nukkit.block.customblock.data.Materials;
import cn.nukkit.item.Item;
import cn.nukkit.item.ItemTool;
import cn.nukkit.math.Vector3f;
import org.jetbrains.annotations.NotNull;

public class BlockWheatGen extends Block implements CustomBlock {

    @NotNull
    @Override
    public String getNamespaceId() {
        return config.PLUGIN_BLOCK_PREFIX + "wheat_gen";
    }

    @Override
    public CustomBlockDefinition getDefinition() {
        return CustomBlockDefinition
                .builder(this, Materials
                        .builder()
                        .any(Materials.RenderMethod.ALPHA_TEST, false, false, "wheat")
                )
                .breakTime(0)
                .collisionBox(new Vector3f(0 ,0, 0), new Vector3f(0, 0, 0))
                .geometry("geometry.astra.wheat_gen")
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
    public int getToolType() {
        return ItemTool.TYPE_HOE;
    }

    @Override
    public boolean canPassThrough() {
        return true; // allow to walk through block
    }

    @Override
    public int getLightFilter() {
        return 0; // set light to go through block
    }

    @Override
    public double breakTime(Item item, Player player) {
        return 0;
    }
}
