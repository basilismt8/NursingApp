package basilis.myapplication.nurs;

import android.content.Context;
import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.ArrayList;

/**
 * Created by BASILIS on 23/2/2018.
 */

public class TitleAdapter extends ArrayAdapter<Title> {
    ArrayList<Title> items=new ArrayList<Title>();
    Context mcontext=null;
    public TitleAdapter(Context context,int resource,ArrayList<Title> t)
    {
        super(context, resource,t);
        mcontext=context;
        items=t;
    }

    public View getView(int position, View convertView, ViewGroup parent) {
        // Get the data item for this position
        Title tlt = getItem(position);
        // Check if an existing view is being reused, otherwise inflate the view
        if (convertView == null) {
            convertView = LayoutInflater.from(getContext()).inflate(R.layout.ana_title_list_item, parent, false);
        }
        // Lookup view for data population
        TextView title = (TextView) convertView.findViewById(R.id.title_ana);
        TextView date = (TextView) convertView.findViewById(R.id.date_ana);
        title.setTextColor(Color.rgb(66, 139, 202));
        title.setTextSize(16);
        date.setTextColor(Color.rgb(37,45,53));
        date.setTextSize(16);
        ImageView icon=null;
        // Populate the data into the template view using the data object
        title.setText(tlt.title);
        date.setText(tlt.date);
        return convertView;
    }
}
