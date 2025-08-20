package basilis.myapplication.nurs;

import android.app.Dialog;
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
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Created by BASILIS on 5/3/2018.
 */

public class ProsIntro extends AppCompatActivity {
    LinearLayout mainLayout = null;
    private String TAG = ProsIntro.class.getSimpleName();
    public static String introtext = null;
    private ProgressDialog proDialog;
    private String url = "http://nursing.ioa.teiep.gr/nursing-app/prosopiko_intr.php?call=one&plinkid=" + HttpHandler.plinkid.substring(HttpHandler.plinkid.length() - 2);
    private String url2 = "http://nursing.ioa.teiep.gr/";
    ArrayList<String> s1 = null;
    LinearLayout ld;

    private void startLoading(String message) {
        proDialog = new ProgressDialog(ProsIntro.this);
        proDialog.setMessage(message);
        proDialog.setProgressStyle(ProgressDialog.STYLE_SPINNER);
        proDialog.setCancelable(false);
        proDialog.show();
    }

    private void stopLoading() {
        proDialog.dismiss();
        proDialog = null;
    }

    static String before(String value, String a) {
        // Return substring containing all characters before a string.
        int posA = value.indexOf(a);
        if (posA == -1) {
            return "";
        }
        return value.substring(0, posA);
    }

