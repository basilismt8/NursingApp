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
 * Created by BASILIS on 22/2/2018.
 */

public class ProsAdapter extends ArrayAdapter<Prosopiko> {
    ArrayList<Prosopiko> items=new ArrayList<Prosopiko>();
    Context mcontext=null;
    public ProsAdapter(Context context,int resource,ArrayList<Prosopiko> t)
    {
        super(context, resource,t);
        mcontext=context;
        items=t;
    }

    public View getView(int position, View convertView, ViewGroup parent) {
        // Get the data item for this position
        Prosopiko pros = getItem(position);
        // Check if an existing view is being reused, otherwise inflate the view
        if (convertView == null) {
            convertView = LayoutInflater.from(getContext()).inflate(R.layout.prosopiko_list_item, parent, false);
        }
        // Lookup view for data population
        TextView title = (TextView) convertView.findViewById(R.id.text_pros);
        title.setTextColor(Color.rgb(37,45,53));
        title.setTextSize(16);
        ImageView icon=null;
        // Populate the data into the template view using the data object
        title.setText(pros.title);
        return convertView;
    }
}
