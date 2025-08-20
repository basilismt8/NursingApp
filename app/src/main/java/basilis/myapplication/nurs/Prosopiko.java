package basilis.myapplication.nurs;

import android.graphics.Bitmap;

/**
 * Created by BASILIS on 22/2/2018.
 */

public class Prosopiko {
    public String title;
    public Bitmap icon;
    public String type;
    public String link;
    public Prosopiko(String n,Bitmap i,String t,String l)
    {
        title=n;
        icon=i;
        type=t;
        link=l;
    }
}
