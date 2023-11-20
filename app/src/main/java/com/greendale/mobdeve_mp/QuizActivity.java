package com.greendale.mobdeve_mp;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.ImageButton;
import android.widget.TextView;

import org.w3c.dom.Text;

public class QuizActivity extends AppCompatActivity {

    TextView quizBtnTxt1, quizBtnTxt2, quizBtnTxt3, quizTitle, quizDescriptionText, quizpointTxt, Test;
    FrameLayout quizBtnFrame1, quizBtnFrame2, quizBtnFrame3;
    ImageButton quizBtn1, quizBtn2, quizBtn3;
    int question = -1;
    int fun, fiery, focused;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        /*if (isQuizDone){
        Intent i = new Intent(QuizActivity.this, Mainscreen.class);
            startActivity(i);
            finish();}
        * */
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_quiz);


        quizDescriptionText = (TextView) findViewById(R.id.quizDescriptionText);
        quizBtnTxt1 = (TextView) findViewById(R.id.quizButton1Txt);
        quizBtnTxt2 = (TextView) findViewById(R.id.quizButton2Txt);
        quizBtnTxt3 = (TextView) findViewById(R.id.quizButton3Txt);
        quizpointTxt = (TextView) findViewById(R.id.quizpointTxt);
        Test = (TextView) findViewById(R.id.Test);
        quizBtnFrame1 = (FrameLayout) findViewById(R.id.quizBtn1Frame);
        quizBtnFrame2 = (FrameLayout) findViewById(R.id.quizBtn2Frame);
        quizBtnFrame3 = (FrameLayout) findViewById(R.id.quizBtn3Frame);
        quizBtn1 = (ImageButton) findViewById(R.id.quizButton1);
        quizBtn2 = (ImageButton) findViewById(R.id.quizButton2);
        quizBtn3 = (ImageButton) findViewById(R.id.quizButton3);
        quizpointTxt.setText((question+1) + "/10");

        quizBtn1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (question != -1) {
                    nextQuestion();
                    fun++;
                }
            }
        });

        quizBtn2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (question == -1) {
                    quizBtnFrame1.setVisibility(View.VISIBLE);
                    quizBtnFrame3.setVisibility(View.VISIBLE);
                    nextQuestion();
                    fiery++;
                }
                else {
                    nextQuestion();
                    fiery++;
                }
            }
        });

        quizBtn3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (question != -1) {
                    nextQuestion();
                    focused++;
                }
            }
        });
    }

    //TODO: make QuizQuestions static later
    private void setQuestion(int q) {
        QuizQuestions question = new QuizQuestions();
        String[][] answers = question.getQuestionAnswers();
        quizDescriptionText.setText(question.getQuestions()[q]);
        quizBtnTxt1.setText(answers[q][0]);
        quizBtnTxt2.setText(answers[q][1]);
        quizBtnTxt3.setText(answers[q][2]);
    }

    private void nextQuestion() {
        SharedPreferences sharedPreferences = getSharedPreferences("SHARED_PREFERENCES",MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferences.edit();
        Test.setText ("Fun: " +fun + " " + "Fiery: "+ fiery + " " + "Focused: " + focused);
        //advance to next question if not yet final question
        if (question < 9) {
            question = question + 1;
            setQuestion(question);
            quizpointTxt.setText((question+1) + "/10");
        }
        //else do something, go to main screen, whatever
        else {
            if (fun > fiery && fun > focused)
            {
                editor.putString("BSPECIES","Birthday Bear");
                editor.apply();
                editor.putBoolean("ISQUIZDONE",true);
                editor.apply();
                //Set local storage variables
                //species = "Birthday Bear"
                //isQuizDone = True
            }
            else if (fiery > fun && fiery > focused){
                editor.putString("BSPECIES","PenguRanger");
                editor.apply();
                editor.putBoolean("ISQUIZDONE",true);
                editor.apply();
                //Set local storage variables
                //species = "PenguRanger"
                //isQuizDone = True
            }
            else {
                editor.putString("BSPECIES","Salacommander");
                editor.apply();
                editor.putBoolean("ISQUIZDONE",true);
                editor.apply();
                //Set local storage variables
                //species = "Salacommander"
                //isQuizDone = True
            }
            Intent i = new Intent(QuizActivity.this, Mainscreen.class);
            startActivity(i);
            finish();
        }
    }
    public class QuizQuestions {
        String[] questions;
        String[][] questionAnswers;
        QuizQuestions() {
            questions = new String[10];
            questionAnswers = new String[10][3];

           //make some placeholders
            for (int i = 0; i < 10; i++) {
                questions[i] = "Question " + (i + 1);
                questionAnswers[i][0] = "Answer 1";
                questionAnswers[i][1] = "Answer 2";
                questionAnswers[i][2] = "Answer 3";
            }
        }

        public String[] getQuestions() {
            return questions;
        }

        public String[][] getQuestionAnswers() {
            return questionAnswers;
        }
    }
}

