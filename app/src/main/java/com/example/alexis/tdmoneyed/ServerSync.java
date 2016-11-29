package com.example.alexis.tdmoneyed;

import android.content.Context;
import android.os.AsyncTask;
import android.telephony.SmsManager;
import android.widget.Toast;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.OutputStream;
import java.net.InetSocketAddress;
import java.net.Socket;
import java.net.UnknownHostException;
import java.nio.ByteBuffer;
import java.nio.ByteOrder;
import java.util.ArrayList;

/**
 * An AsyncTask to communicate with the server
 * and update our budget based on new transactions
 */

public class ServerSync extends AsyncTask<Void, Void, Boolean> {
	private static final String serverAddress = "systemaccess.duckdns.org";
	private static final int port = 52687;
	private static final String budgetFile = "budgetFile.bin";
	private String settingsFile = "settingsFile.bin";
	private Socket s;


	private ArrayList<Category> allCategories;
	private ArrayList<Transaction> allAccountTransactions;
	private String errMessage;
	private Budget budget;
	private Settings settings;
	private Context context;
	private iBudgetUpdate budgetUpdate;

	public ServerSync (Context context, iBudgetUpdate ibu){
		this.context = context;
		this.budgetUpdate = ibu;
	}

	private void close(){
		try{
			s.close();
		}
		catch(Exception ex)
		{

		}
	}

	@Override
	protected Boolean doInBackground(Void... voids) {
		boolean returnValue = true;

		//load budget
		loadBudget();

		//load settings
		loadSettings();

		if (!connect())
			return false;

		//get all the categories
		if (!getAllCategories()) {
			returnValue = false;
			close();
		}

		if (settings.getAccountNumber() == null || settings.getAccountNumber().equals(""))
		{
			close();
			errMessage = "Please enter an account number";
			return false;
		}

		//get all the new transactions for this account
		if (!getAccountTransactions())
			returnValue = false;

		if (returnValue)
			returnValue = updateTransactions();

		if(returnValue)
			saveBudget();

		close();
		return returnValue;
	}

	@Override
	protected void onPostExecute(Boolean res){
		if (budgetUpdate != null) {
			if (!res)
				Toast.makeText(context, errMessage, Toast.LENGTH_LONG).show();
			else
				Toast.makeText(context, "Async Successful!", Toast.LENGTH_LONG).show();
			budgetUpdate.setBudget(budget);
		}


	}

	private void loadBudget(){
		budget = new Budget();
		try {
			ObjectInputStream getBudget = new ObjectInputStream(context.openFileInput(budgetFile));
			budget = (Budget)getBudget.readObject();
			if (budget == null)
				budget = new Budget();
			getBudget.close();
		} catch (FileNotFoundException ex){
			ex.printStackTrace();
		} catch (IOException ex){
			ex.printStackTrace();
		} catch(ClassNotFoundException ex){
			ex.printStackTrace();
		}
	}

	private void loadSettings(){
		settings = new Settings();
		try {
			ObjectInputStream getSettings = new ObjectInputStream(context.openFileInput(settingsFile));
			settings = (Settings)getSettings.readObject();
			if (settings == null)
				settings = new Settings();
			getSettings.close();
		} catch (FileNotFoundException ex){
			ex.printStackTrace();
		} catch (IOException ex){
			ex.printStackTrace();
		} catch(ClassNotFoundException ex){
			ex.printStackTrace();
		}
	}

	private void saveBudget()
	{
		try {
			ObjectOutputStream setBudget = new ObjectOutputStream(context.openFileOutput(budgetFile, context.MODE_PRIVATE));
			setBudget.writeObject(budget);
			setBudget.close();
		}catch (FileNotFoundException ex){
			ex.printStackTrace();
		}catch(IOException ex){
			ex.printStackTrace();
		}
	}
	//connect
	//Attempt connection to the server
	//Accepts: -
	//Returns: true if the connection succeeded, false if not
	private  boolean connect()
	{
		try
		{
			s = new Socket();
			s.connect(new InetSocketAddress(serverAddress, port), 2000);
			return true;
		}
		catch (UnknownHostException uhe) {
			errMessage = "Unknown Host Exception..." + "\n" + uhe.getMessage();
		}
		catch (java.io.IOException ioex) {
			errMessage = "IO Exception..." + "\n" + ioex.getMessage();
		}
		catch (Exception ex) {
			errMessage = "Exception: " + ex.getMessage();
		}
		return  false;
	}

