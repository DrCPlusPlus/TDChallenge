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

		addSlide(AppIntroFragment.newInstance("TD Money Ed", "Budgeting", R.drawable.td_shield, ContextCompat.getColor(this, R.color.TdDarkGreen)));
		addSlide(AppIntroFragment.newInstance("TD Money Ed", "Budgeting", R.drawable.td_shield, ContextCompat.getColor(this, R.color.TdDarkGreen)));
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
