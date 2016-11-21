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
                return CategoryCollege.newInstance();
            case 1:
                return CategoryFood.newInstance();
            case 2:
                return CategoryTravel.newInstance();
            case 3:
                return CategoryDebt.newInstance();
            case 4:
                return CategoryEntertainment.newInstance();
            case 5:
                return CategoryPets.newInstance();
            case 6:
                return CategoryPersonal.newInstance();
            case 7:
                return CategoryIncome.newInstance();
            default:
                return null;
        }
    }

    @Override
    public int getCount() {
        // Show 3 total pages.
        return 8;
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
            case 7:
                return "Income";
        }
        return null;
    }
}