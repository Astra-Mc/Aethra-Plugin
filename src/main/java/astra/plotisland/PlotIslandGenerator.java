package astra.plotisland;

import cn.nukkit.level.ChunkManager;
import cn.nukkit.level.generator.Generator;
import cn.nukkit.math.NukkitRandom;
import cn.nukkit.math.Vector3;

import java.util.HashMap;
import java.util.Map;

public class PlotIslandGenerator extends Generator {
    @Override
    public int getId() {
        return -1;
    }

    @Override
    public void init(ChunkManager chunkManager, NukkitRandom nukkitRandom) {
    }

    @Override
    public void generateChunk(int i, int i1) {
    }

    @Override
    public void populateChunk(int i, int i1) {
    }

    @Override
    public Map<String, Object> getSettings() {
        return new HashMap<>();
    }

    @Override
    public String getName() {
        return "void";
    }

    @Override
    public Vector3 getSpawn() {
        return new Vector3(0, 100, 0);
    }
}
