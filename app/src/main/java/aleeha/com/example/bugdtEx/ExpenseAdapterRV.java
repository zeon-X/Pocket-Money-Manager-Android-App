package aleeha.com.example.bugdtEx;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

public class ExpenseAdapterRV extends RecyclerView.Adapter<ExpenseAdapterRV.ExpenseViewHolder> {

    Context context;
    String[] fieldNames;
    int [] images;

    private static ClickListener clicklistenerVar;

    public ExpenseAdapterRV(Context context, String[] fieldNames, int[] images) {
        this.context = context;
        this.fieldNames = fieldNames;
        this.images = images;
    }


    @NonNull
    @Override
    //obj of expense viewHolder
    public ExpenseViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

        LayoutInflater layoutInflater = LayoutInflater.from(context);
        View view = layoutInflater.inflate(R.layout.expense_fields_sample_view,parent,false);

        return new ExpenseViewHolder(view);
        //xml -> java file -> converted to view -> passed to ExpenseViewHolder class
    }

    @Override
    public void onBindViewHolder(@NonNull ExpenseViewHolder holder, int position) {

        holder.fieldNameTV.setText(fieldNames[position]);
        holder.fieldImageIV.setImageResource(images[position]);

    }

    @Override
    public int getItemCount() {
        return fieldNames.length;
    }

    public class ExpenseViewHolder  extends RecyclerView.ViewHolder implements View.OnClickListener{

        TextView fieldNameTV;
        ImageView fieldImageIV;

        public ExpenseViewHolder(@NonNull View itemView) { // returned view will be here in "itemView"
            super(itemView);

            fieldNameTV = itemView.findViewById(R.id.expanse_name);
            fieldImageIV = itemView.findViewById(R.id.expanse_icon);
            // now we need to go to "onBindViewHolder" for binding

            itemView.setOnClickListener(this);

        }

        @Override
        public void onClick(View view) {
            clicklistenerVar.onItemClick(getAdapterPosition(),view);
        }
    }



    // implementing interface
    public interface ClickListener{
        void onItemClick(int position, View view);
    }

    public void setOnItemClickListener(ClickListener clickListener){
        ExpenseAdapterRV.clicklistenerVar = clickListener;
    }
}
// viewHolder
// holds a single view || freeze it || make changes