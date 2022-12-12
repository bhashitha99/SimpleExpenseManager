package lk.ac.mrt.cse.dbs.simpleexpensemanager.control;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.support.annotation.Nullable;

public class DBHelper extends SQLiteOpenHelper {
    public DBHelper(Context context) {
        super(context,"200391B.db", null, 1);
    }

    @Override
    public void onCreate(SQLiteDatabase DB) {
        DB.execSQL("create table account_details(account_no TEXT primary key, bank_name TEXT, account_holder TEXT, initial_balance NUMERIC)");
    }

    @Override
    public void onUpgrade(SQLiteDatabase DB, int i, int i1) {
        DB.execSQL("drop table if exists account_details");
    }

    public Boolean insertAccount(String account_no,String bank_name,String account_holder, double initial_balance){
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

    public Cursor getdata(){
        SQLiteDatabase DB = this.getWritableDatabase();
        Cursor mycursor = DB.rawQuery("Select * from account_details",null);
        return mycursor;
    }
}
