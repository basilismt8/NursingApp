package com.example.basilis.nursingapp;

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.Typeface;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Bundle;
import android.os.StrictMode;
import android.support.design.widget.NavigationView;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.TypedValue;
import android.view.Gravity;
import android.view.MenuItem;
import android.view.WindowManager;
import android.widget.TextView;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity
        implements
        FragmentArxikh.OnFragmentInteractionListener,
        FragmentAna.OnFragmentInteractionListener,
        FragmentLink.OnFragmentInteractionListener,
        FragmentSpoudes.OnFragmentInteractionListener,
        FragmentProsopiko.OnFragmentInteractionListener,
        FragmentEmail.OnFragmentInteractionListener,
        NavigationView.OnNavigationItemSelectedListener {

    private boolean isNetworkAvailable() {
        ConnectivityManager connectivityManager
                = (ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo activeNetworkInfo = connectivityManager.getActiveNetworkInfo();
        return activeNetworkInfo != null && activeNetworkInfo.isConnected();
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        StrictMode.ThreadPolicy policy = new
                StrictMode.ThreadPolicy.Builder().permitAll().build();
        StrictMode.setThreadPolicy(policy);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
                WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.layout.activity_main);

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.addDrawerListener(toggle);
        toggle.syncState();

        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);
        navigationView.setCheckedItem(R.id.arxikh);
        //NOTE:  Checks first item in the navigation drawer initially
        navigationView.setCheckedItem(R.id.arxikh);

        //NOTE:  Open fragmentArxikh initially.
        FragmentTransaction ft = getSupportFragmentManager().beginTransaction();
        ft.replace(R.id.mainFrame, new FragmentArxikh());
        ft.commit();
        if(!isNetworkAvailable())
        {
            Context context = getApplicationContext();
            CharSequence text = "Συνδεθείτε στα δεδομένα ή στο WiFi";
            int duration = Toast.LENGTH_LONG;
            Toast toast = Toast.makeText(context, text, duration);
            toast.setGravity(Gravity.BOTTOM, 0, 230);
            toast.show();
        }
    }

    @Override
    public void onBackPressed() {
        /*DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressed();
        }*/
    }

    /*@Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings)
        {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }*/

    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        // Handle navigation view item clicks here.
        int id = item.getItemId();
        //NOTE: creating fragment object
        Fragment fragment = null;
        if (id == R.id.arxikh)
        {
            fragment = new FragmentArxikh();
        }
        else if (id == R.id.anakoinoseis)
        {
            fragment = new FragmentAna();
        }
        else if (id == R.id.link)
        {
            fragment = new FragmentLink();
        }
        else if (id == R.id.spoudes)
        {
            if(!isNetworkAvailable())
            {
                Context context = getApplicationContext();
                CharSequence text = "Συνδεθείτε στα δεδομένα ή στο WiFi";
                int duration = Toast.LENGTH_LONG;
                Toast toast = Toast.makeText(context, text, duration);
                toast.setGravity(Gravity.BOTTOM, 0, 230);
                toast.show();
            }
            else
            {
                fragment = new FragmentSpoudes();
            }
        }
        else if (id == R.id.prosopiko)
        {
            fragment = new FragmentProsopiko();
        }
        else if (id == R.id.info)
        {
            AlertDialog.Builder alertDialog=new AlertDialog.Builder(MainActivity.this);
            TextView title = new TextView(MainActivity.this);
            title.setText("NursingApp");
            title.setBackgroundColor(Color.rgb(66,139,202));
            title.setPadding(10,10,10,10);
            title.setGravity(Gravity.CENTER);
            title.setTypeface(null, Typeface.ITALIC);
            title.setTextColor(Color.rgb(37,45,53));
            title.setTextSize(20);
            alertDialog.setMessage("Εφαρμογή για το Τμήμα Νοσηλευτικής του Πανεπιστημιού Ιωαννίων. \n"
                    +"\n"
                    +"Η εφαρμογή παρέχει ανακοινώσεις, στοιχεία επικοινωνίας με το διοικητικό και διδακτικό προσωπικό του τμήματος και άλλες χρήσιμες πληροφορίες. \n"
                    +"\n"
                    +"Developer: ΜΑΣΤΟΡΑΣ ΒΑΣΙΛΕΙΟΣ basilismt8@gmail.com");
            alertDialog.setNegativeButton("Κλείσιμο", new DialogInterface.OnClickListener() {

                @Override
                public void onClick(DialogInterface dialog, int which) {
                    dialog.cancel();
                }
            });
            alertDialog.setCustomTitle(title);
            AlertDialog dialog=alertDialog.show();
            dialog.getButton(AlertDialog.BUTTON_NEGATIVE).setTextSize(TypedValue.COMPLEX_UNIT_SP, 16);
            dialog.getButton(AlertDialog.BUTTON_NEGATIVE).setTextColor(Color.rgb(66,139,202));
            dialog.getButton(AlertDialog.BUTTON_NEGATIVE).setGravity(Gravity.CENTER);
            TextView messageText=(TextView)dialog.findViewById(android.R.id.message);
            messageText.setTextColor(Color.rgb(37,45,53));
            messageText.setGravity(Gravity.LEFT);
            messageText.setTextSize(18);
        }
        else if (id == R.id.map)
        {
            Intent I=new Intent(this,MapsActivity.class);
            startActivity(I);
        }
        else if (id == R.id.sendmail)
        {
            fragment = new FragmentEmail();
        }
        //NOTE: Fragment changing code
        if (fragment != null) {
            FragmentTransaction ft = getSupportFragmentManager().beginTransaction();
            ft.replace(R.id.mainFrame, fragment);
            ft.commit();
        }
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }

    @Override
    public void onFragmentInteraction(String title) {
        // NOTE:  Code to replace the toolbar title based current visible fragment
        getSupportActionBar().setTitle(title);
    }
}
