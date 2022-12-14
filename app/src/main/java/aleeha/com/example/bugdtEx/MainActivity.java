package aleeha.com.example.bugdtEx;

import androidx.annotation.NonNull;
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

import com.google.android.gms.auth.api.signin.GoogleSignIn;
import com.google.android.gms.auth.api.signin.GoogleSignInAccount;
import com.google.android.gms.auth.api.signin.GoogleSignInClient;
import com.google.android.gms.auth.api.signin.GoogleSignInOptions;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;

import java.util.List;

public class MainActivity extends AppCompatActivity {

    //Variables
    private int currentBalance=0;
    private ImageView addMoneyTV;
    private TextView currentBalanceTV,seeAllTV, userNameTV;
    private RecyclerView expenseFieldsRV,transactionReportRV;
    private LinearLayout borrowBTN, lendBTN,reportBTN,refreshBTN;
    ExpenseAdapterRV expenseAdapter;
    TransactionAdapterRV transactionAdapter;

    // local data
    int [] images = {
            R.drawable.pic4,R.drawable.pic6,R.drawable.pic8,R.drawable.pic7,R.drawable.pic9,
            R.drawable.pic10,R.drawable.pic11,R.drawable.pic12,R.drawable.pic13,R.drawable.pic3,
            R.drawable.pic2,R.drawable.pic1,R.drawable.pic16,R.drawable.pic14
    };
    int addMoneyIcon = R.drawable.pic15,lendIcon = R.drawable.lending;
    String [] fieldNames;
    GoogleSignInOptions gso;
    GoogleSignInClient gsc;
    GoogleSignInAccount account;


    public void showAllTransactions(){
        DataBaseTrans dbTrans = new DataBaseTrans(this);
        List<TransactionModel> everyTrans = dbTrans.getEveryOne();

        int len = everyTrans.size();
        String[] fn = new String[len];
        String[] td = new String[len];
        String[] tt = new String[len];
        String[] tDetails = new String[len];
        int[] ta = new int[len];
        int[] img = new int[len];
        int[] id = new int[len];

        int j=0;
        for(int i=len-1; i >=0; --i){
            fn[j] = everyTrans.get(i).getExpenseField();
            ta[j] = everyTrans.get(i).getTransAmount();
            td[j] = everyTrans.get(i).getDate();
            tt[j] = everyTrans.get(i).getTime();
            id[j] = everyTrans.get(i).getId();
            if(everyTrans.get(i).getPosition()==-1)img[j] = addMoneyIcon;
            else if(everyTrans.get(i).getPosition()==-2) img[j] = lendIcon;
            else if(everyTrans.get(i).getPosition()==-500) img[j] = R.drawable.pic13;
            else img[j] = images[everyTrans.get(i).getPosition()];
            tDetails[j] = everyTrans.get(i).getDescription();
            currentBalance+=ta[j]; ++j;
        }

        //SETTING CURRENT BALANCE
        if(currentBalance<0)currentBalanceTV.setTextColor(Color.parseColor("#FF0000"));
        else if(currentBalance>0)  currentBalanceTV.setTextColor(Color.parseColor("#00FF00"));
        currentBalanceTV.setText("BDT "+Integer.toString(currentBalance)+".00");

        TransactionAdapterRV transactionAdapter =
                new TransactionAdapterRV(this, fn, td, tt, ta, img, tDetails);

        transactionAdapter.setOnItemClickListener(new TransactionAdapterRV.ClickListener() {
            @Override
            public void onItemClick(int i, View view) {
                DeleteOptionBottomSheetFragment deleteOptionBottomSheetFragment = new DeleteOptionBottomSheetFragment(MainActivity.this,id[i],fn[i],tDetails[i],td[i],tt[i],ta[i]);
                deleteOptionBottomSheetFragment.show(getSupportFragmentManager(),deleteOptionBottomSheetFragment.getTag());
                currentBalance = 0;

                showAllTransactions();
            }
        });
        //transactionAdapter.notifyDataSetChanged();
        transactionReportRV.setAdapter(transactionAdapter);
        transactionReportRV.setLayoutManager(new LinearLayoutManager(this));
    }

