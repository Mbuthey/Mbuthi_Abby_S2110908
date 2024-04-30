package org.me.gcu.s2110908.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import org.me.gcu.s2110908.R;
import org.me.gcu.s2110908.models.WeatherForecastItem;

import java.util.ArrayList;

public class WeatherForecastAdapter extends ArrayAdapter<WeatherForecastItem> implements View.OnClickListener {

    private ArrayList<WeatherForecastItem> dataSet;
    Context mContext;

    private static class ViewHolder {
        ImageView txtWeatherIcon;
        TextView txtDay;
        TextView txtLowTemp;
        TextView txtHighTemp;
    }

    public WeatherForecastAdapter(ArrayList<WeatherForecastItem> data, Context context) {
        super(context, R.layout.day_forecast_item, data);
        this.dataSet = data;
        this.mContext=context;

    }

    @Override
    public void onClick(View v) {

        int position = (Integer) v.getTag();
        Object object = getItem(position);
        WeatherForecastItem dataModel = (WeatherForecastItem) object;
    }

    private int lastPosition = -1;

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        WeatherForecastItem dataModel = getItem(position);
        ViewHolder viewHolder;
        final View result;

        if (convertView == null) {
            viewHolder = new ViewHolder();
            LayoutInflater inflater = LayoutInflater.from(getContext());
            convertView = inflater.inflate(R.layout.day_forecast_item, parent, false);
            viewHolder.txtWeatherIcon = convertView.findViewById(R.id.weatherIcon);
            viewHolder.txtDay = convertView.findViewById(R.id.txtDay);
            viewHolder.txtLowTemp = convertView.findViewById(R.id.txtLowTemp);
            viewHolder.txtHighTemp = convertView.findViewById(R.id.txtHighTemp);

            result = convertView;
            convertView.setTag(viewHolder);
        } else {
            viewHolder = (ViewHolder) convertView.getTag();
            result = convertView;
        }

        lastPosition = position;

        viewHolder.txtWeatherIcon.setImageResource(dataModel.getWeatherIcon());
        viewHolder.txtDay.setText(dataModel.getDay());
        viewHolder.txtLowTemp.setText(dataModel.getLowTemp());
        viewHolder.txtHighTemp.setText(dataModel.getHighTemp());

        return convertView;
    }

}
