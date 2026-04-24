package edu.uno.csci2830.budget;

import javafx.stage.Stage;
import org.junit.jupiter.api.Test;
import org.testfx.framework.junit5.ApplicationTest;

import static org.testfx.api.FxAssert.verifyThat;
import static org.testfx.matcher.control.LabeledMatchers.hasText;

public class BasicUITest extends ApplicationTest {

    @Override
    public void start(Stage stage) throws Exception {
        new BudgetApp().start(stage);
    }

    @Test
    public void testNavigateToAddTransactionAndBack() {
        // Click "Add Transaction"
        clickOn("#addButton");

        // Verify we are on Add Transaction screen
        verifyThat(".label", hasText("Add Transaction"));

        // Click "Back"
        clickOn("#backButton");

        // Verify we are back on dashboard
        verifyThat(".label", hasText("Personal Budget Tracker"));
    }
}