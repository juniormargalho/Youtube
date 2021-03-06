package com.juniormargalho.youtube.activity;

import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.juniormargalho.youtube.R;
import com.juniormargalho.youtube.adapter.AdapterVideo;
import com.juniormargalho.youtube.api.YoutubeService;
import com.juniormargalho.youtube.helper.RetrofitConfig;
import com.juniormargalho.youtube.helper.YoutubeConfig;
import com.juniormargalho.youtube.listener.RecyclerItemClickListener;
import com.juniormargalho.youtube.model.Item;
import com.juniormargalho.youtube.model.Resultado;
import com.juniormargalho.youtube.model.Video;
import com.miguelcatalan.materialsearchview.MaterialSearchView;

import java.security.Permission;
import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;

public class MainActivity extends AppCompatActivity {
    private RecyclerView recyclerVideos;
    private AdapterVideo adapterVideo;
    private MaterialSearchView searchView;
    private Retrofit retrofit;
    private Resultado resultado;
    private List<Item> videos = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        //inicializar componentes
        recyclerVideos = findViewById(R.id.recyclerVideos);
        searchView = findViewById(R.id.searchView);

        //configuracoes iniciais
        retrofit = RetrofitConfig.getRetrofit();

        //toolbar
        Toolbar toolbar = findViewById(R.id.toolbar);
        toolbar.setTitle("Youtube");
        setSupportActionBar(toolbar);

        recuperarVideos("");

        //searchView
        searchView.setOnQueryTextListener(new MaterialSearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                recuperarVideos(query);
                return true;
            }

            @Override
            public boolean onQueryTextChange(String newText) {
                return false;
            }
        });

        searchView.setOnSearchViewListener(new MaterialSearchView.SearchViewListener() {
            @Override
            public void onSearchViewShown() {
            }

            @Override
            public void onSearchViewClosed() {
                recuperarVideos("");
            }
        });
    }

    private void recuperarVideos(String pesquisa){

        String q = pesquisa.replaceAll(" ", "+");

        YoutubeService youtubeService = retrofit.create(YoutubeService.class);
        youtubeService.recuperarVideos("snippet","date","20", YoutubeConfig.CHAVE_YOUTUBE_API, YoutubeConfig.CANAL_ID, q)
                .enqueue(new Callback<Resultado>() {
                    @Override
                    public void onResponse(Call<Resultado> call, Response<Resultado> response) {
                        if(response.isSuccessful()){
                            resultado = response.body();
                            videos = resultado.items;
                            configuraRecyclerView();
                        }
                    }

                    @Override
                    public void onFailure(Call<Resultado> call, Throwable t) {
                    }
                });
    }

    public void configuraRecyclerView(){
        adapterVideo = new AdapterVideo(videos, this);
        recyclerVideos.setHasFixedSize(true);
        recyclerVideos.setLayoutManager(new LinearLayoutManager(this));
        recyclerVideos.setAdapter(adapterVideo);

        recyclerVideos.addOnItemTouchListener(new RecyclerItemClickListener(this, recyclerVideos,
                new RecyclerItemClickListener.OnItemClickListener() {
                    @Override
                    public void onItemClick(View view, int position) {
                        Item video = videos.get(position);
                        String idVideo = video.id.videoId;
                        Intent i = new Intent(MainActivity.this, PlayerActivity.class);
                        i.putExtra("idVideo", idVideo);
                        startActivity(i);
                    }

                    @Override
                    public void onLongItemClick(View view, int position) {
                    }

                    @Override
                    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                    }
                }));
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.menu_main, menu);
        MenuItem item = menu.findItem(R.id.menu_search);
        searchView.setMenuItem(item);
        return true;
    }
}
