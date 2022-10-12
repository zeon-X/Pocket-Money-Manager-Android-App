package aleeha.com.example.bugdtEx;

import android.content.Context;
import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

public class TransactionAdapterRV extends RecyclerView.Adapter<TransactionAdapterRV.MyViewHolder> {

    Context context;
    String [] transName,transDate,transTime,transDetails;
    int []transAmount;
    int [] icons;

    private static ClickListener clicklistener;

    public TransactionAdapterRV(Context context, String[] transName, String[] transDate, String[] transTime, int[] transAmount, int[] icons, String[] transDetails) {
        this.context = context;
        this.transName = transName;
        this.transDate = transDate;
        this.transTime = transTime;
        this.transAmount = transAmount;
        this.icons = icons;
        this.transDetails=transDetails;
    }

    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

        LayoutInflater layoutInflater = LayoutInflater.from(context);
        View view = layoutInflater.inflate(R.layout.transaction_activity,parent,false);

        return new MyViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHolder holder, int position) {

        holder.transNameTV.setText(transName[position]);
        holder.transDateTV.setText(transDate[position] + " " + transTime[position]);
        holder.transactionDetailsTV.setText(transDetails[position]);
        holder.transIconIV.setImageResource(icons[position]);
        
        if(transAmount[position]<0){
            holder.transAmountTV.setTextColor(Color.parseColor("#FF0000"));
            holder.transAmountTV.setText("- BDT"+Integer.toString(-1*transAmount[position]));
        }
        else{
            holder.transAmountTV.setTextColor(Color.parseColor("#00FF00"));
            holder.transAmountTV.setText("+ BDT"+Integer.toString(transAmount[position]));
        }
    }

    @Override
    public int getItemCount() {
        return transName.length;
    }

    public class MyViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {

        TextView transNameTV,transDateTV,transAmountTV,transactionDetailsTV;
        ImageView transIconIV;

        public MyViewHolder(@NonNull View itemView) {
            super(itemView);

            transNameTV = itemView.findViewById(R.id.transactionFieldNameTV);
            transDateTV = itemView.findViewById(R.id.transactionDateTV);
            transAmountTV = itemView.findViewById(R.id.transactionAmountTV);

            transIconIV = itemView.findViewById(R.id.transactionIconIV);
            transactionDetailsTV = itemView.findViewById(R.id.transactionDetailsTV);

            itemView.setOnClickListener(this);
        }

        @Override
        public void onClick(View view) {
            clicklistener.onItemClick(getAdapterPosition(),view);
        }
    }

    // implementing interface
    public interface ClickListener{
        void onItemClick(int position, View view);
    }

    public void setOnItemClickListener(ClickListener clickListener){
        TransactionAdapterRV.clicklistener = clickListener;
    }
}
