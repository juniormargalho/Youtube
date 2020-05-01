package com.juniormargalho.youtube;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.widget.Toast;

import com.google.android.youtube.player.YouTubeBaseActivity;
import com.google.android.youtube.player.YouTubeInitializationResult;
import com.google.android.youtube.player.YouTubePlayer;
import com.google.android.youtube.player.YouTubePlayerView;

public class MainActivity extends YouTubeBaseActivity implements YouTubePlayer.OnInitializedListener {
    private static final String GOOGLE_API_KEY = "AIzaSyAIA4u-pSxVeSxVOO8P_-584lqn05-FEnQ";
    private YouTubePlayerView youTubePlayerView;
    private YouTubePlayer.PlaybackEventListener playbackEventListener;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        youTubePlayerView = findViewById(R.id.viewYoutubePlayer);
        youTubePlayerView.initialize(GOOGLE_API_KEY, this);

        playbackEventListener = new YouTubePlayer.PlaybackEventListener() {
            @Override
            public void onPlaying() {
                Toast.makeText(MainActivity.this, "Executando!", Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onPaused() {
                Toast.makeText(MainActivity.this, "Pausado!", Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onStopped() {
                Toast.makeText(MainActivity.this, "Parado!", Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onBuffering(boolean b) {
                Toast.makeText(MainActivity.this, "Carregando!", Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onSeekTo(int i) {
                Toast.makeText(MainActivity.this, "Movimentando seekBar!", Toast.LENGTH_SHORT).show();
            }
        };
    }

    @Override
    public void onInitializationSuccess(YouTubePlayer.Provider provider, YouTubePlayer youTubePlayer, boolean foiRestaurado) {
        youTubePlayer.setPlaybackEventListener(playbackEventListener);
        if(!foiRestaurado){
            //youTubePlayer.cueVideo("C4nursAx-hY");
            youTubePlayer.cuePlaylist("PLWz5rJ2EKKc8ibQFkC77xUETd8BCkRbYc");
        }
    }

    @Override
    public void onInitializationFailure(YouTubePlayer.Provider provider, YouTubeInitializationResult youTubeInitializationResult) {
        Toast.makeText(this, "Erro ao iniciar o player!: " + youTubeInitializationResult.toString(), Toast.LENGTH_SHORT).show();
    }
}
