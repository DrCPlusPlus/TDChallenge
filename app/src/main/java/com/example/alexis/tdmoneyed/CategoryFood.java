package com.example.alexis.tdmoneyed;

import android.content.Context;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ListView;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by Alexis on 11/8/2016.
 */

public class CategoryFood extends Fragment implements View.OnFocusChangeListener{

    private ArrayList<ListItem> listItems;
    private Budget budget = BuilderActivity.getBudget();

    public CategoryFood(){}

    public static CategoryFood newInstance() {
        CategoryFood fragment = new CategoryFood();
        return fragment;
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable final ViewGroup container, @Nullable Bundle savedInstanceState) {

        final View rootView = inflater.inflate(R.layout.fragment_food, container, false);
        ListView lv = (ListView)rootView.findViewById(R.id.foodListView);

        createList(); // set budget values
        ListAdapter adapter = new ListAdapter(getActivity(), listItems, this);
        lv.setAdapter(adapter);
        lv.setItemsCanFocus(true);

        return rootView;
    }

    public void createList() {
        listItems = new ArrayList<ListItem>();
        ArrayList<ListItem> foodList = budget.getFood();
        String[] listArray = getResources().getStringArray(R.array.category_food);

        if (foodList == null) {
            for (int idx = 0; idx < listArray.length; ++idx) {
                ListItem item = new ListItem(listArray[idx], false, 0.00);
                listItems.add(item);
            }
        } else {
            for (int idx = 0; idx < listArray.length; ++idx) {
                String itemName = foodList.get(idx).getItem_name();
                Boolean checked = foodList.get(idx).getChecked_Icon();
                Double amount = foodList.get(idx).getAmount();
                ListItem item = new ListItem(itemName, checked, amount);
                listItems.add(item);
            }
        }
    }


    public void onFocusChange(View v, boolean hasFocus) {
        if (!hasFocus) {
            //final int position = v.getId();

            final EditText Caption = (EditText) v;
            ListAdapter.ViewContainer container = (ListAdapter.ViewContainer)Caption.getTag();
            ImageView img = container.toggle_money;

            ListItem li = listItems.get(container.item_id);
            li.setAmount(Double.parseDouble(Caption.getText().toString()));

            Boolean liChecked = li.getChecked_Icon();
            if (!liChecked) {
                li.setChecked_icon(true);
                img.setImageResource(R.drawable.ic_money_green);
            } else {
                li.setChecked_icon(false);
                img.setImageResource(R.drawable.ic_money_grey);
            }
        }
        budget.setFood(listItems);
    }

    class ViewHolder {
        EditText caption;
    }
}
