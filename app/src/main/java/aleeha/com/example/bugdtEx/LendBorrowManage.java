package aleeha.com.example.bugdtEx;

import static java.lang.Integer.parseInt;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.graphics.Color;
import android.os.Bundle;
import android.text.format.DateFormat;
import android.view.View;
import android.widget.Button;
import android.widget.CalendarView;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Date;

public class LendBorrowManage extends AppCompatActivity {

    private Button btn_addTransaction;
    private EditText et_transDescription,et_name,et_amount;
    private TextView tv_date,tv_fieldName,tv_enterAmountToField,tv_todayDate;
    private ImageView iv_fieldLogo;
    private CalendarView cv_datePicker;
    String [] month = {"Jan","Feb","Mar","Apr","May","Jun","Jul","Aug","Sep","Oct","Nov","Dec"};
    String returnDate="",transNote="",toTransName="",fieldName="",dateNow = "",timeNow = "";;
    int transAmount=0,icon;

    DataBaseTrans dbTrans;
    DatabaseLendBorrow dbLB;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_lend_borrow_manage);

        cv_datePicker = findViewById(R.id.calenderPicker);
        btn_addTransaction = findViewById(R.id.addTransaction);
        et_amount = findViewById(R.id.transAmount);
        et_name = findViewById(R.id.toTransName);
        et_transDescription = findViewById(R.id.transNote);
        tv_date = findViewById(R.id.returnDate);
        tv_enterAmountToField = findViewById(R.id.enterAmountToField);
        tv_fieldName = findViewById(R.id.fieldName);
        tv_todayDate = findViewById(R.id.dateNow);
        iv_fieldLogo = findViewById(R.id.fieldLogo);


        Bundle bundle = getIntent().getExtras();
        if(bundle!=null){
            fieldName = bundle.getString("fieldName");
            icon = bundle.getInt("fieldIcon");
            returnDate = bundle.getString("returnWhat");

            tv_fieldName.setText(fieldName);
            tv_enterAmountToField.setText("Enter Amount to " + fieldName);
            tv_date.setText(returnDate);
            iv_fieldLogo.setImageResource(icon);


            if(fieldName.charAt(0)=='L'){
                tv_fieldName.setTextColor(Color.parseColor("#FF0000"));
                btn_addTransaction.setTextColor(Color.parseColor("#FF0000"));
                btn_addTransaction.setText("Lend Money");
            }
            else{
                tv_fieldName.setTextColor(Color.parseColor("#C5FF6C"));
                btn_addTransaction.setTextColor(Color.parseColor("#C5FF6C"));
                btn_addTransaction.setText("Borrow Money");
            }
        }


        cv_datePicker.setOnDateChangeListener(new CalendarView.OnDateChangeListener() {
            @Override
            public void onSelectedDayChange(@NonNull CalendarView calendarView, int i, int i1, int i2) {
                returnDate = month[i1] + " " + Integer.toString(i2) + "," + Integer.toString(i);
                tv_date.setText(returnDate);
            }
        });



        //DATE || NOW + TIME
        if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.O) {
            DateTimeFormatter dateFormat = DateTimeFormatter.ofPattern("MMM dd,yyyy");
            DateTimeFormatter timeFormat = DateTimeFormatter.ofPattern("HH:mm");
            LocalDateTime newTime = LocalDateTime.now();

            dateNow = newTime.format(dateFormat);
            timeNow = newTime.format(timeFormat);

            tv_todayDate.setText(dateNow + " " + timeNow);
        }
        else{
            Date d = new Date();
            dateNow  = DateFormat.format("MMM d,yyyy", d.getTime()).toString();
            tv_todayDate.setText(dateNow);
        }



        dbTrans = new DataBaseTrans(LendBorrowManage.this);
        dbLB = new DatabaseLendBorrow(LendBorrowManage.this);
        btn_addTransaction.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //Save in Transaction
                TransactionModel transactionModel = null;
                try {
                    if(fieldName.charAt(0)=='L'){
                        transactionModel = new TransactionModel(
                                -1,
                                "Lend",
                                -1*parseInt(et_amount.getText().toString()),
                                dateNow.toString()+"",
                                timeNow.toString()+"",
                                et_transDescription.getText().toString()+"",
                                false,
                                -2
                        );
                    }
                    else{
                        transactionModel = new TransactionModel(
                                -1,
                                "Borrow",
                                1*parseInt(et_amount.getText().toString()),
                                dateNow.toString()+"",
                                timeNow.toString()+"",
                                et_transDescription.getText().toString()+"",
                                true,
                                -1
                        );
                    }
                }catch (Exception e){
                    Toast.makeText(LendBorrowManage.this, "Error...", Toast.LENGTH_SHORT).show();
                }

                LendBorrowModel lendBorrowModel = null;
                try{
                    if(fieldName.charAt(0)=='L'){
                        lendBorrowModel =
                                new LendBorrowModel
                                        (-1,
                                        "Lend",
                                        -1*parseInt(et_amount.getText().toString()),
                                                et_name.getText().toString()+"",
                                        dateNow.toString()+"",
                                                returnDate+"",
                                                et_transDescription.getText().toString()+"",
                                                true
                                        );
                    }
                    else{
                        lendBorrowModel =
                                new LendBorrowModel
                                        (-1,
                                                "Borrow",
                                                1*parseInt(et_amount.getText().toString()),
                                                et_name.getText().toString()+"",
                                                dateNow.toString()+"",
                                                returnDate+"",
                                                et_transDescription.getText().toString()+"",
                                                true
                                        );
                    }
                }catch (Exception e){
                    Toast.makeText(LendBorrowManage.this, "Error...", Toast.LENGTH_SHORT).show();
                }

                boolean b1 = dbTrans.addOne(transactionModel);
                boolean b2 = dbLB.addOne(lendBorrowModel);
                if(b1==true && b2==true) {
                    Toast.makeText(LendBorrowManage.this, fieldName+" Success", Toast.LENGTH_SHORT).show();
                    finish();
                }
                else Toast.makeText(LendBorrowManage.this, fieldName+" Error", Toast.LENGTH_SHORT).show();

            }
        });

    }
}