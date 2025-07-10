package zeta.rules;

import zeta.model.ColumnRule;

import java.util.ArrayList;
import java.util.List;

/**
 * Rule to ensure that values in a column do not exceed the max character length.
 * The limit must be defined using the 'maxLength' field in schema.
 */
public class MaxLengthRule implements ValidationRule {

    /**
     * Validates that the length of each value does not exceed the max allowed.
     *
     * @param columnName Name of the column (e.g., "Address")
     * @param columnData List of all values under the column
     * @param rule       Rule definition for the column
     * @return List of error messages if max length is exceeded
     */
    @Override
    public List<String> validate(String columnName, List<String> columnData, ColumnRule rule) {
        List<String> errors = new ArrayList<>();

        if (rule.getMaxLength() != null) {
            int maxAllowed = rule.getMaxLength();

            for (int i = 0; i < columnData.size(); i++) {
                String value = columnData.get(i);
                if (value != null && value.length() > maxAllowed) {
                    errors.add("Row " + (i + 2) + ": Value in column '" + columnName + "' exceeds max length of " + maxAllowed + " characters.");
                }
            }
        }

        return errors;
    }
}
