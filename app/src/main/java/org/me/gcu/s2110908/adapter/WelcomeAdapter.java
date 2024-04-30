package org.me.gcu.s2110908.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import com.google.android.material.snackbar.Snackbar;

import org.me.gcu.s2110908.R;
import org.me.gcu.s2110908.models.WelcomeItemModel;

import java.util.ArrayList;

public class WelcomeAdapter extends ArrayAdapter<WelcomeItemModel> {

    private ArrayList<WelcomeItemModel> dataSet;
    Context mContext;

    // View lookup cache
    private static class ViewHolder {
        TextView txtLocation;
        TextView txtTime;
        TextView txtWeather;
        TextView txtTemp;
        TextView txtHighLow;
    }

    public WelcomeAdapter(ArrayList<WelcomeItemModel> data, Context context) {
        super(context, R.layout.welcome_item, data);
        this.dataSet = data;
        this.mContext=context;

    }

//    @Override
//    public void onClick(View v) {
//
//        int position=(Integer) v.getTag();
//        Object object= getItem(position);
//        WelcomeItemModel dataModel=(WelcomeItemModel)object;
//
////        switch (v.getId())
////        {
////            case R.id.txtLocation:
////                Snackbar.make(v, "Release date ", Snackbar.LENGTH_LONG)
////                        .setAction("No action", null).show();
////                break;
////        }
//    }

    private int lastPosition = -1;

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        // Get the data item for this position
        WelcomeItemModel dataModel = getItem(position);
        // Check if an existing view is being reused, otherwise inflate the view
        ViewHolder viewHolder; // view lookup cache stored in tag

        final View result;

        if (convertView == null) {

            viewHolder = new ViewHolder();
            LayoutInflater inflater = LayoutInflater.from(getContext());
            convertView = inflater.inflate(R.layout.welcome_item, parent, false);
            viewHolder.txtLocation = (TextView) convertView.findViewById(R.id.txtLocation);
//            viewHolder.txtTime = (TextView) convertView.findViewById(R.id.txtTime);
//            viewHolder.txtWeather = (TextView) convertView.findViewById(R.id.txtWeatherCondition);
//            viewHolder.txtTemp = (TextView) convertView.findViewById(R.id.txtTemperature);
//            viewHolder.txtHighLow = (TextView) convertView.findViewById(R.id.txtMinMaxTemperature);

            result=convertView;

            convertView.setTag(viewHolder);
        } else {
            viewHolder = (ViewHolder) convertView.getTag();
            result=convertView;
        }

        lastPosition = position;

        viewHolder.txtLocation.setText(dataModel.getLocation());
//        viewHolder.txtTime.setText(dataModel.getTime());
//        viewHolder.txtWeather.setText(dataModel.getWeather());
//        viewHolder.txtTemp.setText(dataModel.getTemperature());
//        viewHolder.txtHighLow.setText(dataModel.getHighLow());

//        viewHolder.txtLocation.setOnClickListener(this);


        // Return the completed view to render on screen
        return convertView;
    }
}
