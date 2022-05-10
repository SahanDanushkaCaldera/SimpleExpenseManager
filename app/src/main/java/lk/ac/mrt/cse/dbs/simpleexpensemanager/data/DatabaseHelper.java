package lk.ac.mrt.cse.dbs.simpleexpensemanager.data;

import android.content.ContentValues;
import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.support.annotation.Nullable;

import lk.ac.mrt.cse.dbs.simpleexpensemanager.data.model.Account;
import lk.ac.mrt.cse.dbs.simpleexpensemanager.data.model.ExpenseType;

public class DatabaseHelper extends SQLiteOpenHelper {
    public static final String ACCOUNT_TABLE = "Account_Table";
    public static final String TRANSACTION_ID = "ID";
    public static final String ACCOUNT_ID = "Account_ID";
    public static final String BANK_NAME = "Bank_Name";
    public static final String ACCOUNT_HOLDER_NAME = "Account_Holder_Name";
    public static final String BALANCE = "Balance";
    public static final String TRANSACTION_TABLE = "Transaction_Table";
    public static final String TRASACTION_DATE = "Date";
    public static final String TRASACTION_ACCOUNT_ID = "Account_ID";
    public static final String TRANSACTION_AMOUNT = "Amount";
    public static final String EXPENSE_TYPE = "Expense_Type";

    public DatabaseHelper(@Nullable Context context) {
        super(context, "190097J.db", null, 1);
    }

    //when there is no database. when first run the code this method will be called and create the database
    @Override
    public void onCreate(SQLiteDatabase sqLiteDatabase) {
        String queryOfCreatingTable1 = "CREATE TABLE " + ACCOUNT_TABLE + "(" + ACCOUNT_ID + " TEXT PRIMARY KEY," + BANK_NAME + " TEXT," + ACCOUNT_HOLDER_NAME + " TEXT," + BALANCE + " REAL )";
        sqLiteDatabase.execSQL(queryOfCreatingTable1);
        String queryOfCreatingTable2 = "CREATE TABLE " + TRANSACTION_TABLE + "(" + TRANSACTION_ID + " INTEGER PRIMARY KEY AUTOINCREMENT," + TRASACTION_DATE + " DATE, " + TRASACTION_ACCOUNT_ID + " REFERENCES ACCOUNT_TABLE(ACCOUNT_ID) , " + TRANSACTION_AMOUNT + " REAL, " + EXPENSE_TYPE + " TEXT )";
        sqLiteDatabase.execSQL(queryOfCreatingTable2);
    }

    //After creating ,used for the upgrade and modify database
    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int i, int i1) {

    }

    public boolean addNewAccount(Account account) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues cv = new ContentValues();
        cv.put(ACCOUNT_ID, account.getAccountNo());
        cv.put(BANK_NAME, account.getBankName());
        cv.put(ACCOUNT_HOLDER_NAME, account.getAccountHolderName());
        cv.put(BALANCE, account.getBalance());

        long insert = db.insert(ACCOUNT_TABLE, null, cv);
        if (insert == -1) {
            return false;
        } else {
            return true;
        }
    }

    public boolean deleteAccount(String account_no){
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues cv = new ContentValues();
        cv.put(ACCOUNT_ID, account_no);

        long delete = db.delete(ACCOUNT_TABLE, ACCOUNT_ID + "=?", new String[]{account_no});
        if (delete == -1) {
            return false;
        } else {
            return true;
        }
    }
}
