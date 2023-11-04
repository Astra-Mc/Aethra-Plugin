package astra;

import cn.nukkit.level.Level;
import cn.nukkit.level.Location;

public class config {

    // SERVER SETTINGS
    public static final Location PLUGIN_SPAWN_VECTOR = new Location(0, 100, 0, Level.DIMENSION_OVERWORLD); // player's lobby/spawn position

    // BLOCK SETTINGS
    public static final String PLUGIN_BLOCK_PREFIX = "astra:"; //prefix for custom blocks. ex: "astra:"block"

    // MONGODB RELATED SETTINGS
    public static final String MONGODB_LOGIN_INFO = "mongodb://localhost:27017";
    public static final String MONGODB_DATABASE = "MongoDB";
    public static final String MONGODB_COLLECTION_PLAYER = "player";
}