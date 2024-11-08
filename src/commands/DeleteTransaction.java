package commands;

import money.MoneyCheck;

import java.util.Scanner;

public class DeleteTransaction extends Command {
    private final MoneyCheck account;

    public DeleteTransaction(MoneyCheck moneyCheck) {
        this.account = moneyCheck;
    }

    @Override
    public void execute() {
        Scanner scan = new Scanner(System.in);
        System.out.println("Ange beloppet på transaktionen som du vill radera: ");
        int belopp = scan.nextInt();

        account.deleteTransactionFromFile(belopp);
        account.saveTransactionsToFile("transaction.txt");
    }

}
