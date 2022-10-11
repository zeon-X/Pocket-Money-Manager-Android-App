package aleeha.com.example.bugdtEx;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

public class MonthlyReportAdapterRV extends RecyclerView.Adapter<MonthlyReportAdapterRV.MyViewHolder> {

    private Context context;
    private String[] fieldName;
    private int [] amounts;
    private int totalExp;
    int [] icons = {
            R.drawable.pic4,R.drawable.pic6,R.drawable.pic8,R.drawable.pic7,R.drawable.pic9,
            R.drawable.pic10,R.drawable.pic11,R.drawable.pic12,R.drawable.pic13,R.drawable.pic3,
            R.drawable.pic2,R.drawable.pic1,R.drawable.pic16,R.drawable.pic14
    };

    public MonthlyReportAdapterRV(Context context, String[] fieldName, int[] amounts,int totalExp) {
        this.context = context;
        this.fieldName = fieldName;
        this.amounts = amounts;
        this.totalExp = totalExp;
    }

    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater layoutInflater = LayoutInflater.from(context);
        View view = layoutInflater.inflate(R.layout.sample_view_monthly_report,parent,false);
        return new MyViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHolder holder, int i) {
        double pc = ((double) amounts[i]/ (double) totalExp) * 100.00;
        holder.tv_expField.setText(fieldName[i] + " (" + Integer.toString((int) pc)+"%)");
        holder.tv_amount.setText("BDT "+Integer.toString(-1*amounts[i])+".00");
        holder.pb_percentage.setProgress((int)pc);
        holder.iv_icon.setImageResource(icons[i]);
    }

    @Override
    public int getItemCount() {
        return fieldName.length;
    }

    public class MyViewHolder extends RecyclerView.ViewHolder {
        TextView tv_expField, tv_amount;
        ProgressBar pb_percentage;
        ImageView iv_icon;
        public MyViewHolder(@NonNull View itemView) {
            super(itemView);
            tv_amount = itemView.findViewById(R.id.reportExpenseAmountTV);
            tv_expField = itemView.findViewById(R.id.reportExpenseFieldTV);
            pb_percentage = itemView.findViewById(R.id.percentagePB);
            iv_icon = itemView.findViewById(R.id.transactionIconIV);
        }
    }
}
