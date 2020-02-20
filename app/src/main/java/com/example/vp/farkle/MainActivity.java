package com.example.vp.farkle;

import androidx.appcompat.app.AppCompatActivity;

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
                }
            }
        }else if(v.equals(score)){
            int[] valueCount = new int[7];


        }else if(v.equals(stop)){

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
}
