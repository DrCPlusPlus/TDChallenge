package com.example.alexis.tdmoneyed;

import android.content.Context;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.Fragment;
import android.view.View;
import android.widget.AbsListView;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ListView;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.ObjectOutputStream;
import java.util.ArrayList;

import static android.content.Context.MODE_PRIVATE;

/**
 * Created on 2016-11-23.
 */

public abstract class CategoryBase extends Fragment implements View.OnFocusChangeListener {
	protected Budget budget = BuilderActivity.getBudget();
	private ArrayList<ListItem> listItems;
	private String budgetFile = "budgetFile.bin";
	private Context context = App.getAppContext();
	protected ListView lv;

	protected void createList(int stringArrayId, ArrayList<ListItem> workingList) {
		listItems = new ArrayList<ListItem>();
		String[] listArray = getResources().getStringArray(stringArrayId);

		if (workingList == null || workingList.size() < listArray.length) {
			for (int idx = 0; idx < listArray.length; ++idx) {
				ListItem item = new ListItem(listArray[idx], false, 0.00);
				listItems.add(item);
			}
		} else {
			for (int idx = 0; idx < listArray.length; ++idx) {
				String itemName = workingList.get(idx).getItem_name();
				Boolean checked = workingList.get(idx).getChecked_Icon();
				Double amount = workingList.get(idx).getAmount();
				ListItem item = new ListItem(itemName, checked, amount);
				listItems.add(item);
			}
		}

		ListAdapter adapter = new ListAdapter(getActivity(), listItems, this);

		lv.setAdapter(adapter);
		lv.setItemsCanFocus(true);
		lv.setFocusable(false);



		final FloatingActionButton fab = (FloatingActionButton)((BuilderActivity)getActivity()).findViewById(R.id.fab);
		lv.setOnScrollListener(new AbsListView.OnScrollListener() {
			@Override
			public void onScrollStateChanged(AbsListView view, int scrollState) { }

			@Override
			public void onScroll(AbsListView view, int firstVisibleItem, int visibleItemCount, int totalItemCount) {

				int lastItem = firstVisibleItem + visibleItemCount;
				if (firstVisibleItem == 0) {
					fab.setVisibility(View.VISIBLE);
				}else if (lastItem == totalItemCount) {
					fab.setVisibility(View.INVISIBLE);
				}else {
					fab.setVisibility(View.VISIBLE);
				}
			}
		});
	}

	public void onFocusChange(View v, boolean hasFocus) {
		if (!hasFocus) {

			final EditText Caption = (EditText) v;
			ListAdapter.ViewContainer container = (ListAdapter.ViewContainer)Caption.getTag();
			ImageView img = container.toggle_money;

			if (Caption.getText().toString().replace("$", "").replace(",", "").equals(""))
				Caption.setText(Utils.getDoubleAsCurrency(0.0));

			ListItem li = listItems.get(container.item_id);
			li.setAmount(Double.parseDouble(Caption.getText().toString().replace("$", "").replace(",", "")));

			if (li.getAmount() > 0.0){
				li.setChecked_icon(true);
				img.setImageResource(R.drawable.ic_money_green);
			}
			else{
				li.setChecked_icon(false);
				img.setImageResource(R.drawable.ic_money_grey);
			}

			//prevent setting the text of the editText field if it is already equal
			//as setText causes an onFocus event to trigger causing a forever loop
			if (!Utils.getDoubleAsCurrency(li.getAmount()).equals(Caption.getText().toString()))
				Caption.setText(Utils.getDoubleAsCurrency(li.getAmount()));

			setList(listItems);
		}
	}

	@Override
	public void onPause() {
		super.onPause();
		// save application object
		try {
			ObjectOutputStream setBudget = new ObjectOutputStream(context.openFileOutput(budgetFile,MODE_PRIVATE));
			setBudget.writeObject(budget);
			setBudget.close();
		}catch (FileNotFoundException ex){
			ex.printStackTrace();
		}catch(IOException ex){
			ex.printStackTrace();
		}
	}


	//force derive class to implement this method so that we can update the correct list in the budget
	public abstract void setList(ArrayList<ListItem> list);
}
