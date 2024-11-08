package money;

import java.io.*;
import java.text.ParseException;
import java.util.ArrayList;
import java.util.List;

public class FileTransactionSaver implements TransactionSaver {
    private static final String FILE_NAME = "transaction.txt";

    @Override
    public void saveTransaction(Transaction transaction) {
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(FILE_NAME, true))) {
            writer.write(transaction.getId() + "," + transaction.getAmount() + "," +
                    transaction.getDateAsString() + "," + transaction.getType());
            writer.newLine();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void deleteTransaction(int belopp) {
        List<Transaction> transactions = new ArrayList<>();

        try (BufferedReader reader = new BufferedReader(new FileReader(FILE_NAME))) {
            String line;
            while ((line = reader.readLine()) != null) {
                String[] parts = line.split(",");
                if (parts.length != 4) {
                    continue; // Hoppa över rader som inte följer rätt format
                }

                try {
                    int transactionId = Integer.parseInt(parts[0].trim());
                    int amount = Integer.parseInt(parts[1].trim());
                    String date = parts[2].trim();
                    String type = parts[3].trim();

                    // Lägg till transaktioner som inte matchar beloppet som ska tas bort
                    if (amount != belopp) {
                        transactions.add(new Transaction(transactionId, amount, date, type));
                    }
                } catch (NumberFormatException | ParseException e) {
                    System.out.println("Fel vid parsning av rad: " + line);
                    e.printStackTrace();
                }
            }
        } catch (IOException e) {
            System.out.println("Fel vid läsning av filen.");
            e.printStackTrace();
        }

        // Skriv om filen med de kvarvarande transaktionerna
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(FILE_NAME))) {
            for (Transaction transaction : transactions) {
                writer.write(transaction.getId() + "," + transaction.getAmount() + "," +
                        transaction.getDateAsString() + "," + transaction.getType());
                writer.newLine();
            }
        } catch (IOException e) {
            System.out.println("Fel vid skrivning till filen.");
            e.printStackTrace();
        }
    }
}