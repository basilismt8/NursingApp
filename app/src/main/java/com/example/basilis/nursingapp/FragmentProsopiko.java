package com.example.basilis.nursingapp;

import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ListView;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;


/**
 * A simple {@link Fragment} subclass.
 * Activities that contain this fragment must implement the
 * {@link FragmentProsopiko.OnFragmentInteractionListener} interface
 * to handle interaction events.
 * Use the {@link FragmentProsopiko#newInstance} factory method to
 * create an instance of this fragment.
 */
public class FragmentProsopiko extends Fragment {
    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private String TAG = MainActivity.class.getSimpleName();
    String jsonStr1;
    private static String url2="http://nursing.ioa.teiep.gr/nursing-app/prosopiko.php?call=all";
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    private OnFragmentInteractionListener mListener;

    public FragmentProsopiko() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment FragmentProsopiko.
     */
    // TODO: Rename and change types and number of parameters
    public static FragmentProsopiko newInstance(String param1, String param2) {
        FragmentProsopiko fragment = new FragmentProsopiko();
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
        View view= inflater.inflate(R.layout.listview_pros, container, false);

        // NOTE : We are calling the onFragmentInteraction() declared in the MainActivity
        // ie we are sending "Fragment 1" as title parameter when fragment1 is activated
        if (mListener != null) {
            mListener.onFragmentInteraction("Ανθρώπινο Δυναμικό");
        }
        final ArrayList<Prosopiko> proslist = new ArrayList<Prosopiko>();
        ListView lv =(ListView)view.findViewById(R.id.list2);
        final HttpHandler gb = new HttpHandler();
        jsonStr1 = gb.makeServiceCall(url2);
        Log.e(TAG, "Response from url: " + jsonStr1);
        if (jsonStr1!=null)
        {
            try {
                JSONObject jsonObj=new JSONObject(jsonStr1);
                JSONArray prosopiko=jsonObj.getJSONArray("prosopiko");
                for (int i=0;i<prosopiko.length();i++)
                {
                    JSONObject a=prosopiko.getJSONObject(i);
                    String id=a.getString("id");
                    String title=a.getString("title");
                    String link=a.getString("link");
                    String type=a.getString("type");
                    Bitmap bm= BitmapFactory.decodeResource(getResources(),R.id.image_pros);
                    proslist.add(new Prosopiko(title,bm,type,link));
                }
            } catch (final JSONException e) {
                Log.e(TAG, "Json parsing error: " + e.getMessage());
                // TODO Auto-generated catch block
                e.printStackTrace();
            }
        }
        else
        {
            Log.e(TAG, "Couldn't get json from server.");
        }
        ProsAdapter adapter=new ProsAdapter(getContext(),android.R.layout.simple_list_item_1,proslist);
        lv.setAdapter(adapter);
        lv.setOnItemClickListener(new AdapterView.OnItemClickListener()
        {
            @Override
            public void onItemClick(AdapterView<?> parent, View view,
                                    int position, long id) {
                String test=proslist.get(position).link;
                String s=test.substring(0,5);
                if(s.equals("index"))
                {
                    HttpHandler.plinkid=proslist.get(position).link;
                    Intent I=new Intent(getContext(),ProsIntro.class);
                    startActivity(I);
                }
                else if(!s.equals("index"))
                {
                    Intent browserIntent=new Intent(Intent.ACTION_VIEW, Uri.parse(proslist.get(position).link));
                    startActivity(browserIntent);
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
