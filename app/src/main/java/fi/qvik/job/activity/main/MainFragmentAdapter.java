package fi.qvik.job.activity.main;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;

import java.util.Random;

import fi.qvik.job.fragment.BaseFragment;

/**
 * Created by Tommy on 16/03/16.
 */
public class MainFragmentAdapter extends FragmentStatePagerAdapter {

    public MainFragmentAdapter(FragmentManager fm) {
        super(fm);
    }

    @Override
    public Fragment getItem(int position) {
        BaseFragment f;
        switch (position) {
            case 0:
                f = new JobListFragment();
                break;
            default:
            case 1:
                f = new CanvasTestFragment();
                Bundle b = new Bundle();

                int[] array = new int[100];
                Random r = new Random();
                for (int i = 0; i < array.length; i++) {
                    array[i] = r.nextInt(100);
                }
                b.putIntArray(CanvasTestFragment.TEST_DATA_KEY, array);
                f.setArguments(b);

                break;

        }

        return f;
    }

    @Override
    public int getCount() {
        return 2;
    }

    @Override
    public CharSequence getPageTitle(int position) {
        return String.format("page%d", position);
    }
}
