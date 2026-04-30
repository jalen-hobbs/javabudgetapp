package edu.uno.csci2830.budget;

import java.io.*;
import java.util.ArrayList;
import java.util.List;

public class TransactionFileHandler {

    public void saveTransactions(String filename, List<Transaction> transactions)
            throws IOException {

        try (BufferedWriter writer = new BufferedWriter(new FileWriter(filename))) {
            for (Transaction t : transactions) {
                writer.write(t.getDescription() + "," +
                        t.getAmount() + "," +
                        t.getDate() + "," +
                        t.getCategory() + "," +
                        t.getType());
                writer.newLine();
            }
        }
    }

    public List<Transaction> loadTransactions(String filename)
            throws IOException {

        List<Transaction> list = new ArrayList<>();

        try (BufferedReader reader = new BufferedReader(new FileReader(filename))) {
            String line;

            while ((line = reader.readLine()) != null) {
                String[] parts = line.split(",");

                if (parts.length != 5) {
                    throw new IOException("Invalid file format");
                }

                list.add(new Transaction(
                        parts[0],
                        Double.parseDouble(parts[1]),
                        parts[2],
                        parts[3],
                        TransactionType.valueOf(parts[4])
                ));
            }
        }

        return list;
    }
}