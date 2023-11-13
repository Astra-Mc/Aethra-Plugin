package astra.mongodb.codec;

import astra.mongodb.MongoDB;
import astra.mongodb.codec.quests.PlayerQuestCodecProvider;
import astra.Plugin;
import com.mongodb.MongoClientSettings;
import org.bson.codecs.DoubleCodec;
import org.bson.codecs.StringCodec;
import org.bson.codecs.configuration.CodecRegistry;
import org.bson.codecs.configuration.CodecRegistries;

public class CodecRegisterer {

    public static void RegisterCodecs(){

        CodecRegistry codecRegistry = CodecRegistries.fromRegistries(
                CodecRegistries.fromCodecs(new StringCodec(), new DoubleCodec()),
                CodecRegistries.fromProviders(new PlayerQuestCodecProvider()), // Register your PlayerQuestCodecProvider
                MongoClientSettings.getDefaultCodecRegistry()
        );

        Plugin.getInstance().getLogger().info("REGISTERED CODEC");

        MongoDB.setPlayerCollection(MongoDB.getPlayerCollection().withCodecRegistry(codecRegistry));
    }
}
