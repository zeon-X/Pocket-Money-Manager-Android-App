package aleeha.com.example.bugdtEx;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import java.util.List;

public class MainActivity extends AppCompatActivity {

    //Global Variables
    private int currentBalance=0;

    //BTN-S
    private ImageView addMoneyTV;
    private ImageView resetButtonIV;

    //TV
    private TextView currentBalanceTV;
    private TextView transactionDetailsTV;
    private TextView seeAllTV;


    //RecycleView Var
    private RecyclerView expenseFieldsRV;
    private RecyclerView transactionReportRV;

    //adapter variable
    ExpenseAdapterRV expenseAdapter;
    TransactionAdapterRV transactionAdapter;

    // local data
    int [] images = {
            R.drawable.pic4,R.drawable.pic6,R.drawable.pic8,R.drawable.pic7,R.drawable.pic9,
            R.drawable.pic10,R.drawable.pic11,R.drawable.pic12,R.drawable.pic13,R.drawable.pic3,
            R.drawable.pic2,R.drawable.pic1,R.drawable.pic14
    };
    int addMoneyIcon = R.drawable.pic15;
    String [] fieldNames;
    String [] transactionDates,transactionAmount;

    String[] fn,td,tt,tDetails;
    int[] ta,img;


    DataBaseTrans dbTrans;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        //getting the raw data
        fieldNames = getResources().getStringArray(R.array.expense_filed_names);
        transactionDates = getResources().getStringArray(R.array.transaction_dates);
        transactionAmount = getResources().getStringArray(R.array.transaction_amount);

        // RV
        expenseFieldsRV = findViewById(R.id.idRVExpenseFields);
        transactionReportRV = findViewById(R.id.transactionRV);
        // IV
        addMoneyTV = findViewById(R.id.addMoneyBTNIV);
        resetButtonIV = findViewById(R.id.resetBTN);

        //TV
        currentBalanceTV = findViewById(R.id.currentBalanceTV);
        transactionDetailsTV = findViewById(R.id.transactionDetailsTV);
        seeAllTV = findViewById(R.id.seeAllBTN);





        //-----------!!!!!!!!!!!!! DATA FROM DB !!!!!!!!!!!!!!---------------

        dbTrans = new DataBaseTrans(MainActivity.this);
        List<TransactionModel> everyTrans = dbTrans.getEveryOne();

        int len = everyTrans.size();

        fn = new String[len];
        td = new String[len];
        tt = new String[len];
        tDetails = new String[len];
        ta = new int[len];
        img = new int[len];

        int j=0;
        for(int i=len-1; i >=0; --i){
            fn[j] = everyTrans.get(i).getExpenseField();
            ta[j] = everyTrans.get(i).getTransAmount();
            td[j] = everyTrans.get(i).getDate();
            tt[j] = everyTrans.get(i).getTime();
            tDetails[j] = everyTrans.get(i).getDescription();
            if(everyTrans.get(i).getPosition()>=0){
                img[j] = images[everyTrans.get(i).getPosition()];
            }
            else{
                img[j] = addMoneyIcon;
            }

            currentBalance+=ta[j];
            ++j;
        }

        //SETTING CURRENT BALANCE

        if(currentBalance<0)currentBalanceTV.setTextColor(Color.parseColor("#FF0000"));
        else if(currentBalance>0)  currentBalanceTV.setTextColor(Color.parseColor("#00FF00"));

        currentBalanceTV.setText("BDT "+Integer.toString(currentBalance)+".00");



        //-------------------TRANSACTION ADAPTER START------------------------
        transactionAdapter = new TransactionAdapterRV(this,
                fn,
                td,
                tt,
                ta,
                img,
                tDetails
                );
        transactionReportRV.setAdapter(transactionAdapter);
        transactionReportRV.setLayoutManager(new LinearLayoutManager(this));

