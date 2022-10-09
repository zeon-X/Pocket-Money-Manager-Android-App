package aleeha.com.example.bugdtEx;

import static java.lang.Integer.parseInt;

import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;

import android.graphics.Color;
import android.os.Build;
import android.os.Bundle;
import android.text.Editable;
import android.text.format.DateFormat;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Date;
import java.util.List;

public class AddTransaction extends AppCompatActivity {
    ImageView catIconIV;
    TextView catNameTV,dateTV,availAmountTV;
    EditText transAmountET, transDetailsET;
    Button addTransBTN;

    int position=-2;
    int currentBalance=0;

    @RequiresApi(api = Build.VERSION_CODES.O)
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_transaction);
        catIconIV = findViewById(R.id.transIconIV);
        catNameTV = findViewById(R.id.transNameTV);
        dateTV = findViewById(R.id.transDateTV);
        availAmountTV = findViewById(R.id.availAmountTV);
        transAmountET = findViewById(R.id.transAmountET);
        transDetailsET = findViewById(R.id.transDescriptionET);
        addTransBTN = findViewById(R.id.addTransBTN);


        DataBaseTrans dbTrans = new DataBaseTrans(AddTransaction.this);
        List<TransactionModel> everyTrans = dbTrans.getEveryOne();
        int len = everyTrans.size();
        for(int i=0; i < len; ++i) currentBalance+=everyTrans.get(i).getTransAmount();


        String dateNow = "";
        String timeNow = "";
        if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.O) {
            DateTimeFormatter dateFormat = DateTimeFormatter.ofPattern("MMM dd,yyyy");
            DateTimeFormatter timeFormat = DateTimeFormatter.ofPattern("HH:mm");
            LocalDateTime newTime = LocalDateTime.now();
            dateNow = newTime.format(dateFormat);
            timeNow = newTime.format(timeFormat);
            dateTV.setText(dateNow + " " + timeNow);
        }
        else{
            Date d = new Date();
            dateNow  = DateFormat.format("MMM d,yyyy", d.getTime()).toString();
            dateTV.setText(dateNow);
        }




        // RECEIVING PUT EXTRA DATA
        Bundle bundle = getIntent().getExtras();
        if(bundle!=null){
            availAmountTV.setText("Available Balance: BDT " + currentBalance + ".00");
            if(bundle.getInt("addMoney")==1){
                catIconIV.setImageResource(R.drawable.pic15);
                catNameTV.setText("Add Money");
                addTransBTN.setText("Add Money");
                addTransBTN.setTextColor(Color.parseColor("#C5FF6C"));
                position = -1;
            }
            else{
                position = bundle.getInt("position");
                catIconIV.setImageResource(bundle.getInt("fieldIcon"));
                catNameTV.setText(bundle.getString("fieldName"));
                addTransBTN.setTextColor(Color.parseColor("#FF0000"));
            }
        }



        // add transaction btn function
        String finalDateNow = dateNow;
        String finalTimeNow = timeNow;
        addTransBTN.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                TransactionModel transactionModel = null;
                try {
                        // fieldName, amount, date, description, is added
                        if(bundle!=null && bundle.getInt("addMoney")==1){
                             transactionModel = new TransactionModel(
                                     -1,
                                    "Add Money",
                                    1*parseInt(transAmountET.getText().toString()),
                                    finalDateNow.toString()+"",
                                    finalTimeNow.toString()+"",
                                    transDetailsET.getText().toString()+"",
                                    true,
                                     1*position
                                    );
                        } else if (bundle!=null){
                             transactionModel = new TransactionModel(
                                     -1,
                                    bundle.getString("fieldName").toString()+"",
                                    -1*parseInt(transAmountET.getText().toString()),
                                     finalDateNow.toString()+"",
                                     finalTimeNow.toString()+"",
                                    transDetailsET.getText().toString()+"",
                                    false,
                                     1*position
                                    );
                        }
                        //Toast.makeText(AddTransaction.this, transactionModel.toString()+"", Toast.LENGTH_SHORT).show();
                } catch (Exception e){
                    Toast.makeText(AddTransaction.this, "Put some numbers & try again...", Toast.LENGTH_SHORT).show();
                }

                DataBaseTrans dataBaseTrans = new DataBaseTrans(AddTransaction.this);
                boolean b = dataBaseTrans.addOne(transactionModel);
                if(b==true) {
                    Toast.makeText(AddTransaction.this, "Success", Toast.LENGTH_SHORT).show();
                    finish();
                }
                else Toast.makeText(AddTransaction.this, "Error", Toast.LENGTH_SHORT).show();

            }
        });

    }
}