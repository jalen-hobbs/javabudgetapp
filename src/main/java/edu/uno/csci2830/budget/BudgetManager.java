package edu.uno.csci2830.budget;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

/**
 * Manages the transaction list and performs budget calculations.
 *
 * <p>This class is separate from the JavaFX interface so that the budget logic can be tested
 * without needing to open the graphical application.
 */
public class BudgetManager {

    /** List of transactions currently loaded in the program. */
    private final List<Transaction> transactions = new ArrayList<>();

    /** Helper object responsible for CSV file reading and writing. */
    private final TransactionFileHandler fileHandler = new TransactionFileHandler();

    /**
     * Adds a transaction to the budget.
     *
     * @param transaction transaction to add
     */
    public void addTransaction(Transaction transaction) {
        transactions.add(transaction);
    }

    /**
     * Returns a copy of the transaction list.
     *
     * <p>A copy is returned instead of the original list so outside classes cannot directly modify
     * the internal list.
     *
     * @return copy of the saved transactions
     */
    public List<Transaction> getTransactions() {
        return new ArrayList<>(transactions);
    }

    /**
     * Calculates the total amount of income transactions.
     *
     * @return total income
     */
    public double getTotalIncome() {
        return transactions.stream()
                .filter(transaction -> transaction.getType() == TransactionType.INCOME)
                .mapToDouble(Transaction::getAmount)
                .sum();
    }

    /**
     * Calculates the total amount of expense transactions.
     *
     * @return total expenses
     */
    public double getTotalExpenses() {
        return transactions.stream()
                .filter(transaction -> transaction.getType() == TransactionType.EXPENSE)
                .mapToDouble(Transaction::getAmount)
                .sum();
    }

    /**
     * Calculates the current balance.
     *
     * @return total income minus total expenses
     */
    public double getBalance() {
        return getTotalIncome() - getTotalExpenses();
    }

    /**
     * Saves all current transactions to a CSV file.
     *
     * @param filename file path to save to
     * @throws IOException if the file cannot be written
     */
    public void saveToFile(String filename) throws IOException {
        fileHandler.saveTransactions(filename, transactions);
    }

    /**
     * Loads transactions from a CSV file and replaces the current list.
     *
     * @param filename file path to load from
     * @throws IOException if the file cannot be read or has an invalid format
     */
    public void loadFromFile(String filename) throws IOException {
        transactions.clear();
        transactions.addAll(fileHandler.loadTransactions(filename));
    }
}
