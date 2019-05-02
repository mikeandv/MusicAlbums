package com.teenspirit88.musicalbums.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import com.teenspirit88.musicalbums.R;
import com.teenspirit88.musicalbums.model.Song;

import java.util.ArrayList;

public class SongAdapter extends ArrayAdapter<Song> {

    public SongAdapter(Context context, ArrayList<Song> songs) {
        super(context, 0, songs);
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        Song song = getItem(position);

        if(convertView == null) {
            convertView = LayoutInflater.from(getContext()).inflate(R.layout.song_row, parent, false);
        }
        ((TextView) convertView.findViewById(R.id.track_number)).setText(song.getTrackNumber().toString());
        ((TextView) convertView.findViewById(R.id.track_name)).setText(song.getTrackName());

        return convertView;
    }
}
