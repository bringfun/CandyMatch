package com.example.jirkahr.candymatch;

import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.media.MediaPlayer;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

public class MainMenu extends AppCompatActivity {
    MediaPlayer mediaPlayer;
    Dialog levelDetailDialog;
    SharedPreferences mySharedPref;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main_menu);
        mediaPlayer = MediaPlayer.create(getApplicationContext(), R.raw.green_hills);
        mediaPlayer.start();
        mySharedPref = getSharedPreferences("myPref",Context.MODE_PRIVATE);
        levelDetailDialog = new Dialog(this);
    }

    void openlevel1(View view) {
        TextView scoreView;
        Button playBtn;
        levelDetailDialog.setContentView(R.layout.leveldetailpopup);
        levelDetailDialog.show();
        scoreView = levelDetailDialog.findViewById(R.id.scoreDetail);
        playBtn = levelDetailDialog.findViewById(R.id.playLevelBtn);
        scoreView.setText(Integer.toString(mySharedPref.getInt("level1HighScore",0)));
        playBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainMenu.this, GameBoard.class);
                startActivity(intent);
            }
        });
        /*
        Intent intent = new Intent(MainMenu.this, GameBoard.class);
        startActivity(intent);
        */
    }

    @Override
    protected void onPause() {
        super.onPause();
        mediaPlayer.stop();
        mediaPlayer.release();

    }
}