        /*transactionReportRV.setLayoutManager(new LinearLayoutManager(this){
                @Override
                public boolean canScrollVertically() {
                return false;
                }
        });*/

        transactionAdapter.setOnItemClickListener(new TransactionAdapterRV.ClickListener() {
            @Override
            public void onItemClick(int position, View view) {
                Toast.makeText(MainActivity.this, tt[0], Toast.LENGTH_SHORT).show();
            }
        });
        //-------------------TRANSACTION ADAPTER END------------------------




        //--|||||||||||||||||||||| ONCLICK FUNC |||||||||||||||||||||||||||

        addMoneyTV.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(MainActivity.this,AddTransaction.class);
                intent.putExtra("addMoney",1);

                startActivity(intent);
            }
        });

        seeAllTV.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(MainActivity.this,SeeAllTransaction.class);
                startActivity(intent);
            }
        });




        //-------------------EXPENSE FIELD ADAPTER START------------------------
        expenseAdapter = new ExpenseAdapterRV(this,fieldNames,images);
        GridLayoutManager gridLayoutManager = new GridLayoutManager(this,5,GridLayoutManager.VERTICAL,false);

        expenseFieldsRV.setLayoutManager(gridLayoutManager);
        expenseFieldsRV.setAdapter(expenseAdapter);

        //--> onClick Listener
        expenseAdapter.setOnItemClickListener(new ExpenseAdapterRV.ClickListener() {
            @Override
            public void onItemClick(int position, View view) {
                Toast.makeText(getApplicationContext(),fieldNames[position], Toast.LENGTH_SHORT).show();

                Intent intent = new Intent(MainActivity.this,AddTransaction.class);
                intent.putExtra("fieldName",fieldNames[position]);
                intent.putExtra("fieldIcon",images[position]);
                intent.putExtra("position",position);

                startActivity(intent);
            }
        });
        //-------------------EXP ADAPTER END------------------------



    }







    @Override
    protected void onResume() {
        super.onResume();

        currentBalance = 0;

        //getting the VIEWS
        // RV
        transactionReportRV = findViewById(R.id.transactionRV);
        //TV
        currentBalanceTV = findViewById(R.id.currentBalanceTV);

        //-----------!!!!!!!!!!!!! DATA FROM DB !!!!!!!!!!!!!!---------------

        dbTrans = new DataBaseTrans(MainActivity.this);
        List<TransactionModel> everyTrans = dbTrans.getEveryOne();

        int len = everyTrans.size();

        fn = new String[len];
        td = new String[len];
        tt = new String[len];
        tDetails = new String[len];
        ta = new int[len];
        img = new int[len];

        int j=0;
        for(int i=len-1; i >=0; --i){
            fn[j] = everyTrans.get(i).getExpenseField();
            ta[j] = everyTrans.get(i).getTransAmount();
            td[j] = everyTrans.get(i).getDate();
            tt[j] = everyTrans.get(i).getTime();
            if(everyTrans.get(i).getPosition()>=0){
                img[j] = images[everyTrans.get(i).getPosition()];
            }
            else{
                img[j] = addMoneyIcon;
            }
            tDetails[j] = everyTrans.get(i).getDescription();
            currentBalance+=ta[j];
            ++j;
        }

        //SETTING CURRENT BALANCE

        if(currentBalance<0)currentBalanceTV.setTextColor(Color.parseColor("#FF0000"));
        else if(currentBalance>0)  currentBalanceTV.setTextColor(Color.parseColor("#00FF00"));

        currentBalanceTV.setText("BDT "+Integer.toString(currentBalance)+".00");

        //-------------------TRANSACTION ADAPTER START------------------------
        transactionAdapter = new TransactionAdapterRV(this,
                fn,
                td,
                tt,
                ta,
                img,
                tDetails
        );
        transactionReportRV.setAdapter(transactionAdapter);
        transactionReportRV.setLayoutManager(new LinearLayoutManager(this));
    }
}