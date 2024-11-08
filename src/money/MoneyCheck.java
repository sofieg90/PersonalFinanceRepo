package money;

import java.io.*;
import java.text.SimpleDateFormat;
import java.util.*;
import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;

public class MoneyCheck {
    public static List<Transaction> transactions = new ArrayList<>(); // Lista för att spara alla uttag
    private double balance;
    private final TransactionSaver transactionSaver;

    public MoneyCheck(TransactionSaver transactionSaver) {
        this.transactionSaver = transactionSaver;
    }

    public void addTransaction(double amount, Date date, String type) {
        Transaction transaction = new Transaction(type, amount, date);
        System.out.println("Du har lagt till: " + type + " på " + amount + ".");

        // Spara transaktionen
        transactionSaver.saveTransaction(transaction);

        transactions.add(transaction);
        balance += (type.equalsIgnoreCase("lön") ? amount : -amount); // Om det är lön, öka saldo, annars minska
    }

    public void deleteTransactionFromFile(int belopp) {
        transactionSaver.deleteTransaction(belopp); //denna raden får programmet att krasha!!
        boolean found = false;
        Iterator<Transaction> iterator = transactions.iterator();

        while (iterator.hasNext()) {
            Transaction t = iterator.next();
            if (t.getAmount() == belopp) {
              iterator.remove();
                balance -= belopp;
                found = true;
                System.out.println("Transaktionen har raderats.");
                break;
            }
        }
        if (!found) {
            System.out.println("Ingen transaktion med detta belopp hittades.");
        }
    }


    public double getBalance() { // Visa saldo
        balance = 0;
        for (Transaction i : transactions) {
            if (i.getType().equalsIgnoreCase("lön")) {
                balance += i.getAmount();
            } else {
                balance -= i.getAmount();
            }
        }
        return balance;
    }

    public double getTotalForPeriod(int calendarField, String type) {
        Calendar cal = Calendar.getInstance();
        Calendar now = Calendar.getInstance();
        double total = 0;

        for (Transaction i : transactions) {
            if (!i.getType().equalsIgnoreCase(type)) continue;  // Fortsätt om typen inte matchar

            cal.setTime(i.getDate());

            boolean match = false;
            switch (calendarField) {
                case Calendar.YEAR:
                    match = (cal.get(Calendar.YEAR) == now.get(Calendar.YEAR));
                    break;
                case Calendar.MONTH:
                    match = (cal.get(Calendar.YEAR) == now.get(Calendar.YEAR) &&
                            cal.get(Calendar.MONTH) == now.get(Calendar.MONTH));
                    break;
                case Calendar.WEEK_OF_YEAR:
                    match = (cal.get(Calendar.YEAR) == now.get(Calendar.YEAR) &&
                            cal.get(Calendar.WEEK_OF_YEAR) == now.get(Calendar.WEEK_OF_YEAR));
                    break;
                case Calendar.DAY_OF_YEAR:
                    match = (cal.get(Calendar.YEAR) == now.get(Calendar.YEAR) &&
                            cal.get(Calendar.DAY_OF_YEAR) == now.get(Calendar.DAY_OF_YEAR));
                    break;
            }
            if (match) {
                total += i.getAmount();
            }
        }
        return total;
    }

    public void saveTransactionsToFile(String fileName) {
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(fileName))) { //Öppna filen fileName rad för rad med bufferedReader:
            SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd");
            for (Transaction t : transactions) { //Går igenom hela listan med transaktioner
                String formattedDate = formatter.format(t.getDate());
                writer.write(t.getAmount() + "," + formattedDate + "," + t.getType());
                writer.newLine(); //Gör en ny rad efter varje transaiton
            }
            System.out.println("Transaktionen har sparats till filen.");
        } catch (IOException e) {
            System.out.println("Transaktionen kunde inte sparas: " + e.getMessage());
        }
    }

    public void loadTransactionsFromFile(String fileName) {
        transactions.clear(); // Rensa tidigare transaktioner för att undvika dubblering

        try (BufferedReader reader = new BufferedReader(new FileReader(fileName))) { //Öppna filen fileName rad för rad med bufferedReader:
            String line;
            SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd"); //Skriv ut datumet i rätt format
            while ((line = reader.readLine()) != null) { //Läser filen rad för rad tills det tar slut
                String[] data = line.split(","); //delar upp datumet med kommatecken
                double amount = Double.parseDouble(data[0]);
                Date date = formatter.parse(data[1]);
                String type = data[2];
                //Formaterar summa, datum och typ(lön lr köp)

                transactions.add(new Transaction(type, amount, date));
                if (type.equalsIgnoreCase("lön")) {
                    balance += amount; // Lägg till beloppet om det är lön
                } else {
                    balance -= amount; // Dra av beloppet om det är köp
                }
            }
            System.out.println("Transaktioner har laddats från filen.");
        } catch (Exception e) {
            System.out.println("Transaktionen misslyckades tyvärr! " + e.getMessage());
        }
    }
}

