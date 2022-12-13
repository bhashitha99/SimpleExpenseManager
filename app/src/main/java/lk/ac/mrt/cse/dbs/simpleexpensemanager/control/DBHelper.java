package lk.ac.mrt.cse.dbs.simpleexpensemanager.control;
import java.text.SimpleDateFormat;
import java.util.Date;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.support.annotation.Nullable;

import java.text.ParseException;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import lk.ac.mrt.cse.dbs.simpleexpensemanager.data.model.Account;
import lk.ac.mrt.cse.dbs.simpleexpensemanager.data.model.ExpenseType;
import lk.ac.mrt.cse.dbs.simpleexpensemanager.data.model.Transaction;

public class DBHelper extends SQLiteOpenHelper {
    public DBHelper(Context context) {

        super(context,"200391B.db", null, 1);
    }

    @Override
    public void onCreate(SQLiteDatabase DB) {
        DB.execSQL("create table account_details(account_no TEXT primary key, bank_name TEXT, account_holder TEXT, initial_balance TEXT)");
        DB.execSQL("create table logs_details(date TEXT primary key, account_no TEXT, type TEXT, amount TEXT)");
    }

    @Override
    public void onUpgrade(SQLiteDatabase DB, int i, int i1) {
        DB.execSQL("drop table if exists account_details");
        DB.execSQL("drop table if exists logs_details");
    }

    public Boolean insertAccount(String account_no,String bank_name,String account_holder, String initial_balance){
        SQLiteDatabase DB = this.getWritableDatabase();
        ContentValues accountdetails = new ContentValues();
        accountdetails.put("account_no", account_no);
        accountdetails.put("bank_name", bank_name);
        accountdetails.put("account_holder", account_holder);
        accountdetails.put("initial_balance", initial_balance);
        long fin_result=DB.insert("account_details", null,accountdetails);
        if(fin_result==-1){
            return false;
        }else{
            return true;
        }
    }

    public Boolean updatetAccount(String account_no,String bank_name,String account_holder, double initial_balance){
        SQLiteDatabase DB = this.getWritableDatabase();
        ContentValues accountdetails = new ContentValues();
        accountdetails.put("initial_balance", initial_balance);
        Cursor row_cursor = DB.rawQuery("select * from account_details where account_no=?",new String[] {account_no});
        if(row_cursor.getCount()>0){

            long fin_result=DB.update("account_details",accountdetails,"account_no=?", new String[] {account_no});
            if(fin_result==-1){
                return false;
            }else{
                return true;
            }
        }else{
            return false;
        }
    }

    public Boolean removeAccount(String account_no){
        SQLiteDatabase DB = this.getWritableDatabase();
        Cursor row_cursor = DB.rawQuery("select * from account_details where account_no=?",new String[] {account_no});
        if(row_cursor.getCount()>0){

            long fin_result=DB.delete("account_details","account_no=?", new String[] {account_no});
            if(fin_result==-1){
                return false;
            }else{
                return true;
            }
        }else{
            return false;
        }
    }

    public ArrayList getdata(){
        SQLiteDatabase DB = this.getReadableDatabase();
        Cursor mycursor = DB.rawQuery("Select * from account_details",null);
        ArrayList<Account> listOfaccount = new ArrayList<>();
        mycursor.moveToFirst();
        while(!mycursor.isAfterLast()) {
            //append data to array list from cursor
            listOfaccount.add(new Account(mycursor.getString(mycursor.getColumnIndex("account_no")),mycursor.getString(mycursor.getColumnIndex("bank_name")),mycursor.getString(mycursor.getColumnIndex("account_holder")),Double.parseDouble(mycursor.getString(mycursor.getColumnIndex("initial_balance")))));
            mycursor.moveToNext();
        }
        return listOfaccount;
    }

    public Boolean insertlogs(String date,String account_no,String type, String amount){
        SQLiteDatabase DB = this.getWritableDatabase();
        ContentValues accountdetails = new ContentValues();
        accountdetails.put("date", date);
        accountdetails.put("account_no", account_no);
        accountdetails.put("bank_name", type);
        accountdetails.put("type", type);
        accountdetails.put("amount", amount);
        long fin_result=DB.insert("account_details", null,accountdetails);
        if(fin_result==-1){
            return false;
        }else{
            return true;
        }
    }
    public ArrayList getlogata() throws ParseException {
        SQLiteDatabase DB = this.getReadableDatabase();
        ArrayList<Transaction> listOftransactions = new ArrayList<>();

        Cursor mycursor = DB.rawQuery("Select * from logs_details",null);
        mycursor.moveToFirst();
        while(!mycursor.isAfterLast()) {
            listOftransactions.add(new Transaction(new SimpleDateFormat("dd/MM/yyyy").parse(mycursor.getString(1)), mycursor.getString(2), ExpenseType.valueOf(mycursor.getString(3)), Double.parseDouble(mycursor.getString(4))));
            mycursor.moveToNext();
        }
        DB.close();
        return listOftransactions;
    }

}
