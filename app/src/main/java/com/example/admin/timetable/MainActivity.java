package com.example.admin.timetable;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.os.Build;
import android.os.Bundle;
import android.support.design.widget.TabLayout;
import android.support.v4.view.ViewPager;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.text.util.Linkify;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import com.example.admin.timetable.Adapter.DayTabPagerAdapter;
import com.example.admin.timetable.Enum.Day;
import com.example.admin.timetable.Enum.Sort;
import com.example.admin.timetable.Fragment.FmTab;

import java.util.Calendar;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class MainActivity extends AppCompatActivity {
    Toolbar toolbar;
    ViewPager viewPager;
    DayTabPagerAdapter cpa;
    Calendar cal;

    boolean checkFavorite = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        initToolbar();
        initTabViewPager();
        initStatusBar();
    }

    public void initToolbar() {
        toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar); //액션바로 설정, 또한 이는 기본 액션바가 존재하면 에러남. style.xml에서 노액션바해야됨

        ActionBar actionBar = getSupportActionBar();
        actionBar.setDisplayShowTitleEnabled(true); //액션바에 표시되는 제목의 표시유무를 설정

        LayoutInflater inflater = (LayoutInflater) getSystemService(LAYOUT_INFLATER_SERVICE);
        View actionbar = inflater.inflate(R.layout.custom_actionbar, null);

        actionBar.setCustomView(actionbar);
        actionBar.setDisplayHomeAsUpEnabled(true);// 액션바 왼쪽의 <- 버튼
        actionBar.setHomeAsUpIndicator(R.drawable.menu);
        toolbar.setOverflowIcon(getResources().getDrawable(R.drawable.sort_variant));
    }

    public void initTabViewPager() {
        TabLayout tabs = findViewById(R.id.tabs);
        viewPager = findViewById(R.id.viewPager);
        // 탭 눌렀을때 해당 위치로 이동
        tabs.addOnTabSelectedListener(new TabLayout.OnTabSelectedListener() {
            @Override
            public void onTabSelected(TabLayout.Tab tab) {
                viewPager.setCurrentItem(tab.getPosition());
            }

            @Override
            public void onTabUnselected(TabLayout.Tab tab) {

            }

            @Override
            public void onTabReselected(TabLayout.Tab tab) {

            }
        });

        cpa = new DayTabPagerAdapter(getSupportFragmentManager()); //어댑터
        final String[] days = {"일요일", "월요일", "화요일", "수요일", "목요일", "금요일", "토요일", "그 외", "신작"};
        final FmTab[] fragments = {FmTab.newInstance(Day.SUNDAY), FmTab.newInstance(Day.MONDAY),
                FmTab.newInstance(Day.TUESDAY), FmTab.newInstance(Day.WEDNESDAY),
                FmTab.newInstance(Day.THURSDAY), FmTab.newInstance(Day.FRIDAY),
                FmTab.newInstance(Day.SATURDAY), FmTab.newInstance(Day.ETC), FmTab.newInstance(Day.NEW)};
        for (int i = 0; i < fragments.length; i++) {
            cpa.addFragment(days[i], fragments[i]);
        }
        viewPager.setAdapter(cpa);

        viewPager.setCurrentItem(checkDate());
        viewPager.addOnPageChangeListener(new TabLayout.TabLayoutOnPageChangeListener(tabs)); // 뷰페이저 이동했을때 해당 위치로 이동

        tabs.setupWithViewPager(viewPager);
    }

    private int checkDate() {
        cal = Calendar.getInstance();
        int nWeek = cal.get(Calendar.DAY_OF_WEEK); //일요일 : 1 ~ 토요일 : 7
        return nWeek - 1;
    }

    private void changeSort(Sort sort) {
        int index = viewPager.getCurrentItem();
        cpa.changeSort(index, sort);
    }

    private void initStatusBar() {
        if (Build.VERSION.SDK_INT >= 21) {
            // 21 버전 이상일 때
            getWindow().setStatusBarColor(getResources().getColor(R.color.colorPrimaryDark));
        }
    }

    private void showDialog(Context context) {
        AlertDialog.Builder builder = new AlertDialog.Builder(context);
        LayoutInflater layoutInflater = MainActivity.this.getLayoutInflater();
        View view = layoutInflater.inflate(R.layout.dialog_info, null);
        final TextView editText_message = view.findViewById(R.id.infoDiaog_textview_message);
        String message = "앱 버전 : 애니편성표 ver.1.0\n\n\n앱 개발자 : syshinkr@hanmail.net\nAPI 제공 : 애니시아";
        editText_message.setText(message);
        Linkify.TransformFilter mTransform = new Linkify.TransformFilter() {
            @Override public String transformUrl(Matcher match, String url) { return ""; }
        };
        Pattern pattern = Pattern.compile("애니시아");
        Linkify.addLinks(editText_message, pattern, "https://www.anissia.net",null,mTransform);

        builder.setView(view).setPositiveButton("확인", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {

            }
        });

        builder.create().show();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                Toast.makeText(this, "준비 중입니다", Toast.LENGTH_SHORT).show();
                return true;
            case R.id.action_info :
                showDialog(this);
                return true;
            case R.id.action_favorite:
                item.setIcon(checkFavorite ? R.drawable.heart_outline : R.drawable.heart);
                //TODO : 즐겨찾기 버튼 애니메이션 넣기
//                item.startAnimation(AnimationUtils.loadAnimation(this, R.anim.favorite_anim));
                checkFavorite = checkFavorite ?  false : true;
                Toast.makeText(this, "준비 중입니다", Toast.LENGTH_SHORT).show();
                return true;
            case R.id.action_title_asc:
                changeSort(Sort.TITLE_ASC);
                return true;
            case R.id.action_title_desc:
                changeSort(Sort.TITLE_DESC);
                return true;
            case R.id.action_time_asc:
                changeSort(Sort.TIME_ASC);
                return true;
            case R.id.action_time_desc:
                changeSort(Sort.TIME_DESC);
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }
}