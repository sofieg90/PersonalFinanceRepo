package commands;

import money.MoneyCheck;
import java.util.Calendar;

public class Income extends Command {
    private final MoneyCheck account;

    public Income(MoneyCheck moneyCheck) {
        this.account = moneyCheck;
    }

    @Override
    public void execute() {

        double incomeYear = account.getTotalForPeriod(java.util.Calendar.YEAR, "lön");
        double incomeMonth = account.getTotalForPeriod(java.util.Calendar.MONTH, "lön");
        double incomeWeek = account.getTotalForPeriod(java.util.Calendar.WEEK_OF_YEAR, "lön");
        double incomeDay = account.getTotalForPeriod(java.util.Calendar.DAY_OF_YEAR, "lön");

        System.out.println("Din inkomst årsvis: " + incomeYear + " kr.");
        System.out.println("Din inkomst månadsvis: " + incomeMonth + " kr.");
        System.out.println("Din inkomst veckovis: " + incomeWeek + " kr.");
        System.out.println("Din inkomst idag: " + incomeDay + " kr.");

    }
}
