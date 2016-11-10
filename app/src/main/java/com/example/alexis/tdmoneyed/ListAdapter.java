package com.example.alexis.tdmoneyed;


import android.app.Activity;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.List;

public class ListAdapter extends BaseAdapter {

    Context context;
    List<ListItem> listItems;
    //MainActivity mainActivity;

    ListAdapter(Context context, List<ListItem> listItems/*, MainActivity mainActivity*/) {
        this.context = context;
        this.listItems = listItems;
        //this.mainActivity = mainActivity;
    }

    @Override
    public int getCount() {

        return listItems.size();
    }

    @Override
    public Object getItem(int position) {

        return listItems.get(position);
    }

    @Override
    public long getItemId(int position) {

        return listItems.indexOf(getItem(position));
    }

    private class ViewContainer{
        TextView list_item;
        ImageView toggle_money;
        int item_id;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        final ViewContainer holder = new ViewContainer();
        LayoutInflater inflater = (LayoutInflater) context.getSystemService(Activity.LAYOUT_INFLATER_SERVICE);

        if(convertView == null)
            convertView = inflater.inflate(R.layout.list_item, null);

        holder.list_item = (TextView)convertView.findViewById(R.id.list_item);
        holder.toggle_money = (ImageView)convertView.findViewById(R.id.toggle_money);
        holder.item_id = position;

        ListItem item_pos = listItems.get(position);
        holder.list_item.setText(item_pos.getList_item());
        holder.toggle_money.setTag(item_pos.getChecked_Icon());
        if(item_pos.getChecked_Icon())
            holder.toggle_money.setImageResource(R.drawable.ic_money_green);
        else
            holder.toggle_money.setImageResource(R.drawable.ic_money_grey);

        return convertView;
    }
}
