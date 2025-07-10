package zeta.engine;

import zeta.model.ColumnRule;
import zeta.rules.*;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * RuleEngine is responsible for coordinating all validations
 * against the input CSV data using the rules defined in the schema.
 */
public class RuleEngine {

    private final List<ValidationRule> validationRules;

    /**
     * Initializes the rule engine with all supported validation rule strategies.
     */
    public RuleEngine() {
        this.validationRules = List.of(
                new NonNullRule(),
                new UniqueRule(),
                new DataTypeRule(),
                new PatternRule(),
                new MaxLengthRule()
        );
    }

    /**
     * Executes validation on the given CSV data using the schema.
     *
     * @param csvData Map of column names to their respective values from CSV
     * @param schema  Map of column names to ColumnRule definitions from schema
     * @return A list of all validation errors encountered
     */
    public List<String> validateCsv(Map<String, List<String>> csvData, Map<String, ColumnRule> schema) {
        List<String> allErrors = new ArrayList<>();

        for (Map.Entry<String, List<String>> entry : csvData.entrySet()) {
            String columnName = entry.getKey();
            List<String> columnValues = entry.getValue();

            // Get the rule config for the column
            ColumnRule columnRule = schema.get(columnName);
            if (columnRule == null) {
                continue; // no rules defined for this column
            }

            // Apply all registered rules
            for (ValidationRule rule : validationRules) {
                List<String> errors = rule.validate(columnName, columnValues, columnRule);
                allErrors.addAll(errors);
            }
        }

        return allErrors;
    }
}
