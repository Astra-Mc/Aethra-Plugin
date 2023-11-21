package astra;

import cn.nukkit.level.Level;
import cn.nukkit.level.Location;

public class Config {

    // SIMPLE SERVER RELATED SETTINGS
    public static final Location PLUGIN_SPAWN_VECTOR = new Location(0, 100, 0, Level.DIMENSION_OVERWORLD); // player's lobby/spawn position

    // BLOCK RELATED SETTINGS
    public static final String PLUGIN_BLOCK_PREFIX = "astra:"; //prefix for custom blocks. ex: "astra:"block"

    // MONGODB RELATED SETTINGS
    public static final String MONGODB_LOGIN_INFO = "mongodb://localhost:27017";
    public static final String MONGODB_DATABASE = "MongoDB";
    public static final String MONGODB_COLLECTION_PLAYER = "player";

    // FORM RELATED SETTINGS
    public static final int FORM_MAX_TIME_OUT_PERIOD = 6000; // 5min (6000 Ticks / 20 Ticks per sec)
    public static final String FORM_SEND_SETTINGS_SYMBOL = "\uE100";
    public static final String FORM_SEND_SKILLS_SYMBOL = "\uE101";
    public static final String FORM_SEND_WELCOME_SYMBOL = "\uE102";

    // SIDEPANEL RELATED SETTINGS
    public static final String SIDEPANEL_SEND_NAME_SYMBOL = "\uE100";
    public static final String SIDEPANEL_SEND_COINS_SYMBOL = "\uE101";
    public static final String SIDEPANEL_SEND_RANK_SYMBOL = "\uE102";

    // PLOT ISLAND RELATED SETTINGS
    public static final String PLOT_ISLAND_WORLD_FOLDER = "worlds/";
    public static final String PLOT_ISLAND_LEVEL_FILE = "/level.dat";

    // LANGUAGE RELATED SETTINGS
    public enum Languages {
        ENGLISH_GB,
        GERMAN_DE,
        GERMAN_AU,
        FRENCH_FR,
        SPANISH_SP
    }

    // RANK RELATED SETTINGS
    public enum Ranks {
        OWNER,
        ADMIN,
        SUPPORT,
        DEV,
        BUILDER,
        BOOSTER,
        ELITE,
        MEMBER
    }
}