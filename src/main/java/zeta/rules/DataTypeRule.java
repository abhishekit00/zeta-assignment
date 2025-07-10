package zeta.rules;

import zeta.model.ColumnRule;

import java.util.ArrayList;
import java.util.List;

/**
 * Rule to validate that all values in a column match the expected data type.
 * Supported data types: Integer, String
 */
public class DataTypeRule implements ValidationRule {

    /**
     * Validates the data type of each value in the column based on schema definition.
     *
     * @param columnName Name of the column (e.g., "ID", "Zipcode")
     * @param columnData List of all values in the column
     * @param rule       Rule definition for the column
     * @return List of validation error messages
     */
    @Override
    public List<String> validate(String columnName, List<String> columnData, ColumnRule rule) {
        List<String> errors = new ArrayList<>();

        String expectedType = rule.getDataType();
        if (expectedType == null || expectedType.isEmpty()) {
            return errors; // No dataType specified, skip
        }

        for (int i = 0; i < columnData.size(); i++) {
            String value = columnData.get(i);

            // Skip validation if value is null or empty and nullable=true
            if (value == null || value.trim().isEmpty()) {
                continue;
            }

            boolean isValid = switch (expectedType.toLowerCase()) {
                case "integer" -> isInteger(value);
                case "string" -> true; // No need to validate
                default -> false; // unsupported type
            };

            if (!isValid) {
                errors.add("Row " + (i + 2) + ": '" + value + "' in column '" + columnName +
                        "' is not a valid " + expectedType + ".");
            }
        }

        return errors;
    }

    // Utility method to check if a string is an integer
    private boolean isInteger(String value) {
        try {
            Integer.parseInt(value);
            return true;
        } catch (NumberFormatException e) {
            return false;
        }
    }
}
