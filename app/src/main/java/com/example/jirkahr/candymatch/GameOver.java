package com.example.jirkahr.candymatch;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

public class GameOver extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_game_over);

        Intent intent = getIntent();
        int score = intent.getIntExtra("TOTAL_SCORE",0);

        TextView scoredScore = findViewById(R.id.scoredScore);
        scoredScore.setText(Integer.toString(score));
    }

    void home(View view) {
        Intent intent = new Intent(GameOver.this, MainMenu.class);
        startActivity(intent);
    }
}
