package generic;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.persistence.AttributeConverter;
import javax.persistence.Converter;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Converter
public class JsonToListConverter implements AttributeConverter<List<String>, String> {

    private static final Logger log = LoggerFactory.getLogger(JsonToListConverter.class);
    private static ObjectMapper mapper;

    static {
        mapper = new ObjectMapper();
    }

    @Override
    public String convertToDatabaseColumn(List<String> strings) {
        if (strings == null) {
            return "{}";
        }

        try {
            return mapper.writeValueAsString(strings);

        } catch (Exception e) {
            log.error("Error converting list to JSON");
            return null;
        }
    }

    @Override
    public List<String> convertToEntityAttribute(String s) {
        if (s == null) {
            return null;
        }

        try {
            return mapper.readValue(s, new TypeReference<List<String>>() {});

        } catch (Exception e) {
            log.error("Error converting JSON to list");
            return null;
        }
    }
}
