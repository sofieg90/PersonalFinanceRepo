package test;

import money.FileTransactionSaver;
import money.MoneyCheck;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.Calendar;
import java.util.Date;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class TestPersonalFinance {
    private MoneyCheck moneyCheck;

    @BeforeEach
    public void setUp() {
        moneyCheck = new MoneyCheck(new FileTransactionSaver()); // Skapa ett nytt MoneyCheck-objekt före varje test
        MoneyCheck.transactions.clear(); //Rensar innan nästa test
    }

    @Test
    public void testAddMoney() { //Testa lägga in lön på kontot
        moneyCheck.addTransaction(5000, new Date(), "lön");
        int expected = 5000;
        double actual = moneyCheck.getBalance();
        assertEquals(expected, actual);
    }

    @Test
    public void testAddMoneyAndBuy() { //Testa lägga in lön och minus för köp
        moneyCheck.addTransaction(5000, new Date(), "lön");
        moneyCheck.addTransaction(500, new Date(), "köp");
        int expected = 4500;
        double actual = moneyCheck.getBalance();
        assertEquals(expected, actual);
    }

    @Test
    public void testDeleteTransaction() { //Testa radera en transaktion
        moneyCheck.addTransaction(1000, new Date(), "lön");
        moneyCheck.deleteTransactionFromFile(1000);
        int expected = 0;
        double actual = moneyCheck.getBalance();
        assertEquals(expected, actual);
    }

    @Test
    public void testGetYear() { //Testar årslön
        Calendar cal = Calendar.getInstance();
        cal.set(Calendar.YEAR, 2024); //Datumklassen testar år 2024
        moneyCheck.addTransaction(25000, cal.getTime(), "lön"); //Kopplar lönen till året
        int expected = 25000;
        double actual = moneyCheck.getTotalForPeriod(Calendar.YEAR, "lön");
        assertEquals(expected, actual);
    }

    @Test
    public void testSaveLoadTransactions() { //Testar att spara och ladda transaktion
        moneyCheck.addTransaction(2500 + 500, new Date(), "lön");
        moneyCheck.saveTransactionsToFile("test_transactions.txt"); //sparar lönen till filen
        moneyCheck.loadTransactionsFromFile("test_transactions.txt"); //rensar tidigare transaktioner från fil
        int expected = 3000;
        double actual = moneyCheck.getBalance();
        assertEquals(expected, actual);
    }
}

