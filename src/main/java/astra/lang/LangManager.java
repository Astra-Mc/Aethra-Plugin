package astra.lang;

import astra.Config;
import astra.lang.languages.de_de;
import astra.lang.languages.en_gb;
import astra.mongodb.PlayerDB;
import cn.nukkit.Player;
import com.google.gson.Gson;

import java.util.HashMap;

public class LangManager {
    
    private static Gson gson;

    private static HashMap<String, String> en_gb_text;
    private static HashMap<String, String> de_de_text;

    public static Gson getGson() {
        return gson;
    }

    public static void Start(){
        gson = new Gson();

        de_de_text = de_de.Init();
        en_gb_text = en_gb.Init();
    }

    public static void Stop(){}

    public static String getString(Config.Languages language, String identifier){

        return switch (language) {
            case ENGLISH_GB -> en_gb_text.get(identifier);
            case GERMAN_DE -> de_de_text.get(identifier);
            default -> "<#ID NOT FOUND | LANG: "+language+" | ID: "+identifier+" #>";
        };
    }

    public static String getString(Player player, String identifier){
        Config.Languages language = PlayerDB.getPlayerLanguage(player);

        return switch (language) {
            case ENGLISH_GB -> en_gb_text.get(identifier);
            case GERMAN_DE -> de_de_text.get(identifier);
            default -> "<#ID NOT FOUND | LANG: "+language+" | ID: "+identifier+" #>";
        };
    }
}