    void prosintro() {
        final HttpHandler gb = new HttpHandler();
        final String jsonStr = gb.makeServiceCall(url);
        Log.e(TAG, "Response from url: " + jsonStr);
        if (jsonStr != null) {
            try {
                JSONObject jsonObj = new JSONObject(jsonStr);
                JSONArray prosIntro = jsonObj.getJSONArray("prosintr");
                for (int i = 0; i < prosIntro.length(); i++) {
                    JSONObject a = prosIntro.getJSONObject(i);
                    introtext = a.getString("introtext");
                }
            } catch (final JSONException e) {
                Log.e(TAG, "Json parsing error: " + e.getMessage());
                // TODO Auto-generated catch block
                e.printStackTrace();
            }
        } else {
            Log.e(TAG, "Couldn't get json from server.");
            runOnUiThread(new Runnable() {
                @Override
                public void run() {
                    Toast.makeText(getApplicationContext(),
                            "Συνδεθείτε στα δεδομένα ή στο WiFi",
                            Toast.LENGTH_LONG)
                            .show();
                }
            });
        }
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
        setContentView(R.layout.prosintro);
        ImageView im1 = (ImageView) findViewById(R.id.backpr);
        im1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();
            }
        });
        ImageView im2 = (ImageView) findViewById(R.id.phone);
        im2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                AlertDialog.Builder ab = new AlertDialog.Builder(ProsIntro.this);
                TextView title = new TextView(ProsIntro.this);
                title.setTextColor(Color.rgb(37, 45, 53));
                title.setPadding(10, 10, 10, 10);
                title.setGravity(Gravity.CENTER);
                title.setTypeface(null, Typeface.ITALIC);
                title.setBackgroundColor(Color.rgb(66, 139, 202));
                title.setText("Τηλέφωνα Επικοινωνίας Με Βάση Τον Πίνακα");
                title.setTextSize(20);
                final ArrayList<String> s = new ArrayList<String>();
                Pattern pattern = Pattern.compile("(69+\\w+)|(26510+\\w+)");
                Matcher matcher = pattern.matcher(introtext);
                while (matcher.find()) {
                    s.add(matcher.group());
                }
                final ArrayAdapter<String> adapter = new ArrayAdapter<String>(ProsIntro.this, android.R.layout.simple_list_item_1,s);
                ab.setNegativeButton("Κλείσιμο", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.dismiss();
                    }
                });
                ab.setAdapter(adapter, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        String strName = adapter.getItem(which);
                        Intent i = new Intent(Intent.ACTION_DIAL);
                        i.setData(Uri.parse("tel:"+strName));
                        /*if (ActivityCompat.checkSelfPermission(ProsIntro.this, Manifest.permission.CALL_PHONE) != PackageManager.PERMISSION_GRANTED) {
                            return;
                        }*/
                        startActivity(i);
                    }
                });
                ab.setCustomTitle(title);
                AlertDialog dialog1=ab.show();
                dialog1.getButton(AlertDialog.BUTTON_NEGATIVE).setTextSize(TypedValue.COMPLEX_UNIT_SP, 16);
                dialog1.getButton(AlertDialog.BUTTON_NEGATIVE).setTextColor(Color.rgb(66,139,202));
            }
        });
        ImageView im3=(ImageView) findViewById(R.id.email);
        im3.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View v) {
                AlertDialog.Builder ab = new AlertDialog.Builder(ProsIntro.this);
                TextView title=new TextView(ProsIntro.this);
                title.setTextColor(Color.rgb(37,45,53));
                title.setPadding(10,10,10,10);
                title.setGravity(Gravity.CENTER);
                title.setTypeface(null, Typeface.ITALIC);
                title.setBackgroundColor(Color.rgb(66,139,202));
                title.setText("Emails Με Βάση Τον Πίνακα");
                final ArrayList<String> s=new ArrayList<String>();
                Pattern pattern=Pattern.compile("(mailto:)(\\b[\\w.%-]+@[-.\\w]+\\.[A-Za-z]{2,4}\\b)");
                Matcher matcher=pattern.matcher(introtext);
                while(matcher.find())
                {
                    s.add(matcher.group());
                }
                final ArrayList<String> snew=new ArrayList<String>();
                for(int i=0;i<s.size();i++)
                {
                    snew.add(s.get(i).substring(7));
                }
                final ArrayAdapter<String> adapter=new ArrayAdapter<String>(ProsIntro.this,android.R.layout.simple_list_item_1,snew);
                ab.setNegativeButton("Κλείσιμο", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.dismiss();
                    }
                });
                ab.setAdapter(adapter, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        final String email=adapter.getItem(which);
                        AlertDialog.Builder ab1 = new AlertDialog.Builder(ProsIntro.this);

                        LinearLayout l=new LinearLayout(ProsIntro.this);
                        l.setOrientation(LinearLayout.VERTICAL);
                        ab1.setView(l);
                        final Dialog d=ab1.create();
                        TextView title=new TextView(ProsIntro.this);
                        title.setTextColor(Color.rgb(37,45,53));
                        title.setPadding(10,10,10,10);
                        title.setGravity(Gravity.CENTER);
                        title.setTypeface(null, Typeface.ITALIC);
                        title.setBackgroundColor(Color.rgb(66,139,202));
                        title.setText("Αποστολή Email σε : "+email+"");
                        l.addView(title);
                        final EditText emailBody=new EditText(ProsIntro.this);
                        emailBody.setHint("Γράψτε το κείμενο του Email εδώ!!!");
                        emailBody.setLines(10);
                        l.addView(emailBody);
                        final ImageView sendmail=new ImageView(ProsIntro.this);
                        Bitmap bm= BitmapFactory.decodeResource(getResources(), R.drawable.sendmail);
                        final int width=66;
                        final int height=66;
                        bm=bm.createScaledBitmap(bm, width,height, true);
                        sendmail.setImageBitmap(bm);
                        l.addView(sendmail);
                        sendmail.setOnClickListener(new View.OnClickListener()
                        {
                            @Override
                            public void onClick(View v)
                            {
                                Intent i=new Intent(Intent.ACTION_SEND);
                                i.setType("type/plain");
                                i.putExtra(Intent.EXTRA_EMAIL,new String[]{email});
                                i.putExtra(Intent.EXTRA_SUBJECT, "Αποστολή από την εφαρμογή NursingApp");
                                String myMessage=emailBody.getText().toString();
                                i.putExtra(Intent.EXTRA_TEXT, Html.fromHtml(myMessage));
                                try {
                                    startActivity(Intent.createChooser(i,"Αποστολή Email..."));
                                } catch(android.content.ActivityNotFoundException ex)
                                {
                                    Toast.makeText(ProsIntro.this,"Δεν υπάρχρει εγκατεστημένο πρόγραμμα διαχείρισης email.",Toast.LENGTH_SHORT).show();
                                }
                            }
                        });
                        Button closeButton=new Button(ProsIntro.this);
                        l.addView(closeButton);
                        closeButton.setText("Κλείσιμο");
                        closeButton.setOnClickListener(new View.OnClickListener()
                        {
                            @Override
                            public void onClick(View v) {
                                d.cancel();
                            }
                        });
                        d.show();
                        Window window=d.getWindow();
                        window.setLayout(LinearLayout.LayoutParams.WRAP_CONTENT, LinearLayout.LayoutParams.WRAP_CONTENT);
                    }
                });
                ab.setCustomTitle(title);
                AlertDialog dialog1=ab.show();
                dialog1.getButton(AlertDialog.BUTTON_NEGATIVE).setTextSize(TypedValue.COMPLEX_UNIT_SP, 16);
                dialog1.getButton(AlertDialog.BUTTON_NEGATIVE).setTextColor(Color.rgb(66,139,202));
            }
        });
        ImageView im4=(ImageView) findViewById(R.id.bio);
        im4.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View v) {
                AlertDialog.Builder ab=new AlertDialog.Builder(ProsIntro.this);
                TextView title=new TextView(ProsIntro.this);
                title.setTextColor(Color.rgb(37,45,53));
                title.setPadding(10,10,10,10);
                title.setGravity(Gravity.CENTER);
                title.setTypeface(null, Typeface.ITALIC);
                title.setBackgroundColor(Color.rgb(66,139,202));
                title.setText("Βιογραφικα σημειώματα Με Βάση Τον Πίνακα");
                s1=new ArrayList<String>();
                ArrayList<String> s2=new ArrayList<String>();
                Pattern pattern=Pattern.compile("(images\\/[A-Za-z]*\\/[A-Za-z]*_[A-Za-z]*_[A-Za-z]*.pdf)|(images\\/[A-Za-z]*_[A-Za-z]*[0-9]*_gr.pdf)");
                Matcher matcher=pattern.matcher(introtext);
                while(matcher.find())
                {
                    s1.add(matcher.group());
                }
                for(int i=0;i<s1.size();i++)
                {
                    s2.add(s1.get(i).substring(s1.get(i).lastIndexOf("/") + 1));
                }
                final ArrayAdapter<String> adapter=new ArrayAdapter<String>(ProsIntro.this,android.R.layout.simple_list_item_1,s2);
                ab.setNegativeButton("Κλείσιμο", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.dismiss();
                    }
                });
                ab.setAdapter(adapter, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, final int which) {
                        startLoading("Λήψη...");
                        new Thread(new Runnable() {
                            @Override
                            public void run()
                            {
                                String url=url2+before(s1.get(which),s1.get(which).substring(s1.get(which).lastIndexOf("/") + 1))+"/"+adapter.getItem(which);
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
                ab.setCustomTitle(title);
                AlertDialog dialog1=ab.show();
                dialog1.getButton(AlertDialog.BUTTON_NEGATIVE).setTextSize(TypedValue.COMPLEX_UNIT_SP, 16);
                dialog1.getButton(AlertDialog.BUTTON_NEGATIVE).setTextColor(Color.rgb(66,139,202));
            }
        });
        prosintro();
        WebView wv =(WebView) findViewById(R.id.webpr);
        wv.getSettings().setJavaScriptEnabled(true);
        final boolean enableLinks=false;
        wv.setWebViewClient(new WebViewClient() {

            @Override
            public boolean shouldOverrideUrlLoading(WebView view, String url) {
                return !enableLinks;
            }
        });
        wv.loadData(introtext,"text/html; charset=UTF-8", null);
    }

}
