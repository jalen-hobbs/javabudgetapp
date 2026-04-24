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
        Button viewButton = new Button("View Transactions");
        Button summaryButton = new Button("View Summary");
        Button exitButton = new Button("Exit");

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

    }

    private void showTransactionsScreen() {

    }

    private void showSummaryScreen() {

    }
}
