package lk.ac.mrt.cse.dbs.simpleexpensemanager.data.impl;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.support.annotation.Nullable;

import java.util.ArrayList;
import java.util.List;

import lk.ac.mrt.cse.dbs.simpleexpensemanager.data.AccountDAO;
import lk.ac.mrt.cse.dbs.simpleexpensemanager.data.DatabaseHelper;
import lk.ac.mrt.cse.dbs.simpleexpensemanager.data.exception.InvalidAccountException;
import lk.ac.mrt.cse.dbs.simpleexpensemanager.data.model.Account;
import lk.ac.mrt.cse.dbs.simpleexpensemanager.data.model.ExpenseType;

public class PersistantAccountDAO extends DatabaseHelper implements AccountDAO {
    public PersistantAccountDAO(@Nullable Context context) {
        super(context);
    }

    @Override
    public List<String> getAccountNumbersList() {
        SQLiteDatabase db = this.getReadableDatabase();
        List<String> AccountNumberList = new ArrayList<>();
        String queryString = "SELECT " + ACCOUNT_ID + " FROM " + ACCOUNT_TABLE + ";";
        Cursor cursor = db.rawQuery(queryString, null);
        if (cursor.moveToFirst()) {
            do {
                String accountNumber = cursor.getString(0);
                AccountNumberList.add(accountNumber);
            } while (cursor.moveToNext());
        }
        cursor.close();
//        db.close();
        return AccountNumberList;
    }

    @Override
    public List<Account> getAccountsList() {
        SQLiteDatabase db = this.getReadableDatabase();
        List<Account> AccountList = new ArrayList<>();
        String queryString = "SELECT * FROM " + ACCOUNT_TABLE + ";";
        Cursor cursor = db.rawQuery(queryString, null);
        if (cursor.moveToFirst()) {
            do {
                String accountNumber = cursor.getString(0);
                String bankName = cursor.getString(1);
                String bankHolderName = cursor.getString(2);
                Double accountBalance = cursor.getDouble(3);
                AccountList.add(new Account(accountNumber, bankName, bankHolderName, accountBalance));
            } while (cursor.moveToNext());
        }
        cursor.close();
//        db.close();
        return AccountList;
    }

    @Override
    public Account getAccount(String accountNo) throws InvalidAccountException {
        SQLiteDatabase db = this.getReadableDatabase();
        Account account;
        String queryString = "SELECT * FROM " + ACCOUNT_TABLE + " WHERE " + ACCOUNT_ID + " =\"" + accountNo + "\";";
        Cursor cursor = db.rawQuery(queryString, null);
        if (cursor.moveToFirst()) {
            String accountNumber = cursor.getString(0);
            String bankName = cursor.getString(1);
            String bankHolderName = cursor.getString(2);
            Double accountBalance = cursor.getDouble(3);
            account = new Account(accountNumber, bankName, bankHolderName, accountBalance);
        } else {
            account = null;
        }
        cursor.close();
//        db.close();
        return account;
    }

    @Override
    public void addAccount(Account account) {
        addNewAccount(account);
    }

    @Override
    public void removeAccount(String accountNo) throws InvalidAccountException {
        deleteAccount(accountNo);
    }

    @Override
    public void updateBalance(String accountNo, ExpenseType expenseType, double amount) throws InvalidAccountException {
        SQLiteDatabase db = this.getWritableDatabase();
        Account account = this.getAccount(accountNo);
        double balance = account.getBalance();

        if (expenseType == expenseType.EXPENSE){
            balance -= amount;
        } else {
            balance += amount;
        }

        String queryString = "UPDATE " + ACCOUNT_TABLE + " SET " + BALANCE + " = " + balance + " WHERE " + ACCOUNT_ID + " = \"" + accountNo +"\";";
        db.execSQL(queryString);
        db.close();
    }
}