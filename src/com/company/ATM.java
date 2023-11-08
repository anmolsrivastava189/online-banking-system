package com.company;
import java.util.*;
public class ATM {
    static Scanner sc = new Scanner(System.in);
    static Bank bank1 = new Bank("HDFC Bank");
    public static void main(String[] args) {
        while(true) {
            System.out.println("Menu:");
            System.out.println("1.- Enter Bank Portal.");
            System.out.println("2.- Register a New Customer.");
            System.out.println("3.- Open a New Account.");
            System.out.println("4.- Exit.");
            System.out.print("Option: ");
            int op = sc.nextInt();
            switch (op) {
                case 1: {
                    User curUser = mainMenuPrompt(bank1);
                    displayUserMenu(curUser);
                    break;
                }
                case 2: {
                    sc.nextLine();
                    System.out.print("First Name: ");
                    String fName = sc.next();
                    System.out.print("Last Name: ");
                    String lName = sc.next();
                    System.out.print("SetUp a Pin: ");
                    String pin = sc.next();
                    User newUser = bank1.addUser(fName, lName, pin);
                    break;
                }
                case 3: {
                    System.out.print("User I.D.- ");
                    String id = sc.next();
                    User curUser = bank1.getUserFromID(id);
                    if (curUser == null) {
                        System.out.println("No such User Exists!");
                        break;
                    }
                    System.out.print("Account Name: ");
                    String name = sc.next();
                    Account newAccount = new Account(name, curUser, bank1);
                    bank1.addAccount(newAccount);
                    curUser.addAccount(newAccount);
                    break;
                }
                case 4: {
                    System.out.println("Exiting...........");
                    return;
                }
                default: {
                    System.out.println("Enter a valid choice!");
                }
            }
        }
    }
    static User mainMenuPrompt(Bank bank)
    {
        User user = null;
        System.out.format("Welcome to %s!\n",bank.getName());
        do{
            System.out.print("User I.D.- ");
            String id = sc.next();
            System.out.print("Pin: ");
            String pin = sc.next();
            user = bank.userLogin(id,pin);
            if(user == null)
            {
                System.out.println("Invalid Details! Please try again!");
                break;
            }
        }while(user == null);
        return user;
    }
    static void displayUserMenu(User curUser)
    {
        if(curUser == null)
        {
            return;
        }
        while(true)
        {
            curUser.printAccountsSummary();
            System.out.format("Hello %s! What would you like to do?\n",curUser.getFirstName());
            System.out.println("Menu:");
            System.out.println("1.-Display Account Transaction History.");
            System.out.println("2.-Withdrawal.");
            System.out.println("3.-Deposit.");
            System.out.println("4.-Transfer.");
            System.out.println("5.-Quit.");
            System.out.print("Option: ");
            int op = sc.nextInt();
            switch(op)
            {
                case 1:{
                    showTransHistory(curUser);
                    break;
                }
                case 2:{
                    withdrawFunds(curUser);
                    break;
                }
                case 3:{
                    depositFunds(curUser);
                    break;
                }
                case 4:{
                    transferFunds(curUser);
                    break;
                }
                case 5:{
                    sc.nextLine();
                    System.out.println("Thank You!");
                    System.out.println("Quitting.......");
                    return;
                }
                default:{
                    System.out.println("Enter a Valid Choice!");
                }
            }
        }
    }
    static void showTransHistory(User curUser)
    {
        boolean isValid = true;
        do{
            isValid = true;
            System.out.format("Account No.(1-%d)- ",curUser.numAcc());
            int num = sc.nextInt();
            if(num<1 || num>curUser.numAcc())
            {
                isValid = false;
                System.out.println("Invalid Number! Please try again!");
            }
            else
            {
                curUser.displayAccTransHistory(num);
            }
        }while(!isValid);
    }
    static void withdrawFunds(User curUser)
    {
        int fromAccount = -1;
        boolean isValid = true;
        do{
            isValid = true;
            System.out.format("No. of the Account to Withdraw from(1-%d): ",curUser.numAcc());
            int num = sc.nextInt();
            if(num<1 || num>curUser.numAcc())
            {
                isValid = false;
                System.out.println("Invalid Number! Please enter a valid number!");
            }
            else
            {
                fromAccount = num;
            }
        }while(!isValid);
        double accBalance = curUser.getAccBalance(fromAccount);
        if(accBalance == 0)
        {
            System.out.println("Current Balance: Rs 0.");
            System.out.println("Cannot Withdraw Funds!");
            return;
        }
        do{
            isValid = true;
            System.out.format("Amount to Withdraw(Current Balance: Rs %.02f): Rs ",accBalance);
            double amount = sc.nextDouble();
            if(amount < 0)
            {
                isValid = false;
                System.out.println("Amount should be greater than zero!");
            }
            else if(amount > accBalance)
            {
                isValid = false;
                System.out.println("Insufficient Balance!");
            }
            else
            {
                sc.nextLine();
                System.out.print("Enter a Memo: ");
                String memo = sc.nextLine();
                curUser.addAccTrans(fromAccount,(-1)*amount,memo);
                System.out.println("Withdrawal Successful!");
            }
        }while(!isValid);

    }
    static void depositFunds(User curUser)
    {
        int toAccount = -1;
        boolean isValid = true;
        do{
            isValid = true;
            System.out.format("Account No.(1-%d): ",curUser.numAcc());
            int num = sc.nextInt();
            if(num<1 || num>curUser.numAcc())
            {
                isValid = false;
                System.out.println("Invalid Number! Please enter a valid number!");
            }
            else
            {
                toAccount = num;
            }
        }while(!isValid);
        do{
            isValid = true;
            System.out.print("Amount to Deposit: Rs ");
            double amount = sc.nextDouble();
            if(amount < 0)
            {
                isValid = false;
                System.out.println("Amount should be greater than zero!");
            }
            else
            {
                sc.nextLine();
                System.out.print("Enter a Memo: ");
                String memo = sc.nextLine();
                curUser.addAccTrans(toAccount,amount,memo);
                System.out.println("Deposit Successful!");
            }
        }while(!isValid);
    }
    static void transferFunds(User curUser)
    {
        int fromAcc = -1,toAcc = -1;
        double accBalance = 0;
        boolean isValid = true;
        do{
            isValid = true;
            System.out.format("No. of the Account to transfer from(1-%d): ",curUser.numAcc());
            int num = sc.nextInt();
            if(num<1 || num>curUser.numAcc())
            {
                isValid = false;
                System.out.println("Invalid Number! Please try again!");
            }
            else
            {
                fromAcc = num;
                accBalance = curUser.getAccBalance(num);
            }
        }while(!isValid);
        User receiver = null;
        String accId = "";
        do{
            isValid = true;
            System.out.print("Unique Universal I.D. of the Account to transfer to: ");
            accId = sc.next();
            receiver = bank1.getUserFromAccId(accId);
            if(receiver == null)
            {
                isValid = false;
                System.out.println("No Account Found! Please try again!");
            }
        }while(!isValid);
        do{
            isValid = true;
            System.out.format("Amount to transfer(Current Balance: Rs %.02f): Rs ",accBalance);
            double amount = sc.nextDouble();
            if(amount < 0)
            {
                isValid = false;
                System.out.println("Amount must be greater than zero!");
            }
            else if(amount > accBalance)
            {
                isValid = false;
                System.out.println("Insufficient Balance! Please try again!");
            }
            else
            {
                toAcc = receiver.getIndexFromAccId(accId);
                curUser.addAccTrans(fromAcc,(-1)*amount,"Transfer to Account: "+accId);
                receiver.addAccTrans(toAcc,amount,"Transfer from Account: "+curUser.getAccUuid(fromAcc));
                System.out.println("Transaction Successful!");
            }
        }while(!isValid);
    }
}