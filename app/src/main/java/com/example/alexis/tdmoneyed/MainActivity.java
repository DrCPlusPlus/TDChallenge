package com.example.alexis.tdmoneyed;

import android.content.Context;
import android.content.Intent;
import android.nfc.NdefMessage;
import android.nfc.NdefRecord;
import android.nfc.NfcAdapter;
import android.nfc.NfcEvent;
import android.os.Bundle;
import android.os.Parcelable;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.Serializable;

public class MainActivity extends AppCompatActivity implements Serializable, NfcAdapter.CreateNdefMessageCallback {

    private Budget budget;
    private String budgetFile = "budgetFile.bin";
    private Context context = App.getAppContext();
    private TextView income, budgeted, saveGoal, spent, saveActual;
	private Button btnShareBudget;
	private NfcAdapter mNfcAdapter;
	boolean beamEnabled;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // set toolbar
        Toolbar my_toolbar = (Toolbar) findViewById(R.id.my_toolbar);
        setSupportActionBar(my_toolbar);
        getSupportActionBar().setTitle("Financial Education");
        getSupportActionBar().setIcon(R.drawable.td_shield);

		btnShareBudget = (Button)findViewById(R.id.shareBtn);
		beamEnabled = false;
        try {
            ObjectInputStream getBudget = new ObjectInputStream(openFileInput(budgetFile));
            budget = (Budget)getBudget.readObject();
            if(budget == null)
                budget = new Budget();
            getBudget.close();
        } catch (FileNotFoundException ex){
            ex.printStackTrace();
        } catch (IOException ex){
            ex.printStackTrace();
        } catch(ClassNotFoundException ex){
            ex.printStackTrace();
        }

        income = (TextView)findViewById(R.id.income);
        budgeted = (TextView)findViewById(R.id.budgeted);
        saveGoal = (TextView)findViewById(R.id.savings_goal);
        spent = (TextView)findViewById(R.id.spent);
        saveActual = (TextView)findViewById(R.id.actual_savings);
		setHomeScreenInfo();

        mNfcAdapter = NfcAdapter.getDefaultAdapter(this);
        if (mNfcAdapter == null) {
            Toast.makeText(this, "NFC is not available", Toast.LENGTH_LONG).show();
			btnShareBudget.setEnabled(false);
        }
    }

	private void setHomeScreenInfo(){
		if(budget != null) {

			income.setText(Utils.getDoubleAsCurrency(budget.getIncome()));
			budgeted.setText(Utils.getDoubleAsCurrency(budget.getBudgeted()));
			saveGoal.setText(Utils.getDoubleAsCurrency(budget.getSaveGoal()));
			spent.setText(Utils.getDoubleAsCurrency(budget.getSpent()));
			saveActual.setText(Utils.getDoubleAsCurrency(budget.getSaveActual()));

		}
	}

    public void onClickBuild(View view){
        Intent ib = new Intent(this, BuilderActivity.class);
        startActivity(ib);
    }

    public void onClickSummary(View view){
        Intent is = new Intent(this, SummaryActivity.class);
        startActivity(is);
    }

	//Share Button Click
    public void onClickShare(View view){
		beamEnabled = !beamEnabled;
		if (beamEnabled) {
			// Register callback
			mNfcAdapter.setNdefPushMessageCallback(this, this);
			Toast.makeText(this, "Ready to share budget!", Toast.LENGTH_LONG).show();
		}
		else{
			//disable callback
			mNfcAdapter.setNdefPushMessageCallback(null, this);
			Toast.makeText(this, "Sharing disabled!", Toast.LENGTH_LONG).show();
		}


    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_builder, menu);
        return true;
    }

    // ToDo: exclude current activity from menu
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();
        Intent i;
        //noinspection SimplifiableIfStatement
        if (id == R.id.action_home) {
            i = new Intent(this, MainActivity.class);
            startActivity(i);
            return true;
        }
        if (id == R.id.action_builder) {
            i = new Intent(this, BuilderActivity.class);
            startActivity(i);
            return true;
        }
        if (id == R.id.action_summary) {
            i = new Intent(this, SummaryActivity.class);
            startActivity(i);
            return true;
        }
        if (id == R.id.action_settings) {
            i = new Intent(this, SettingsActivity.class);
            startActivity(i);
            return true;
        }
        if (id == R.id.action_help) {
            i = new Intent(this, HelpActivity.class);
            startActivity(i);
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

	private void saveBudget(){
		try {
			ObjectOutputStream setBudget = new ObjectOutputStream(openFileOutput(budgetFile, MODE_PRIVATE));
			setBudget.writeObject(budget);
			setBudget.close();
		}catch (FileNotFoundException ex){
			ex.printStackTrace();
		}catch(IOException ex){
			ex.printStackTrace();
		}
	}

    @Override
    protected void onPause() {
        super.onPause();
        // save application object
        saveBudget();
    }

	//region Android Beam Stuff

	@Override
	public void onResume() {
		super.onResume();
		// Check to see that the Activity started due to an Android Beam
		if (NfcAdapter.ACTION_NDEF_DISCOVERED.equals(getIntent().getAction())) {
			processIntent(getIntent());
		}
	}

    @Override
    public NdefMessage createNdefMessage(NfcEvent nfcEvent) {
		NdefMessage msg = new NdefMessage(
				new NdefRecord[] { NdefRecord.createMime("application/com.example.alexis.tdmoneyed", Utils.toByteArray(budget))
						/**
						 * The Android Application Record (AAR) is commented out. When a device
						 * receives a push with an AAR in it, the application specified in the AAR
						 * is guaranteed to run. The AAR overrides the tag dispatch system.
						 * You can add it back in to guarantee that this
						 * activity starts when receiving a beamed message. For now, this code
						 * uses the tag dispatch system.
						*/
						,NdefRecord.createApplicationRecord("com.example.alexis.tdmoneyed")
				});
		return msg;
    }

	@Override
	public void onNewIntent(Intent intent) {
		// onResume gets called after this to handle the intent
		setIntent(intent);
	}

    /**
     * Parses the NDEF Message from the intent and places it in the
     */
    void processIntent(Intent intent) {
        Parcelable[] rawMsgs = intent.getParcelableArrayExtra(
                NfcAdapter.EXTRA_NDEF_MESSAGES);
        // only one message sent during the beam
        NdefMessage msg = (NdefMessage) rawMsgs[0];
        // record 0 contains the MIME type, record 1 is the AAR, if present
		budget = (Budget)(Utils.fromByteArray(msg.getRecords()[0].getPayload()));

		saveBudget();
		setHomeScreenInfo();
    }
	//endregion
}
