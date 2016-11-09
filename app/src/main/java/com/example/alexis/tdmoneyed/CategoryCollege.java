package com.example.alexis.tdmoneyed;

import android.database.DataSetObserver;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ListAdapter;
import android.widget.ListView;

/**
 * Created by Alexis on 11/8/2016.
 */

public class CategoryCollege extends Fragment {
    /**
     * The fragment argument representing the section number for this
     * fragment.
     */
    private static final String ARG_SECTION_NUMBER = "section_number";

    public CategoryCollege(){}

    /**
     * Returns a new instance of this fragment for the given section
     * number.
     */
    public static CategoryCollege newInstance() {
        CategoryCollege fragment = new CategoryCollege();
//        Bundle args = new Bundle();
//        args.putInt(ARG_SECTION_NUMBER, sectionNumber);
//        fragment.setArguments(args);
        return fragment;
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_college, container, false);
        ListView lv = (ListView)rootView.findViewById(R.id.collegeListView);
        lv.setAdapter(new ArrayAdapter<String>(getActivity(), android.R.layout.simple_list_item_checked, getResources().getStringArray(R.array.category_college)));
        return rootView;
    }
}