package com.example.deannhom.fragment;

import android.app.AlertDialog;
import android.app.Dialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.example.deannhom.ManagerActivity;
import com.example.deannhom.R;
import com.example.deannhom.UploadSongActivity;
import com.example.deannhom.adapter.Song;
import com.example.deannhom.adapter.SongAdapter;
import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.ArrayList;
import java.util.List;

public class PlaylistFragment extends Fragment {
    private RecyclerView rcvSongs;
    private EditText edtAlbum_art, edt_artist, edt_songDuration, edtSongLink, edt_songtitle, edt_songcategory, edt_album;
    private Button btnAddSong;
    private SongAdapter mSongAdapter;
    private List<Song> mListSong;
    private Button btnSearch;
    private EditText edtSearch;
    private String key;
    private int i = 0;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_playlist, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        initUi(view);
        getListUserFromRealtimeDatabase(key, i);
    }

    private void initUi(View view) {
        btnSearch = view.findViewById(R.id.btn_search);
        edtSearch = view.findViewById(R.id.edt_search);
        btnAddSong = view.findViewById(R.id.btn_add_song);
        rcvSongs = view.findViewById(R.id.rcv_songs);
        rcvSongs = view.findViewById(R.id.rcv_songs);

        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getContext());
        rcvSongs.setLayoutManager(linearLayoutManager);
        DividerItemDecoration dividerItemDecoration = new DividerItemDecoration(getContext(), DividerItemDecoration.VERTICAL);
        rcvSongs.addItemDecoration(dividerItemDecoration);

        mListSong = new ArrayList<>();

        mSongAdapter = new SongAdapter(mListSong, new SongAdapter.IClickListener() {
            @Override
            public void onClickUpdateItem(Song song) {
                openDialogUpdateItem(song);
            }

            @Override
            public void onClickDeleteItem(Song song) {
                onClickDeleteData(song);
            }
        });

        rcvSongs.setAdapter(mSongAdapter);
        btnAddSong.setOnClickListener(new View.OnClickListener(){

            @Override
            public void onClick(View v) {
                Intent i = new Intent(getContext(), UploadSongActivity.class);
                startActivity(i);
            }
        });
    }

    private void onClickDeleteData(Song song) {
        new AlertDialog.Builder(getContext())
                .setTitle(getString(R.string.app_name))
                .setMessage("Are you sure?")
                .setPositiveButton("OK", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        FirebaseDatabase database = FirebaseDatabase.getInstance();
                        DatabaseReference mRef = database.getReference("songs");
                        mRef.child(song.getSongDuration()).removeValue(new DatabaseReference.CompletionListener() {
                            @Override
                            public void onComplete(@Nullable DatabaseError error, @NonNull DatabaseReference ref) {
                                getListUserFromRealtimeDatabase(key, i);
                                Toast.makeText(getContext(), "Delete data success", Toast.LENGTH_SHORT).show();
                            }
                        });
                    }
                })
                .setNegativeButton("Cancel", null)
                .show();
    }

    private void openDialogUpdateItem(Song song) {
        final Dialog dialog = new Dialog(getContext());
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        dialog.setContentView(R.layout.layout_dialog_update);
        Window window = dialog.getWindow();
        window.setLayout(WindowManager.LayoutParams.MATCH_PARENT, WindowManager.LayoutParams.WRAP_CONTENT);
        window.setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        dialog.setCancelable(false);

        EditText edtUdAlbumArt = dialog.findViewById(R.id.edt_ud_albumart);
        EditText edtUdArtist = dialog.findViewById(R.id.edt_ud_artist);
        EditText edtUdSongDuration = dialog.findViewById(R.id.edt_ud_songDuration);
        EditText edtUdSongLink = dialog.findViewById(R.id.edt_ud_songLink);
        EditText edtUdSongTitle = dialog.findViewById(R.id.edt_ud_songTitle);
        EditText edtUdSongCategory = dialog.findViewById(R.id.edt_ud_songscategory);
        EditText edtUdAlbum = dialog.findViewById(R.id.edt_ud_album);

        Button btUpdate = dialog.findViewById(R.id.btn_Update);
        Button btCancel = dialog.findViewById(R.id.btnCancel);

        edtUdAlbumArt.setText(song.getAlbum_art());
        edtUdArtist.setText(song.getArtist());
        edtUdSongDuration.setText(song.getSongDuration());
        edtUdSongCategory.setText(song.getSongscategory());
        edtUdSongLink.setText(song.getSongLink());
        edtUdSongTitle.setText(song.getSongTitle());
        edtUdAlbum.setText(song.getAlbum());

        btCancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                dialog.dismiss();
            }
        });

        btUpdate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                FirebaseDatabase database = FirebaseDatabase.getInstance();
                DatabaseReference myRef = database.getReference("songs");
                String newAlbum_art = edtUdAlbumArt.getText().toString().trim();

                song.setAlbum_art(newAlbum_art);
                String newArtist = edtUdArtist.getText().toString().trim();

                song.setArtist(newArtist);
                String newSongDuration = edtUdSongDuration.getText().toString().trim();

                song.setSongDuration(newSongDuration);
                String newSongTitle = edtUdSongTitle.getText().toString().trim();
                song.setSongTitle(newSongTitle);
                String newSongcategory = edtUdSongCategory.getText().toString().trim();
                song.setSongscategory(newSongcategory);
                String newSongLink = edtUdSongLink.getText().toString().trim();
                song.setSongLink(newSongLink);
                String newAlbum = edtUdAlbum.getText().toString().trim();
                song.setAlbum(newAlbum);
                // child(name song)
                myRef.child(song.getSongDuration()).updateChildren(song.toMap(), new DatabaseReference.CompletionListener() {
                    @Override
                    public void onComplete(@Nullable DatabaseError error, @NonNull DatabaseReference ref) {
                        Toast.makeText(getContext(), "Update data success", Toast.LENGTH_SHORT).show();
                        getListUserFromRealtimeDatabase(key, i);
                        dialog.dismiss();
                    }
                });

            }
        });
        dialog.show();
    }

    private void getListUserFromRealtimeDatabase(String key, int i) {
        FirebaseDatabase database = FirebaseDatabase.getInstance();
        DatabaseReference myRef = database.getReference("songs");
        myRef.addChildEventListener(new ChildEventListener() {
            @Override
            public void onChildAdded(@NonNull DataSnapshot snapshot, @Nullable String previousChildName) {
                Song song = snapshot.getValue(Song.class);
                if (song != null) {
                    switch (i) {
                        case 0: {
                            mListSong.add(song);
                            break;
                        }
                        case 1:
                            if (song.getSongTitle().contains(key)) {
                                mListSong.add(song);
                            }
                    }
                    mSongAdapter.notifyDataSetChanged();
                }
            }

            @Override
            public void onChildChanged(@NonNull DataSnapshot snapshot, @Nullable String previousChildName) {
                Song song = snapshot.getValue(Song.class);
                if (song != null || mListSong == null || mListSong.isEmpty()) {
                    return;

                }
                for (int i = 0; i <= mListSong.size(); i++) {
                    if (song.getSongDuration() == mListSong.get(i).getSongDuration()) {
                        mListSong.set(i, song);
                        break;
                    }
                }
                mSongAdapter.notifyDataSetChanged();
            }

            @Override
            public void onChildRemoved(@NonNull DataSnapshot snapshot) {
                Song song = snapshot.getValue(Song.class);
                if (song != null || mListSong == null || mListSong.isEmpty()) {
                    return;

                }
                for (int i = 0; i <= mListSong.size(); i++) {
                    if (song.getSongDuration() == mListSong.get(i).getSongDuration()) {
                        mListSong.remove(mListSong.get(i));
                        break;
                    }
                }
                //load lại recycleview
                mSongAdapter.notifyDataSetChanged();

            }

            @Override
            public void onChildMoved(@NonNull DataSnapshot snapshot, @Nullable String previousChildName) {

            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
    }
}