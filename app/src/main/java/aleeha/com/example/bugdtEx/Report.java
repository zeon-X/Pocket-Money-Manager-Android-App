package aleeha.com.example.bugdtEx;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.Toast;

import com.google.android.gms.auth.api.Auth;
import com.google.android.gms.auth.api.signin.GoogleSignInOptions;
import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.api.GoogleApi;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.common.api.ResultCallback;
import com.google.android.gms.common.api.Status;
import com.google.firebase.auth.FirebaseAuth;

public class Report extends AppCompatActivity {

    private LinearLayout dailyReportBTN,weeklyReportBTN,monthlyReportBTN,lendReportBTN,borrowReportBTN,resetBTN,logoutBTN;

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
        logoutBTN = findViewById(R.id.logoutLL);

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

        logoutBTN.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                FirebaseAuth.getInstance().signOut();

                Toast.makeText(Report.this, "Logout!", Toast.LENGTH_SHORT).show();
                Intent intent = new Intent(Report.this,Intro.class);
                startActivity(intent);
            }
        });

    }

}