public interface BankAccount {
    String getAccountName();
    String accountType();
    double checkBalance();
    boolean deposit(double value, String color);
    boolean withdraw(double value, String color);
}
