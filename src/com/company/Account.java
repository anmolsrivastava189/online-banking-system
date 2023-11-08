package com.company;
import java.util.*;
public class Account {
    private String name;
    private String uuid;
    private User holder;
    private ArrayList<Transaction> transactions;
    public Account(String name,User holder,Bank bank)
    {
        this.name = name;
        this.holder = holder;
        this.uuid = bank.getNewAccUuid();
        this.transactions = new ArrayList<Transaction>();
        //holder.addAccount(this);
        //bank.addAccount(this);
    }
    public String getUuid()
    {
        return uuid;
    }
    public double getBalance()
    {
        double balance = 0;
        for(Transaction transaction: this.transactions)
        {
            balance += transaction.getAmount();
        }
        return balance;
    }
    public String getSummaryLine()
    {
        double balance = this.getBalance();
        if(balance >= 0)
        {
            return String.format("%s: Rs %.02f: %s",this.uuid,balance,this.name);
        }
        else
        {
            return String.format("%s: Rs (%.02f): %s",this.uuid,balance,this.name);
        }
    }
    public void displayTransHistory()
    {
        System.out.format("Transaction History for Account %s:\n",this.uuid);
        for(int i=this.transactions.size()-1;i>=0;i--)
        {
            System.out.format("%s\n",this.transactions.get(i).getSummaryLine());
        }
    }
    public void addTransaction(double amount,String memo)
    {
        Transaction transaction = new Transaction(amount,memo,this);
        this.transactions.add(transaction);
    }
    public User getHolder()
    {
        return this.holder;
    }
}