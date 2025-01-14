package lescano.forohub.utils;

import lescano.forohub.entities.TopicType;
import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.databind.DeserializationContext;
import com.fasterxml.jackson.databind.JsonDeserializer;

import java.io.IOException;

public class TopicTypeDeserializer extends JsonDeserializer<TopicType> {

    @Override
    public TopicType deserialize(JsonParser jsonParser, DeserializationContext deserializationContext) throws IOException {
        String value = jsonParser.getText().trim();
        return TopicType.fromString(value);
    }
}
