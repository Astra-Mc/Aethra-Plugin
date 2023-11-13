package astra.block;

import astra.Config;
import cn.nukkit.Player;
import cn.nukkit.block.BlockTransparentMeta;
import cn.nukkit.block.customblock.CustomBlock;
import cn.nukkit.block.customblock.CustomBlockDefinition;
import cn.nukkit.block.customblock.data.*;
import cn.nukkit.blockproperty.BlockProperties;
import cn.nukkit.blockproperty.IntBlockProperty;
import cn.nukkit.item.Item;
import cn.nukkit.item.ItemTool;
import cn.nukkit.level.Level;
import cn.nukkit.math.BlockFace;
import cn.nukkit.math.Vector3;
import cn.nukkit.math.Vector3f;
import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class BlockWheatGen extends BlockTransparentMeta implements CustomBlock {
    public static final IntBlockProperty GROWTH = new IntBlockProperty("astra:growth", false, 7);
    public static final List<Permutation> permutations = new ArrayList<>();

    public void grow(){
        if (this.getIntValue("astra:growth") < 7) {
            this.setIntValue("astra:growth", this.getIntValue("astra:growth") + 1);
            Level level = this.getLevel();
            if (level != null) {
                level.setBlock((int) x, (int) y, (int) z, this, true, true);
            }
        }
    }

    @NotNull
    @Override
    public BlockProperties getProperties() {
        return new BlockProperties(
                GROWTH
        );
    }

    @NotNull
    @Override
    public String getNamespaceId() {
        return Config.PLUGIN_BLOCK_PREFIX + "wheat_gen";
    }

    @Override
    public void onPlayerRightClick(@NotNull Player player, Item item, BlockFace face, Vector3 clickPoint) {
        grow();
    }

    @Override
    public int onUpdate(int type) {
        if (type == Level.BLOCK_UPDATE_RANDOM){

            grow();

            return Level.BLOCK_UPDATE_RANDOM;
        }
        return 0;
    }

    private Permutation createPermutation(int growthLevel) {
        return new Permutation(Component.builder()
                .selectionBox(new SelectionBox(-8, 0, -8, 16, (growthLevel+1) * 2, 16))
                .materialInstances(
                        Materials.builder()
                                .any(Materials.RenderMethod.ALPHA_TEST, false, false, "wheat_gen_" + growthLevel))
                .build(), "query.block_property('astra:growth') == " + growthLevel
        );
    }

    @Override
    public CustomBlockDefinition getDefinition() {
        this.setIntValue("astra:growth", new Random().nextInt(7));

        if (permutations.isEmpty()) {
            for (int i = 0; i <= 7; i++) {
                permutations.add(createPermutation(i));
            }
        }

        return CustomBlockDefinition
                .builder(this, "")
                .breakTime(0)
                .collisionBox(new Vector3f(0, 0, 0), new Vector3f(0, 0, 0))
                .geometry("geometry.atlic.wheat_gen")
                .permutations(permutations.toArray(new Permutation[0]))
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

    @Override
    public Item[] getDrops(Item item) {
        return new Item[]{};
    }
}
