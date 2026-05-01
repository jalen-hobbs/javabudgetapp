package edu.uno.csci2830.budget;

import javafx.stage.Stage;
import org.junit.jupiter.api.Test;
import org.testfx.framework.junit5.ApplicationTest;

import static org.testfx.api.FxAssert.verifyThat;
import static org.testfx.matcher.control.LabeledMatchers.hasText;

/**
 * UI test class using TestFX.
 *
 * These tests verify basic navigation between screens in the JavaFX application.
 * The goal is to ensure buttons correctly switch scenes.
 */
public class BasicUITest extends ApplicationTest {

    /**
     * Starts the JavaFX application before each test.
     */
    @Override
    public void start(Stage stage) {
        new BudgetApp().start(stage);
    }

    /**
     * Test navigating from Dashboard -> Add Transaction -> Dashboard.
     */
    @Test
    public void testNavigateToAddTransactionAndBack() {
        // Click "Add Transaction" button
        clickOn("#addButton");

        // Verify that Add Transaction screen is shown
        verifyThat("#addTitle", hasText("Add Transaction"));

        // Click back button
        clickOn("#addBackButton");

        // Verify we returned to dashboard
        verifyThat("#dashboardTitle", hasText("Personal Budget Tracker"));
    }

    /**
     * Test navigating from Dashboard -> View Transactions -> Dashboard.
     */
    @Test
    public void testNavigateToTransactionsAndBack() {
        // Click "View Transactions"
        clickOn("#viewButton");

        // Verify transactions screen is displayed
        verifyThat("#transactionsTitle", hasText("All Transactions"));

        // Go back
        clickOn("#transactionsBackButton");

        // Confirm dashboard is shown again
        verifyThat("#dashboardTitle", hasText("Personal Budget Tracker"));
    }

    /**
     * Test navigating from Dashboard -> Summary -> Dashboard.
     */
    @Test
    public void testNavigateToSummaryAndBack() {
        // Click "View Summary"
        clickOn("#summaryButton");

        // Verify summary screen appears
        verifyThat("#summaryTitle", hasText("Budget Summary"));

        // Go back
        clickOn("#summaryBackButton");

        // Confirm return to dashboard
        verifyThat("#dashboardTitle", hasText("Personal Budget Tracker"));
    }
}