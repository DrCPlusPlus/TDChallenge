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

public class CategoryTravel extends CategoryBase{

    public CategoryTravel(){}

    public static CategoryTravel newInstance() {
        CategoryTravel fragment = new CategoryTravel();
        return fragment;
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable final ViewGroup container, @Nullable Bundle savedInstanceState) {
        final View rootView = inflater.inflate(R.layout.fragment_travel, container, false);
        lv = (ListView)rootView.findViewById(R.id.travelListView);

        createList(R.array.category_transportation, budget.getTransport()); // set budget values
        return rootView;
    }

    @Override
    public void setList(ArrayList<ListItem> list) {
        budget.setTransport(list);
    }
}
