package aleeha.com.example.bugdtEx;

import android.content.Context;
import android.graphics.Color;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.Locale;

public class LendBorrowReportAdapterRV extends RecyclerView.Adapter<LendBorrowReportAdapterRV.MyViewHolder> {

    Context context;
    String[] personName, issueDate, returnDate,note;
    int [] lendAmount;

    //constructor
    public LendBorrowReportAdapterRV(Context context, String[] personName, String[] issueDate, String[] returnDate, String[] note, int[] lendAmount) {
        this.context = context;
        this.personName = personName;
        this.issueDate = issueDate;
        this.returnDate = returnDate;
        this.note = note;
        this.lendAmount = lendAmount;
    }



    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

        LayoutInflater layoutInflater = LayoutInflater.from(context);
        View view = layoutInflater.inflate(R.layout.lend_borrow_report_sample_view, parent, false);

        return new MyViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHolder holder, int i) {
        holder.tv_personName.setText("NAME: "+personName[i].toUpperCase(Locale.ROOT));
        holder.tv_issueDate.setText("Issue Date: "+issueDate[i]);
        holder.tv_returnDate.setText("Return Date: "+returnDate[i]);
        holder.tv_note.setText("Note: "+note[i]);
        if(lendAmount[i]<0){
            holder.tv_amount.setText("BDT" + Integer.toString(-1*lendAmount[i]) + ".00");
            holder.tv_amount.setTextColor(Color.parseColor("#FF0000"));
        }
        else{
            holder.tv_amount.setText("BDT" + Integer.toString(lendAmount[i]) + ".00");
            holder.tv_amount.setTextColor(Color.parseColor("#00FF00"));
        }

    }

    @Override
    public int getItemCount() {
        return lendAmount.length;
    }

    public class MyViewHolder extends RecyclerView.ViewHolder {

        TextView tv_personName,tv_issueDate, tv_returnDate,  tv_note,tv_amount;

        public MyViewHolder(@NonNull View itemView) {
            super(itemView);

            tv_personName = itemView.findViewById(R.id.personNameTV);
            tv_issueDate = itemView.findViewById(R.id.issueDateTV);
            tv_returnDate = itemView.findViewById(R.id.returnDateTV);
            tv_note = itemView.findViewById(R.id.noteTV);
            tv_amount = itemView.findViewById(R.id.issueAmountTV);
        }
    }
}
