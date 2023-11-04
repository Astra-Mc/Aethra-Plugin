package astra.lang;

import astra.lang.languages.de_de;
import astra.lang.languages.en_gb;
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

    public static String getString(LangConfig.Languages language, String identifier){

        switch (language){
            case ENGLISH_GB:
                return en_gb_text.get(identifier);

            case GERMAN_DE:
                return de_de_text.get(identifier);

            default:
                return null;
        }
    }
}
