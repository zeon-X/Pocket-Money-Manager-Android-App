package aleeha.com.example.bugdtEx;

import android.content.Context;
import android.os.Bundle;
import androidx.fragment.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.Toast;

import com.google.android.material.bottomsheet.BottomSheetDialogFragment;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link ResetFragment#newInstance} factory method to
 * create an instance of this fragment.
 *
 */
public class ResetFragment extends BottomSheetDialogFragment {

    LinearLayout ll_resetBTN;
    Context context;

    public ResetFragment(Context context) {
        this.context = context;
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
     * @return A new instance of fragment ResetFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static ResetFragment newInstance(String param1, String param2) {
        ResetFragment fragment = new ResetFragment();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }

    public ResetFragment() {
        // Required empty public constructor
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
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_reset, container, false);
        ll_resetBTN = view.findViewById(R.id.ResetBtn);
        ll_resetBTN.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                DataBaseTrans db_trans = new DataBaseTrans(context);
                boolean b1 = db_trans.delete_db();
                DatabaseLendBorrow db_lb = new DatabaseLendBorrow(context);
                boolean b = db_lb.delete_db();
                Toast.makeText(context, "Reset Successful", Toast.LENGTH_SHORT).show();
                Toast.makeText(context, "All data has been Erased!", Toast.LENGTH_SHORT).show();
                dismiss();
            }
        });
        return view;
    }
}