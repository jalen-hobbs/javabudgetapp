package edu.uno.csci2830.budget;

import java.io.*;
import java.util.ArrayList;
import java.util.List;

public class TransactionFileHandler {

    public void saveTransactions(String filename, List<Transaction> transactions)
            throws IOException {

        try (BufferedWriter writer = new BufferedWriter(new FileWriter(filename))) {
            writer.write("description,amount,date,category,type");
            writer.newLine();

            for (Transaction transaction : transactions) {
                writer.write(transaction.getDescription() + ","
                        + transaction.getAmount() + ","
                        + transaction.getDate() + ","
                        + transaction.getCategory() + ","
                        + transaction.getType());
                writer.newLine();
            }
        }
    }

    public List<Transaction> loadTransactions(String filename)
            throws IOException {

        List<Transaction> transactions = new ArrayList<>();
        File file = new File(filename);

        if (!file.exists()) {
            return transactions;
        }

        try (BufferedReader reader = new BufferedReader(new FileReader(file))) {
            String line = reader.readLine(); // skip header

            while ((line = reader.readLine()) != null) {
                String[] parts = line.split(",");

                if (parts.length != 5) {
                    throw new IOException("Invalid CSV format.");
                }

                transactions.add(new Transaction(
                        parts[0],
                        Double.parseDouble(parts[1]),
                        parts[2],
                        parts[3],
                        TransactionType.valueOf(parts[4])
                ));
            }
        }

        return transactions;
    }
}