package com.example.mydiary;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

public class CustomAdapter extends BaseAdapter {

    String[] titles, subtitles;
    Context context;

    LayoutInflater inflater;

    public CustomAdapter(Context context, String[] titles, String[] subtitles) {
        this.context = context;
        this.titles = titles;
        this.subtitles = subtitles;
    }

    @Override
    public int getCount() {
        return titles.length;
    }

    @Override
    public Object getItem(int i) {
        return null;
    }

    @Override
    public long getItemId(int i) {
        return 0;
    }

    @Override
    public View getView(int i, View view, ViewGroup viewGroup) {
        if(view == null) {
            inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            view = inflater.inflate(R.layout.sample_list, viewGroup, false);
        }
        TextView titleView = view.findViewById(R.id.titleListId);
        TextView subtitleView = view.findViewById(R.id.subtitleListId);
        titleView.setText(titles[i]);
        subtitleView.setText(subtitles[i]);
        return view;
    }
}
