package zeta.rules;

import zeta.model.ColumnRule;

import java.util.ArrayList;
import java.util.List;
import java.util.regex.Pattern;

/**
 * Rule to validate that column values match a given regex pattern.
 * The pattern must be defined in the schema using the 'pattern' field.
 */
public class PatternRule implements ValidationRule {

    /**
     * Validates column values against a provided regex pattern.
     *
     * @param columnName Name of the column (e.g., "Zipcode")
     * @param columnData List of values in that column
     * @param rule       The rule config for that column
     * @return List of error messages for invalid values
     */
    @Override
    public List<String> validate(String columnName, List<String> columnData, ColumnRule rule) {
        List<String> errors = new ArrayList<>();

        if (rule.getPattern() != null && !rule.getPattern().isEmpty()) {
            Pattern pattern = Pattern.compile(rule.getPattern());

            for (int i = 0; i < columnData.size(); i++) {
                String value = columnData.get(i);
                if (!pattern.matcher(value).matches()) {
                    errors.add("Row " + (i + 2) + ": '" + value + "' in column '" + columnName +
                            "' does not match required pattern: " + rule.getPattern());
                }
            }
        }

        return errors;
    }
}
