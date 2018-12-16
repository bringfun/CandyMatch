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

    SharedPreferences mySharedPref;
    SharedPreferences.Editor mySharedEditor;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cleared);

        mySharedPref = getSharedPreferences("myPref", Context.MODE_PRIVATE);

        Intent intent = getIntent();
        score = intent.getIntExtra("TOTAL_SCORE",0);

        TextView scoredScore = findViewById(R.id.scoredScore);
        scoredScore.setText(Integer.toString(score));

        saveScore();

        //scoredScore.setText(Integer.toString(mySharedPref.getInt("level1HighScore",0))+" from preferences");
    }

    private void saveScore(){
        mySharedEditor = mySharedPref.edit();
        mySharedEditor.putInt("level1HighScore", this.score);
        mySharedEditor.commit();
    }

    void home(View view) {
        Intent intent = new Intent(Cleared.this, MainMenu.class);
        startActivity(intent);
    }
}
