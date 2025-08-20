package com.example.basilis.nursingapp;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;


/**
 * A simple {@link Fragment} subclass.
 * Activities that contain this fragment must implement the
 * {@link FragmentLink.OnFragmentInteractionListener} interface
 * to handle interaction events.
 * Use the {@link FragmentLink#newInstance} factory method to
 * create an instance of this fragment.
 */
public class FragmentLink extends Fragment {
    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    private OnFragmentInteractionListener mListener;

    public FragmentLink() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment FragmentLink.
     */
    // TODO: Rename and change types and number of parameters
    public static FragmentLink newInstance(String param1, String param2) {
        FragmentLink fragment = new FragmentLink();
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
        View view= inflater.inflate(R.layout.xrisimoi_sindesmoi, container, false);

        // NOTE : We are calling the onFragmentInteraction() declared in the MainActivity
        // ie we are sending "Fragment 1" as title parameter when fragment1 is activated
        if (mListener != null) {
            mListener.onFragmentInteraction("Χρήσιμοι Σύνδεσμοι");
        }

        ImageView im1=(ImageView) view.findViewById(R.id.vathmoi);
        im1.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View v) {
                Intent browserIntent = new Intent(Intent.ACTION_VIEW, Uri.parse("https://gmweb.teiep.gr/unistudent/login.asp?mnuID=student"));
                startActivity(browserIntent);
            }
        });
        ImageView im2=(ImageView) view.findViewById(R.id.moodle);
        im2.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View v) {
                Intent browserIntent = new Intent(Intent.ACTION_VIEW, Uri.parse("http://moodle.ioa.teiep.gr/"));
                startActivity(browserIntent);
            }
        });
        ImageView im3=(ImageView) view.findViewById(R.id.paso);
        im3.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View v) {
                Intent browserIntent = new Intent(Intent.ACTION_VIEW, Uri.parse("https://submit-academicid.minedu.gov.gr/"));
                startActivity(browserIntent);
            }
        });
        ImageView im4=(ImageView) view.findViewById(R.id.dasta);
        im4.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View v) {
                Intent browserIntent = new Intent(Intent.ACTION_VIEW, Uri.parse("http://dasta.teiep.gr/"));
                startActivity(browserIntent);
            }
        });
        ImageView im5=(ImageView) view.findViewById(R.id.eudoxos);
        im5.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View v) {
                Intent browserIntent = new Intent(Intent.ACTION_VIEW, Uri.parse("http://eudoxus.gr/"));
                startActivity(browserIntent);
            }
        });
        ImageView im6=(ImageView) view.findViewById(R.id.modip);
        im6.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View v) {
                Intent browserIntent = new Intent(Intent.ACTION_VIEW, Uri.parse("http://modip.teiep.gr/"));
                startActivity(browserIntent);
            }
        });
        ImageView im7=(ImageView) view.findViewById(R.id.tei);
        im7.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View v) {
                Intent browserIntent = new Intent(Intent.ACTION_VIEW, Uri.parse("http://www.teiep.gr/"));
                startActivity(browserIntent);
            }
        });
        ImageView im8=(ImageView) view.findViewById(R.id.diavgeia);
        im8.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View v) {
                Intent browserIntent = new Intent(Intent.ACTION_VIEW, Uri.parse("https://et.diavgeia.gov.gr/f/teiep"));
                startActivity(browserIntent);
            }
        });
        ImageView im9=(ImageView) view.findViewById(R.id.Uregister);
        im9.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View v) {
                Intent browserIntent = new Intent(Intent.ACTION_VIEW, Uri.parse("https://mypassword.teiep.gr/reset_password.php"));
                startActivity(browserIntent);
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
