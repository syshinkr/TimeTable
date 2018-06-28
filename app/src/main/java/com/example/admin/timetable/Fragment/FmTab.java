package com.example.admin.timetable.Fragment;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.admin.timetable.Adapter.DayRecyclerViewAdapter;
import com.example.admin.timetable.Enum.Day;
import com.example.admin.timetable.R;
import com.example.admin.timetable.Enum.Sort;

public class FmTab extends Fragment{
    private static final String ARG_INT_DAY = "day";
    private int iDay = 0;
    private DayRecyclerViewAdapter dayRecyclerViewAdapter;

    public FmTab() {

    }

    public static FmTab newInstance(Day day) {
        FmTab fragment = new FmTab();
        Bundle args = new Bundle();

        int iDay = day.getValue();
        args.putInt(ARG_INT_DAY, iDay);
        fragment.setArguments(args);

        return fragment;
    }
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            iDay = getArguments().getInt(ARG_INT_DAY);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        ViewGroup v = (ViewGroup) inflater.inflate(R.layout.ani_recyclerview, container, false);
        RecyclerView recyclerView = v.findViewById(R.id.mainactivity_recyclerView);
        recyclerView.setHasFixedSize(true);
        recyclerView.scrollToPosition(0);
        recyclerView.setLayoutManager(new LinearLayoutManager(inflater.getContext()));
        dayRecyclerViewAdapter = new DayRecyclerViewAdapter(container.getContext(), iDay);
        recyclerView.setAdapter(dayRecyclerViewAdapter);

        return v;
    }

    public void changeSort(Sort sort) {
        dayRecyclerViewAdapter.changeSort(sort);
    }

}
