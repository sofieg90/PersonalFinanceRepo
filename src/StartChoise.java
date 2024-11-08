import commands.*;
import money.FileTransactionSaver;
import money.MoneyCheck;

import java.util.Scanner;

public class StartChoise {
    static Scanner scan = new Scanner(System.in);
    private static final MoneyCheck account = new MoneyCheck(new FileTransactionSaver());
    private static final CommandManager commandManager = new CommandManager();

    public static void start() {
        account.loadTransactionsFromFile("transaction.txt"); //Laddar transaktioner från filen filename

        commandManager.registerCommand(1, new AddTransaction(account));
        commandManager.registerCommand(2, new DeleteTransaction(account));
        commandManager.registerCommand(3, new ViewBalance(account));
        commandManager.registerCommand(4, new Expends(account));
        commandManager.registerCommand(5, new Income(account));

        while (true) {
            System.out.println("Vad vill du göra? Välj en siffra: ");
            System.out.println("1. Lägga till en transaktion");
            System.out.println("2. Radera en transaktion");
            System.out.println("3. Kontoinformation");
            System.out.println("4. Visa mina utgifter");
            System.out.println("5. Visa mina inkomster");
            System.out.println("6. Avsluta");
            int num = scan.nextInt();

            if (num == 6) {
                account.saveTransactionsToFile("transaction.txt");
                System.out.println("Programmet avslutas. Ha en fin dag!");
                break;
            }
            commandManager.executeCommand(num); // Kör kommandot baserat på användarens val
        }
        scan.close(); // Stäng scannern när loopen är klar
    }
}