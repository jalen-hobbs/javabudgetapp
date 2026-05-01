package edu.uno.csci2830.budget;

import java.io.IOException;
import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.ListView;
import javafx.scene.control.TextField;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

/**
 * JavaFX application class for the Personal Budget Tracker.
 *
 * <p>This class is responsible for building the user interface, switching between screens,
 * handling button clicks, and showing alert messages when an error or confirmation occurs.
 */
public class BudgetApp extends Application {

    /** File used to store saved transaction data. */
    private static final String DATA_FILE = "transactions.csv";

    /** Budget manager that stores transactions and performs calculations. */
    private final BudgetManager manager = new BudgetManager();

    /** Main window used by the JavaFX application. */
    private Stage stage;

    /**
     * Starts the JavaFX application and loads any previously saved transactions.
     *
     * @param stage the primary application window
     */
    @Override
    public void start(Stage stage) {
        this.stage = stage;
        stage.setTitle("Personal Budget Tracker");

        try {
            manager.loadFromFile(DATA_FILE);
        } catch (IOException e) {
            showAlert("Load Error", "Could not load saved transactions.");
        }

        showDashboard();
    }

    /** Displays the main dashboard screen with navigation buttons. */
    private void showDashboard() {
        Label title = new Label("Personal Budget Tracker");
        title.setId("dashboardTitle");

        Button addButton = new Button("Add Transaction");
        addButton.setId("addButton");

        Button viewButton = new Button("View Transactions");
        viewButton.setId("viewButton");

        Button summaryButton = new Button("View Summary");
        summaryButton.setId("summaryButton");

        Button exitButton = new Button("Exit");
        exitButton.setId("exitButton");

        addButton.setOnAction(e -> showAddTransactionScreen());
        viewButton.setOnAction(e -> showTransactionsScreen());
        summaryButton.setOnAction(e -> showSummaryScreen());

        // Save data before closing so the user's changes persist between sessions.
        exitButton.setOnAction(e -> {
            try {
                manager.saveToFile(DATA_FILE);
            } catch (IOException ex) {
                showAlert("Save Error", "Could not save transactions.");
            }

            stage.close();
        });

        VBox layout = new VBox(15, title, addButton, viewButton, summaryButton, exitButton);
        layout.setStyle("-fx-padding: 20; -fx-alignment: center;");

        stage.setScene(new Scene(layout, 400, 300));
        stage.show();
    }

    /** Displays the form used to create a new income or expense transaction. */
    private void showAddTransactionScreen() {
        Label title = new Label("Add Transaction");
        title.setId("addTitle");

        TextField descriptionField = new TextField();
        descriptionField.setPromptText("Description");

        TextField amountField = new TextField();
        amountField.setPromptText("Amount");

        TextField dateField = new TextField();
        dateField.setPromptText("Date");

        ComboBox<String> categoryBox = new ComboBox<>();
        categoryBox.getItems().addAll(
                "Dining", "Transportation", "Bills", "Entertainment", "Income", "Other");
        categoryBox.setPromptText("Category");

        ComboBox<TransactionType> typeBox = new ComboBox<>();
        typeBox.getItems().addAll(TransactionType.INCOME, TransactionType.EXPENSE);
        typeBox.setPromptText("Type");

        Button submitButton = new Button("Submit");
        submitButton.setId("submitButton");

        Button backButton = new Button("Back");
        backButton.setId("addBackButton");

        submitButton.setOnAction(e -> {
            try {
                String description = descriptionField.getText();
                double amount = Double.parseDouble(amountField.getText());
                String date = dateField.getText();
                String category = categoryBox.getValue();
                TransactionType type = typeBox.getValue();

                validateTransactionInput(description, date, category, type);

                Transaction transaction = new Transaction(description, amount, date, category, type);
                manager.addTransaction(transaction);
                manager.saveToFile(DATA_FILE);

                Alert alert = new Alert(Alert.AlertType.INFORMATION);
                alert.setTitle("Success");
                alert.setHeaderText(null);
                alert.setContentText("Transaction added successfully.");

                // Return to the dashboard after the user closes the confirmation dialog.
                alert.setOnHidden(event -> showDashboard());
                alert.showAndWait();
            } catch (NumberFormatException ex) {
                showAlert("Invalid Amount", "Amount must be a valid number.");
            } catch (IllegalArgumentException ex) {
                showAlert("Invalid Input", ex.getMessage());
            } catch (IOException ex) {
                showAlert("Save Error", "Could not save transaction.");
            }
        });

        backButton.setOnAction(e -> showDashboard());

        VBox layout = new VBox(
                10,
                title,
                descriptionField,
                amountField,
                dateField,
                categoryBox,
                typeBox,
                submitButton,
                backButton);

        layout.setStyle("-fx-padding: 20;");
        stage.setScene(new Scene(layout, 400, 400));
    }

