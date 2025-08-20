package com.example.basilis.nursingapp;

import android.app.DownloadManager;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.webkit.WebView;
import android.widget.Button;
import android.widget.ImageView;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.regex.Matcher;
import java.util.regex.Pattern;


/**
 * A simple {@link Fragment} subclass.
 * Activities that contain this fragment must implement the
 * {@link FragmentSpoudes.OnFragmentInteractionListener} interface
 * to handle interaction events.
 * Use the {@link FragmentSpoudes#newInstance} factory method to
 * create an instance of this fragment.
 */
public class FragmentSpoudes extends Fragment {
    private String TAG = MainActivity.class.getSimpleName();
    public static String introtext=null;
    private String url = "http://nursing.ioa.teiep.gr/nursing-app/article.php?call=all";
    private String url2 ="http://nursing.ioa.teiep.gr/";
    private ProgressDialog proDialog;
    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    private OnFragmentInteractionListener mListener;

    private void startLoading(String message)
    {
        proDialog = new ProgressDialog(getContext());
        proDialog.setMessage(message);
        proDialog.setProgressStyle(ProgressDialog.STYLE_SPINNER);
        proDialog.setCancelable(false);
        proDialog.show();
    }

    private void stopLoading()
    {
        proDialog.dismiss();
        proDialog = null;
    }

    String downloadLinks(String text)
    {
        String regex = "((http:\\/\\/|https:\\/\\/)?(www.)?(((\\/)*[a-zA-Z0-9-]_*|[a-zA-Z0-9-]){2,}\\.){1,4}([a-zA-Z]){2,6}(\\/([a-zA-Z-_\\/\\.0-9#:?=&;,]*)?)?)";
        Pattern p = Pattern.compile(regex);
        Matcher m = p.matcher(text);
        String apot=null;

        while(m.find())
        {
            String urlStr = m.group();
            char[] stringArray1 = urlStr.toCharArray();
            if(urlStr.startsWith("(") && urlStr.endsWith(")"))
            {
                char[]stringArray=urlStr.toCharArray();
                char[]newArray=new char[stringArray.length-2];
                System.arraycopy(stringArray,1,newArray,0,stringArray.length-2);
                urlStr=new String(newArray);
                System.out.println("Finally Url ="+newArray.toString());
            }
            System.out.println("...Url..."+urlStr);
            apot=urlStr;
        }
        return apot;
    }

    String retrieveLinks(String text)
    {
        String regex = "((http:\\/\\/|https:\\/\\/)?(www.)?(([a-zA-Z0-9-]_*|[a-zA-Z0-9-]){2,}\\.){1,4}([a-zA-Z]){2,6}(\\/([a-zA-Z-_\\/\\.0-9#:?=&;,]*)?)?)";
        Pattern p = Pattern.compile(regex);
        Matcher m = p.matcher(text);
        String apot=null;
        while(m.find())
        {
            String urlStr = m.group();
            char[] stringArray1 = urlStr.toCharArray();
            if(urlStr.startsWith("(") && urlStr.endsWith(")"))
            {
                char[]stringArray=urlStr.toCharArray();
                char[]newArray=new char[stringArray.length-2];
                System.arraycopy(stringArray,1,newArray,0,stringArray.length-2);
                urlStr=new String(newArray);
                System.out.println("Finally Url ="+newArray.toString());
            }
            System.out.println("...Url..."+urlStr);
            apot=urlStr;
        }
        return apot;
    }

    public FragmentSpoudes() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment FragmentSpoudes.
     */
    // TODO: Rename and change types and number of parameters
    public static FragmentSpoudes newInstance(String param1, String param2) {
        FragmentSpoudes fragment = new FragmentSpoudes();
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
        View view= inflater.inflate(R.layout.spoudes, container, false);

        // NOTE : We are calling the onFragmentInteraction() declared in the MainActivity
        // ie we are sending "Fragment 1" as title parameter when fragment1 is activated
        if (mListener != null) {
            mListener.onFragmentInteraction("Σπουδές");
        }
        final HttpHandler gb = new HttpHandler();
        final String jsonStr = gb.makeServiceCall(url);
        Log.e(TAG, "Response from url: " + jsonStr);
        if (jsonStr!=null)
        {
            try {
                JSONObject jsonObj=new JSONObject(jsonStr);
                JSONArray article=jsonObj.getJSONArray("article");
                for (int i=0; i<article.length();i++)
                {
                    JSONObject a=article.getJSONObject(i);
                    introtext=a.getString("introtext");
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
        ImageView download =(ImageView) view.findViewById(R.id.download);
        download.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View v) {
                startLoading("Λήψη...");
                new Thread(new Runnable() {
                    @Override
                    public void run()
                    {
                        String url = url2+"/"+downloadLinks(introtext);
                        DownloadManager.Request request = new DownloadManager.Request(Uri.parse(url));
                        request.setDescription("NursingApp");
                        request.setTitle(retrieveLinks(introtext).toString());
                        if(Build.VERSION.SDK_INT>=Build.VERSION_CODES.HONEYCOMB)
                        {
                            request.allowScanningByMediaScanner();
                            request.setNotificationVisibility(DownloadManager.Request.VISIBILITY_VISIBLE_NOTIFY_COMPLETED);
                        }
                        DownloadManager manager = (DownloadManager) getContext().getSystemService(Context.DOWNLOAD_SERVICE);
                        manager.enqueue(request);
                        getActivity().runOnUiThread(new Runnable() {
                            @Override
                            public void run()
                            {
                                stopLoading();
                            }
                        });
                    }
                }).start();
            }
        });
        ImageView master =(ImageView) view.findViewById(R.id.master);
        master.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View v) {
                Intent browserIntent = new Intent(Intent.ACTION_VIEW, Uri.parse("http://nursing-msc.ioa.teiep.gr/"));
                startActivity(browserIntent);
            }
        });
        WebView wv =(WebView) view.findViewById(R.id.web);
        wv.getSettings().setJavaScriptEnabled(true);
        wv.loadData(introtext.substring(introtext.indexOf('\n'),introtext.length()-1),"text/html; charset=UTF-8", null);
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
