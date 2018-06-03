package com.example.developer.newnote.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.example.developer.newnote.Note;
import com.example.developer.newnote.R;

import java.util.ArrayList;
import java.util.List;


public class MyAdapter extends BaseAdapter {
    Context context;
    List<Note> notes = new ArrayList<>();

    public MyAdapter(Context context, List<Note> notes ) {
        this.context = context;
        this.notes = notes;
    }

    @Override
    public int getCount() {
        return notes.size();

    }

    @Override
    public Object getItem(int i) {
        return notes.get(i);
    }

    @Override
    public long getItemId(int i) {
        return i;
    }

    @Override
    public View getView(int i, View view, ViewGroup viewGroup) {
        if (view == null){
            LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            view = inflater.inflate(R.layout.custom_list,null);
        }
        TextView textView = view.findViewById(R.id.txt_title);
        textView.setText(notes.get(i).getTitle());
        TextView txt_date = view.findViewById(R.id.txt_date_time);
        txt_date.setText(notes.get(i).getDate());
        return view;
    }
}
