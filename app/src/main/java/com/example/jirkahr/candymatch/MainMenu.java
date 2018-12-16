package com.example.jirkahr.candymatch;

import android.content.Intent;
import android.media.MediaPlayer;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;

public class MainMenu extends AppCompatActivity {
    MediaPlayer mediaPlayer;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main_menu);
        mediaPlayer = MediaPlayer.create(getApplicationContext(), R.raw.green_hills);
        mediaPlayer.start();
    }

    void play(View view) {
        Intent intent = new Intent(MainMenu.this, GameBoard.class);
        startActivity(intent);
    }

    @Override
    protected void onPause() {
        super.onPause();
        mediaPlayer.stop();
        mediaPlayer.release();

    }
}
