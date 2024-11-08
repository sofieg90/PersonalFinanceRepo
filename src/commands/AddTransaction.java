package commands;

import money.MoneyCheck;

import java.util.Date;
import java.util.Scanner;

public class AddTransaction extends Command {
    private MoneyCheck account;


    public AddTransaction(MoneyCheck moneyCheck) {

        this.account = moneyCheck;
    }

    @Override
    public void execute() {
        Scanner scan = new Scanner(System.in);
        System.out.println("Vill du lägga till LÖN eller KÖP?");
        String type = scan.nextLine();

        if (!type.equalsIgnoreCase("lön") && !type.equalsIgnoreCase("köp")) {
            System.out.println("Du kan endast skriva LÖN eller KÖP. ");
            return;
        }

        System.out.println("Ange beloppet: ");
        double ammount = scan.nextDouble();
        account.addTransaction(ammount, new Date(), type);
        account.saveTransactionsToFile("transaction.txt");

    }
}
