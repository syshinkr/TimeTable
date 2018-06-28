package com.example.admin.timetable.Adapter;

import android.content.Context;
import android.database.Cursor;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.AnimationUtils;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

import com.example.admin.timetable.DB.DBOpenHelper;
import com.example.admin.timetable.DB.MyDB;
import com.example.admin.timetable.Enum.Day;
import com.example.admin.timetable.Fragment.CaptionBottomSheetDialog;
import com.example.admin.timetable.Model.AniModel;
import com.example.admin.timetable.R;
import com.example.admin.timetable.Server.Client;
import com.example.admin.timetable.Server.MyRetrofit;
import com.example.admin.timetable.Enum.Sort;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

// RecyclerView Adapter
public class DayRecyclerViewAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {
    private final int ANI_SUCCESS = 200;
    private final int INSPECTION = 404;

    private Cursor cursor;
    private Context context;
    DBOpenHelper mDBOpenHelper;
    ArrayList<AniModel> aniModels = new ArrayList<>();
    AniModel aniModel;
    private int day;

    boolean absent;
    boolean favorite;

    public DayRecyclerViewAdapter(Context context, int day) {
        this.context = context;
        this.day = day;

        mDBOpenHelper = new DBOpenHelper(this.context);
        mDBOpenHelper.open();
        getAniData(context, day);
//        insetToDB();
//        getAniFromDB();
        mDBOpenHelper.close();
    }

    //TODO : db 추가
    void getAniFromDB() {
        cursor = null;
        cursor = mDBOpenHelper.getAllColumn();

        aniModels.clear();
        while(cursor.moveToNext()) {
            absent = (cursor.getInt(cursor.getColumnIndex(MyDB.Create.ABSENT)) == 1);
            favorite = (cursor.getInt(cursor.getColumnIndex(MyDB.Create.FAVORITE)) == 1);

            aniModel = new AniModel(
                    cursor.getInt(cursor.getColumnIndex(MyDB.Create.ID)),
                    cursor.getString(cursor.getColumnIndex(MyDB.Create.TITLE)),
                    cursor.getString(cursor.getColumnIndex(MyDB.Create.TIME)),
                    cursor.getString(cursor.getColumnIndex(MyDB.Create.GENRE)),
                    cursor.getString(cursor.getColumnIndex(MyDB.Create.LINK)),
                    absent,
                    cursor.getString(cursor.getColumnIndex(MyDB.Create.SD)),
                    cursor.getString(cursor.getColumnIndex(MyDB.Create.ED)),
                    favorite
            );
            aniModels.add(aniModel);
        }
        notifyDataSetChanged();
        cursor.close();
    }
    void insetToDB() {
        for(int i = 0 ; i < aniModels.size(); i++) {
            AniModel target = aniModels.get(i);

            mDBOpenHelper.updateColumn(target.getId(),
                    target.getTitle(),
                    target.getTime(),
                    target.getGenre(),
                    target.getHomeLink(),
                    target.isAbsent(),
                    target.getSd(),
                    target.getEd());
        }
    }

    void getAniData(final Context context, int day) {
        MyRetrofit retrofit = Client.getInstance().create(MyRetrofit.class);
        Call<List<AniModel>> call = retrofit.getAni(day);
        call.enqueue(new Callback<List<AniModel>>() {
            @Override
            public void onResponse(Call<List<AniModel>> call, Response<List<AniModel>> response) {
//                Log.i("RvAdapter", response.raw().request().url().toString());
                switch (response.code()) {
                    case ANI_SUCCESS:
                        aniModels = (ArrayList<AniModel>) response.body();
                        notifyDataSetChanged();
                        break;
                    case INSPECTION:
                        Toast.makeText(context, "서버 점검으로 데이터를 불러올 수 없습니다", Toast.LENGTH_SHORT).show();
                        break;
                    default:
                        Toast.makeText(context, "모종의 이유로 서버와의 통신이 불가합니다.\n서버 코드 : " + response.code(), Toast.LENGTH_SHORT).show();
                        break;
                }
            }

            @Override
            public void onFailure(Call<List<AniModel>> call, Throwable t) {
                Toast.makeText(context, "서버와 연결 실패", Toast.LENGTH_SHORT).show();
                Log.i("RVAdapter", "메시지:" + t.getMessage());
                t.printStackTrace();
                Log.i("RVAdapter", t.getSuppressed().toString());
            }
        });
    }

    void getOHLIAniData(final Context context, int day) {
        MyRetrofit retrofit = Client.getOHLIInstance().create(MyRetrofit.class);
        Call<List<AniModel>> call = retrofit.getOHLIAni(day);
        call.enqueue(new Callback<List<AniModel>>() {
            @Override
            public void onResponse(Call<List<AniModel>> call, Response<List<AniModel>> response) {
                switch (response.code()) {
                    case ANI_SUCCESS :
                        aniModels = (ArrayList<AniModel>) response.body();
                    case INSPECTION :
                        Toast.makeText(context, "서버 점검으로 데이터를 불러올 수 없습니다", Toast.LENGTH_SHORT).show();
                        break;
                    default:
                        Toast.makeText(context, "모종의 이유로 서버와의 통신이 불가합니다.\n서버 코드 : " + response.code(), Toast.LENGTH_SHORT).show();
                        break;
                }
            }

            @Override
            public void onFailure(Call<List<AniModel>> call, Throwable t) {

            }
        });
    }



