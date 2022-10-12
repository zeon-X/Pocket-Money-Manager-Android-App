package aleeha.com.example.bugdtEx;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.text.format.DateFormat;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class DailyReport extends AppCompatActivity {

    private RecyclerView transactionReportRV;
    private TextView tv_todayReport;
    int totalAdded=0, totalExpense=0;
    int [] images = {
            R.drawable.pic4,R.drawable.pic6,R.drawable.pic8,R.drawable.pic7,R.drawable.pic9,
            R.drawable.pic10,R.drawable.pic11,R.drawable.pic12,R.drawable.pic13,R.drawable.pic3,
            R.drawable.pic2,R.drawable.pic1,R.drawable.pic16,R.drawable.pic14
    };
    int addMoneyIcon = R.drawable.pic15,lendIcon = R.drawable.lending;
    String date_now,date_db;
    TransactionAdapterRV transactionAdapter;
    TextView totalAddedTV,totalExpenseTV;


    public void showAllTransactions(String x){
        DataBaseTrans dbTrans = new DataBaseTrans(DailyReport.this);
        List<TransactionModel> everyTrans = dbTrans.getEveryOne();
        totalAdded=0; totalExpense=0;

        ArrayList<Integer>pos = new ArrayList<Integer>();
        int len2 = everyTrans.size();

        for(int i=0; i<len2; ++i){
            date_db = everyTrans.get(i).getDate();
            int cnt=0;
            for(int j=0;j<11;++j){
                if(x.charAt(j)!=date_db.charAt(j)){
                    cnt=-1; break;
                }
            }
            if(cnt==0){
                pos.add(i);
            }
        }

        int len = pos.size();

        String[] fn = new String[len];
        String[] td = new String[len];
        String[] tt = new String[len];
        String[] tDetails = new String[len];
        int[] ta = new int[len];
        int[] img = new int[len];
        int[] id = new int[len];

        int j;
        for(int i=0; i <len; ++i){
            j = pos.get(i);
            fn[i] = everyTrans.get(j).getExpenseField();
            ta[i] = everyTrans.get(j).getTransAmount();
            td[i] = everyTrans.get(j).getDate();
            tt[i] = everyTrans.get(j).getTime();
            tDetails[i] = everyTrans.get(j).getDescription();
            id[i] = everyTrans.get(j).getId();
            if(everyTrans.get(j).getPosition()==-1) img[i] = addMoneyIcon;
            else if(everyTrans.get(j).getPosition()==-2) img[i] = lendIcon;
            else if(everyTrans.get(i).getPosition()==-500) img[i] = R.drawable.pic13;
            else img[i] = images[everyTrans.get(j).getPosition()];

            if(everyTrans.get(j).getPosition()==-1){
                totalAdded+=ta[i];
            }
            if(everyTrans.get(j).getPosition()>-1 && everyTrans.get(j).getPosition()!=8){
                totalExpense+=(ta[i]*-1);
            }
        }

        totalAddedTV.setText("BDT "+Integer.toString(totalAdded) + ".00");
        totalExpenseTV.setText("BDT "+Integer.toString(totalExpense)+".00");

        TransactionAdapterRV transactionAdapter =
                new TransactionAdapterRV(DailyReport.this, fn, td, tt, ta, img, tDetails);

        transactionAdapter.setOnItemClickListener(new TransactionAdapterRV.ClickListener() {
            @Override
            public void onItemClick(int i, View view) {
                Toast.makeText(DailyReport.this, fn[i] + " touched...", Toast.LENGTH_SHORT).show();
            }
        });

        transactionReportRV.setAdapter(transactionAdapter);
        transactionReportRV.setLayoutManager(new LinearLayoutManager(DailyReport.this));
    }




    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_daily_report);

        totalAdded=0; totalExpense=0;
        tv_todayReport = findViewById(R.id.todayReport);
        transactionReportRV = findViewById(R.id.transactionRV);
        totalExpenseTV = findViewById(R.id.totalExpense);
        totalAddedTV = findViewById(R.id.totalAdded);

        if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.O) {
            DateTimeFormatter dateFormat = DateTimeFormatter.ofPattern("MMM dd,yyyy");
            LocalDateTime newTime = LocalDateTime.now();
            date_now = newTime.format(dateFormat);
        }
        else{
            Date d = new Date();
            date_now  = DateFormat.format("MMM d,yyyy", d.getTime()).toString();
        }
        date_now = date_now.toString();
        date_now+="";
        tv_todayReport.setText("Daily Report: " + date_now);

        totalAdded=0; totalExpense=0;
        showAllTransactions(date_now);
    }

    @Override
    protected void onResume() {
        super.onResume();

        totalAdded=0; totalExpense=0;

        transactionReportRV = findViewById(R.id.transactionRV);
        totalExpenseTV = findViewById(R.id.totalExpense);
        totalAddedTV = findViewById(R.id.totalAdded);

        if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.O) {
            DateTimeFormatter dateFormat = DateTimeFormatter.ofPattern("MMM dd,yyyy");
            LocalDateTime newTime = LocalDateTime.now();
            date_now = newTime.format(dateFormat);
        }
        else{
            Date d = new Date();
            date_now  = DateFormat.format("MMM d,yyyy", d.getTime()).toString();
        }

        date_now = date_now.toString();
        date_now+="";
        totalAdded=0; totalExpense=0;
        showAllTransactions(date_now);
    }
}