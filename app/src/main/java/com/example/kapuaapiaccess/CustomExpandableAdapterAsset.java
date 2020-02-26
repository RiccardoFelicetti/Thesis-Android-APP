package com.example.kapuaapiaccess;

import android.content.Context;
import android.graphics.Typeface;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseExpandableListAdapter;
import android.widget.EditText;
import android.widget.TextView;

import com.example.kapuaapiaccess.asset.Channel;

import java.util.HashMap;
import java.util.List;

public class CustomExpandableAdapterAsset extends BaseExpandableListAdapter {

    Context context;
    List<String> assetsName;
    HashMap<String, List<Channel>> listAsset;

    public CustomExpandableAdapterAsset(Context context, List<String> assetsName, HashMap<String, List<Channel>> listAsset ){

        this.context=context;
        this.assetsName=assetsName;
        this.listAsset=listAsset;

    }


    @Override
    public int getGroupCount() {
        return assetsName.size();
    }

    @Override
    public int getChildrenCount(int groupPosition) {
        return listAsset.get(assetsName.get(groupPosition)).size();
    }

    @Override
    public Object getGroup(int groupPosition) {
        return assetsName.get(groupPosition);
    }

    @Override
    public Object getChild(int groupPosition, int childPosition) {
        return this.listAsset.get(this.assetsName.get(groupPosition))
                .get(childPosition);
    }

    @Override
    public long getGroupId(int groupPosition) {
        return groupPosition;
    }

    @Override
    public long getChildId(int groupPosition, int childPosition) {
        return childPosition;
    }

    @Override
    public boolean hasStableIds() {
        return false;
    }

    @Override
    public View getGroupView(int groupPosition, boolean isExpanded, View convertView, ViewGroup parent) {

        String listTitle = (String) getGroup(groupPosition);
        if (convertView == null) {
            LayoutInflater layoutInflater = (LayoutInflater) this.context.
                    getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            convertView = layoutInflater.inflate(R.layout.list_group, null);
        }
        TextView listTitleTextView = (TextView) convertView
                .findViewById(R.id.listAssetName);
        listTitleTextView.setTypeface(null, Typeface.BOLD);
        listTitleTextView.setText(listTitle);
        return convertView;

    }

    @Override
    public View getChildView(int groupPosition, int childPosition, boolean isLastChild, View convertView, ViewGroup parent) {

        ViewHolder holder;
        final Channel expandedListText = (Channel) getChild(groupPosition, childPosition);
        if (convertView == null) {
            LayoutInflater layoutInflater = (LayoutInflater) this.context
                    .getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            convertView = layoutInflater.inflate(R.layout.list_channels, null);

            holder= new ViewHolder(convertView);

            holder.textViewChannelValue.addTextChangedListener(new TextWatcher()
            {
                public void afterTextChanged(Editable s){}
                public void beforeTextChanged(CharSequence s, int start, int count, int after) {

                }
                public void onTextChanged(CharSequence s, int start, int before, int count) {

                        expandedListText.setValue(holder.textViewChannelValue.getText().toString());

                }
            });
          /*
            holder.textViewChannelValue.setOnFocusChangeListener(new View.OnFocusChangeListener()
            {
                @Override
                public void onFocusChange(View v, boolean hasFocus)
                {
                    if(!hasFocus)
                        expandedListText.setValue(holder.textViewChannelValue.getText().toString());
                }
            });
            */

            convertView.setTag(holder);

        }else{
            holder = (ViewHolder) convertView.getTag();
        }

        TextView textViewChannelName = (TextView) convertView
                .findViewById(R.id.channelName);
        textViewChannelName.setText(expandedListText.getName()+": ");

        holder.textViewChannelValue.setText(expandedListText.getValue());
        /*
        EditText textViewChannelValue = (EditText) convertView
                .findViewById(R.id.channelValue);
        textViewChannelValue.setText(expandedListText.getValue());
        */

        return convertView;
    }

    @Override
    public boolean isChildSelectable(int groupPosition, int childPosition) {
        return true;
    }
}

class ViewHolder{
    EditText textViewChannelValue;

    public ViewHolder(View v)
    {
        textViewChannelValue = (EditText) v.findViewById(R.id.channelValue);
    }
}