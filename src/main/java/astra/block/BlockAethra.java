package astra.block;

import astra.Config;
import astra.item.instances.ItemInteractInfo;
import cn.nukkit.Player;
import cn.nukkit.block.Block;
import cn.nukkit.form.element.ElementLabel;
import cn.nukkit.form.window.FormWindowCustom;
import cn.nukkit.item.Item;
import cn.nukkit.math.BlockFace;
import cn.nukkit.math.Vector3;
import org.jetbrains.annotations.NotNull;

import java.util.List;
import java.util.Objects;

public abstract class BlockAethra extends Block {
    public int getAbilityBreakTimeFactor(){
        return 1;
    };

    public int getAbilityDropFactor(){
        return 1;
    };

    @Override
    public void onPlayerRightClick(@NotNull Player player, Item item, BlockFace face, Vector3 clickPoint) {
        if (Objects.equals(item.getNamespaceId(), new ItemInteractInfo().getNamespaceId())) {
            FormWindowCustom form = new FormWindowCustom(Config.FORM_SEND_INFO_SYMBOL + "Info "+this.getName(), List.of(
                    new ElementLabel("BREAK_TIME_FACTOR: " + getAbilityBreakTimeFactor()),
                    new ElementLabel("DROP_FACTOR: " + getAbilityDropFactor())
            ));

            player.showFormWindow(form);
        }
    }
}
