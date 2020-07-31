import java.util.concurrent.locks.ReentrantLock;

public class MyBank {
    public static void main(String[] args) {
        SavingsAccount myAccount = new SavingsAccount("Person A", 1000);
        SavingsAccount myAccount1 = new SavingsAccount("Person B", 0);
        SavingsAccount myAccount2 = new SavingsAccount("Person C", 100000);
        ReentrantLock withdrawalLock = new ReentrantLock();

        System.out.println(myAccount.getAccountName() + " Balance: " + myAccount.checkBalance());
        System.out.println(myAccount1.getAccountName() + " Balance: " + myAccount1.checkBalance());
        System.out.println(myAccount2.getAccountName() + " Balance: " + myAccount2.checkBalance());

        Thread thread1 = new Thread(new TransactionsRunnable(
                myAccount, myAccount1,1, "USD", 500, withdrawalLock
        ));

        Thread thread2 = new Thread(new TransactionsRunnable(
                myAccount1, myAccount2,2, "USD", 500,  withdrawalLock
        ));

        Thread thread3 = new Thread(new TransactionsRunnable(
                myAccount2, myAccount,3, "INR", 31495,  withdrawalLock
        ));

        Thread thread4 = new Thread(new TransactionsRunnable(
                myAccount, myAccount1,4, "INR", 1000,  withdrawalLock
        ));

        Thread thread5 = new Thread(new TransactionsRunnable(
                myAccount, myAccount1,5, "INR", 31495,  withdrawalLock
        ));

        thread1.start();
        thread2.start();
        thread3.start();
        thread4.start();
        thread5.start();


        while(thread1.isAlive()||thread2.isAlive()||thread3.isAlive()||thread4.isAlive()||thread5.isAlive());

        System.out.println(myAccount.getAccountName() + " Balance: " + myAccount.checkBalance());
        System.out.println(myAccount1.getAccountName() + " Balance: " + myAccount1.checkBalance());
        System.out.println(myAccount2.getAccountName() + " Balance: " + myAccount2.checkBalance());
    }
}
