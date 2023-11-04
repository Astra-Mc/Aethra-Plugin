package astra.block;

import astra.config;
import cn.nukkit.block.BlockTransparent;
import cn.nukkit.block.customblock.CustomBlock;
import cn.nukkit.block.customblock.CustomBlockDefinition;
import cn.nukkit.block.customblock.data.Materials;
import cn.nukkit.item.Item;
import cn.nukkit.math.Vector3f;
import org.jetbrains.annotations.NotNull;

public class BlockOreMagicGenCooldown extends BlockTransparent implements CustomBlock {

    @NotNull
    @Override
    public String getNamespaceId() {
        return config.PLUGIN_BLOCK_PREFIX + "magic_gen_cooldown";
    }

    @Override
    public CustomBlockDefinition getDefinition() {
        return CustomBlockDefinition
                .builder(this, Materials
                        .builder()
                        .any(Materials.RenderMethod.BLEND, "magic_ore_gen_cooldown") // set Block Render Method to be transparent and texture to "glass"
                )
                .breakTime(-1) // make block unbreakable (for client)
                .collisionBox(new Vector3f(0, 0, 0), new Vector3f(0, 0, 0)) // allow to walk through block (for client)
                .selectionBox(new Vector3f(0, 0, 0), new Vector3f(0, 0, 0)) // set Block to not be selectable (for client)
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
