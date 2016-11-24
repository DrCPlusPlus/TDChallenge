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

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

public class CategoryPersonal extends CategoryBase {

    public CategoryPersonal(){}

    public static CategoryPersonal newInstance() {
        CategoryPersonal fragment = new CategoryPersonal();
        return fragment;
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable final ViewGroup container, @Nullable Bundle savedInstanceState) {

        final View rootView = inflater.inflate(R.layout.fragment_personal, container, false);
        lv = (ListView)rootView.findViewById(R.id.personalListView);
        createList(R.array.category_personal, budget.getPersonal()); // set budget values
        return rootView;
    }

    @Override
    public void setList(ArrayList<ListItem> list) {
        budget.setPersonal(list);
    }
}
