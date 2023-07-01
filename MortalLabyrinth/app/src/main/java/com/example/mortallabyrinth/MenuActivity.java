package com.example.mortallabyrinth;

import android.app.Activity;
import android.content.Intent;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class MenuActivity extends Activity {
    private MediaPlayer mediaPlayer;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_menu);

        Button startButton = findViewById(R.id.startButton);
        startButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Démarrer le jeu
                Intent intent = new Intent(MenuActivity.this, MainActivity.class);
                startActivity(intent);
                finish(); // Terminer l'activité du menu pour revenir à MainActivity
            }
        });
    }

    @Override
    protected void onStart() {
        super.onStart();
        // Démarrer la musique du menu
        mediaPlayer = MediaPlayer.create(this, R.raw.menu);
        mediaPlayer.setLooping(true);
        mediaPlayer.start();
    }

    @Override
    protected void onStop() {
        super.onStop();
        // Arrêter la musique du menu lorsque l'activité est en arrière-plan
        if (mediaPlayer != null) {
            mediaPlayer.stop();
            mediaPlayer.release();
            mediaPlayer = null;
        }
    }
}
