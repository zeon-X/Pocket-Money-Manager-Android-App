package aleeha.com.example.bugdtEx;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.text.format.DateFormat;
import android.widget.TextView;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Date;
import java.util.List;

public class MonthlyReport extends AppCompatActivity {

    private TextView tv_addMoney,tv_expenseMoney,tv_monthTransAct;
    private RecyclerView rv_monthlyReport;

    private DataBaseTrans db_trans;
    private List<TransactionModel> all_transactions,modified_trans;
    String [] fieldNames;
    int [] transaction;
    int totalExp = 0,totalAdded=0,totalSave=0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_monthly_report);
        fieldNames = getResources().getStringArray(R.array.expense_filed_names);
        int fieldLength = fieldNames.length;
        transaction = new int[fieldLength];
        tv_addMoney = findViewById(R.id.reportAddMoneyTV);
        tv_expenseMoney = findViewById(R.id.reportExpenseTV);
        tv_monthTransAct = findViewById(R.id.monthTransActivity);
        rv_monthlyReport = findViewById(R.id.monthlyReportRV);

        db_trans = new DataBaseTrans(MonthlyReport.this);
        all_transactions = db_trans.getEveryOne();

        String dateNow = "",  monthNow="";
        if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.O) {
            DateTimeFormatter dateFormat = DateTimeFormatter.ofPattern("MMM dd,yyyy");
            DateTimeFormatter monthFormat = DateTimeFormatter.ofPattern("MMMM, yyyy");
            LocalDateTime newTime = LocalDateTime.now();
            dateNow = newTime.format(dateFormat);
            monthNow = newTime.format(monthFormat);
        }
        else{
            Date d = new Date();
            dateNow  = DateFormat.format("MMM dd,yyyy", d.getTime()).toString();
            monthNow  = DateFormat.format("MMMM, yyyy", d.getTime()).toString();
        }
        tv_monthTransAct.setText(monthNow + "'s Activity");


        int n = all_transactions.size();
        int len = dateNow.length();
        boolean flag=true;
        // for all the transaction
        for(int i=0;i<n;++i){
            flag = true;
            String trans_date = all_transactions.get(i).getDate();

            //checking if the month is same
            for(int j=0;j<len;++j){
                if(j==3 || j==4 || j==5 || j==6) continue;
                if(trans_date.charAt(j)!=dateNow.charAt(j)) {
                    flag=false; break;
                }
            }
            if(flag && all_transactions.get(i).isMoneyAdded()==false){ //same month (flag==true) || now check the expense field and add the amount
                String fn = all_transactions.get(i).getExpenseField();
                for(int j=0;j<fieldLength;++j){
                    int len2 = fieldNames[j].length();
                    if(len2 == fn.length()){
                        int k=0;
                        for(k=0; k<len2;++k){
                            if(fn.charAt(k)!=fieldNames[j].charAt(k)) {
                                break;
                            }
                        }
                        if(k==len2){
                            transaction[j]+=all_transactions.get(i).getTransAmount();
                            if(all_transactions.get(i).getPosition()!=8) {
                                totalExp += all_transactions.get(i).getTransAmount();
                            }
                            else {
                                totalSave += all_transactions.get(i).getTransAmount();
                            }
                        }
                    }
                }
            }
            else if(flag && all_transactions.get(i).isMoneyAdded()==true && all_transactions.get(i).getPosition()!=-500){
                    totalAdded += all_transactions.get(i).getTransAmount();
            }
        }

        tv_addMoney.setText(Integer.toString(totalAdded+totalSave));
        tv_expenseMoney.setText(Integer.toString(-1*totalExp));

        MonthlyReportAdapterRV monthlyReportAdapterRV = new MonthlyReportAdapterRV(MonthlyReport.this, fieldNames, transaction,totalExp);
        rv_monthlyReport.setAdapter(monthlyReportAdapterRV);
        rv_monthlyReport.setLayoutManager(new LinearLayoutManager(this));
    }
}