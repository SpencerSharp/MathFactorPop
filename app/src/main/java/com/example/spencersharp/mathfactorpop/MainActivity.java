package com.example.spencersharp.mathfactorpop;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;

public class MainActivity extends AppCompatActivity {

    static final String modeKey = "com.example.spencersharp.modeKey";
    static final String difficultyKey = "com.example.spencersharp.difficultyKey";
    int mode = 0;
    int difficulty = 0;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }

    public void onDifficultyRadioButtonClicked(View v)
    {
        if(v.getId()==R.id.difficultyselect_easy)
            difficulty = 0;
        else if(v.getId()==R.id.difficultyselect_medium)
            difficulty = 1;
        else if(v.getId()==R.id.difficultyselect_hard)
            difficulty = 2;
        else if(v.getId()==R.id.difficultyselect_insane)
            difficulty = 3;
    }

    public void onModeRadioButtonClicked(View v)
    {
        if(v.getId()==R.id.modeselect_infinite)
            mode = 0;
        else if(v.getId()==R.id.modeselect_time)
            mode = 1;
    }

    public void startGame(View v)
    {
        if(mode==0)
        {
            Intent gameActInfinite = new Intent(this, GameActivityInfinite.class);
            gameActInfinite.putExtra(MainActivity.difficultyKey, difficulty);
            startActivity(gameActInfinite);
        }
        else if(mode == 1)
        {
            Intent gameActAccuracy = new Intent(this, GameActivityTime.class);
            gameActAccuracy.putExtra(MainActivity.difficultyKey, difficulty);
            startActivity(gameActAccuracy);
        }
    }
}