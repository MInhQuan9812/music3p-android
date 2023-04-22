package com.example.deannhom.adapter;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.deannhom.R;

import java.util.List;

public class SongAdapter extends RecyclerView.Adapter<SongAdapter.SongViewHolder> {
    private List<Song> mListSong;
    private IClickListener mIClickListener;


    public SongAdapter(List<Song> mListSong, IClickListener mIClickListener) {
        this.mListSong = mListSong;
        this.mIClickListener = mIClickListener;
    }

    public interface IClickListener{
        void onClickUpdateItem(Song song);
        void onClickDeleteItem(Song song);
    }
    @NonNull
    @Override
    public SongAdapter.SongViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item,parent,false);
        return new SongViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull SongAdapter.SongViewHolder holder, int position) {
        Song song = mListSong.get(position);
        if (song == null)
            return;

        holder.tvAlbum_art.setText(song.getAlbum_art());
        holder.artist.setText(song.getArtist());
        holder.songscategory.setText(song.getSongscategory());
        holder.songLink.setText(song.getSongLink());
        holder.songTitle.setText(song.getSongTitle());
        holder.songDuration.setText(song.getSongDuration());

        holder.btnUpdate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                mIClickListener.onClickUpdateItem(song);
            }
        });
        holder.btnDelete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                mIClickListener.onClickDeleteItem(song);
            }
        });
    }

    @Override
    public int getItemCount() {
        if(mListSong != null)
            return mListSong.size();
        return 0;
    }
    public class SongViewHolder extends RecyclerView.ViewHolder {
        private TextView tvAlbum_art;
        private TextView artist;
        private TextView songDuration;
        private TextView songLink;
        private TextView songTitle;
        private TextView songscategory;
        private Button btnUpdate;
        private Button btnDelete;
        public SongViewHolder(@NonNull View itemView) {
            super(itemView);
            tvAlbum_art = itemView.findViewById(R.id.album_art);
            artist = itemView.findViewById(R.id.artist);
            songDuration = itemView.findViewById(R.id.songDuration);
            songLink = itemView.findViewById(R.id.songLink);
            songTitle = itemView.findViewById(R.id.songTitle);
            songscategory = itemView.findViewById(R.id.songscategory);
            btnUpdate = itemView.findViewById(R.id.btn_Update);
            btnDelete =itemView.findViewById(R.id.btn_Delete);
        }
    }
}
