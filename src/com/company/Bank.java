package com.company;
import java.util.*;
public class Bank {
    private String name;
    private ArrayList<User> users;
    private ArrayList<Account> accounts;
    public Bank(String name)
    {
        this.name = name;
        this.users = new ArrayList<User>();
        this.accounts = new ArrayList<Account>();
    }
    public String getNewUserUuid()
    {
        String uuid = "";
        int len = 6;
        Random rand = new Random();
        boolean isUnique = true;
        do {
            uuid = "";
            isUnique = true;
            for (int i = 0; i < len; i++) {
                uuid += rand.nextInt(10);
            }
            for (User user : this.users) {
                if (uuid.compareTo(user.getUuid()) == 0)
                {
                    isUnique = false;
                    break;
                }
            }
        }while(!isUnique);
        return uuid;
    }
    public String getNewAccUuid(){
        String uuid = "";
        int len = 10;
        Random rand = new Random();
        boolean isUnique = true;
        do {
            uuid = "";
            isUnique = true;
            for (int i = 0; i < len; i++) {
                uuid += rand.nextInt(10);
            }
            for (Account account : this.accounts) {
                if (uuid.compareTo(account.getUuid()) == 0)
                {
                    isUnique = false;
                    break;
                }
            }
        }while(!isUnique);
        return uuid;
    }
    public void addAccount(Account account)
    {
        this.accounts.add(account);
    }
    public User addUser(String firstName,String lastName,String pin)
    {
        User newUser = new User(firstName,lastName,pin,this);
        this.users.add(newUser);
        Account newAccount = new Account("Savings",newUser,this);
        newUser.addAccount(newAccount);
        this.addAccount(newAccount);
        return newUser;
    }
    public User userLogin(String userId,String pin)
    {
        for(User user: this.users)
        {
            if(user.getUuid().compareTo(userId)==0 && user.validatePin(pin))
            {
                return user;
            }
        }
        return null;
    }
    public String getName()
    {
        return this.name;
    }
    public User getUserFromID(String id)
    {
        for(User user: this.users)
        {
            if(user.getUuid().compareTo(id) == 0)
            {
                return user;
            }
        }
        return null;
    }
    public User getUserFromAccId(String accId)
    {
        for(Account acc: this.accounts)
        {
            if(acc.getUuid().compareTo(accId) == 0)
            {
                return acc.getHolder();
            }
        }
        return null;
    }
}