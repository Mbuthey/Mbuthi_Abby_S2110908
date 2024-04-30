package org.me.gcu.s2110908.adapter;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentStatePagerAdapter;

import org.me.gcu.s2110908.fragments.WeatherForecastFragment;

import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class WeatherForecastPagerAdapter extends FragmentStatePagerAdapter {

    private List<String> locations = Arrays.asList("Mauritius", "Glasgow", "London", "New York", "Oman", "Bangladesh");


    public WeatherForecastPagerAdapter(FragmentManager fm) {
        super(fm);
    }

    @Override
    public Fragment getItem(int position) {
        // Create and return the WeatherForecastFragment for the given location
        return WeatherForecastFragment.newInstance(locations.get(position));
    }

    @Override
    public int getCount() {
        return locations.size();
    }

    @Override
    public CharSequence getPageTitle(int position) {
        return locations.get(position);
//        return null;
    }
}
