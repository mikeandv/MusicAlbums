package com.teenspirit88.musicalbums.adapter;

import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.squareup.picasso.Picasso;
import com.teenspirit88.musicalbums.R;
import com.teenspirit88.musicalbums.util.RoundRectTransform;
import com.teenspirit88.musicalbums.model.Album;

import org.threeten.bp.ZonedDateTime;
import java.util.ArrayList;

/**
 * Адаптер для RecyclerView
 */
public class AlbumAdapter extends RecyclerView.Adapter<AlbumAdapter.AlbumViewHolder> {

    private ArrayList<Album> dataList;
    private OnClickListener onClickListener;

    public AlbumAdapter(ArrayList<Album> dataList, OnClickListener onClickListener) {
        this.dataList = dataList;
        this.onClickListener = onClickListener;
    }

    @NonNull
    @Override
    public AlbumViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater layoutInflater = LayoutInflater.from(parent.getContext());
        View view = layoutInflater.inflate(R.layout.item_row, parent, false);
        return new AlbumViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull AlbumViewHolder holder, int position) {
        holder.artistName.setText(dataList.get(position).getArtistName());
        holder.albumName.setText(dataList.get(position).getCollectionName());
        holder.releaseDate.setText(Integer.toString(ZonedDateTime.parse(dataList.get(position).getReleaseDate()).getYear()));
        //

        Picasso
                .get()
                .load(dataList.get(position).getArtworkUrl60())
                .transform(new RoundRectTransform(7,0))
                .placeholder(R.color.placeholderColor)
                .error(R.drawable.ic_placeholder)

                .into(holder.artworkSmall);
    }

    @Override
    public int getItemCount() {
        return dataList.size();
    }

    class AlbumViewHolder extends RecyclerView.ViewHolder {
        TextView artistName;
        TextView albumName;
        TextView releaseDate;
        ImageView artworkSmall;

        AlbumViewHolder(@NonNull View itemView) {
            super(itemView);

            albumName = (TextView) itemView.findViewById(R.id.album_label);
            artistName = (TextView) itemView.findViewById(R.id.artist_label);
            releaseDate = (TextView) itemView.findViewById(R.id.release_date_label);
            artworkSmall = (ImageView) itemView.findViewById(R.id.artwork_small);

            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    Album album = dataList.get(getLayoutPosition());
                    onClickListener.OnItemClick(album);
                }
            });
        }
    }
}
