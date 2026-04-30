package edu.uno.csci2830.budget;

public class Transaction {

    private final String description;
    private final double amount;
    private final String date;
    private final String category;
    private final TransactionType type;

    public Transaction(String description, double amount, String date,
                       String category, TransactionType type) {
        this.description = description;
        this.amount = amount;
        this.date = date;
        this.category = category;
        this.type = type;
    }

    public String getDescription() {
        return description;
    }

    public double getAmount() {
        return amount;
    }

    public String getDate() {
        return date;
    }

    public String getCategory() {
        return category;
    }

    public TransactionType getType() {
        return type;
    }
}