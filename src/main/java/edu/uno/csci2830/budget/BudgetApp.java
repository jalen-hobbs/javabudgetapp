package edu.uno.csci2830.budget;

import javafx.application.Application;
import javafx.collections.FXCollections;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

public class BudgetApp extends Application {

    private final BudgetManager manager = new BudgetManager();
    private Stage stage;

    @Override
    public void start(Stage stage) {
        this.stage = stage;
        stage.setTitle("Personal Budget Tracker");
        showDashboard();
    }

    private void showDashboard() {
        Label title = new Label("Personal Budget Tracker");

        Button addButton = new Button("Add Transaction");
        addButton.setId("addButton");
        Button viewButton = new Button("View Transactions");
        addButton.setId("viewButton");
        Button summaryButton = new Button("View Summary");
        summaryButton.setId("summaryButton");
        Button exitButton = new Button("Exit");
        addButton.setId("addButton");

        addButton.setOnAction(e -> showAddTransactionScreen());
        viewButton.setOnAction(e -> showTransactionsScreen());
        summaryButton.setOnAction(e -> showSummaryScreen());
        exitButton.setOnAction(e -> stage.close());

        VBox layout = new VBox(15, title, addButton, viewButton, summaryButton, exitButton);
        layout.setStyle("-fx-padding: 20; -fx-alignment: center;");

        stage.setScene(new Scene(layout, 400, 300));
        stage.show();
    }

    private void showAddTransactionScreen() {
        Label title = new Label("Add Transaction");

        TextField descriptionField = new TextField();
        descriptionField.setPromptText("Description");

        TextField amountField = new TextField();
        amountField.setPromptText("Amount");

        TextField dateField = new TextField();
        dateField.setPromptText("Date");

        ComboBox<String> categoryBox = new ComboBox<>();
        categoryBox.getItems().addAll("Dining", "Transportation", "Bills", "Entertainment", "Income", "Other");
        categoryBox.setPromptText("Category");

        ComboBox<TransactionType> typeBox = new ComboBox<>();
        typeBox.getItems().addAll(TransactionType.INCOME, TransactionType.EXPENSE);
        typeBox.setPromptText("Type");

        Button submitButton = new Button("Submit");
        submitButton.setId("submitButton");
        Button backButton = new Button("Back");
        backButton.setId("backButton");

        submitButton.setOnAction(e -> {
            try {
                String description = descriptionField.getText();
                double amount = Double.parseDouble(amountField.getText());
                String date = dateField.getText();
                String category = categoryBox.getValue();
                TransactionType type = typeBox.getValue();

                if (description.isBlank() || date.isBlank() || category == null || type == null) {
                    throw new IllegalArgumentException("Please fill out all fields.");
                }

                Transaction transaction = new Transaction(description, amount, date, category, type);
                manager.addTransaction(transaction);

                showAlert("Success", "Transaction added successfully!");
                showDashboard();
            } catch (NumberFormatException ex) {
                showAlert("Invalid Amount", "Amount must be a valid number.");
            } catch (IllegalArgumentException ex) {
                showAlert("Invalid Input", ex.getMessage());
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
                backButton
        );

        layout.setStyle("-fx-padding: 20;");
        stage.setScene(new Scene(layout, 400, 400));
    }

    private void showTransactionsScreen() {
        //TODO: Develop screen once more transaction logic is implemented.
    }

    private void showSummaryScreen() {
        //TODO: Develop screen once more transaction logic is implemented.
    }

    private void showAlert(String title, String message) {
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle(title);
        alert.setHeaderText(null);
        alert.setContentText(message);
        alert.showAndWait();
    }

    public static void main(String[] args) {
        launch(args);
    }
}