    public void changeSort(Sort sort) {
        if(! (aniModels.size() > 0) ) {
            Toast.makeText(context, "정렬할 데이터가 존재하지 않습니다", Toast.LENGTH_SHORT).show();
            return;
        }
        switch (sort) {
            case TIME_ASC:
                Collections.sort(aniModels, new AscendingTime());
                notifyDataSetChanged();
                break;
            case TIME_DESC:
                Collections.sort(aniModels, new DescendingTime());
                notifyDataSetChanged();
                break;
            case TITLE_ASC:
                Collections.sort(aniModels, new AscendingTitle());
                notifyDataSetChanged();
                break;
            case TITLE_DESC:
                Collections.sort(aniModels, new DescendingTitle());
                notifyDataSetChanged();
                break;
        }
    }

    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.ani_item, null);
        return new DayViewHolder(v);
    }

    @Override
    public void onBindViewHolder(@NonNull final RecyclerView.ViewHolder holder, final int position) {
        final AniModel aniModel = aniModels.get(position);
        final String formattedSd = yearFormat(aniModel.getSd());
        final String formattedEd = yearFormat(aniModel.getEd());

        ((DayViewHolder) holder).title.setText(aniModel.getTitle());
        if(day == Day.ETC.getValue() || day == Day.NEW.getValue()) {
            ((DayViewHolder) holder).extra.setText(formattedSd);
        } else {
            ((DayViewHolder) holder).extra.setText(timeFormat(aniModel.getTime()) + " - " + aniModel.getGenre());
        }

        ((DayViewHolder) holder).favorite.setBackgroundResource(R.drawable.baseline_favorite_border_black_18dp);
        ((DayViewHolder) holder).favorite.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //즐겨찾기 추가했을 때
                if(!aniModels.get(position).isFavorite()) {
                    aniModels.get(position).setFavorite(true);
                    ((DayViewHolder) holder).favorite.setBackgroundResource(R.drawable.baseline_favorite_black_18dp);
                }else {
                    aniModels.get(position).setFavorite(false);
                    ((DayViewHolder) holder).favorite.setBackgroundResource(R.drawable.baseline_favorite_border_black_18dp);
                }
                ((DayViewHolder) holder).favorite.startAnimation(AnimationUtils.loadAnimation(context, R.anim.favorite_anim));
            }
        });


        ((DayViewHolder) holder).itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                CaptionBottomSheetDialog captionBottomSheetDialog = CaptionBottomSheetDialog.newInstance(aniModel.getTitle(), formattedSd + " ~ " + formattedEd, aniModel.getId());
                captionBottomSheetDialog.show(((AppCompatActivity) context).getSupportFragmentManager(), "caption dialog");
            }
        });
    }
    String timeFormat(String t) {
        return t.substring(0, 2) + "시 " + t.substring(2, 4) + "분";
    }

    String yearFormat(String time) {
        if(time.equals("00000000")) {
            return "";
        }

        String year = time.substring(0, 4); // xxxx년
        if(year.equals("2099")) {
            return "방영시간 정보 없음";
        } else {
            year = year + "년 ";
        }
        String month = time.substring(4, 6);
        month = isVaildData(month, "월 ");
        String day = time.substring(6, 8);
        day = isVaildData(day, "일 ");

        return year + month + day;
    }
    /**
     *
     * @param time 년도, 월, 일 등이 들어옴
     * @param format 년, 월, 일 등의 데이터가 들어옴
     * @return
     */
    String isVaildData(String time, String format) {
        return time.equals("99") ? "" : (time + format + " ");
    }

    @Override
    public int getItemCount() {
        return aniModels.size();
    }

    public class DayViewHolder extends RecyclerView.ViewHolder {
        TextView title;
        TextView extra;
        ImageButton favorite;

        public DayViewHolder(View itemView) {
            super(itemView);
            title = itemView.findViewById(R.id.titleText);
            extra = itemView.findViewById(R.id.extraText);
            favorite = itemView.findViewById(R.id.favorite);
        }
    }

    // 제목 내림차순
    class DescendingTitle implements Comparator<AniModel> {
        @Override
        public int compare(AniModel o1, AniModel o2) {
            return o2.getTitle().compareTo(o1.getTitle());
        }
    }
    //제목 오름차순
    class AscendingTitle implements Comparator<AniModel> {
        @Override
        public int compare(AniModel o1, AniModel o2) {
            return o1.getTitle().compareTo(o2.getTitle());
        }
    }

    // 방영시간 내림차순
    class DescendingTime implements Comparator<AniModel> {
        @Override
        public int compare(AniModel o1, AniModel o2) {
            return o2.getTime().compareTo(o1.getTime());
        }
    }

    // 방영시간 오름차순
    class AscendingTime implements Comparator<AniModel> {
        @Override
        public int compare(AniModel o1, AniModel o2) {
            return o1.getTime().compareTo(o2.getTime());
        }
    }
}
