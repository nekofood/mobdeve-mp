package com.greendale.mobdeve_mp;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.widget.ImageButton;
import android.widget.TextView;

public class QuizActivity extends AppCompatActivity {

    TextView quizBtnTxt1, quizBtnTxt2, quizBtnTxt3, quizTitle, quizDescriptionText;
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

    //TODO: make QuizQuestions static later
    private void setQuestion(int q) {
        QuizQuestions question = new QuizQuestions();
        quizDescriptionText.setText(question.questions[q]);
        quizBtnTxt1.setText(question.questionAnswers[q][0]);
        quizBtnTxt2.setText(question.questionAnswers[q][1]);
        quizBtnTxt3.setText(question.questionAnswers[q][2]);
    }
    public class QuizQuestions {
        String[] questions;
        String[][] questionAnswers;
        QuizQuestions() {
            String[] questions = new String[10];
            String[][] questionAnswers = new String[10][3];

           //make some placeholders
            for (int i = 0; i < 10; i++) {
                questions[i] = "Question " + (i + 1);
                questionAnswers[i][0] = "Answer 1";
                questionAnswers[i][1] = "Answer 2";
                questionAnswers[i][2] = "Answer 3";
            }
        }
    }
}

