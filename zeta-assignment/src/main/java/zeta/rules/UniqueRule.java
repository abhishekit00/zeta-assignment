package zeta.rules;

import zeta.model.ColumnRule;

import java.util.*;

/**
 * Rule to ensure that all values in a column are unique
 * when the 'unique' flag is true in the schema definition.
 */
public class UniqueRule implements ValidationRule {

    /**
     * Validates that all values in the column are unique.
     *
     * @param columnName Name of the column (e.g., "ID")
     * @param columnData List of all values under that column
     * @param rule       The column-specific rule from schema
     * @return List of error messages for duplicate values
     */
    @Override
    public List<String> validate(String columnName, List<String> columnData, ColumnRule rule) {
        List<String> errors = new ArrayList<>();

        if (Boolean.TRUE.equals(rule.getUnique())) {
            Set<String> seen = new HashSet<>();
            for (int i = 0; i < columnData.size(); i++) {
                String value = columnData.get(i);
                if (!seen.add(value)) {
                    errors.add("Row " + (i + 2) + ": Duplicate value '" + value + "' found in column '" + columnName + "'.");
                }
            }
        }

        return errors;
    }
}
