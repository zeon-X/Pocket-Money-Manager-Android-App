package aleeha.com.example.bugdtEx;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.DialogInterface;
import android.graphics.Color;
import android.os.Bundle;
import android.view.View;
import android.widget.Toast;

import java.util.List;

public class SeeAllTransaction extends AppCompatActivity {

    private RecyclerView transactionReportRV;
    TransactionAdapterRV transactionAdapter;

    // local data
    int [] images = {
            R.drawable.pic4,R.drawable.pic6,R.drawable.pic8,R.drawable.pic7,R.drawable.pic9,
            R.drawable.pic10,R.drawable.pic11,R.drawable.pic12,R.drawable.pic13,R.drawable.pic3,
            R.drawable.pic2,R.drawable.pic1,R.drawable.pic16,R.drawable.pic14
    };
    int addMoneyIcon = R.drawable.pic15,lendIcon=R.drawable.lending;



    public void showAllTransactions(){
        DataBaseTrans dbTrans = new DataBaseTrans(SeeAllTransaction.this);
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
            else img[j] = images[everyTrans.get(i).getPosition()];
            tDetails[j] = everyTrans.get(i).getDescription();
            ++j;
        }


        TransactionAdapterRV transactionAdapter =
                new TransactionAdapterRV(SeeAllTransaction.this, fn, td, tt, ta, img, tDetails);

        transactionAdapter.setOnItemClickListener(new TransactionAdapterRV.ClickListener() {
            @Override
            public void onItemClick(int i, View view) {
                DeleteOptionBottomSheetFragment DOBSF = new DeleteOptionBottomSheetFragment(SeeAllTransaction.this,id[i],fn[i],tDetails[i],td[i],tt[i],ta[i]);
                DOBSF.show(getSupportFragmentManager(),DOBSF.getTag());
                showAllTransactions();
            }
        });

        transactionReportRV.setAdapter(transactionAdapter);
        transactionReportRV.setLayoutManager(new LinearLayoutManager(this));

//        return transactionAdapter;
    }


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_see_all_transaction);

        //||||||||||||||||||--- TRANSACTION ADAPTER
        transactionReportRV = findViewById(R.id.transactionRV);
        showAllTransactions();
//        transactionAdapter = getTransactionAdapter();
//        transactionReportRV.setAdapter(transactionAdapter);
//        transactionReportRV.setLayoutManager(new LinearLayoutManager(this));
    }


    @Override
    protected void onResume() {
        super.onResume();

        //||||||||||||||||||--- TRANSACTION ADAPTER
        transactionReportRV = findViewById(R.id.transactionRV);
        showAllTransactions();
//        transactionAdapter = getTransactionAdapter();
//        transactionReportRV.setAdapter(transactionAdapter);
//        transactionReportRV.setLayoutManager(new LinearLayoutManager(this));
    }
}