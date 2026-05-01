package edu.uno.csci2830.budget;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.io.TempDir;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

/**
 * Unit tests for TransactionFileHandler.
 *
 * These tests verify file saving/loading behavior,
 * including handling invalid or missing files.
 */
public class TransactionFileHandlerTest {

    /**
     * Temporary directory provided by JUnit.
     * Ensures tests do not affect real files.
     */
    @TempDir
    Path tempDir;

    /**
     * Test saving transactions to a file and loading them back.
     */
    @Test
    public void testSaveAndLoadTransactions() throws IOException {
        TransactionFileHandler fileHandler = new TransactionFileHandler();
        Path file = tempDir.resolve("transactions.csv");

        List<Transaction> transactions = List.of(
                new Transaction("Paycheck", 500.00, "2026-04-30", "Income", TransactionType.INCOME),
                new Transaction("Groceries", 45.50, "2026-04-30", "Dining", TransactionType.EXPENSE)
        );

        // Save transactions
        fileHandler.saveTransactions(file.toString(), transactions);

        // Load them back
        List<Transaction> loaded = fileHandler.loadTransactions(file.toString());

        // Verify data integrity
        assertEquals(2, loaded.size());
        assertEquals("Paycheck", loaded.get(0).getDescription());
        assertEquals(500.00, loaded.get(0).getAmount(), 0.001);
        assertEquals(TransactionType.INCOME, loaded.get(0).getType());
    }

    /**
     * Test that loading a non-existent file returns an empty list.
     */
    @Test
    public void testLoadMissingFileReturnsEmptyList() throws IOException {
        TransactionFileHandler fileHandler = new TransactionFileHandler();
        Path missingFile = tempDir.resolve("missing.csv");

        List<Transaction> loaded = fileHandler.loadTransactions(missingFile.toString());

        assertTrue(loaded.isEmpty());
    }

    /**
     * Test that an invalid CSV format triggers an IOException.
     */
    @Test
    public void testInvalidCsvFormatThrowsIOException() throws IOException {
        TransactionFileHandler fileHandler = new TransactionFileHandler();
        Path file = tempDir.resolve("bad.csv");

        // Write malformed CSV data
        Files.writeString(file, "description,amount,date,category,type\nBad Row,12.00\n");

        assertThrows(IOException.class, () -> fileHandler.loadTransactions(file.toString()));
    }
}