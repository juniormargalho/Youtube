package com.juniormargalho.youtube.activity;

import android.os.Bundle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.juniormargalho.youtube.R;
import com.juniormargalho.youtube.adapter.AdapterVideo;
import com.juniormargalho.youtube.model.Video;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {
    private static final String GOOGLE_API_KEY = "AIzaSyAIA4u-pSxVeSxVOO8P_-584lqn05-FEnQ";

    private RecyclerView recyclerVideos;

    private List<Video> videos = new ArrayList<>();
    private AdapterVideo adapterVideo;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        //inicializar componentes
        recyclerVideos = findViewById(R.id.recyclerVideos);

        //toolbar
        Toolbar toolbar = findViewById(R.id.toolbar);
        toolbar.setTitle("Youtube");
        setSupportActionBar(toolbar);

        recuperarVideos();

        //adapter
        adapterVideo = new AdapterVideo(videos, this);

        //recyclerView
        recyclerVideos.setHasFixedSize(true);
        recyclerVideos.setLayoutManager(new LinearLayoutManager(this));
        recyclerVideos.setAdapter(adapterVideo);

    }

    private void recuperarVideos(){

        Video video1 = new Video();
        video1.setTitulo("Video 1 muito interessante!");
        videos.add(video1);

        Video video2 = new Video();
        video2.setTitulo("Video 2 muito interessante!");
        videos.add(video2);

    }

}
