package com.example.alexis.tdmoneyed;

/*
* Intro adapted from: https://github.com/paolorotolo/AppIntro
* */

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.content.ContextCompat;

import com.github.paolorotolo.appintro.AppIntro;
import com.github.paolorotolo.appintro.AppIntroFragment;

public class TDMoneyEdIntro extends AppIntro {

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		//setContentView(R.layout.activity_tdmoney_ed_intro);

		// Add your slide's fragments here
		// AppIntro will automatically generate the dots indicator and buttons.
//		addSlide(first_fragment);
//		addSlide(second_fragment);
//		addSlide(third_fragment);
//		addSlide(fourth_fragment);

		// Instead of fragments, you can also use our default slide
		// Just set a title, description, background and image. AppIntro will do the rest

		addSlide(AppIntroFragment.newInstance("TD Money Ed", "Get started by clicking the Manage Budget button on the home screen.", R.drawable.slide_main, ContextCompat.getColor(this, R.color.TDLightGreen)));
		addSlide(AppIntroFragment.newInstance("Create your budget", "Scroll through the categories and enter values that apply to your budget. Submit your input by clicking the folder button at the bottom of the screen.", R.drawable.slide_budget, ContextCompat.getColor(this, R.color.TDLightGreen)));
		addSlide(AppIntroFragment.newInstance("Settings", "Enter your information to receive a detailed text message if you go out of budget on any of the categories.", R.drawable.slide_settings, ContextCompat.getColor(this, R.color.TDLightGreen)));
		addSlide(AppIntroFragment.newInstance("Share your budget", "Insure your phones NFC and Beam capabilities are active. Click the Share Budget button on the home screen on two phones and align them back to back to transfer your budget data from one phone to the other.", R.drawable.slide_beam, ContextCompat.getColor(this, R.color.TDLightGreen)));
		addSlide(AppIntroFragment.newInstance("Widget", "Drag the widget to your home screen for quick review of your budgets totals. If the widgets header turns red click on the widget to launch the app and find out which category was over spent on.", R.drawable.slide_widget, ContextCompat.getColor(this, R.color.TDLightGreen)));
		addSlide(AppIntroFragment.newInstance("Main Menu", "Relocate yourself within the app any time by accessing the apps main menu within the toolbar.", R.drawable.slide_menu, ContextCompat.getColor(this, R.color.TDLightGreen)));
		// OPTIONAL METHODS

		// SHOW or HIDE the statusbar
		showStatusBar(true);

		// Edit the color of the nav bar on Lollipop+ devices
		//setNavBarColor(R.color.TDLightGreen);

		// Turn vibration on and set intensity
		// NOTE: you will need to ask VIBRATE permission in Manifest if you haven't already
//		setVibrate(true);
//		setVibrateIntensity(30);

		// Animations -- use only one of the below. Using both could cause errors.
		setFadeAnimation(); // OR
//		setZoomAnimation(); // OR
//		setFlowAnimation(); // OR
//		setSlideOverAnimation(); // OR
//		setDepthAnimation(); // OR
		//setCustomTransformer(yourCustomTransformer);

		// Permissions -- takes a permission and slide number
		//askForPermissions(new String[]{Manifest.permission.NFC}, 3);
	}

	@Override
	public void onDonePressed(Fragment currentFragment) {
		MainActivity.introShown = true;
		SharedPreferences settings = getSharedPreferences("tdmoneystate", Context.MODE_PRIVATE);
		SharedPreferences.Editor editor = settings.edit();

		editor.putBoolean("introDisplayed", true);
		editor.apply();
		finish();
	}

	@Override
	public void onSkipPressed(Fragment currentFragment){
		MainActivity.introShown = true;
		finish();
	}
}
