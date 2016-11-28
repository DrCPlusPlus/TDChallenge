package com.example.alexis.tdmoneyed;

import android.content.Context;
import android.net.Uri;
import android.os.Bundle;
import android.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.RelativeLayout;

import com.github.mikephil.charting.charts.BarChart;
import com.github.mikephil.charting.data.BarData;
import com.github.mikephil.charting.data.BarDataSet;
import com.github.mikephil.charting.data.BarEntry;
import com.github.mikephil.charting.utils.ColorTemplate;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.util.ArrayList;


/**
 * A simple {@link Fragment} subclass.
 * Activities that contain this fragment must implement the
 * {@link ChartFragment.OnFragmentInteractionListener} interface
 * to handle interaction events.
 * Use the {@link ChartFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class ChartFragment extends Fragment {

  //  private OnFragmentInteractionListener mListener;
    private Context context = App.getAppContext();
    protected Budget budget;// = MainActivity.getBudget();
    private String budgetFile = "budgetFile.bin";

    public ChartFragment() {
        // Required empty public constructor
    }

    // TODO: Rename and change types and number of parameters
    public static ChartFragment newInstance(String param1, String param2) {
        ChartFragment fragment = new ChartFragment();
        Bundle args = new Bundle();

        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View v = inflater.inflate(R.layout.fragment_chart, container, false);
        //RelativeLayout rl = (RelativeLayout)v.findViewById(R.id.inner_layout);
        FrameLayout rl = (FrameLayout)v.findViewById(R.id.frag_layout);
        ImageView imgChart = (ImageView)v.findViewById(R.id.chart);

  //      budget = MainActivity.getBudget();
        budget = new Budget();
        try {
            ObjectInputStream getBudget = new ObjectInputStream(context.openFileInput(budgetFile));
            budget = (Budget)getBudget.readObject();
            getBudget.close();
        } catch (FileNotFoundException ex){
            ex.printStackTrace();
        } catch (IOException ex){
            ex.printStackTrace();
        } catch(ClassNotFoundException ex){
            ex.printStackTrace();
        }

        ArrayList<BarEntry> entries = new ArrayList<>();
        if(budget != null) {
            // set bar chart
            float budgetChart = (float) budget.getBudgeted();
            float saveGoalChart = (float) budget.getSaveGoal();
            float spentChart = (float) budget.getSpent();
            float saveActualChart = (float) budget.getSaveActual();
            entries.add(new BarEntry(budgetChart, 0));
            entries.add(new BarEntry(saveGoalChart, 1));
            entries.add(new BarEntry(spentChart, 2));
            entries.add(new BarEntry(saveActualChart, 3));
        }
        BarDataSet dataset = new BarDataSet(entries, "Budget Totals");
        // charts x-axis labels
        ArrayList<String> labels = new ArrayList<String>();
        labels.add("Budget");
        labels.add("Goal");
        labels.add("Spent");
        labels.add("Saved");
        // create chart
        BarChart chart = new BarChart(context);
        float x = 5;
        rl.addView(chart);
        BarData data = new BarData(labels, dataset);
        chart.setData(data);
        chart.setDescription("Budgets Totals");
        dataset.setColors(ColorTemplate.VORDIPLOM_COLORS);

        return v;
    }

    // TODO: Rename method, update argument and hook method into UI event
    public void onButtonPressed(Uri uri) {

    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);

    }

    @Override
    public void onDetach() {
        super.onDetach();

    }


    public interface OnFragmentInteractionListener {
        // TODO: Update argument type and name
        void onFragmentInteraction(Uri uri);
    }
}
