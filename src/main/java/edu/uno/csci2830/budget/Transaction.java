package edu.uno.csci2830.budget;

/**
 * Represents one income or expense entry in the budget tracker.
 */
public class Transaction {

    /** Short explanation of the transaction. */
    private final String description;

    /** Dollar amount for the transaction. */
    private final double amount;

    /** Date when the transaction occurred. */
    private final String date;

    /** Category used to group the transaction. */
    private final String category;

    /** Whether the transaction is income or an expense. */
    private final TransactionType type;

    /**
     * Creates a new transaction.
     *
     * @param description short explanation of the transaction
     * @param amount dollar amount
     * @param date transaction date
     * @param category transaction category
     * @param type income or expense type
     */
    public Transaction(
            String description, double amount, String date, String category, TransactionType type) {
        this.description = description;
        this.amount = amount;
        this.date = date;
        this.category = category;
        this.type = type;
    }

    /**
     * Gets the transaction description.
     *
     * @return description text
     */
    public String getDescription() {
        return description;
    }

    /**
     * Gets the transaction amount.
     *
     * @return dollar amount
     */
    public double getAmount() {
        return amount;
    }

    /**
     * Gets the transaction date.
     *
     * @return date text
     */
    public String getDate() {
        return date;
    }

    /**
     * Gets the transaction category.
     *
     * @return category text
     */
    public String getCategory() {
        return category;
    }

    /**
     * Gets whether the transaction is income or an expense.
     *
     * @return transaction type
     */
    public TransactionType getType() {
        return type;
    }
}
