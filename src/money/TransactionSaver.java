package money;

public interface TransactionSaver {
    void saveTransaction(Transaction transaction);

    void deleteTransaction(int belopp);
}