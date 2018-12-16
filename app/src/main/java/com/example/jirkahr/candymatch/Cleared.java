package com.example.jirkahr.candymatch;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

public class Cleared extends AppCompatActivity {

    private int score;
    private int wood;
    private int gold;
    private int crystal;
    private int stone;
    private int level;

    SharedPreferences mySharedPref;
    SharedPreferences.Editor mySharedEditor;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cleared);

        mySharedPref = getSharedPreferences("myPref", Context.MODE_PRIVATE);

        Intent intent = getIntent();
        this.score = intent.getIntExtra("TOTAL_SCORE",0);
        this.wood = intent.getIntExtra("WOOD",0);
        this.gold = intent.getIntExtra("GOLD",0);
        this.crystal = intent.getIntExtra("CRYSTAL",0);
        this.stone = intent.getIntExtra("STONE",0);
        this.level = intent.getIntExtra("LEVEL", 1);

        TextView scoredScore = findViewById(R.id.scoredScore);
        scoredScore.setText(Integer.toString(score));

        saveScore();
    }

    private void saveScore(){
        int totalWood = mySharedPref.getInt("totalWoodScore",0);
        int totalGold = mySharedPref.getInt("totalGoldScore",0);
        int totalCrystal = mySharedPref.getInt("totalCrystalScore",0);
        int totalStone = mySharedPref.getInt("totalStoneScore",0);
        int totalGamesPlayed = mySharedPref.getInt("totalGamesPlayed",0);

        mySharedEditor = mySharedPref.edit();
        mySharedEditor.putInt("level"+this.level+"HighScore", this.score);
        mySharedEditor.putInt("totalWoodScore", totalWood+this.wood);
        mySharedEditor.putInt("totalGoldScore", totalGold+this.gold);
        mySharedEditor.putInt("totalCrystalScore", totalCrystal+this.crystal);
        mySharedEditor.putInt("totalStoneScore", totalStone+this.stone);
        mySharedEditor.putInt("totalGamesPlayed", totalGamesPlayed+1);
        mySharedEditor.commit();
    }

    void home(View view) {
        Intent intent = new Intent(Cleared.this, MainMenu.class);
        startActivity(intent);
    }
}
