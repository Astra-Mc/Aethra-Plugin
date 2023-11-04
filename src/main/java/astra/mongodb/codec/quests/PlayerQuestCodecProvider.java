package astra.mongodb.codec.quests;

import astra.playerquestsystem.PlayerQuest;
import org.bson.codecs.Codec;
import org.bson.codecs.configuration.CodecProvider;
import org.bson.codecs.configuration.CodecRegistry;

public class PlayerQuestCodecProvider implements CodecProvider {

    public PlayerQuestCodecProvider() {}
    @Override
    @SuppressWarnings("unchecked")
    public <T> Codec<T> get(Class<T> clazz, CodecRegistry registry) {
        if (clazz == PlayerQuest.class) {
            return (Codec<T>) new PlayerQuestCodec(registry);
        }
        // return null when not a provider for the requested class
        return null;
    }
}
