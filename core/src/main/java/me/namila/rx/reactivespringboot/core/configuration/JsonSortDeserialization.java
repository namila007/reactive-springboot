package me.namila.rx.reactivespringboot.core.configuration;

import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.databind.DeserializationContext;
import com.fasterxml.jackson.databind.JsonDeserializer;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.node.ArrayNode;
import org.springframework.boot.jackson.JsonComponent;
import org.springframework.data.domain.Sort;
import org.springframework.data.domain.Sort.Order;

import java.io.IOException;
//ToDo JsonProperty value is not deserialized for pagebale sort properly
@JsonComponent
public class JsonSortDeserialization extends JsonDeserializer<Sort> {

    @Override
    public Sort deserialize(JsonParser p, DeserializationContext ctxt) throws IOException {
        final ArrayNode node = p.getCodec().readTree(p);
        final Order[] orders = new Order[node.size()];

        int i = 0;
        for (JsonNode jsonNode : node) {
            orders[i] =
                    new Order(
                            Sort.Direction.valueOf(jsonNode.get("direction").asText()),
                            jsonNode.get("property").asText());
            i++;
        }
        return Sort.by(orders);
    }
}
