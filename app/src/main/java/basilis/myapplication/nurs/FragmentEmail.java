package basilis.myapplication.nurs;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.text.Html;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;


/**
 * A simple {@link Fragment} subclass.
 * Activities that contain this fragment must implement the
 * {@link FragmentEmail.OnFragmentInteractionListener} interface
 * to handle interaction events.
 * Use the {@link FragmentEmail#newInstance} factory method to
 * create an instance of this fragment.
 */
public class FragmentEmail extends Fragment {
    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    private OnFragmentInteractionListener mListener;

    public FragmentEmail() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment FragmentEmail.
     */
    // TODO: Rename and change types and number of parameters
    public static FragmentEmail newInstance(String param1, String param2) {
        FragmentEmail fragment = new FragmentEmail();
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
                             Bundle savedInstanceState) {
        View view= inflater.inflate(R.layout.email, container, false);

        // NOTE : We are calling the onFragmentInteraction() declared in the MainActivity
        // ie we are sending "Fragment 1" as title parameter when fragment1 is activated
        if (mListener != null) {
            mListener.onFragmentInteraction("Αποστολή Email Στο Τμήμα");
        }
        final EditText subject=(EditText) view.findViewById(R.id.subject);
        final EditText emailbody=(EditText) view.findViewById(R.id.email_body);
        ImageView im1=(ImageView) view.findViewById(R.id.send_email);
        im1.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View v) {
                Intent i=new Intent(Intent.ACTION_SEND);
                i.setType("type/plain");
                i.putExtra(Intent.EXTRA_EMAIL,new String[]{"basilismt8@gmail.com"});
                i.putExtra(Intent.EXTRA_SUBJECT, ""+Html.fromHtml(subject.getText().toString())+"");
                String myMessage=emailbody.getText().toString();
                i.putExtra(Intent.EXTRA_TEXT, Html.fromHtml(myMessage));
                try {
                    startActivity(Intent.createChooser(i,"Αποστολή Email..."));
                } catch(android.content.ActivityNotFoundException ex)
                {
                    Toast.makeText(getContext(),"Δεν υπάρχρει εγκατεστημένο πρόγραμμα διαχείρισης email.",Toast.LENGTH_SHORT).show();
                }
            }
        });

        return view;
    }

    // TODO: Rename method, update argument and hook method into UI event
    public void onButtonPressed(String title) {
        if (mListener != null) {
            mListener.onFragmentInteraction(title);
        }
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        if (context instanceof OnFragmentInteractionListener) {
            mListener = (OnFragmentInteractionListener) context;
        } else {
            throw new RuntimeException(context.toString()
                    + " must implement OnFragmentInteractionListener");
        }
    }

    @Override
    public void onDetach() {
        super.onDetach();
        mListener = null;
    }

    /**
     * This interface must be implemented by activities that contain this
     * fragment to allow an interaction in this fragment to be communicated
     * to the activity and potentially other fragments contained in that
     * activity.
     * <p>
     * See the Android Training lesson <a href=
     * "http://developer.android.com/training/basics/fragments/communicating.html"
     * >Communicating with Other Fragments</a> for more information.
     */
    public interface OnFragmentInteractionListener {
        // TODO: Update argument type and name
        public void onFragmentInteraction(String title);
    }
}
