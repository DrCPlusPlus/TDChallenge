package com.example.alexis.tdmoneyed;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;

import java.util.ArrayList;

public class CategoryCollege extends CategoryBase {

    public CategoryCollege(){}

    public static CategoryCollege newInstance() {
        CategoryCollege fragment = new CategoryCollege();
        return fragment;
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable final ViewGroup container, @Nullable Bundle savedInstanceState) {

        final View rootView = inflater.inflate(R.layout.fragment_college, container, false);
		lv = (ListView)rootView.findViewById(R.id.collegeListView);

        createList(R.array.category_college, budget.getCollege()); // set budget values

        return rootView;
    }

	@Override
	public void setList(ArrayList<ListItem> list) {
		budget.setCollege(list);
	}
}
