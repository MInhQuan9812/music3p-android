package com.example.deannhom;

import android.os.Bundle;
import android.view.View;
import android.widget.ProgressBar;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.deannhom.adapter.JcSongsAdapter;
import com.example.deannhom.model.GetSongs;
import com.example.jean.jcplayer.model.JcAudio;
import com.example.jean.jcplayer.view.JcPlayerView;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;

public class SongsActivity extends AppCompatActivity {
    RecyclerView recyclerView;
    ProgressBar progressBar;
    Boolean checkin = false;
    List<GetSongs> mupload;
    JcSongsAdapter adapter;
    DatabaseReference databaseReference;
    ValueEventListener valueEventListener;
    JcPlayerView jcPlayerView;
    ArrayList<JcAudio> jcAudios = new ArrayList<>();
    private  int currentIndex ;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_songs);
        recyclerView = findViewById( R.id.recyclerview);
        progressBar = findViewById(R.id.progressbarshowsong);
        jcPlayerView = findViewById(R.id.jcplayer);
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        mupload = new ArrayList<>();
        recyclerView.setAdapter(adapter);
        adapter = new JcSongsAdapter(getApplicationContext(), mupload, new JcSongsAdapter.RecyclerItemClickListener() {
            @Override
            public void onClickListener(GetSongs songs, int postion) {

                changeSelectedSong(postion);

                jcPlayerView.playAudio(jcAudios.get(postion));
                jcPlayerView.setVisibility(View.VISIBLE);
                jcPlayerView.createNotification();

            }
        });


        databaseReference = FirebaseDatabase.getInstance().getReference("songs");
        valueEventListener = databaseReference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {

                mupload.clear();
                for(DataSnapshot dss: dataSnapshot.getChildren()){
                    GetSongs getSongs = dss.getValue(GetSongs.class);
                    getSongs.setmKey(dss.getKey());
                    currentIndex = 0;
                    final String s = getIntent().getExtras().getString("songs");
                    if(s==getSongs.getSongsCategory()){
                        mupload.add(getSongs);
                        checkin = true;
                        jcAudios.add(JcAudio.createFromURL(getSongs.getSongTitle(),getSongs.getSongLink()));
                    }
                }

                adapter.setSeletedPosition(0);
                recyclerView.setAdapter(adapter);
                adapter.notifyDataSetChanged();
                progressBar.setVisibility(View.GONE);

                if(checkin){
                    jcPlayerView.initPlaylist(jcAudios,null);

                }else {
                    Toast.makeText(SongsActivity.this, "there is no songs!", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

                progressBar.setVisibility(View.GONE);

            }
        });

    }
    public void changeSelectedSong (int index){
        adapter.notifyItemChanged(adapter.getSeletedPosition());
        currentIndex = index;
        adapter.setSeletedPosition(currentIndex);
        adapter.notifyItemChanged(currentIndex);
    }

}