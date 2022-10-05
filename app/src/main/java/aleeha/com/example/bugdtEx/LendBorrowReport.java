package aleeha.com.example.bugdtEx;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.graphics.Color;
import android.os.Bundle;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

public class LendBorrowReport extends AppCompatActivity {

    private RecyclerView rv_lbTransaction;
    private TextView tv_whatBalance,tv_balance;

    private DatabaseLendBorrow db_LendBorrow;
    private List<LendBorrowModel> db_res;
    private String[] personName,issueDate,returnDate,note;
    private int[] issueAmount;

    private int total = 0;
    private int isLend = -1;

    private LendBorrowReportAdapterRV lendBorrowReportAdapterRV;


    String[] listToString(List<String> list){
        int len  = list.size();
        String[] x = new String[len];
        int j=len-1;
        for(int i=0; i <len; ++i){
            x[i] = list.get(j);
            j--;
        }
        return x;
    }

    int[] listToInt(List<Integer> list){
        int len  = list.size();
        int[] x = new int[len];

        int j=len-1;
        for(int i=0; i <len; ++i){
            x[i] = list.get(j);
            j--;
        }
        return x;
    }


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_lend_borrow_report);

        rv_lbTransaction = findViewById(R.id.lbTransactionRV);
        tv_balance = findViewById(R.id.lbAmountTV);
        tv_whatBalance = findViewById(R.id.lbBalanceTV);


        //--------------data from bundle
        Bundle bundle = getIntent().getExtras();
        if(bundle!=null) isLend = bundle.getInt("cat");

        //--------------collect data from db
        db_LendBorrow = new DatabaseLendBorrow(LendBorrowReport.this);
        db_res = db_LendBorrow.getEveryOne(); //all the fetched data are here in the list

        int len_tem = db_res.size();

        List<String> pn = new ArrayList<String>();
        List<String> id = new ArrayList<String>();
        List<String> rd = new ArrayList<String>();
        List<String> n = new ArrayList<String>();
        List<Integer> ia = new ArrayList<Integer>();

        if(isLend == 1){
            for(int i=0; i <len_tem;  ++i){
                if(db_res.get(i).getAmount() < 0) {
                    pn.add(db_res.get(i).getPersonName());
                    id.add(db_res.get(i).getDate());
                    rd.add(db_res.get(i).getReturnDate());
                    n.add(db_res.get(i).getNote());
                    ia.add(db_res.get(i).getAmount());

                    total+=db_res.get(i).getAmount();
                }
            }
        }
        else{
            for(int i=0; i <len_tem;  ++i){
                if(db_res.get(i).getAmount() > 0) {
                    pn.add(db_res.get(i).getPersonName());
                    id.add(db_res.get(i).getDate());
                    rd.add(db_res.get(i).getReturnDate());
                    n.add(db_res.get(i).getNote());
                    ia.add(db_res.get(i).getAmount());

                    total+=db_res.get(i).getAmount();
                }
            }
        }
//
//
//


        //---------changing ui based on lend or borrow
        //BORROW
        if(isLend == 0){
            tv_balance.setText("BDT "+Integer.toString(total));
            tv_balance.setTextColor(Color.parseColor("#00FF00"));
            tv_whatBalance.setText("Borrow Balance");
        }
        //LEND
        else{
            tv_balance.setText("BDT "+ Integer.toString(-1*total));
            tv_balance.setTextColor(Color.parseColor("#FF0000"));
            tv_whatBalance.setText("Lend Balance");
        }






        personName =  listToString(pn);
        issueDate =listToString(id);
        returnDate = listToString(rd);
        note = listToString(n);
        issueAmount = listToInt(ia);



        //----------------setting adaptor to rv
        lendBorrowReportAdapterRV = new LendBorrowReportAdapterRV(LendBorrowReport.this, personName, issueDate, returnDate, note, issueAmount);
        rv_lbTransaction.setLayoutManager(new LinearLayoutManager(this));
        rv_lbTransaction.setAdapter(lendBorrowReportAdapterRV);

    }
}