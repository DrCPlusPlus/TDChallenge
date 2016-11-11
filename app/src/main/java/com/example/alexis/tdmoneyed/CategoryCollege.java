package com.example.alexis.tdmoneyed;

import android.content.Context;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ImageView;
import android.widget.ListView;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by Alexis on 11/8/2016.
 */

public class CategoryCollege extends Fragment {

    /**
     * The fragment argument representing the section number for this fragment.
     */
    private static final String ARG_SECTION_NUMBER = "section_number";
    private String[] listArray;
    public List<ListItem> listItems;
    private ListView lv;
    //private static PagerAdapter parent;




    public CategoryCollege(){}

    /**
     * Returns a new instance of this fragment for the given section number.
     */
    public static CategoryCollege newInstance() {
        CategoryCollege fragment = new CategoryCollege();
 //       parent = parent;

//        Bundle args = new Bundle();
//        args.putInt(ARG_SECTION_NUMBER, sectionNumber);
//        fragment.setArguments(args);
        return fragment;
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        final View rootView = inflater.inflate(R.layout.fragment_college, container, false);
        lv = (ListView)rootView.findViewById(R.id.collegeListView);

        createList();
        ListAdapter adapter = new ListAdapter(getActivity(), listItems);
        lv.setAdapter(adapter);

        lv.setOnItemClickListener( new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                ListItem li = (ListItem)adapterView.getItemAtPosition(i);
                ImageView img = (ImageView) view.findViewById(R.id.toggle_money);
                if(li.getChecked_Icon() != true){
                    li.setChecked_icon(true);
                    img.setImageResource(R.drawable.ic_money_green);
                }
                else {
                    li.setChecked_icon(false);
                    img.setImageResource(R.drawable.ic_money_grey);
                }
                //parent.budget.setCollege(listItems);
                Budget bud = (Budget)getActivity().getApplication();
                bud.setCollege(listItems);
            }
        });

        return rootView;
    }

    public void createList(){
        listItems = new ArrayList<ListItem>();
        listArray = getResources().getStringArray(R.array.category_college);

        for(int idx = 0; idx < listArray.length; ++idx){
            ListItem item = new ListItem(listArray[idx], false);

            listItems.add(item);
        }
    }
}
