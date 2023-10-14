package astra.listener;

import astra.block.BlockMagicGenCooldown;
import astra.events.CoinsUpdateEvent;
import astra.mongodb.PlayerDB;
import astra.plugin;
import astra.block.BlockMagicGen;

import cn.nukkit.Player;
import cn.nukkit.Server;
import cn.nukkit.block.Block;
import cn.nukkit.event.EventHandler;
import cn.nukkit.event.Listener;
import cn.nukkit.event.block.BlockBreakEvent;
import cn.nukkit.level.Sound;
import cn.nukkit.math.Vector3;
import cn.nukkit.math.Vector3f;
import cn.nukkit.network.protocol.types.GameType;
import cn.nukkit.scheduler.Task;

import java.util.concurrent.ThreadLocalRandom;

public class BlockBreakAction implements Listener {

    private static final int BlockMagicGenID = new BlockMagicGen().getId(); // get custom block IDS at runtime (bcz they aren't consistent)

    @EventHandler
    public void onBlockBreak(BlockBreakEvent event) {

        Block block = event.getBlock(); // get block from event
        Player player = event.getPlayer(); // get player from event
        if (block.getId() == BlockMagicGenID) {
            if (GameType.from(player.gamemode) == GameType.SURVIVAL) {
                player.getLevel().addSound(new Vector3(block.x, block.y, block.z), Sound.BREAK_AMETHYST_BLOCK);

                for (int i = 0; i < 15; i++) {

                    plugin.getInstance().getServer().getScheduler().scheduleDelayedTask(new Task() {
                        @Override
                        public void onRun(int i) {
                            player.getLevel().addParticleEffect(new Vector3f(
                                    (float) (block.x + ThreadLocalRandom.current().nextDouble(0.25, 0.75)),
                                    (float) (block.y + ThreadLocalRandom.current().nextDouble(0.25, 0.75)),
                                    (float) (block.z + ThreadLocalRandom.current().nextDouble(0.25, 0.75))
                            ), "astra:magic_ore_break", -1, player.getLevel().getDimension(), player);
                        }
                    }, i, true);
                }

                PlayerDB.getInstance().updatePlayerCoinsInDB(player.getLoginChainData().getXUID(), PlayerDB.getInstance().getPlayerCoinsFromDB(player.getLoginChainData().getXUID()) + 1);

                Server.getInstance().getPluginManager().callEvent(new CoinsUpdateEvent(player, PlayerDB.getInstance().getPlayerCoinsFromDB(player.getLoginChainData().getXUID())));

                plugin.getInstance().getServer().getScheduler().scheduleDelayedTask(new Task() {

                    @Override
                    public void onRun(int i) {
                        block.level.setBlock(new Vector3(block.x, block.y, block.z), new BlockMagicGenCooldown()); // run a scheduled task to replace the block with the cooldown block (Need of scheduled task bcz: can't replace block directly ~ it needs a little delay)
                    }

                }, 1);

                plugin.getInstance().getServer().getScheduler().scheduleDelayedTask(new Task() {

                    @Override
                    public void onRun(int i) {
                        block.level.setBlock(new Vector3(block.x, block.y, block.z), new BlockMagicGen()); // run a scheduled task to replace the block after time with the generator block again
                    }

                }, 500);

            }
        }
    }
}
