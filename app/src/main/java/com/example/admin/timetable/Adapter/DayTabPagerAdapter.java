package com.example.admin.timetable.Adapter;

import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;

import com.example.admin.timetable.Fragment.FmTab;
import com.example.admin.timetable.Enum.Sort;

import java.util.ArrayList;

public class DayTabPagerAdapter extends FragmentStatePagerAdapter {
    ArrayList<String> Title_days = new ArrayList<>();
    public ArrayList<FmTab> fList = new ArrayList<>();

    public DayTabPagerAdapter(FragmentManager fm) {
        super(fm);
    }

    public void addFragment(String days, FmTab fragment) {
        this.Title_days.add(days);
        this.fList.add(fragment);
    }

    public void changeSort(int index, Sort sort) {
        fList.get(index).changeSort(sort);
    }

    @Override
    public CharSequence getPageTitle(int position) {
        return Title_days.get(position);
    }

    @Override
    public FmTab getItem(int position) {
        return this.fList.get(position);
    }

    @Override
    public int getCount() {
        return fList.size();
    }
}
