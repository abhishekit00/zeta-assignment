package zeta.rules;

import zeta.model.ColumnRule;

import java.util.ArrayList;
import java.util.List;

/**
 * Rule to ensure that a column does not contain null or empty values
 * if the `nullable` property is set to false in schema.
 */
public class NonNullRule implements ValidationRule {

    /**
     * Validates that no row in the column is null or empty, if nullable=false.
     *
     * @param columnName Name of the column
     * @param columnData List of all values under the column
     * @param rule       The column-specific rule from schema
     * @return List of error messages (if any)
     */
    @Override
    public List<String> validate(String columnName, List<String> columnData, ColumnRule rule) {
        List<String> errors = new ArrayList<>();

        // If nullable is not explicitly set to false, skip validation
        if (Boolean.FALSE.equals(rule.getNullable())) {
            for (int i = 0; i < columnData.size(); i++) {
                String value = columnData.get(i);
                if (value == null || value.trim().isEmpty()) {
                    errors.add("Row " + (i + 2) + ": '" + columnName + "' cannot be null or empty.");
                }
            }
        }

        return errors;
    }
}
