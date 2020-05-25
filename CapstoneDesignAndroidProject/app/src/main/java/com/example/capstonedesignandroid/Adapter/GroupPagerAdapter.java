package com.example.capstonedesignandroid.Adapter;


import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentStatePagerAdapter;

import com.example.capstonedesignandroid.Fragment.GroupFragment1;
import com.example.capstonedesignandroid.Fragment.GroupFragment2;
import com.example.capstonedesignandroid.Fragment.GroupFragment3;

public class GroupPagerAdapter extends FragmentStatePagerAdapter {
    int mNumOfTabs;

    public GroupPagerAdapter(FragmentManager fm, int NumOfTabs) {
        super(fm);
        this.mNumOfTabs = NumOfTabs;
    }

    @Override
    public Fragment getItem(int position) {

        switch (position) {
            case 0:
                GroupFragment1 tab1 = new GroupFragment1();
                return tab1;
            case 1:
                GroupFragment2 tab2 = new GroupFragment2();
                return tab2;
            case 2:
                GroupFragment3 tab3 = new GroupFragment3();
                return tab3;
            default:
                return null;
        }
    }

    @Override
    public int getCount() {
        return mNumOfTabs;
    }
}

