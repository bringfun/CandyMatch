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
    private MediaPlayer mediaPlayer;
    private Dialog levelDetailDialog;
    private Button level1btn;
    private Button level2btn;
    private Button level3btn;
    private SharedPreferences mySharedPref;
    private int level = 0;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main_menu);
        mediaPlayer = MediaPlayer.create(getApplicationContext(), R.raw.green_hills);
        mediaPlayer.start();
        mySharedPref = getSharedPreferences("myPref",Context.MODE_PRIVATE);
        levelDetailDialog = new Dialog(this);
        level1btn = findViewById(R.id.level1btn);
        level2btn = findViewById(R.id.level2btn);
        level3btn = findViewById(R.id.level3btn);
        level1btn.setOnClickListener(buttonListener);
        level2btn.setOnClickListener(buttonListener);
        level3btn.setOnClickListener(buttonListener);
    }

    View.OnClickListener buttonListener = new View.OnClickListener() {
        @Override
        public void onClick (View v) {
            if(v == level1btn) {
                level = 1;
                openDetail();
            }
            else if(v == level2btn) {
                level = 2;
                openDetail();
            }
            else if(v == level3btn) {
                level = 3;
                openDetail();
            }
        }
    };

    void openDetail() {
        TextView levelLabel;
        TextView scoreView;
        Button playBtn;
        levelDetailDialog.setContentView(R.layout.leveldetailpopup);
        levelDetailDialog.show();
        levelLabel = levelDetailDialog.findViewById(R.id.levelLabel);
        scoreView = levelDetailDialog.findViewById(R.id.scoreDetail);
        playBtn = levelDetailDialog.findViewById(R.id.playLevelBtn);
        levelLabel.setText("Level "+level);
        scoreView.setText(Integer.toString(mySharedPref.getInt("level"+level+"HighScore",0)));
        playBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainMenu.this, GameBoard.class);
                intent.putExtra("LEVEL", level);
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
