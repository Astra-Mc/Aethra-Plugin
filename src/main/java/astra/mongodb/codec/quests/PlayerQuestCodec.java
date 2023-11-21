package astra.mongodb.codec.quests;

import astra.playerquest.PlayerQuest;
import cn.nukkit.utils.BossBarColor;
import org.bson.BsonReader;
import org.bson.BsonType;
import org.bson.BsonWriter;
import org.bson.codecs.Codec;
import org.bson.codecs.DecoderContext;
import org.bson.codecs.EncoderContext;
import org.bson.codecs.configuration.CodecRegistry;

public class PlayerQuestCodec implements Codec<PlayerQuest> {

    private final Codec<String> stringCodec;
    private final Codec<Long> longCodec;
    private final Codec<Float> floatCodec;

    public PlayerQuestCodec(CodecRegistry registry){
        this.stringCodec = registry.get(String.class);
        this.longCodec = registry.get(Long.class);
        this.floatCodec = registry.get(Float.class);
    }

    @Override
    public void encode(BsonWriter writer, PlayerQuest value, EncoderContext encoderContext) {
        writer.writeStartDocument();

        writer.writeName("title");
        stringCodec.encode(writer, value.getTitle(), encoderContext);

        writer.writeName("color");
        stringCodec.encode(writer, value.getColor().name(), encoderContext);

        writer.writeName("length");
        floatCodec.encode(writer, value.getLength(), encoderContext);

        writer.writeName("bossbar_id");
        longCodec.encode(writer, value.getBossbar_ID(), encoderContext);

        writer.writeName("quest_id");
        stringCodec.encode(writer, value.getID(), encoderContext);

        writer.writeEndDocument();
    }

    @Override
    public PlayerQuest decode(BsonReader reader, DecoderContext decoderContext) {
        reader.readStartDocument();

        String title = "title";
        BossBarColor color = BossBarColor.PINK;
        Float length = 100f;
        Long bossbar_id = 0L;
        String ID = "ID";

        while (reader.readBsonType() != BsonType.END_OF_DOCUMENT) {
            String fieldName = reader.readName();

            switch (fieldName) {
                case "title" -> {
                    title = stringCodec.decode(reader, decoderContext);
                }
                case "color" -> {
                    color = BossBarColor.valueOf(stringCodec.decode(reader, decoderContext));
                }
                case "length" -> {
                    length = floatCodec.decode(reader, decoderContext);
                }
                case "bossbar_id" -> {
                    bossbar_id = longCodec.decode(reader, decoderContext);
                }
                case "quest_id" -> {
                    ID = stringCodec.decode(reader, decoderContext);
                }
            }
        }
        reader.readEndDocument();

        PlayerQuest quest = new PlayerQuest(
                title,
                color,
                length,
                ID
        );

        quest.setBossbar_ID(bossbar_id);

        return quest;
    }


    @Override
    public Class<PlayerQuest> getEncoderClass() {
        return PlayerQuest.class;
    }
}