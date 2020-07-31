import java.util.concurrent.TimeUnit;
import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.Lock;

public class TransactionsRunnable implements Runnable{
    private SavingsAccount fromAccount;
    private SavingsAccount toAccount;
    private int threadID;
    private String currency;
    private double value;
    private Lock transactionLock;
    private Condition conditionLock;
    public TransactionsRunnable(SavingsAccount from, SavingsAccount to, int id, String cur,
                                double amt, Lock lock){
        this.fromAccount = from;
        this.toAccount = to;
        this.threadID = id;
        this.currency = cur;
        this.value = amt;
        this.transactionLock = lock;
        this.conditionLock = lock.newCondition();
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
    @Override
    public void run() {
        System.out.print(colors[threadID-1]);
        transactionLock.lock();
        System.out.println("Initiated Transaction of " + this.value + " " + this.currency + " "
                + fromAccount.getAccountName() + " to " + toAccount.getAccountName());

        try{
            if(fromAccount.checkBalance() < this.value){
                System.out.println("Awaiting for capital in " + fromAccount.getAccountName());
                conditionLock.await(5, TimeUnit.SECONDS);
            }

            if(fromAccount.convertCurrencyTransaction(this.threadID, this.currency,
                        this.value, "Withdraw")){
                    toAccount.convertCurrencyTransaction(this.threadID, this.currency,
                            this.value, "Deposit");
                    conditionLock.signal();
            }
            transactionLock.unlock();
        } catch (InterruptedException e) {
            e.printStackTrace();
            transactionLock.unlock();
        }
        System.out.print(ANSI_RESET);
    }
}