    /** Displays all transactions currently stored by the budget manager. */
    private void showTransactionsScreen() {
        Label title = new Label("All Transactions");
        title.setId("transactionsTitle");

        ListView<String> transactionList = new ListView<>();

        for (Transaction transaction : manager.getTransactions()) {
            transactionList.getItems().add(formatTransactionForDisplay(transaction));
        }

        Button backButton = new Button("Back to Dashboard");
        backButton.setId("transactionsBackButton");
        backButton.setOnAction(e -> showDashboard());

        VBox layout = new VBox(10, title, transactionList, backButton);
        layout.setStyle("-fx-padding: 20;");

        stage.setScene(new Scene(layout, 600, 400));
    }

    /** Displays total income, total expenses, and the current balance. */
    private void showSummaryScreen() {
        Label title = new Label("Budget Summary");
        title.setId("summaryTitle");

        Label incomeLabel = new Label("Total Income: $" + String.format("%.2f", manager.getTotalIncome()));
        Label expenseLabel = new Label(
                "Total Expenses: $" + String.format("%.2f", manager.getTotalExpenses()));
        Label balanceLabel = new Label("Current Balance: $" + String.format("%.2f", manager.getBalance()));

        Button backButton = new Button("Back to Dashboard");
        backButton.setId("summaryBackButton");
        backButton.setOnAction(e -> showDashboard());

        VBox layout = new VBox(15, title, incomeLabel, expenseLabel, balanceLabel, backButton);
        layout.setStyle("-fx-padding: 20; -fx-alignment: center;");

        stage.setScene(new Scene(layout, 400, 300));
    }

    /**
     * Checks that the required transaction fields have been filled in.
     *
     * @param description transaction description entered by the user
     * @param date transaction date entered by the user
     * @param category transaction category selected by the user
     * @param type income or expense type selected by the user
     * @throws IllegalArgumentException if any required input is missing
     */
    private void validateTransactionInput(
            String description, String date, String category, TransactionType type) {
        if (description.isBlank() || date.isBlank() || category == null || type == null) {
            throw new IllegalArgumentException("Please fill out all fields.");
        }
    }

    /**
     * Converts a transaction into a readable line of text for the transaction list screen.
     *
     * @param transaction the transaction to format
     * @return formatted transaction text
     */
    private String formatTransactionForDisplay(Transaction transaction) {
        return transaction.getDate()
                + " | "
                + transaction.getDescription()
                + " | "
                + transaction.getCategory()
                + " | "
                + transaction.getType()
                + " | $"
                + String.format("%.2f", transaction.getAmount());
    }

    /**
     * Shows a basic information alert to the user.
     *
     * @param title alert window title
     * @param message message shown inside the alert
     */
    private void showAlert(String title, String message) {
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle(title);
        alert.setHeaderText(null);
        alert.setContentText(message);
        alert.showAndWait();
    }

    /**
     * Launches the JavaFX application.
     *
     * @param args command-line arguments
     */
    public static void main(String[] args) {
        launch(args);
    }
}
