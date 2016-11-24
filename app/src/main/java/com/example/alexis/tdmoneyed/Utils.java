package com.example.alexis.tdmoneyed;

import android.support.annotation.Nullable;
import android.util.Log;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.ObjectInput;
import java.io.ObjectInputStream;
import java.io.ObjectOutput;
import java.io.ObjectOutputStream;
import java.text.NumberFormat;

/**
 * Utils
 * Offers static methods to perform various tasks
 *
 */

public class Utils {

	@Nullable
	public static byte[] toByteArray(Object o){
		ByteArrayOutputStream bos = new ByteArrayOutputStream();
		ObjectOutput out = null;
		try {
			out = new ObjectOutputStream(bos);
			out.writeObject(o);
			out.flush();
			return bos.toByteArray();
		}
		catch(IOException ex){
			Log.e("Utils.toByteArray", "IOException " + ex.getMessage());
		}
		finally {
			try {
				bos.close();
			} catch (IOException ex) {
				// ignore close exception
			}
		}
		return null;
	}

	@Nullable
	public static Object fromByteArray(byte[] bytes){
		ByteArrayInputStream bis = new ByteArrayInputStream(bytes);
		ObjectInput in = null;
		try {
			in = new ObjectInputStream(bis);
			return in.readObject();

		}
		catch(IOException ex)
		{
			Log.e("Utils.fromByteArray", "IOException " + ex.getMessage());
		}
		catch(ClassNotFoundException cnfe)
		{
			Log.e("Utils.fromByteArray", "ClassNotFoundException " + cnfe.getMessage());
		}
		finally {
			try {
				if (in != null) {
					in.close();
				}
			} catch (IOException ex) {
				// ignore close exception
			}
		}
		return null;
	}

	public static String getDoubleAsCurrency(double amount){
		NumberFormat formatter = NumberFormat.getCurrencyInstance();
		return formatter.format(amount);
	}

}
