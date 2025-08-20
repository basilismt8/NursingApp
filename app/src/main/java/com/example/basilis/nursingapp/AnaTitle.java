package com.example.basilis.nursingapp;

import android.app.DownloadManager;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.graphics.Typeface;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.os.StrictMode;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.text.Html;
import android.util.Log;
import android.util.TypedValue;
import android.view.Gravity;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.webkit.URLUtil;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Created by BASILIS on 21/2/2018.
 */

public class AnaTitle extends AppCompatActivity {
    private String TAG = AnaTitle.class.getSimpleName();
    String introtext=null;
    ArrayList<String> st=new ArrayList<String>();
    ArrayList<String> s=new ArrayList<String>();
    ArrayList<String> s1=new ArrayList<String>();
    private String url1 ="http://nursing.ioa.teiep.gr/nursing-app/ana_title.php?call=one&catid="+HttpHandler.catid;
    private String url2 ="http://nursing.ioa.teiep.gr/";
    private ProgressDialog proDialog;

    private void startLoading(String message)
    {
        proDialog=new ProgressDialog(AnaTitle.this);
        proDialog.setMessage(message);
        proDialog.setProgressStyle(ProgressDialog.STYLE_SPINNER);
        proDialog.setCancelable(false);
        proDialog.show();
    }

    private void stopLoading()
    {
        proDialog.dismiss();
        proDialog=null;
    }

    static String before(String value, String a) {
        // Return substring containing all characters before a string.
        int posA = value.indexOf(a);
        if (posA == -1) {
            return "";
        }
        return value.substring(0, posA);
    }

