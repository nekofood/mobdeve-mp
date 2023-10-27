package com.greendale.mobdeve_mp;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.widget.ImageButton;
import android.widget.TextView;

public class QuizActivity extends AppCompatActivity {

    TextView quizBtnTxt1, quizBtnTxt2, quizBtnTxt3;
    ImageButton quizBtn1, quizBtn2, quizBtn3;
    int question = 0;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_quiz);

        quizBtnTxt1 = (TextView) findViewById(R.id.quizButton1Txt);
        quizBtnTxt2 = (TextView) findViewById(R.id.quizButton2Txt);
        quizBtnTxt3 = (TextView) findViewById(R.id.quizButton3Txt);
        quizBtn1 = (ImageButton) findViewById(R.id.quizButton1);
        quizBtn2 = (ImageButton) findViewById(R.id.quizButton2);
        quizBtn3 = (ImageButton) findViewById(R.id.quizButton3);
    }
}