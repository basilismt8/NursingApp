package com.example.basilis.nursingapp;

import android.graphics.Bitmap;

/**
 * Created by BASILIS on 23/2/2018.
 */

public class Title {
    public String title;
    public String date;
    public String introtext;
    public Bitmap icon;
    public Title(String n, String d, Bitmap i, String in)
    {
        title=n;
        date=d;
        icon=i;
        introtext=in;
    }
}