    /*
    public void showTrans(){
        DataBaseTrans dbTrans = new DataBaseTrans(this);
        List<TransactionModel> everyTrans = dbTrans.getEveryOne();

        //SETTING CURRENT BALANCE
        for(int i=0; i<everyTrans.size();++i) currentBalance+=everyTrans.get(i).getTransAmount();
        if(currentBalance<0)currentBalanceTV.setTextColor(Color.parseColor("#FF0000"));
        else if(currentBalance>0)  currentBalanceTV.setTextColor(Color.parseColor("#00FF00"));
        currentBalanceTV.setText("BDT "+Integer.toString(currentBalance)+".00");

        TransactionAdapterUpgraded transactionAdapter = new TransactionAdapterUpgraded(this, everyTrans);

        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(this);
        linearLayoutManager.setReverseLayout(true);
        linearLayoutManager.setStackFromEnd(true);
        transactionAdapter.setOnItemClickListener(new TransactionAdapterUpgraded.ClickListener() {
            @Override
            public void onItemClick(int i, View view) {
                Toast.makeText(MainActivity.this, "clicked..", Toast.LENGTH_SHORT).show();
            }
        });
        transactionReportRV.setAdapter(transactionAdapter);
        transactionReportRV.setLayoutManager(linearLayoutManager);
    }
     */


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        //getting the raw data
        fieldNames = getResources().getStringArray(R.array.expense_filed_names);
        expenseFieldsRV = findViewById(R.id.idRVExpenseFields);
        transactionReportRV = findViewById(R.id.transactionRV);
        addMoneyTV = findViewById(R.id.addMoneyBTNIV);
        refreshBTN = findViewById(R.id.refreshBTNLL);
        currentBalanceTV = findViewById(R.id.currentBalanceTV);
        //transactionDetailsTV = findViewById(R.id.transactionDetailsTV);
        seeAllTV = findViewById(R.id.seeAllBTN);
        userNameTV = findViewById(R.id.userNameTV);
        reportBTN = findViewById(R.id.reportLL);
        lendBTN = findViewById(R.id.lendLL);
        borrowBTN = findViewById(R.id.borrowLL);


        ///*
        //||||||||||||||||||--- GOOGLE AUTH DATA
        gso = new GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN).requestEmail().build();
        gsc = GoogleSignIn.getClient(MainActivity.this,gso);
        account = GoogleSignIn.getLastSignedInAccount(this);

        if(account!=null){
            userNameTV.setText(""+account.getDisplayName());
            Toast.makeText(this, "Welcome "+account.getDisplayName(), Toast.LENGTH_SHORT).show();

//            resetButtonIV.setOnClickListener(new View.OnClickListener() {
//                @Override
//                public void onClick(View view) {
//                    SignOutUser();
//                }
//            });
        }
        else{
            userNameTV.setText("Hello Cypher Stark");
        }
        //*/



        //||||||||||||||||||--- TRANSACTION ADAPTER
        showAllTransactions();
        //showTrans();


        //||||||||||||||||||---EXPENSE FIELD ADAPTER START
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





        //||||||||||||||||||---ALL BUTTON ON-CLICK FUNC IMPLEMENTATION
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

        reportBTN.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(MainActivity.this,Report.class);
                startActivity(intent);
            }
        });

        lendBTN.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(MainActivity.this,LendBorrowManage.class);
                intent.putExtra("fieldName","Lend");
                intent.putExtra("fieldIcon",R.drawable.pic14);
                intent.putExtra("returnWhat","Pick a Collection Date");
                startActivity(intent);
            }
        });

        borrowBTN.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(MainActivity.this,LendBorrowManage.class);
                intent.putExtra("fieldName","Borrow");
                intent.putExtra("fieldIcon",R.drawable.pic13);
                intent.putExtra("returnWhat","Pick a Return Date");
                startActivity(intent);
            }
        });

        refreshBTN.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Toast.makeText(MainActivity.this, "Refresh..", Toast.LENGTH_SHORT).show();
                currentBalance=0;
                showAllTransactions();
            }
        });
    }

    //SIGN OUT BTN CLICK
    private void SignOutUser() {
        gsc.signOut().addOnCompleteListener(new OnCompleteListener<Void>() {
            @Override
            public void onComplete(@NonNull Task<Void> task) {
                Toast.makeText(MainActivity.this, "Signed Out", Toast.LENGTH_SHORT).show();
                userNameTV.setText("Hello Cypher Stark");
            }
        });
    }



    @Override
    protected void onResume() {
        super.onResume();

        currentBalance = 0;
        transactionReportRV = findViewById(R.id.transactionRV);
        currentBalanceTV = findViewById(R.id.currentBalanceTV);


        //TRANSACTION ADAPTER
        showAllTransactions();
        //showTrans();
    }
}