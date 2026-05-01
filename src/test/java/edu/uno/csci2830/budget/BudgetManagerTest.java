package edu.uno.csci2830.budget;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

/**
 * Unit tests for BudgetManager.
 *
 * These tests verify that transactions are stored correctly
 * and that financial calculations (income, expenses, balance) are accurate.
 */
public class BudgetManagerTest {

    /**
     * Test that adding a transaction increases the list size.
     */
    @Test
    public void testAddTransaction() {
        BudgetManager manager = new BudgetManager();

        manager.addTransaction(new Transaction(
                "Groceries", 45.50, "2026-04-30", "Dining", TransactionType.EXPENSE));

        assertEquals(1, manager.getTransactions().size());
    }

    /**
     * Test that total income is calculated correctly.
     */
    @Test
    public void testGetTotalIncome() {
        BudgetManager manager = new BudgetManager();

        // Add multiple income and expense transactions
        manager.addTransaction(new Transaction(
                "Paycheck", 500.00, "2026-04-30", "Income", TransactionType.INCOME));
        manager.addTransaction(new Transaction(
                "Gift", 25.00, "2026-04-30", "Income", TransactionType.INCOME));
        manager.addTransaction(new Transaction(
                "Lunch", 12.00, "2026-04-30", "Dining", TransactionType.EXPENSE));

        // Only income should be counted
        assertEquals(525.00, manager.getTotalIncome(), 0.001);
    }

    /**
     * Test that total expenses are calculated correctly.
     */
    @Test
    public void testGetTotalExpenses() {
        BudgetManager manager = new BudgetManager();

        manager.addTransaction(new Transaction(
                "Lunch", 12.00, "2026-04-30", "Dining", TransactionType.EXPENSE));
        manager.addTransaction(new Transaction(
                "Gas", 35.00, "2026-04-30", "Transportation", TransactionType.EXPENSE));

        assertEquals(47.00, manager.getTotalExpenses(), 0.001);
    }

    /**
     * Test that balance (income - expenses) is correct.
     */
    @Test
    public void testGetBalance() {
        BudgetManager manager = new BudgetManager();

        manager.addTransaction(new Transaction(
                "Paycheck", 500.00, "2026-04-30", "Income", TransactionType.INCOME));
        manager.addTransaction(new Transaction(
                "Groceries", 45.50, "2026-04-30", "Dining", TransactionType.EXPENSE));

        assertEquals(454.50, manager.getBalance(), 0.001);
    }
}