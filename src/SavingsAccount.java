public class SavingsAccount implements BankAccount{
    private String name;
    private double amount;
    static private double interestRate = 10;

    public SavingsAccount(String name, double initialAmount){
        this.name = name;
        this.amount = initialAmount;
    }


    public static String getInterestRate(){
        return (interestRate + "%");
    }

    @Override
    public String getAccountName() {
        return (this.name);
    }

    @Override
    public String accountType() {
        return ("Savings Account");
    }

    @Override
    public double checkBalance() {
        return this.amount;
    }

    @Override
    public boolean deposit(double value, String color) {
        this.amount += value;
        System.out.println(color + "Deposited " + value + " successfully in "
                + this.getAccountName() + "'s account." + "\u001B[0m");
        return true;
    }

    @Override
    public boolean withdraw(double value, String color) {
        if (amount >= value) {
            this.amount -= value;
            System.out.println(color + value + " withdrawn from " + this.getAccountName() +
                    "'s savings account." + "\u001B[0m");
            return true;
        } else {
            System.out.println(color + "Not enough money in " + this.getAccountName() +
                    "'s the savings account." + "\u001B[0m");
            return false;
        }

    }

    public static final String ANSI_RESET = "\u001B[0m";
    public static final String ANSI_RED = "\u001B[31m";
    public static final String ANSI_BLUE = "\u001B[34m";
    public static final String ANSI_GREEN = "\u001B[32m";
    public static final String ANSI_YELLOW = "\u001B[33m";
    public static final String ANSI_PURPLE = "\u001B[35m";
    public static final String ANSI_CYAN = "\u001B[36m";
    public static final String[] colors = new String[]{ANSI_RED, ANSI_BLUE,
            ANSI_GREEN, ANSI_PURPLE, ANSI_CYAN};

    public boolean convertCurrencyTransaction(int threadID, String currency,
                                           double value, String transactionType){
        String color = this.colors[threadID-1];
        System.out.print(color + this.getAccountName() + " ");
        switch(currency){
            case "USD":
                value = value*70;
                System.out.println(color + currency + " converted to " + value +" INR." + ANSI_RESET);
                break;
            case "JPY":
                value = value*0.7;
                System.out.println(color + currency + " converted to " + value +" INR." + ANSI_RESET);
                break;
        }

        switch(transactionType){
            case "Withdraw":
                return (this.withdraw(value, color));

            case "Deposit":
                return (this.deposit(value, color));
        }
        System.out.print("\u001B[0m");
        return false;
    }


}
