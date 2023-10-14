package astra.particle;

import cn.nukkit.level.particle.GenericParticle;
import cn.nukkit.level.particle.Particle;
import cn.nukkit.math.Vector3;

public class BreakMagicOre extends GenericParticle {
    public BreakMagicOre(Vector3 pos) {
        super(pos, Particle.TYPE_WAX);
    }
}