    public void makeList()
    {
        final ArrayList<Title> textlist = new ArrayList<Title>();
        ListView lv =(ListView) findViewById(R.id.list4);
        final HttpHandler gb = new HttpHandler();
        final String jsonStr1 = gb.makeServiceCall(url1);
        Log.e(TAG, "Response from url: " + jsonStr1);
        if (jsonStr1!=null)
        {
            try {
                JSONObject jsonObj=new JSONObject(jsonStr1);
                JSONArray text=jsonObj.getJSONArray("title");
                for (int i=0;i<text.length();i++)
                {
                    JSONObject a=text.getJSONObject(i);
                    String publish=a.getString("publish_up");
                    String title=a.getString("title");
                    introtext=a.getString("introtext");
                    Bitmap bm= BitmapFactory.decodeResource(getResources(),R.id.image_title);
                    textlist.add(new Title(title,publish,bm,introtext));
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
        TitleAdapter adapter=new TitleAdapter(this,android.R.layout.simple_list_item_1,textlist);
        lv.setAdapter(adapter);
        lv.setOnItemClickListener(new AdapterView.OnItemClickListener()
        {
            @Override
            public void onItemClick(AdapterView<?> parent, View view,
                                    final int position, long id) {

                String target=textlist.get(position).introtext;
                String http="((http:\\/\\/|https:\\/\\/)?(www.)?(([a-zA-Z0-9-]_*|@[a-zA-Z0-9-]){2,}\\.){1,4}([a-zA-Z]){2,6}(\\/([a-zA-Z-_\\/\\.0-9#:?=&;,]*)?)?)|(images\\/((\\/)*[a-zA-Z0-9-.]_*|[a-zA-Z0-9-.]_*|(\\/)*[^u0000-u007F]_*){2,}\\.){1,4}([a-zA-Z]){2,6}(\\/([a-zA-Z-_\\/\\.0-9#:?=&;,]*)?)?";
                Pattern pattern=Pattern.compile(http);
                Matcher m=pattern.matcher(target);
                String http1="(images\\/((\\/)*[a-zA-Z0-9-.]_*|[a-zA-Z0-9-.]_*|(\\/)*[^u0000-u007F]_*){2,}\\.){1,4}([a-zA-Z]){2,6}(\\/([a-zA-Z-_\\/\\.0-9#:?=&;,]*)?)?";
                Pattern pattern1=Pattern.compile(http1,Pattern.CASE_INSENSITIVE);
                Matcher m1=pattern1.matcher(textlist.get(position).introtext);
                final ArrayList<String> s=new ArrayList<String>();
                final ArrayList<String> s1=new ArrayList<String>();
                final ArrayList<String> st=new ArrayList<String>();
                while(m.find())
                {
                    s.add(m.group());
                }
                while(m1.find())
                {
                    s1.add(m1.group());
                }
                for(int i=0;i<s.size();i++)
                {
                    if(!(s.get(i).substring (0,7).toString().equals("images/")))
                    {
                        st.add(s.get(i));
                    }
                }
                for(int i=0;i<st.size();i++)
                {
                    if(!(URLUtil.isHttpUrl(st.get(i))||URLUtil.isHttpsUrl(st.get(i))))
                    {
                        st.remove(i);
                    }
                }
                for(int i=0;i<st.size();i++)
                {
                    if((st.get(i).matches("(\\b[\\w.%-]+@[-.\\w]+\\.[A-Za-z]{2,4}\\b)")))
                    {
                        st.remove(i);
                    }
                }
                final ArrayList<String> s1new=new ArrayList<String>();
                for(int i=0;i<s1.size();i++)
                {
                    s1new.add(s1.get(i).substring(s1.get(i).lastIndexOf("/") + 1));
                }
                AlertDialog.Builder alert = new AlertDialog.Builder(AnaTitle.this);
                TextView title=new TextView(AnaTitle.this);
                title.setTextColor(Color.rgb(37,45,53));
                title.setPadding(10,10,10,10);
                title.setGravity(Gravity.CENTER);
                title.setTypeface(null, Typeface.ITALIC);
                title.setBackgroundColor(Color.rgb(66,139,202));
                title.setText(textlist.get(position).title);
                title.setTextSize(20);
                alert.setMessage(Html.fromHtml(textlist.get(position).introtext));
                alert.setPositiveButton("Κλείσιμο", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.dismiss();
                    }
                });
                alert.setNegativeButton("Κατεβάστε τα αρχεία", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        AlertDialog.Builder ab = new AlertDialog.Builder(AnaTitle.this);
                        TextView title=new TextView(AnaTitle.this);
                        title.setTextColor(Color.rgb(37,45,53));
                        title.setPadding(10,10,10,10);
                        title.setGravity(Gravity.CENTER);
                        title.setTypeface(null, Typeface.ITALIC);
                        title.setBackgroundColor(Color.rgb(66,139,202));
                        title.setText(textlist.get(position).title);
                        title.setTextSize(20);
                        final ArrayAdapter<String> adapter=new ArrayAdapter<String>(AnaTitle.this,android.R.layout.simple_list_item_1,s1new);
                        ab.setAdapter(adapter, new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, final int which) {
                                startLoading("Λήψη...");
                                new Thread(new Runnable() {
                                    @Override
                                    public void run()
                                    {
                                        String url=url2+before(s1.get(which),s1.get(which).substring(s1.get(which).lastIndexOf("/") + 1))+"/"+adapter.getItem(which);
                                        //String url=url2+"/"+adapter.getItem(which);
                                        DownloadManager.Request request = new DownloadManager.Request(Uri.parse(url));
                                        request.setDescription("NursingApp");
                                        request.setTitle(adapter.getItem(which).toString());
                                        if(Build.VERSION.SDK_INT>=Build.VERSION_CODES.HONEYCOMB)
                                        {
                                            request.allowScanningByMediaScanner();
                                            request.setNotificationVisibility(DownloadManager.Request.VISIBILITY_VISIBLE_NOTIFY_COMPLETED);
                                        }
                                        DownloadManager manager=(DownloadManager) getSystemService(Context.DOWNLOAD_SERVICE);
                                        manager.enqueue(request);
                                        runOnUiThread(new Runnable() {
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
                        ab.setNegativeButton("Κλείσιμο", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                dialog.dismiss();
                            }
                        });
                        ab.setCustomTitle(title);
                        AlertDialog dialog1=ab.show();
                        dialog1.getButton(AlertDialog.BUTTON_NEGATIVE).setTextSize(TypedValue.COMPLEX_UNIT_SP, 16);
                        dialog1.getButton(AlertDialog.BUTTON_NEGATIVE).setTextColor(Color.rgb(66,139,202));
                    }
                });
                alert.setNeutralButton("Ανοίξτε τα link", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        AlertDialog.Builder abl = new AlertDialog.Builder(AnaTitle.this);
                        TextView title=new TextView(AnaTitle.this);
                        title.setTextColor(Color.rgb(37,45,53));
                        title.setPadding(10,10,10,10);
                        title.setGravity(Gravity.CENTER);
                        title.setTypeface(null, Typeface.ITALIC);
                        title.setBackgroundColor(Color.rgb(66,139,202));
                        title.setText(textlist.get(position).title);
                        title.setTextSize(20);
                        final ArrayAdapter<String> adapter1=new ArrayAdapter<String>(AnaTitle.this,android.R.layout.simple_list_item_1,st);
                        abl.setAdapter(adapter1, new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, final int which) {
                                Intent browserIntent = new Intent(Intent.ACTION_VIEW, Uri.parse(adapter1.getItem(which)));
                                startActivity(browserIntent);
                            }
                        });
                        abl.setNegativeButton("Κλείσιμο", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                dialog.dismiss();
                            }
                        });
                        abl.setCustomTitle(title);
                        AlertDialog dialog1=abl.show();
                        dialog1.getButton(AlertDialog.BUTTON_NEGATIVE).setTextSize(TypedValue.COMPLEX_UNIT_SP, 16);
                        dialog1.getButton(AlertDialog.BUTTON_NEGATIVE).setTextColor(Color.rgb(66,139,202));
                    }
                });
                alert.setCustomTitle(title);
                AlertDialog dialog1=alert.show();
                dialog1.getButton(AlertDialog.BUTTON_POSITIVE).setTextSize(TypedValue.COMPLEX_UNIT_SP, 16);
                dialog1.getButton(AlertDialog.BUTTON_NEUTRAL).setTextSize(TypedValue.COMPLEX_UNIT_SP, 16);
                dialog1.getButton(AlertDialog.BUTTON_NEGATIVE).setTextSize(TypedValue.COMPLEX_UNIT_SP, 16);
                dialog1.getButton(AlertDialog.BUTTON_POSITIVE).setTextColor(Color.rgb(66,139,202));
                dialog1.getButton(AlertDialog.BUTTON_NEUTRAL).setTextColor(Color.rgb(66,139,202));
                dialog1.getButton(AlertDialog.BUTTON_NEGATIVE).setTextColor(Color.rgb(66,139,202));
                TextView messageText1=(TextView)dialog1.findViewById(android.R.id.message);
                messageText1.setTextColor(Color.rgb(37,45,53));
                messageText1.setGravity(Gravity.CENTER);
                messageText1.setTextSize(18);
                if(st.size()==0)
                {
                    Button button = dialog1.getButton(AlertDialog.BUTTON_NEUTRAL);
                    if (button!=null)
                    {
                        button.setEnabled(false);
                        button.setTextColor(Color.LTGRAY);
                    }
                }
                if(s1.size()==0)
                {
                    Button button1 = dialog1.getButton(AlertDialog.BUTTON_NEGATIVE);
                    if (button1!=null)
                    {
                        button1.setEnabled(false);
                        button1.setTextColor(Color.LTGRAY);
                    }
                }
            }
        });
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        StrictMode.ThreadPolicy policy = new
                StrictMode.ThreadPolicy.Builder().permitAll().build();
        StrictMode.setThreadPolicy(policy);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
                WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.layout.listview_ana_title);
        ImageView im1=(ImageView) findViewById(R.id.back);
        im1.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View v) {
                onBackPressed();
            }
        });
        ImageView im2=(ImageView) findViewById(R.id.refresh);
        im2.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View v) {
                Intent intent = getIntent();
                finish();
                startActivity(intent);
            }
        });
        makeList();
    }
}
