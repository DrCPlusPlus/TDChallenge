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


public class CategoryDebt extends CategoryBase{


    public CategoryDebt(){}

    public static CategoryDebt newInstance() {
        CategoryDebt fragment = new CategoryDebt();
        return fragment;
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable final ViewGroup container, @Nullable Bundle savedInstanceState) {

        final View rootView = inflater.inflate(R.layout.fragment_debt, container, false);
        lv = (ListView)rootView.findViewById(R.id.debtListView);

        createList(R.array.category_debt, budget.getDebt()); // set budget values

        return rootView;
    }


    @Override
    public void setList(ArrayList<ListItem> list) {
        budget.setDebt(list);
    }
}
