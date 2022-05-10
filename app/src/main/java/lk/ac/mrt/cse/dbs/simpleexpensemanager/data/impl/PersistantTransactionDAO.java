package lk.ac.mrt.cse.dbs.simpleexpensemanager.data.impl;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.icu.text.SimpleDateFormat;
import android.os.Build;
import android.support.annotation.Nullable;
import android.support.annotation.RequiresApi;

import java.text.ParseException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Locale;

import lk.ac.mrt.cse.dbs.simpleexpensemanager.data.DatabaseHelper;
import lk.ac.mrt.cse.dbs.simpleexpensemanager.data.TransactionDAO;
import lk.ac.mrt.cse.dbs.simpleexpensemanager.data.model.ExpenseType;
import lk.ac.mrt.cse.dbs.simpleexpensemanager.data.model.Transaction;

public class PersistantTransactionDAO extends DatabaseHelper implements TransactionDAO {
    public PersistantTransactionDAO(@Nullable Context context) {
        super(context);
    }

    @Override
    public void logTransaction(Date date, String accountNo, ExpenseType expenseType, double amount) {
        Transaction transaction = new Transaction(date, accountNo, expenseType, amount);
        SQLiteDatabase db = this.getReadableDatabase();
        ContentValues cv = new ContentValues();
        cv.put(TRASACTION_DATE, String.valueOf(date));
        cv.put(ACCOUNT_ID, accountNo);
        cv.put(TRANSACTION_AMOUNT, amount);
        cv.put(EXPENSE_TYPE, String.valueOf(expenseType));

        db.insert(TRANSACTION_TABLE, null, cv);
    }

    @RequiresApi(api = Build.VERSION_CODES.N)
    @Override
    public List<Transaction> getAllTransactionLogs() {
        SQLiteDatabase db = this.getReadableDatabase();
        List<Transaction> transactionList = new ArrayList<>();
        String queryString = "SELECT * FROM " + TRANSACTION_TABLE + ";";
        Cursor cursor = db.rawQuery(queryString, null);
        if (cursor.moveToFirst()) {
            do {
                String date = cursor.getString(1);
                String account_ID = cursor.getString(2);
                Double amount = cursor.getDouble(3);
                String type = cursor.getString(4);
                ExpenseType expenseType = ExpenseType.valueOf(type);

                SimpleDateFormat simpleDateFormat = new SimpleDateFormat("EEE MMM dd HH:mm:ss zzz yyyy", Locale.ENGLISH);
                Date date1 = null;
                try {
                    date1 = simpleDateFormat.parse(date);

                } catch (ParseException e) {
                    e.printStackTrace();
                }

                Transaction transaction = new Transaction(date1, account_ID, expenseType, amount);
                transactionList.add(transaction);
            } while (cursor.moveToNext());
        }
        cursor.close();
        db.close();
        return transactionList;
    }

    @RequiresApi(api = Build.VERSION_CODES.N)
    @Override
    public List<Transaction> getPaginatedTransactionLogs(int limit) {
        SQLiteDatabase db = this.getReadableDatabase();
        List<Transaction> transactionList = new ArrayList<>();
        String queryString = "SELECT * FROM " + TRANSACTION_TABLE + " LIMIT " + limit + ";";
        Cursor cursor = db.rawQuery(queryString, null);
        if (cursor.moveToFirst()) {
            do {
                String date = cursor.getString(1);
                String account_ID = cursor.getString(2);
                Double amount = cursor.getDouble(3);
                String type = cursor.getString(4);
                ExpenseType expenseType = ExpenseType.valueOf(type);

                SimpleDateFormat simpleDateFormat = new SimpleDateFormat("EEE MMM dd HH:mm:ss zzz yyyy", Locale.ENGLISH);
                Date date1 = null;
                try {
                    date1 = simpleDateFormat.parse(date);

                } catch (ParseException e) {
                    e.printStackTrace();
                }

                Transaction transaction = new Transaction(date1, account_ID, expenseType, amount);
                transactionList.add(transaction);
            } while (cursor.moveToNext());
        }
        cursor.close();
        db.close();
        return transactionList;
    }
}
