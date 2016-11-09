package com.example.alexis.tdmoneyed;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;

/**
 * A {@link FragmentPagerAdapter} that returns a fragment corresponding to
 * one of the tabs/pages.
 */
public class PagerAdapter extends FragmentPagerAdapter {

    public PagerAdapter(FragmentManager fm) {
        super(fm);
    }

    @Override
    public Fragment getItem(int position) {
        // getItem is called to instantiate the fragment for the given page.
        // Return a PlaceholderFragment (defined as a static inner class below).
        switch(position) {
            case 0:
                return CatCollegeActivity.newInstance(1);
            case 1:
                return CatFoodActivity.newInstance(2);
            case 2:
                return CatTravelActivity.newInstance(3);
            case 3:
                return CatDebtActivity.newInstance(4);
            case 4:
                return CatEntertainActivity.newInstance(5);
            case 5:
                return CatPetsActivity.newInstance(6);
            case 6:
                return CatPersonalActivity.newInstance(7);
            default:
                return null;
        }
    }

    @Override
    public int getCount() {
        // Show 3 total pages.
        return 7;
    }

    @Override
    public CharSequence getPageTitle(int position) {
        switch (position) {
            case 0:
                return "College";
            case 1:
                return "Food";
            case 2:
                return "Transportation";
            case 3:
                return "Debt";
            case 4:
                return "Entertainment";
            case 5:
                return "Pets";
            case 6:
                return "Personal";
        }
        return null;
    }
}