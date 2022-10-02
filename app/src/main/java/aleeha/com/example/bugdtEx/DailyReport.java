package aleeha.com.example.bugdtEx;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.text.format.DateFormat;
import android.widget.TextView;
import android.widget.Toast;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class DailyReport extends AppCompatActivity {

    DataBaseTrans dbTrans;

    private RecyclerView transactionReportRV;

    String[] fn,td,tt,tDetails;
    int[] ta,img;
    int totalAdded=0, totalExpense=0;
    int [] images = {
            R.drawable.pic4,R.drawable.pic6,R.drawable.pic8,R.drawable.pic7,R.drawable.pic9,
            R.drawable.pic10,R.drawable.pic11,R.drawable.pic12,R.drawable.pic13,R.drawable.pic3,
            R.drawable.pic2,R.drawable.pic1,R.drawable.pic14,R.drawable.pic15
    };
    String date_now,date_db;

    TransactionAdapterRV transactionAdapter;

    TextView totalAddedTV,totalExpenseTV;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_daily_report);

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


        dbTrans = new DataBaseTrans(DailyReport.this);
        List<TransactionModel> everyTrans = dbTrans.getEveryOne();

        ArrayList<Integer>pos = new ArrayList<Integer>();
        int len2 = everyTrans.size();
        int len=0;

        for(int i=0; i<len2; ++i){
            date_db = everyTrans.get(i).getDate();
            int cnt=0;
            for(int j=0;j<11;++j){
                if(date_now.charAt(j)!=date_db.charAt(j)){
                    cnt=-1; break;
                }
            }
            if(cnt==0){
                pos.add(i);
            }
        }

        len = pos.size();
//        Toast.makeText(this, Integer.toString(len), Toast.LENGTH_SHORT).show();

        fn = new String[len];
        td = new String[len];
        tt = new String[len];
        tDetails = new String[len];
        ta = new int[len];
        img = new int[len];

        int j=0;
        for(int i=0; i <len; ++i){
            j = pos.get(i);

            fn[i] = everyTrans.get(j).getExpenseField();
            ta[i] = everyTrans.get(j).getTransAmount();
            td[i] = everyTrans.get(j).getDate();
            tt[i] = everyTrans.get(j).getTime();
            tDetails[i] = everyTrans.get(j).getDescription();

            if(everyTrans.get(j).getPosition()>=0){
                img[i] = images[everyTrans.get(j).getPosition()];
            }
            else{
                img[i] = images[13];
            }

            if(ta[i]>=0) totalAdded+=ta[i];
            else totalExpense+=(ta[i]*-1);
        }

        totalAddedTV.setText("BDT "+Integer.toString(totalAdded) + ".00");
        totalExpenseTV.setText("BDT "+Integer.toString(totalExpense)+".00");


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