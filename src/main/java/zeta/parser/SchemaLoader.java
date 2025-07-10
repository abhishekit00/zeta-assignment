package zeta.parser;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import zeta.model.ColumnRule;

import java.io.File;
import java.io.IOException;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

/**
 * Utility class to load the validation schema from a JSON file.
 */
public class SchemaLoader {

    /**
     * Parses the schema.json and returns column-wise rules.
     *
     * @param filePath path to schema.json
     * @return Map of columnName -> ColumnRule
     * @throws IOException if file not found or invalid
     */
    public static Map<String, ColumnRule> loadSchema(String filePath) throws IOException {
        ObjectMapper mapper = new ObjectMapper();
        JsonNode root = mapper.readTree(new File(filePath));
        JsonNode columnsNode = root.get("columns");

        Map<String, ColumnRule> schema = new HashMap<>();
        Iterator<Map.Entry<String, JsonNode>> fields = columnsNode.fields();

        while (fields.hasNext()) {
            Map.Entry<String, JsonNode> field = fields.next();
            ColumnRule rule = mapper.treeToValue(field.getValue(), ColumnRule.class);
            schema.put(field.getKey(), rule);
        }

        return schema;
    }
}
