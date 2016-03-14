package myapp.tae.ac.uk.myweatherapp.ui.adapters;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;

import myapp.tae.ac.uk.myweatherapp.ui.fragments.FragmentTodayWeather;
import myapp.tae.ac.uk.myweatherapp.ui.fragments.FragmentWindIfo;

/**
 * Created by Karma on 12/03/16.
 */
public class AdapterViewPager extends FragmentPagerAdapter {
    FragmentTodayWeather fTodayWeather;
    FragmentWindIfo fWindInfo;

    public AdapterViewPager(FragmentManager fm) {
        super(fm);
        fTodayWeather = new FragmentTodayWeather();
        fWindInfo = new FragmentWindIfo();
    }

    @Override
    public Fragment getItem(int position) {
        switch (position) {
            case 0:
                return fTodayWeather;
            case 1:
                return fWindInfo;
            default:
                return null;
        }
    }

    @Override
    public CharSequence getPageTitle(int position) {
        switch (position) {
            case 0:
                return "Today Weather";
            case 1:
                return "Today Wind";
            default:
                return null;
        }
    }

    @Override
    public int getCount() {
        return 2;
    }
}
