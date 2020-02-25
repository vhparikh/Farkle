/*
    Create by: Vatsal Parikh
    Date: 2/25/2020
    Dice game inspired by Mr. Galbraith's video tutorials
 */

package com.example.vp.farkle;

import androidx.appcompat.app.AppCompatActivity;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.graphics.Color;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.TextClock;
import android.widget.TextView;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {

    ImageButton[] buttons = new ImageButton[6];
    int[] buttonState = new int[6];
    int[] dieImages = new int[6];
    int[] dieValue = new int[6];
    final int HOT_DIE = 0;
    final int SCORE_DIE = 1;
    final int LOCKED_DIE = 2;
    Button roll;
    Button score;
    Button stop;
    TextView currentScoreTV;
    TextView totalScoreTV;
    TextView currentRoundTV;
    int currentScore;
    int totalScore;
    int currentRound;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        buttons[0] = (ImageButton)this.findViewById(R.id.imageButton1);
        buttons[1] = (ImageButton)this.findViewById(R.id.imageButton2);
        buttons[2] = (ImageButton)this.findViewById(R.id.imageButton3);
        buttons[3] = (ImageButton)this.findViewById(R.id.imageButton4);
        buttons[4] = (ImageButton)this.findViewById(R.id.imageButton5);
        buttons[5] = (ImageButton)this.findViewById(R.id.imageButton6);
        for(int a = 0; a < buttons.length; a++){
            buttons[a].setOnClickListener(this);
            buttons[a].setEnabled(false);
            buttons[a].setBackgroundColor(Color.LTGRAY);
        }
        roll = (Button)this.findViewById(R.id.button1);
        roll.setOnClickListener(this);
        score = (Button)this.findViewById(R.id.button2);
        score.setOnClickListener(this);
        score.setEnabled(false);
        stop = (Button)this.findViewById(R.id.button3);
        stop.setOnClickListener(this);
        stop.setEnabled(false);
        currentScoreTV = (TextView)this.findViewById(R.id.textView1);
        totalScoreTV = (TextView)this.findViewById(R.id.textView2);
        currentRoundTV = (TextView)this.findViewById(R.id.textView3);
        dieImages[0] = R.drawable.one;
        dieImages[1] = R.drawable.two;
        dieImages[2] = R.drawable.three;
        dieImages[3] = R.drawable.four;
        dieImages[4] = R.drawable.five;
        dieImages[5] = R.drawable.six;




    }

    @Override
    public void onClick(View v) {
        if(v.equals(roll)){
            for (int i = 0; i < buttons.length; i++){
                if(buttonState[i] == HOT_DIE){
                    int choice = (int)(Math.random() * 6);
                    dieValue[i] = choice;
                    buttons[i].setImageResource(dieImages[choice]);
                    buttons[i].setEnabled(true);
                    roll.setEnabled(false);
                    score.setEnabled(true);
                    stop.setEnabled(false);
                }
            }
        }else if(v.equals(score)){
            int[] valueCount = new int[7];
            for (int a = 0; a < buttonState.length; a++) {
                if(buttonState[a] == SCORE_DIE){
                    valueCount[dieValue[a] + 1]++;
                }
            }
            if((valueCount[2] > 0 && valueCount[2] < 3) ||
                    (valueCount[3] > 0 && valueCount[3] < 3) ||
            (valueCount[4] > 0 && valueCount[4] < 3) ||
            (valueCount[6] > 0 && valueCount[6] < 3)){
                AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(this);
                alertDialogBuilder.setTitle("Invalid Die Selected");
                alertDialogBuilder
                        .setMessage("You can only select scoring dice.")
                        .setCancelable(false)
                        .setPositiveButton("OK",new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog,int id) {
                                dialog.dismiss();
                            }
                        });
                AlertDialog alertDialog = alertDialogBuilder.create();
                alertDialog.show();
            }else if (valueCount[1] == 0 && valueCount[2] == 0 &&
                    valueCount[3] == 0 && valueCount[4] == 0 &&
                    valueCount[5] == 0 && valueCount[6] == 0){
                AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(this);
                alertDialogBuilder.setTitle("No score!");
                alertDialogBuilder
                        .setMessage("Forfeit score and go to next round?")
                        .setCancelable(false)
                        .setPositiveButton("Yes",new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog,int id) {
                                totalScore += currentScore;
                                currentScore= 0;
                                currentRound++;
                                currentScoreTV.setText("Current Score: " + currentScore);
                                currentRoundTV.setText("Current Round: " + currentRound);
                                totalScoreTV.setText("Total Score: " + totalScore);
                                resetDice();
                                dialog.dismiss();
                            }
                        })
                        .setNegativeButton("No",new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog,int id) {
                                dialog.cancel();
                            }
                        });
                AlertDialog alertDialog = alertDialogBuilder.create();
                alertDialog.show();

            }else{
                if(valueCount[1] < 3){
                    currentScore += (valueCount[1] * 100);
                }
                if (valueCount[5] < 3){
                    currentScore += (valueCount[5] * 50);
                }
                if (valueCount[1] >= 3){
                    currentScore += ((valueCount[1] - 2) * 1000);
                }
                if (valueCount[2] >= 3){
                    currentScore += ((valueCount[2] - 2) * 200);
                }
                if (valueCount[3] >= 3){
                    currentScore += ((valueCount[3] - 2) * 300);
                }
                if (valueCount[4] >= 3){
                    currentScore += ((valueCount[4] - 2) * 400);
                }
                if (valueCount[5] >= 3){
                    currentScore += ((valueCount[5] - 2) * 500);
                }
                if (valueCount[6] >= 3){
                    currentScore += ((valueCount[6] - 2) * 600);
                }
                currentScoreTV.setText("Current Score: " + currentScore);
                for (int a = 0; a < buttons.length; a++) {
                    if(buttonState[a] ==  SCORE_DIE){
                        buttonState[a] = LOCKED_DIE;
                        buttons[a].setBackgroundColor(Color.BLUE);
                        buttons[a].setEnabled(false);
                    }
                }
                int lockedCount = 0;
                for (int i = 0; i < buttons.length ; i++) {
                    if(buttonState[i] == LOCKED_DIE){
                        lockedCount++;
                    }
                }
                if(lockedCount == 6){
                    for (int i = 0; i < buttons.length ; i++) {
                        buttonState[i] = HOT_DIE;
                        buttons[i].setBackgroundColor(Color.LTGRAY);
                    }
                }

                for (int a = 0; a < buttons.length; a++) {
                    if(buttonState[a] == HOT_DIE){
                        buttons[a].setEnabled(false);
                    }
                }
                roll.setEnabled(true);
                score.setEnabled(false);
                stop.setEnabled(true);

            }

        }else if(v.equals(stop)){
            totalScore += currentScore;
            currentScore = 0;
            totalScoreTV.setText("Total Score: " + totalScore);
            currentScoreTV.setText("Current Score: " + currentScore);
            currentRound++;
            currentRoundTV.setText("Current Round: " + currentRound);
            resetDice();

        }else{
            for (int i = 0; i < buttons.length; i++) {
                if(v.equals(buttons[i])){
                    if(buttonState[i] == HOT_DIE){
                        buttons[i].setBackgroundColor(Color.RED);
                        buttonState[i] = SCORE_DIE;
                    }else{
                        buttons[i].setBackgroundColor(Color.LTGRAY);
                        buttonState[i] = HOT_DIE;
                    }
                }
            }
        }
    }

    private void resetDice() {
        for (int a = 0; a < buttons.length; a++) {
            buttons[a].setEnabled(false);
            buttonState[a] = HOT_DIE;
            buttons[a].setBackgroundColor(Color.LTGRAY);
        }
        roll.setEnabled(true);
        score.setEnabled(false);
        stop.setEnabled(false);
    }
}
