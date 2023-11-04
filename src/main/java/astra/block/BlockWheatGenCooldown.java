package astra.block;

import astra.config;
import cn.nukkit.block.BlockTransparent;
import cn.nukkit.block.customblock.CustomBlock;
import cn.nukkit.block.customblock.CustomBlockDefinition;
import cn.nukkit.block.customblock.data.Materials;
import cn.nukkit.item.Item;
import cn.nukkit.math.Vector3f;
import org.jetbrains.annotations.NotNull;

public class BlockWheatGenCooldown extends BlockTransparent implements CustomBlock {

    @NotNull
    @Override
    public String getNamespaceId() {
        return config.PLUGIN_BLOCK_PREFIX + "wheat_gen_cooldown";
    }

    @Override
    public CustomBlockDefinition getDefinition() {
        return CustomBlockDefinition
                .builder(this, Materials
                        .builder()
                        .any(Materials.RenderMethod.BLEND, false, false, "wheat_transparent")
                )
                .breakTime(-1)
                .selectionBox(new Vector3f(0, 0, 0), new Vector3f(0, 0, 0))
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
    public boolean isBreakable(Item item) {
        return false; // make block unbreakable
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
    public boolean shouldBeRegisteredInCreative() {
        return false; // set block to not be in creative menu
    }
}
