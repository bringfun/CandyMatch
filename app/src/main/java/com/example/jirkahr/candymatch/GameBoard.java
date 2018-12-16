package com.example.jirkahr.candymatch;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.TextView;

public class GameBoard extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_game_board);

        Intent intent = getIntent();
        int lvl = intent.getIntExtra("LEVEL",2);

        TextView ws = findViewById(R.id.woodScore);
        TextView gs = findViewById(R.id.goldScore);
        TextView cs = findViewById(R.id.crystalScore);
        TextView ss = findViewById(R.id.stoneScore);
        TextView ml = findViewById(R.id.movesLeftView);

        GameView gv = findViewById(R.id.gameView);
        gv.setLevel(lvl);
        gv.setWoodScore(ws);
        gv.setGoldScore(gs);
        gv.setCrystalScore(cs);
        gv.setStoneScore(ss);
        gv.setMovesLeftView(ml);


        //GameView gv = new GameView(this);
        //bundle = gv.getData();
        //TextView woodScore = findViewById(R.id.woodScore);
        //woodScore.setText(bundle.getInt("test data"));
    }
}
