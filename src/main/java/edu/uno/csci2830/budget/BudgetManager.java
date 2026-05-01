package edu.uno.csci2830.budget;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class BudgetManager {

    private final List<Transaction> transactions = new ArrayList<>();

    public void addTransaction(Transaction t) {
        transactions.add(t);
    }

    public List<Transaction> getTransactions() {
        return new ArrayList<>(transactions);
    }

    public double getTotalIncome() {
        return transactions.stream()
                .filter(t -> t.getType() == TransactionType.INCOME)
                .mapToDouble(Transaction::getAmount)
                .sum();
    }

    public double getTotalExpenses() {
        return transactions.stream()
                .filter(t -> t.getType() == TransactionType.EXPENSE)
                .mapToDouble(Transaction::getAmount)
                .sum();
    }

    public double getBalance() {
        return getTotalIncome() - getTotalExpenses();
    }

    private final TransactionFileHandler fileHandler = new TransactionFileHandler();

    public void saveToFile(String filename) throws IOException {
        fileHandler.saveTransactions(filename, transactions);
    }

    public void loadFromFile(String filename) throws IOException {
        transactions.clear();
        transactions.addAll(fileHandler.loadTransactions(filename));
    }
}