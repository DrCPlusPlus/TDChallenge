package com.example.alexis.tdmoneyed;


import android.app.Activity;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.ArrayList;

public class ListAdapter extends BaseAdapter {

    private Context context;
    private ArrayList<ListItem> listItems;
    private Budget budget  = BuilderActivity.getBudget();
    private View.OnFocusChangeListener listener;

    ListAdapter(Context context, ArrayList<ListItem> listItems, View.OnFocusChangeListener listener) {
        this.context = context;
        this.listener = listener;
        if(budget != null)
            this.listItems = listItems;

        notifyDataSetChanged();
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

    public class ViewContainer{
        TextView list_item;
        ImageView toggle_money;
        int item_id;
        EditText caption;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        final ViewContainer holder = new ViewContainer();
        LayoutInflater inflater = (LayoutInflater) context.getSystemService(Activity.LAYOUT_INFLATER_SERVICE);

        if(convertView == null)
            convertView = inflater.inflate(R.layout.list_item, null);

        holder.list_item = (TextView)convertView.findViewById(R.id.list_item);
        holder.caption = (EditText) convertView.findViewById(R.id.item_edit);
        holder.toggle_money = (ImageView)convertView.findViewById(R.id.toggle_money);
        holder.item_id = position;
        ListItem item_pos = listItems.get(position);
        holder.list_item.setText(item_pos.getItem_name());
        holder.toggle_money.setTag(item_pos.getChecked_Icon());

        holder.caption.setTag(holder);
        if(item_pos.getAmount() == 0.00) {
            holder.caption.setText("0.00");
            holder.toggle_money.setImageResource(R.drawable.ic_money_grey);
        } else {
            holder.caption.setText(item_pos.getAmount().toString());
            holder.toggle_money.setImageResource(R.drawable.ic_money_green);
        }


 //       holder.caption.setText("0.00");
        //we need to update adapter once we finish with editing
        if (listener != null) {
            holder.caption.setOnFocusChangeListener(listener);
        }

        return convertView;
    }
}
