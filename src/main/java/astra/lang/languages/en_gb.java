package astra.lang.languages;

import astra.lang.LangManager;
import astra.Plugin;
import io.leangen.geantyref.TypeToken;

import java.io.InputStream;
import java.io.InputStreamReader;
import java.lang.reflect.Type;
import java.util.HashMap;

public class en_gb {
    public static HashMap<String, String> Init(){
        InputStream inputStream = LangManager.class.getResourceAsStream("/languages/en-gb.json");

        assert inputStream != null;
        InputStreamReader reader = new InputStreamReader(inputStream);
        Type type = new TypeToken<HashMap<String, String>>() {}.getType();

        HashMap<String, String> data = LangManager.getGson().fromJson(reader, type);

        Plugin.getInstance().getLogger().info("INITIALIZED EN-GB\n"+data);

        return data;
    }
}
