package com.example.admin.timetable.Fragment;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.design.widget.BottomSheetDialogFragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.admin.timetable.Adapter.CaptionRecyclerViewAdapter;
import com.example.admin.timetable.R;

public class CaptionBottomSheetDialog extends BottomSheetDialogFragment {

    private static final String ARG_TITLE = "title";
    private static final String ARG_SD = "date";
    private static final String ARG_INDEX = "index";

    private String title="";
    private String date =""; // start date
    private int index = 0; // 애니 고유 번호

    public CaptionBottomSheetDialog() {
        // Required empty public constructor
    }

    public static CaptionBottomSheetDialog newInstance(String title, String date, int index) {
        CaptionBottomSheetDialog fragment = new CaptionBottomSheetDialog();
        Bundle args = new Bundle();
        args.putString(ARG_TITLE, title);
        args.putString(ARG_SD, date);
        args.putInt(ARG_INDEX, index);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            title = getArguments().getString(ARG_TITLE);
            date = getArguments().getString(ARG_SD);
            index = getArguments().getInt(ARG_INDEX);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.fragment_caption_bottom_sheet_dialog, container, false);
        return v;
    }

    @Override
    public void onViewCreated(@NonNull View v, @Nullable Bundle savedInstanceState) {
        RecyclerView recyclerView = v.findViewById(R.id.bottomSheetDialog_recyclerView);
        recyclerView.setLayoutManager(new LinearLayoutManager(this.getContext()));
        recyclerView.setAdapter(new CaptionRecyclerViewAdapter(this.getContext(), index));

        TextView textViewTitle = v.findViewById(R.id.bottomSheetDialog_textview_title);
        textViewTitle.setText(title);
        TextView textViewDate = v.findViewById(R.id.bottomSheetDialog_textview_date);
        textViewDate.setText(date);
    }
}
