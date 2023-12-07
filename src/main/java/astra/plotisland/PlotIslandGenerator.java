package astra.plotisland;

import cn.nukkit.level.ChunkManager;
import cn.nukkit.level.generator.Generator;
import cn.nukkit.math.NukkitRandom;
import cn.nukkit.math.Vector3;

import java.util.Map;

public class PlotIslandGenerator extends Generator {
    private ChunkManager chunkManager;
    public PlotIslandGenerator(Map options) {}

    @Override
    public int getId() {
        return 1;
    }

    @Override
    public void init(ChunkManager chunkManager, NukkitRandom nukkitRandom) {
        this.chunkManager = chunkManager;
    }

    @Override
    public void generateChunk(int chX, int chZ) {}

    @Override
    public void populateChunk(int i, int i1) {}

    @Override
    public Map<String, Object> getSettings() {
        return null;
    }

    @Override
    public String getName() {
        return "VOID";
    }

    @Override
    public Vector3 getSpawn() {
        return new Vector3(0.5, 100, 0.5);
    }

    @Override
    public ChunkManager getChunkManager() {
        return chunkManager;
    }
}
