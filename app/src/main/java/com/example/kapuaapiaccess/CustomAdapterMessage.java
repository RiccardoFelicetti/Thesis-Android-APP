package com.example.kapuaapiaccess;

import android.content.Context;
import android.location.Location;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import com.example.kapuaapiaccess.message.Item;
import com.example.kapuaapiaccess.message.Message;
import com.example.kapuaapiaccess.message.Metric;

import java.util.List;

public class CustomAdapterMessage extends ArrayAdapter<Metric> {

    public CustomAdapterMessage(Context context, int textViewResourceId,
                                List<Metric> objects) {
        super(context, textViewResourceId, objects);

    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        return getViewOptimize(position, convertView, parent);
    }


    public View getViewOptimize(int position, View convertView, ViewGroup parent) {
        ViewHolder viewHolder = null;
        if (convertView == null) {
            LayoutInflater inflater = (LayoutInflater) getContext()
                    .getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            convertView = inflater.inflate(R.layout.row_layout, null);

            viewHolder = new ViewHolder();
            viewHolder.name = (TextView)convertView.findViewById(R.id.textViewName);
            viewHolder.value=(TextView)convertView.findViewById(R.id.textViewValue);


            convertView.setTag(viewHolder);

        } else {
            viewHolder = (ViewHolder) convertView.getTag();
        }

      Metric metric= getItem(position);

        viewHolder.name.setText(metric.name);
        viewHolder.value.setText(metric.value);

        return convertView;
    }


    private class ViewHolder {

        public TextView name;
        public TextView value;

    }


}
