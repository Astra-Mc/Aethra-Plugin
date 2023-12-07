package astra.block.instances;

import astra.Config;
import astra.block.BlockAethra;
import cn.nukkit.block.Block;
import cn.nukkit.block.customblock.CustomBlock;
import cn.nukkit.block.customblock.CustomBlockDefinition;
import cn.nukkit.item.customitem.data.ItemCreativeGroup;
import org.jetbrains.annotations.NotNull;

public class BlockStonePath extends BlockAethra implements CustomBlock {
    @NotNull
    @Override
    public String getNamespaceId() {
        return Config.BLOCK_NAMESPACE_PREFIX + "stone_path";
    }

    @Override
    public CustomBlockDefinition getDefinition() {
        return CustomBlockDefinition
                .builder(
                        this,
                        "stone_path"
                )
                .creativeGroup(ItemCreativeGroup.WOOL)
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
}
