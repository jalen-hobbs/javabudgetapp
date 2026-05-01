# Personal Budget Tracker

## Project Overview

Personal Budget Tracker is a JavaFX desktop application that allows users to record income and expense transactions. The goal of the project is to help users keep track of basic spending, income, and account balance information in a simple interface.

This project was created for my CSCI2830 project. It demonstrates multiple Java classes, JavaFX GUI screens, file input/output, exception handling, and unit testing.

## Features

- Add income or expense transactions
- View all saved transactions
- View a budget summary showing:
  - total income
  - total expenses
  - current balance
- Save transaction data to a CSV file
- Load saved transaction data when the application starts
- Handle invalid input and file errors with alert messages

## Project Structure

```text
javabudgetapp/
├── pom.xml
├── README.md
└── src/
    ├── main/
    │   └── java/
    │       └── edu/uno/csci2830/budget/
    │           ├── BudgetApp.java
    │           ├── BudgetManager.java
    │           ├── Transaction.java
    │           ├── TransactionFileHandler.java
    │           └── TransactionType.java
    └── test/
        └── java/
            └── edu/uno/csci2830/budget/
                ├── BasicUITest.java
                ├── BudgetManagerTest.java
                └── TransactionFileHandlerTest.java
```

## Main Classes

### `BudgetApp`
The JavaFX application class. It creates the GUI screens and handles navigation between the dashboard, add transaction screen, transaction list screen, and summary screen.

### `BudgetManager`
Stores the list of transactions and performs budget calculations, such as total income, total expenses, and current balance.

### `Transaction`
Represents one budget entry. Each transaction has a description, amount, date, category, and transaction type.

### `TransactionFileHandler`
Handles file input/output. It saves transactions to a CSV file and loads transactions from that file when the application starts.

### `TransactionType`
An enum used to identify whether a transaction is income or an expense.

## File I/O

The application saves data in a CSV file named:

```text
transactions.csv
```

Example CSV format:

```text
description,amount,date,category,type
Paycheck,500.0,2026-04-30,Income,INCOME
Lunch,12.5,2026-04-30,Dining,EXPENSE
```

If the file does not exist yet, the program starts with an empty transaction list. When a transaction is added, the list is saved back to the CSV file.

## How to Run the Application

From the project root directory, run:

```bash
mvn javafx:run
```

## How to Run Tests

From the project root directory, run:

```bash
mvn test
```

## Current Status

The current version includes the main GUI flow, basic transaction entry, CSV saving/loading, and a basic UI navigation test. Future improvements could include editing transactions, deleting transactions, category summaries, and stronger unit tests for file handling.
