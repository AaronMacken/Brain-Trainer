package com.aaron.example.braintrainer;

import android.media.MediaPlayer;
import android.os.CountDownTimer;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.Random;


public class MainActivity extends AppCompatActivity {
    TextView additionTextView;
    TextView timerTextView;
    TextView scoreTextView;
    LinearLayout topLayout;
    LinearLayout bottomLayout;
    TextView topLeftTextView;
    TextView topRightTextView;
    TextView bottomLeftTextView;
    TextView bottomRightTextView;
    TextView startTextView;
    TextView highScoreTextView;

    int scoreCorrect;
    int scoreTotal;
    int winningInteger;

    int randomNumber;
    int secondAdditionInteger;

    Boolean isPlaying = false;
    CountDownTimer timer;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        timerTextView = findViewById(R.id.timerTextView);
        additionTextView = findViewById(R.id.additionTextView);
        scoreTextView = findViewById(R.id.scoreTextView);
        topLayout = findViewById(R.id.linearLayoutTop);
        bottomLayout = findViewById(R.id.linearLayoutBottom);
        topLeftTextView = findViewById(R.id.topLeftTextView);
        topRightTextView = findViewById(R.id.topRightTextView);
        bottomLeftTextView = findViewById(R.id.bottomLeftTextView);
        bottomRightTextView = findViewById(R.id.bottomRightTextView);
        startTextView = findViewById(R.id.startTextView);
        highScoreTextView = findViewById(R.id.highScoreTextView);

        // Background music
        MediaPlayer mPlayer = MediaPlayer.create(this, R.raw.braintrainersong);
        mPlayer.start();
        mPlayer.setLooping(true);
        scoreCorrect = 0;
        scoreTotal = 0;
        scoreTextView.setText(Integer.toString(scoreCorrect) + "/" + Integer.toString(scoreTotal));

    }

    // Start button clicked... Game begins
    public void startGameClicked(View v){
        // Game UI visible & Start button invisible
        showGameUI();
        // Method that loads random integers
        getRandomVariables();

        if(isPlaying == false){
            isPlaying = true;
            timer = new CountDownTimer((15 * 1000), 1000) {
                @Override
                public void onTick(long millisUntilFinished) {
                    timerTextView.setText((Long.toString(millisUntilFinished/1000)));
                    //Log.i("Time remaining",Long.toString(millisUntilFinished/1000));
                }
                @Override
                public void onFinish() {
                    isPlaying = false;
                    highScoreTextView.setText("Score: " + scoreTextView.getText().toString());
                    hideGameUI();
                    scoreTextView.setText("0/0");
                    scoreCorrect = 0;
                    scoreTotal = 0;
                }
            }.start();
        }else{
            isPlaying = false;
        }


    }
    // Method determines if user chose the correct answer. Score & total questions attempted are incremented
    public void userGuessClicked(View v){
        TextView textView = (TextView) v;
        String textViewText = textView.getText().toString();

        if(Integer.parseInt(textViewText) == (randomNumber + secondAdditionInteger)){
            scoreCorrect++;
            scoreTotal++;
            updateScore();
            Log.i("TextViewText", textViewText);

            // Loads new set of numbers
            getRandomVariables();
        }else{
            scoreTotal++;
            updateScore();
            getRandomVariables();
        }

    }

    public void updateScore(){
        scoreTextView.setText(Integer.toString(scoreCorrect) + "/" + Integer.toString(scoreTotal));
    }


    public void getRandomVariables() {
        // Array list from 1 - 20
        ArrayList
                <Integer> arrayList = new ArrayList<>();
        for(int i = 1; i <= 20; i++) {
            arrayList.add(i);
        }

        // array created from random elements of previous array list
        int[] randomArray = new int[4];
        for (int count = 0; count < 4; count++) {
            randomArray[count] = arrayList.remove((int)(Math.random() * arrayList.size()));
        }
        //Log.i("RandomArray", Arrays.toString(randomArray));

        // Random integer used as the first addition integer
        Random random = new Random();
        randomNumber = random.nextInt(20);
        //Log.i("RandomInt", Integer.toString(randomNumber));

        // Selects a random element from the previous array.. uses this as the second addition integer
        int randomElement = new Random().nextInt(randomArray.length);
        //Log.i("RandomElement", Integer.toString(randomElement));
        //Log.i("RandomElementInteger", Integer.toString(randomArray[randomElement]));
        secondAdditionInteger = (randomArray[randomElement]);
        additionTextView.setText(Integer.toString(randomNumber) + " + " + Integer.toString(randomArray[randomElement]));

        // Winning integer is 1st random + 2nd random integer...
        winningInteger = ((randomNumber) + (randomArray[randomElement]));
        // Log.i("Winner", Integer.toString(randomNumber)  + " " +  Integer.toString(randomArray[randomElement]));
        //Log.i("Winnger...", Integer.toString(winningInteger));

        // ARRAY WITH WINNING INTEGER
        int[] winningArray = randomArray;
        winningArray[randomElement] = winningInteger;
        //Log.i("RandomArray..", Arrays.toString(randomArray));


        // SETTING TEXTVIEWS
        topLeftTextView.setText(Integer.toString(winningArray[0]));
        topRightTextView.setText(Integer.toString(winningArray[1]));
        bottomLeftTextView.setText(Integer.toString(winningArray[2]));
        bottomRightTextView.setText(Integer.toString(winningArray[3]));
    }

    public void hideGameUI(){
        additionTextView.setVisibility(View.INVISIBLE);
        timerTextView.setVisibility(View.INVISIBLE);
        scoreTextView.setVisibility(View.INVISIBLE);
        topLayout.setVisibility(View.INVISIBLE);
        bottomLayout.setVisibility(View.INVISIBLE);
        startTextView.setVisibility(View.VISIBLE);
        highScoreTextView.setVisibility(View.VISIBLE);
    }

    public void showGameUI(){
        additionTextView.setVisibility(View.VISIBLE);
        timerTextView.setVisibility(View.VISIBLE);
        scoreTextView.setVisibility(View.VISIBLE);
        topLayout.setVisibility(View.VISIBLE);
        bottomLayout.setVisibility(View.VISIBLE);
        startTextView.setVisibility(View.INVISIBLE);
        highScoreTextView.setVisibility(View.INVISIBLE);
    }





}
