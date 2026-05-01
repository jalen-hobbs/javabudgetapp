package edu.uno.csci2830.budget;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

/**
 * Handles saving and loading transaction data using a CSV file.
 */
public class TransactionFileHandler {

    /** Header row written at the top of the CSV file. */
    private static final String CSV_HEADER = "description,amount,date,category,type";

    /**
     * Saves a list of transactions to a CSV file.
     *
     * @param filename file path to write to
     * @param transactions transactions to save
     * @throws IOException if the file cannot be written
     */
    public void saveTransactions(String filename, List<Transaction> transactions) throws IOException {
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(filename))) {
            writer.write(CSV_HEADER);
            writer.newLine();

            for (Transaction transaction : transactions) {
                writer.write(formatTransactionAsCsvLine(transaction));
                writer.newLine();
            }
        }
    }

    /**
     * Loads transactions from a CSV file.
     *
     * <p>If the file does not exist yet, an empty list is returned. This allows the app to start
     * normally the first time it is opened.
     *
     * @param filename file path to read from
     * @return list of loaded transactions
     * @throws IOException if the file cannot be read or has an invalid CSV format
     */
    public List<Transaction> loadTransactions(String filename) throws IOException {
        List<Transaction> transactions = new ArrayList<>();
        File file = new File(filename);

        if (!file.exists()) {
            return transactions;
        }

        try (BufferedReader reader = new BufferedReader(new FileReader(file))) {
            reader.readLine(); // Skip the header row.

            String line;
            while ((line = reader.readLine()) != null) {
                transactions.add(parseTransaction(line));
            }
        }

        return transactions;
    }

    /**
     * Converts a transaction object into one CSV row.
     *
     * @param transaction transaction to format
     * @return CSV row text
     */
    private String formatTransactionAsCsvLine(Transaction transaction) {
        return transaction.getDescription()
                + ","
                + transaction.getAmount()
                + ","
                + transaction.getDate()
                + ","
                + transaction.getCategory()
                + ","
                + transaction.getType();
    }

    /**
     * Converts one CSV row into a Transaction object.
     *
     * @param line CSV row text
     * @return transaction created from the CSV row
     * @throws IOException if the row does not contain the expected five fields
     */
    private Transaction parseTransaction(String line) throws IOException {
        String[] parts = line.split(",");

        if (parts.length != 5) {
            throw new IOException("Invalid CSV format.");
        }

        return new Transaction(
                parts[0],
                Double.parseDouble(parts[1]),
                parts[2],
                parts[3],
                TransactionType.valueOf(parts[4]));
    }
}
