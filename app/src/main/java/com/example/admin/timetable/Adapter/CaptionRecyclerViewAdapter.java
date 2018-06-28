package com.example.admin.timetable.Adapter;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.example.admin.timetable.Model.CaptionModel;
import com.example.admin.timetable.R;
import com.example.admin.timetable.Server.Client;
import com.example.admin.timetable.Server.MyRetrofit;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class CaptionRecyclerViewAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {
    private final int CAPTION_SUCCESS = 200;
    private final int INSPECTION = 404;

    List<CaptionModel> captions = new ArrayList<>();
    Context context;

    public CaptionRecyclerViewAdapter(Context context, int s) {
        this.context = context;
        getCaption(s);
    }


    private void getCaption(int index) {
        MyRetrofit retrofit = Client.getInstance().create(MyRetrofit.class);
        Call<List<CaptionModel>> call = retrofit.getCaption(index);
        call.enqueue(new Callback<List<CaptionModel>>() {
            @Override
            public void onResponse(Call<List<CaptionModel>> call, Response<List<CaptionModel>> response) {
                switch (response.code()) {
                    case CAPTION_SUCCESS :
                        captions = response.body();
                        notifyDataSetChanged();
                        break;
                    case INSPECTION:
                        captions.add(new CaptionModel("", "", "", ""));
                        Toast.makeText(context, "서버 점검으로 데이터를 불러올 수 없습니다", Toast.LENGTH_SHORT).show();

                        break;
                    default:
                        captions.add(new CaptionModel("", "", "", ""));
                        Toast.makeText(context, "모종의 이유로 서버와의 통신이 불가합니다.\n서버 코드 : " + response.code(), Toast.LENGTH_LONG).show();
                        break;
                }
            }

            @Override
            public void onFailure(Call<List<CaptionModel>> call, Throwable t) {
                Toast.makeText(context, "서버와 연결 실패", Toast.LENGTH_SHORT).show();
                Log.i("RVAdapter", t.getMessage());
                t.printStackTrace();
                Log.i("RVAdapter", t.getSuppressed().toString());
            }
        });

    }


    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view  = LayoutInflater.from(parent.getContext()).inflate(R.layout.caption_item, parent, false);
        return new CaptionViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, int position) {
        final CaptionModel captionModel = captions.get(position);

        ((CaptionViewHolder)holder).textView_captionCreater.setText(episodeFormat(captionModel.getEpisode()) + "화 : "+ captionModel.getName());
        ((CaptionViewHolder)holder).textView_date.setText(timeFormat(captionModel.getDate()));
        ((CaptionViewHolder)holder).itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if ( ! captionModel.getAddress().equals("")) {
                    context.startActivity(new Intent(Intent.ACTION_VIEW, Uri.parse(captionModel.getAddress())));
                } else {
                    Toast.makeText(context, "주소 정보가 없습니다", Toast.LENGTH_SHORT).show();
                }

            }
        });
    }

    String episodeFormat(String s) {
        if(!(s.length() > 0 )) {
            return s;
        }
        int num = Integer.parseInt(s.substring(0, 4));
        int n2 = s.charAt(4) - '0';
        if (n2 == 0) {
            return String.valueOf(num);
        } else {
            return num + "." + n2;
        }
    }

    String timeFormat(String t) {
        if(!(t.length() > 0)) {
            return t;
        }
        if(t.equals("00000000000000")) {
            return "시간 정보 없음";
        }
        return t.substring(4, 6) + "월 "
                + t.substring(6, 8) + "일 "
                + t.substring(8, 10) + "시 "
                + t.substring(10, 12) + "분 ";
    }
    @Override
    public int getItemCount() {
        return captions.size();
    }

    private class CaptionViewHolder extends RecyclerView.ViewHolder {
        TextView textView_captionCreater;
        TextView textView_date;

        public CaptionViewHolder(View view) {
            super(view);
            textView_captionCreater = view.findViewById(R.id.captionItem_title);
            textView_date = view.findViewById(R.id.captionItem_date);
        }
    }
}
