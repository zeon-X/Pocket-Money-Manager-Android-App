package aleeha.com.example.bugdtEx;

import android.content.Context;
import android.graphics.Color;
import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.material.bottomsheet.BottomSheetDialogFragment;

import java.util.Locale;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link DeleteOptionBottomSheetFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class DeleteOptionBottomSheetFragment extends BottomSheetDialogFragment {

    Context context;
    String fieldName,note,tt,transDate; int transAmount;
    TextView tv_fn,tv_note,tv_td,tv_tt,tv_ta;
    LinearLayout ll_dltBtn,ll_editBtn, ll_shareBtn;

    public DeleteOptionBottomSheetFragment() {
        // Required empty public constructor
        fieldName = "fieldName";
        note = "note";
        transDate = "transDate";
        transAmount =0;
    }

    public DeleteOptionBottomSheetFragment(Context context, String fieldName, String note, String transDate, String tt,int transAmount) {
        this.context = context;
        this.fieldName = fieldName;
        this.note = note;
        this.tt = tt;
        this.transDate = transDate;
        this.transAmount = transAmount;
    }

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;


    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment DeleteOptionBottomSheetFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static DeleteOptionBottomSheetFragment newInstance(String param1, String param2) {
        DeleteOptionBottomSheetFragment fragment = new DeleteOptionBottomSheetFragment();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);

        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mParam1 = getArguments().getString(ARG_PARAM1);
            mParam2 = getArguments().getString(ARG_PARAM2);
        }




    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState)  {



        // Inflate the layout for this fragment
        View view =  inflater.inflate(R.layout.fragment_delete_option_bottom_sheet, container, false);
        tv_fn = view.findViewById(R.id.fieldNameTV);
        tv_note = view.findViewById(R.id.transNoteTV);
        tv_ta = view.findViewById(R.id.transAmountTV);
        tv_td = view.findViewById(R.id.transDateTV);
        ll_dltBtn = view.findViewById(R.id.deleteBtn);
        ll_editBtn = view.findViewById(R.id.editBtn);
        ll_shareBtn = view.findViewById(R.id.shareBtn);

        tv_fn.setText(""+fieldName.toUpperCase(Locale.ROOT));
        tv_note.setText("Note: "+note);
        if(transAmount<0) {
            tv_ta.setText("- BDT " + Integer.toString(-1*transAmount)+".00");
            tv_ta.setTextColor(Color.parseColor("#FF0000"));
        }
        else {
            tv_ta.setText("+ BDT " + Integer.toString(transAmount)+".00");
            tv_ta.setTextColor(Color.parseColor("#00FF00"));
        }
        tv_td.setText("Date: "+transDate + "\nTime: " + tt);

        ll_dltBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Toast.makeText(context, "dltBtn Clicked...", Toast.LENGTH_SHORT).show();
            }
        });
        ll_editBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Toast.makeText(context, "editBtn Clicked...", Toast.LENGTH_SHORT).show();
            }
        });
        ll_shareBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Toast.makeText(context, "shareBtn Clicked...", Toast.LENGTH_SHORT).show();
            }
        });


        return view;
    }



}