	private void getServerErrorMessageAndDisplay()
	{
		try {

			int packageSize = getNumberOfElementsInStream();

			byte[] receiver = new byte[packageSize];
			s.getInputStream().read(receiver);

			StringBuilder sb = new StringBuilder();

			for (int x = 0; x < packageSize; ++x)
			{
				if (0 == receiver[x])
					break;
				sb.append((char)receiver[x]);
			}

			errMessage = sb.toString();		//TODO display somehow? toast message?

		}
		catch(IOException ex){

		}
	}

	public boolean getAccountTransactions()
	{
		try {
			OutputStream os = s.getOutputStream();


			byte[] size = new byte[settings.getAccountNumber().length() + 2 *(Integer.SIZE / Byte.SIZE) + 1 ];
			ByteBuffer buffer = ByteBuffer.wrap(size);
			buffer.order(ByteOrder.LITTLE_ENDIAN);
			buffer.putInt(1);
			buffer.put(Definitions.GET_NEW_TRANSACTIONS_FOR_ACCOUNT);
			buffer.putInt((settings.getAccountNumber().length()));
			buffer.put(settings.getAccountNumber().getBytes());

			os.write(size);
			os.flush();
			int TransactionStructureSize = 24;     //from c++ server

			byte returnCode = receiveCode();

			if (Definitions.ACK_OK == returnCode)
			{
				int packageSize = getNumberOfElementsInStream();

				if (packageSize == 0)
				{
					allAccountTransactions = new ArrayList<>();
					return true;
				}

				//get the transactions
				byte[] receiver = new byte[packageSize * TransactionStructureSize];

				s.getInputStream().read(receiver);

				buffer = ByteBuffer.wrap(receiver);
				buffer.order(ByteOrder.LITTLE_ENDIAN);

				ArrayList<Transaction> items = new ArrayList<>();
				for (int i = 0; i < packageSize; ++i)
				{

					int id = buffer.getInt();
					int accountId = buffer.getInt();
					double Amount = buffer.getDouble();
					int categoryId = buffer.getInt();
					boolean appliedToPhone = buffer.get() != 0;
					//these consume the padding that c++ puts in the structure
					short dummyShort = buffer.getShort();
					byte dummyByte = buffer.get();
					Transaction t = new Transaction(id, appliedToPhone, categoryId, Amount, accountId);
					items.add(t);
					budget.addTransaction(t);
				}
				allAccountTransactions = items;
				checkIfOverBudget();
				return true;
			}
			else if (Definitions.ACK_ERROR == returnCode)
			{
				getServerErrorMessageAndDisplay();
			}
		}
		catch(Exception ex){
			errMessage = "error getting transactions " + ex.getMessage();
		}
		return false;
	}

	//getNumberOfElementsInStream
	//The server will send the number of elements that are in the stream in the
	//first four bytes of the stream
	//Accepts: -
	//Returns: an integer representing the number of elements in the stream
	//Throws: IOException if there is an issue with the Socket
	private int getNumberOfElementsInStream() throws IOException
	{
		byte[] receiver = new byte[4];      //get the number of elements
		s.getInputStream().read(receiver);
		ByteBuffer bb = ByteBuffer.wrap(receiver);
		bb.order(ByteOrder.LITTLE_ENDIAN);

		return bb.getInt();
	}

	private byte receiveCode()
	{
		try{
			//BufferedInputStream is = new BufferedInputStream(s.getInputStream());
			byte[] receiver = new byte[5];      //get the number of elements
			s.getInputStream().read(receiver);

			//first four bytes are the size which is always 1
			//so just return the last byte which is the return code (ACK_OK OR ACK_ERROR)
			return receiver[4];
		}
		catch(IOException ex)
		{
			errMessage = "error getting return code " + ex.getMessage();
		}
		return -1;
	}


