package zeta.rules;

import zeta.model.ColumnRule;

import java.util.List;

/**
 * Strategy interface for all validation rules.
 * Each rule implements this and provides logic to validate column data.
 */
public interface ValidationRule {

    /**
     * Validates a specific column using the rule.
     *
     * @param columnName Name of the column (e.g., "ID", "Zipcode")
     * @param columnData All values in that column (1 entry per row)
     * @param rule       Column-specific schema config
     * @return List of validation error messages (if any)
     */
    List<String> validate(String columnName, List<String> columnData, ColumnRule rule);
}
