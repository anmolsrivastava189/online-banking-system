package com.company;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.*;
public class User {
    private String firstName;
    private String lastName;
    private String uuid;
    private byte pinHash[];
    private ArrayList<Account> accounts;
    public User(String firstName,String lastName,String pin,Bank bank)
    {
        this.firstName = firstName;
        this.lastName = lastName;
        try{
            MessageDigest md = MessageDigest.getInstance("MD5");
            this.pinHash = md.digest(pin.getBytes());
        }
        catch(NoSuchAlgorithmException e){
            System.out.println("Some error occurred in encoding the Pin!");
            System.out.println(e);
        }
        this.uuid = bank.getNewUserUuid();
        this.accounts = new ArrayList<Account>();
        System.out.format("New User: %s %s with I.D.- %s Created!\n",firstName,lastName,uuid);
    }
    public void addAccount(Account account)
    {
        this.accounts.add(account);
    }
    public String getUuid()
    {
        return uuid;
    }
    public boolean validatePin(String ePin)
    {
        try {
            MessageDigest md = MessageDigest.getInstance("MD5");
            return MessageDigest.isEqual(md.digest(ePin.getBytes()),this.pinHash);
        }
        catch(NoSuchAlgorithmException e)
        {
            System.out.println("Some error occurred in encoding the Pin!");
            System.out.println(e);
        }
        return false;
    }
    public String getFirstName()
    {
        return this.firstName;
    }
    public void printAccountsSummary()
    {
        System.out.format("%s's Accounts Summary:\n",this.firstName);
        for(int i=0;i<this.accounts.size();i++)
        {
            System.out.format("%d.- %s\n",(i+1),this.accounts.get(i).getSummaryLine());
        }
    }
    public int numAcc()
    {
        return this.accounts.size();
    }
    public void displayAccTransHistory(int num)
    {
        num--;
        this.accounts.get(num).displayTransHistory();
    }
    public double getAccBalance(int num)
    {
        num--;
        return this.accounts.get(num).getBalance();
    }
    public String getAccUuid(int idx)
    {
        idx--;
        return this.accounts.get(idx).getUuid();
    }
    public void addAccTrans(int num,double amount,String memo)
    {
        num--;
        this.accounts.get(num).addTransaction(amount,memo);
    }
    public int getIndexFromAccId(String accId)
    {
        for(int i=0;i<this.accounts.size();i++)
        {
            if(this.accounts.get(i).getUuid().compareTo(accId) == 0)
            {
                return i+1;
            }
        }
        return -1;
    }
}
