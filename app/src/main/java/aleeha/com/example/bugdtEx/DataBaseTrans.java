package aleeha.com.example.bugdtEx;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.widget.Toast;

import androidx.annotation.Nullable;

import java.util.ArrayList;
import java.util.List;

public class DataBaseTrans extends SQLiteOpenHelper {

    public static final String TRANSACTION_TABLE = "TRANSACTION_TABLE";
    public static final String COLUMN_FIELD = "TRANS_FIELD";
    public static final String COLUMN_AMOUNT = "TRANS_AMOUNT";
    public static final String COLUMN_DATE = "TRANS_DATE";
    public static final String COLUMN_TIME = "TRANS_TIME";
    public static final String COLUMN_DESCRIPTION = "TRANS_DESCRIPTION";
    public static final String COLUMN_IS_ADDED = "TRANS_IS_ADDED";
    public static final String COLUMN_ID = "ID";
    public static final String COLUMN_POSITION = "POSITION";

    public DataBaseTrans(@Nullable Context context) {
        super(context, "transaction.db", null ,1);
    }

    // 1st time create db
    @Override
    public void onCreate(SQLiteDatabase sqLiteDatabase) {
        String createTableStatement =
                "CREATE TABLE " + TRANSACTION_TABLE + " (" + COLUMN_ID + " INTEGER PRIMARY KEY AUTOINCREMENT, " + COLUMN_FIELD + " TEXT, " + COLUMN_AMOUNT + " INT, " + COLUMN_DATE + " TEXT, " + COLUMN_TIME + " TEXT, " + COLUMN_DESCRIPTION + " TEXT, " + COLUMN_IS_ADDED + " BOOL, " + COLUMN_POSITION + " INT )";
        sqLiteDatabase.execSQL(createTableStatement);
    }

    // upgrade old db || modify schema
    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int i, int i1) {

    }

    // create || insert data
    public boolean addOne(TransactionModel transactionModel){
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues cv = new ContentValues();
        cv.put(COLUMN_FIELD,transactionModel.getExpenseField());
        cv.put(COLUMN_AMOUNT,transactionModel.getTransAmount());
        cv.put(COLUMN_DATE,transactionModel.getDate());
        cv.put(COLUMN_TIME,transactionModel.getTime());
        cv.put(COLUMN_DESCRIPTION,transactionModel.getDescription());
        cv.put(COLUMN_IS_ADDED,transactionModel.isMoneyAdded());
        cv.put(COLUMN_POSITION,transactionModel.getPosition());

        long insert = db.insert(TRANSACTION_TABLE, null, cv);
        if(insert == -1) return false;
        else return true;
    }

    // read data
    public List<TransactionModel> getEveryOne(){
        List<TransactionModel> returnList = new ArrayList<>();
        String queryString = "SELECT * FROM " + TRANSACTION_TABLE;
        SQLiteDatabase db = this.getReadableDatabase();
        try{
            Cursor cursor = db.rawQuery(queryString,null);
            if (cursor.moveToFirst()) {
                // loop through the cursor
                do{
                    int id = cursor.getInt(0);
                    String fieldName = cursor.getString(1);
                    int amount = cursor.getInt(2);
                    String date = cursor.getString(3);
                    String time = cursor.getString(4);
                    String description = cursor.getString(5);
                    Boolean isAdded = cursor.getInt(6)==1 ? true : false;
                    int position = cursor.getInt(7);
                    TransactionModel newTransaction = new TransactionModel(id,fieldName,amount,date,time,description,isAdded,position);
                    returnList.add(newTransaction);
                }
                while (cursor.moveToNext());
            } else {}
            cursor.close();
            db.close();
        }
        catch (Exception e){}
        return returnList;
    }


    //delete data
    public boolean deleteOne(Context c, int id){
        SQLiteDatabase db = this.getWritableDatabase();
        String ID = Integer.toString(id);
        String queryString = "DELETE FROM "+ TRANSACTION_TABLE + " WHERE " + COLUMN_ID + " = "+ID ;
        Cursor cursor = db.rawQuery(queryString, null);
        if(cursor.moveToFirst()) return true;
        else return false;
    }



}
