package astra;

import cn.nukkit.math.Vector3;

public class config {

    // JUST SETTINGS

    public static final String PLUGIN_BLOCK_PREFIX = "astra:"; //prefix for custom blocks. ex: "astra:"block"


    // PLUGIN RELATED SETTINGS
    public static final Vector3 PLUGIN_SPAWN_VECTOR = new Vector3(0, 100, 0); // player's lobby/spawn position
    public static final String MONGODB_LOGIN_INFO = "mongodb://localhost:27017";


    // MONGODB RELATED SETTINGS
    public static final String MONGODB_DATABASE_INFO = "MongoDB";
    public static final String MONGODB_COLLECTION_PLAYER = "player";
    public enum PlayerLanguages {
        ENGLISH,
        DEUTSCH
    }
}