	private boolean getAllCategories()
	{
		try {
			OutputStream os = s.getOutputStream();

			byte[] size = new byte[5];
			ByteBuffer buffer = ByteBuffer.wrap(size);
			buffer.order(ByteOrder.LITTLE_ENDIAN);
			buffer.putInt(1);
			buffer.put(Definitions.GET_ALL_CATEGORIES);
			os.write(size);
			os.flush();
			int CategoryStructureSize = 52;     //from c++ server

			byte returnCode = receiveCode();

			if (Definitions.ACK_OK == returnCode)
			{
				int packageSize = getNumberOfElementsInStream();

				if (packageSize == 0)
				{
					allCategories = new ArrayList<>();
					return true;
				}

				//get the transactions
				byte[] receiver = new byte[packageSize * CategoryStructureSize];

				s.getInputStream().read(receiver);


				buffer = ByteBuffer.wrap(receiver);
				buffer.order(ByteOrder.LITTLE_ENDIAN);

				ArrayList<Category> items = new ArrayList<>();
				for (int i = 0; i < packageSize; ++i)
				{
					int id = buffer.getInt();
					StringBuilder sb = new StringBuilder();
					byte[] name = new byte[CategoryStructureSize - 4];
					buffer.get(name);
					for (int x = 0; x < name.length; ++x )
					{
						if (0 == name[x])
							break;
						sb.append((char) name[x]);
					}

					items.add(new Category(id, sb.toString()));
				}
				allCategories = items;
				budget.setCategories(items);
				return true;
			}
			else if (Definitions.ACK_ERROR == returnCode)
			{
				getServerErrorMessageAndDisplay();
			}
		}
		catch(Exception ex){
			errMessage = "error getting transactions " + ex.getMessage();
		}
		return false;
	}

	//update the transactions to indicate
	//they are now part of the budget
	//and return them to the server
	private boolean updateTransactions()
	{
		try {
			for (Transaction t : allAccountTransactions) {
				t.setAppliedToPhone(true);
				boolean res = updateTransaction(t);
				if (!res)
					return false;
			}
		}
		catch(IOException ex)
		{
			errMessage = "Unable to update transactions! " + ex.getMessage();
			return false;
		}
		return true;
	}

	private boolean updateTransaction(Transaction t) throws IOException
	{
		OutputStream os = s.getOutputStream();

		byte[] size = new byte[5];
		ByteBuffer buffer = ByteBuffer.wrap(size);
		buffer.order(ByteOrder.LITTLE_ENDIAN);
		buffer.putInt(1);
		buffer.put(Definitions.UPDATE_TRANSACTION);
		os.write(size);
		os.flush();

		buffer = ByteBuffer.allocate(24 + 4);			//size of the transaction structure in c++ server + 4 for the size indicator
		buffer.order(ByteOrder.LITTLE_ENDIAN);

		buffer.putInt(24);
		buffer.putInt(t.getId());
		buffer.putInt(t.getAccountId());
		buffer.putDouble(t.getAmount());
		buffer.putInt(t.getCategoryId());
		buffer.put(t.isAppliedToPhone() ? (byte)1:(byte)0);

		os.write(buffer.array());

		byte returnCode = receiveCode();
		return returnCode == Definitions.ACK_OK;
	}

	//checkIfOverBudget
	//check if the user has gone over budget
	//if so sends a text message to the guardian if provided
	//will only run on subsequent new transactions that will put the user into further debt
	private void checkIfOverBudget(){
		if (budget.isOverBudget()) {
			try {
				StringBuilder sb = new StringBuilder();
				String overBudgetCategories = budget.getOverBudgetCategories();
				SmsManager smsManager = SmsManager.getDefault();

				if(!settings.getGardiansNumber().equals("")) {
					sb.append("TDMoneyEd would like you to know that a youth assigned to you has gone over budget in the following categories: ");
					sb.append(overBudgetCategories);
					smsManager.sendTextMessage(settings.getGardiansNumber(), null, sb.toString(), null, null);
				}

				if(!settings.getYouthsNumber().equals("")) {
					sb = new StringBuilder();
					sb.append("TDMoneyEd would like you to know that you have gone over your budget in the following categories, your guardian will be notified as well: ");
					sb.append(overBudgetCategories);
					smsManager.sendTextMessage(settings.getYouthsNumber(), null, sb.toString(), null, null);
				}
			}
			catch (Exception e) {
				e.printStackTrace();
			}
		}
	}
}
