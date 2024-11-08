package commands;

import money.MoneyCheck;

import java.util.Calendar;

public class Expends extends Command {
    private final MoneyCheck account;

    public Expends(MoneyCheck moneyCheck) {
        this.account = moneyCheck;
    }

    @Override
    public void execute() {
        double totalYear = account.getTotalForPeriod(Calendar.YEAR, "KÖP");
        double totalMonth = account.getTotalForPeriod(Calendar.MONTH, "KÖP");
        double totalWeek = account.getTotalForPeriod(Calendar.WEEK_OF_YEAR, "KÖP");
        double totalDay = account.getTotalForPeriod(Calendar.DAY_OF_YEAR, "KÖP");

        System.out.println("Det här året har du spenderat: " + totalYear + " kr.");
        System.out.println("Den här månaden har du spenderat: " + totalMonth + " kr.");
        System.out.println("Den här veckan har du spenderat: " + totalWeek + " kr.");
        System.out.println("Idag har du spenderat: " + totalDay + " kr.");

    }
}
