package aleeha.com.example.bugdtEx;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;
import android.widget.Toast;

import androidx.annotation.Nullable;

import java.util.ArrayList;
import java.util.List;

public class DatabaseLendBorrow extends SQLiteOpenHelper {

    public static final String TABLE_NAME = "LEND_BORROW_TABLE";
    public static final String COLUMN_ID = "ID";
    public static final String COLUMN_FIELD_NAME = "FIELD_NAME";
    public static final String COLUMN_AMOUNT = "AMOUNT";
    public static final String COLUMN_PERSON_NAME = "PERSON_NAME";
    public static final String COLUMN_DATE = "DATE";
    public static final String COLUMN_RETURN_DATE = "RETURN_DATE";
    public static final String COLUMN_NOTE = "NOTE";
    public static final String COLUMN_STATUS = "STATUS";

    //constructor
    public DatabaseLendBorrow(@Nullable Context context) {
        super(context, "lendBorrowTransactions.db", null, 1);
    }

    //1st time creating the db schems
    @Override
    public void onCreate(SQLiteDatabase sqLiteDatabase) {
        String createTableStatement =
                "CREATE TABLE " + TABLE_NAME + " (" + COLUMN_ID + " INTEGER PRIMARY KEY AUTOINCREMENT, " + COLUMN_FIELD_NAME + " TEXT, " + COLUMN_AMOUNT + " INT, " + COLUMN_PERSON_NAME + " TEXT, " + COLUMN_DATE + " TEXT, " + COLUMN_RETURN_DATE + " TEXT, " + COLUMN_NOTE + " TEXT, " + COLUMN_STATUS + " BOOL)";
        sqLiteDatabase.execSQL(createTableStatement);
    }
    //use white update the db schema
    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int i, int i1) {

    }


    //!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!---IMPLEMENT FUNCTIONS-----

    // CREATE || insert-data
    public boolean addOne(LendBorrowModel lbm){
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues cv = new ContentValues();

        cv.put(COLUMN_FIELD_NAME,lbm.getFieldName());
        cv.put(COLUMN_AMOUNT,lbm.getAmount());
        cv.put(COLUMN_PERSON_NAME,lbm.getPersonName());
        cv.put(COLUMN_DATE,lbm.getDate());
        cv.put(COLUMN_RETURN_DATE,lbm.getReturnDate());
        cv.put(COLUMN_NOTE,lbm.getNote());
        cv.put(COLUMN_STATUS,lbm.isStatus());

        long insert = db.insert(TABLE_NAME,null,cv);
        Log.d("TAG", String.valueOf(insert));
        if (insert == -1) return false;
        else return true;
    }

    // GET || read-data || return type List that contains data like LendBorrowModel
    public List<LendBorrowModel> getEveryOne(){
        List<LendBorrowModel> returnList = new ArrayList<>();

        String queryString = "SELECT * FROM "+TABLE_NAME;
        SQLiteDatabase db = this.getReadableDatabase();

        try{
            Cursor cursor = db.rawQuery(queryString, null);
            if(cursor.moveToFirst()){ //cursor-not-empty
                do{
                    int id = cursor.getInt(0);
                    String fieldName = cursor.getString(1);
                    int amount = cursor.getInt(2);
                    String personName = cursor.getString(3);
                    String date = cursor.getString(4);
                    String returnDate = cursor.getString(5);
                    String note = cursor.getString(6);
                    boolean status = cursor.getInt(7)==1?true:false;

                    returnList.add(new LendBorrowModel(id,fieldName,amount,personName,date,returnDate,note,status));
                    // added the LendBorrowModel Type looped obj in the array
                }
                while (cursor.moveToNext());
            } else{}//No-data
        } catch (Exception e){}//no action

        return  returnList;
    }

    public boolean delete_db(){
        SQLiteDatabase db = this.getWritableDatabase();
        String queryString = "DELETE FROM "+ TABLE_NAME;
        Cursor cursor = db.rawQuery(queryString, null);
        if(cursor.moveToFirst()) return true;
        else return false;
    }

}
