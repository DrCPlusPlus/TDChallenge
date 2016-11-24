package com.example.alexis.tdmoneyed;

import android.content.Context;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ListView;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.ObjectOutputStream;
import java.util.ArrayList;

import static android.content.Context.MODE_PRIVATE;

/**
 * Created by Alexis on 11/18/2016.
 */

public class CategoryIncome extends CategoryBase {

    public CategoryIncome(){}

    public static CategoryIncome newInstance() {
        CategoryIncome fragment = new CategoryIncome();
        return fragment;
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable final ViewGroup container, @Nullable Bundle savedInstanceState) {
        final View rootView = inflater.inflate(R.layout.fragment_totals, container, false);
        lv = (ListView)rootView.findViewById(R.id.totalsListView);

        createList(R.array.category_totals, budget.getTotals()); // set budget values
        return rootView;
    }

    @Override
    public void setList(ArrayList<ListItem> list) {
        budget.setTotals(list);

        budget.setTotals(list);
        Double sumIncome = 0.0;
        for(int idx = 0; idx < list.size(); ++idx){
            sumIncome += list.get(idx).getAmount();
        }
        budget.setIncome(sumIncome);
    }
}
