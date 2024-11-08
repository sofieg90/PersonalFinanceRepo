package money;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

public class Transaction {
    private static int counter = 0;
    private final int id;
    private final double amount;
    private final String type;
    private final Date date;

    public Transaction(String type, double amount, Date date) {
        this.id = ++counter;
        this.amount = amount;
        this.type = type;
        this.date = date;
    }

    public Transaction(int id, double amount, String date, String type) throws ParseException {
        this.id = id;
        this.amount = amount;
        this.type = type;
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd"); // Format för datum
        this.date = sdf.parse(date);
    }

    public int getId() {
        return id;
    }

    public double getAmount() {
        return amount;
    }

    public String getDateAsString() {
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
        return sdf.format(date);
    }

    public String getType() {
        return type;
    }


    public Date getDate() {
        return date;
    }
}
