package aleeha.com.example.bugdtEx;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.Toast;

public class Report extends AppCompatActivity {

    private LinearLayout dailyReportBTN,weeklyReportBTN,monthlyReportBTN,lendReportBTN,borrowReportBTN,resetBTN;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_report);

        dailyReportBTN = findViewById(R.id.dailyReportLL);
//        weeklyReportBTN = findViewById(R.id.weeklyReportLL);
        monthlyReportBTN = findViewById(R.id.monthlyReportLL);
        lendReportBTN = findViewById(R.id.lendReportLL);
        borrowReportBTN = findViewById(R.id.borrowReportLL);
        resetBTN = findViewById(R.id.resetEveryThingLL);

        dailyReportBTN.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent  = new Intent(Report.this,DailyReport.class);
                startActivity(intent);
            }
        });


//        weeklyReportBTN.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//                Toast.makeText(Report.this, "Function not implemented Yet", Toast.LENGTH_SHORT).show();
//            }
//        });
        monthlyReportBTN.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent  = new Intent(Report.this,MonthlyReport.class);
                startActivity(intent);
            }
        });
        lendReportBTN.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(Report.this,LendBorrowReport.class);
                intent.putExtra("cat",1);
                startActivity(intent);
            }
        });
        borrowReportBTN.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(Report.this,LendBorrowReport.class);
                intent.putExtra("cat",0);
                startActivity(intent);
            }
        });

        resetBTN.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                ResetFragment rf = new ResetFragment(Report.this);
                rf.show(getSupportFragmentManager(),rf.getTag());
            }
        });

    }
}