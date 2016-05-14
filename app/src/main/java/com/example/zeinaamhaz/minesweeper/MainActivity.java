package com.example.zeinaamhaz.minesweeper;


import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ScrollView;

import com.example.zeinaamhaz.minesweeper.model.MinesweeperModel;
import com.example.zeinaamhaz.minesweeper.view.MinesweeperView;

public class MainActivity extends AppCompatActivity {

    private ScrollView layoutContent;
    private MinesweeperView gameField;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        layoutContent = (ScrollView) findViewById(R.id.layoutContent);

        gameField = (MinesweeperView) findViewById(R.id.gameField);

        Button btnRestart = (Button) findViewById(R.id.btnRestart);
        btnRestart.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // clear the game field
                gameField.restartGame();
            }
        });

        Button btnTry = (Button) findViewById(R.id.btnTry);
        btnTry.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //sets toggle to Try on click
                gameField.whichToggle(MinesweeperModel.TRY);
                showSnackBarMessage(getString(R.string.text_try));
            }
        });

        Button btnFlag = (Button) findViewById(R.id.btnFlag);
        btnFlag.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //sets toggle to Flag on click
                gameField.whichToggle(MinesweeperModel.FLAG);
                showSnackBarMessage(getString(R.string.text_flag));
            }
        });
    }

    public void showSnackBarMessage(String msg) {
        Snackbar.make(layoutContent, msg, Snackbar.LENGTH_LONG).setAction(
                R.string.action_cancel, new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        // this code is called when action is pressed on Snackbar
                        gameField.restartGame();
                    }
                }
        ).show();
    }

}