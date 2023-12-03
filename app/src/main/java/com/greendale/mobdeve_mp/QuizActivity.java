package com.greendale.mobdeve_mp;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
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
    int fun, fiery, focused = 0;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        SharedPreferences sharedPref = getSharedPreferences("SHARED_PREFERENCES", MODE_PRIVATE);
        Boolean isQuizDone = sharedPref.getBoolean("ISQUIZDONE", false); //default to "quiz not done yet"
        if (isQuizDone) {
            Intent i = new Intent(QuizActivity.this, Mainscreen.class);
            startActivity(i);
            finish();
        }

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

    private void setQuestion(int q) {
        QuizQuestions question = new QuizQuestions();
        String[][] answers = question.getQuestionAnswers();
        quizDescriptionText.setText(question.getQuestions()[q]);
        quizBtnTxt1.setText(answers[q][0]);
        quizBtnTxt2.setText(answers[q][1]);
        quizBtnTxt3.setText(answers[q][2]);
    }

    private void nextQuestion() {
        String finalSpecies = "Birthday Bear"; //Fallback value
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
            //Decide which pet the user is assigned based on stats
            if (fun > fiery && fun > focused)
            {
                finalSpecies = "Birthday Bear";
            }
            else if (fiery > fun && fiery > focused){
                finalSpecies = "PenguRanger";
            }
            else if (focused > fun && focused > fiery) {
                finalSpecies = "Salacommander";
            }
            editor.putString("BSPECIES", finalSpecies);
            editor.putBoolean("ISQUIZDONE",true);
            //Set up the other important stats
            editor.putBoolean("ISNOTIFSON", true);
            editor.putBoolean("CANEVO",false);
            editor.putInt("BCHUNGER", 100);
            editor.putInt("BCTHIRST", 100);
            editor.putInt("BCLOVE", 100);
            editor.putInt("WINS", 0);
            editor.apply();

            Intent i = new Intent(QuizActivity.this, Mainscreen.class);
            startActivity(i);
            finish();
        }
    }
    //Helper object containing all the quiz questions
    public class QuizQuestions {
        String[] questions;
        String[][] questionAnswers;
        QuizQuestions() {
            questions = new String[10];
            questionAnswers = new String[10][3];

            questions[0] = "You've won a small lottery and have money to spend. What do you do?";
            questionAnswers[0][0] = "Throw a party";
            questionAnswers[0][1] = "Donate it to charity";
            questionAnswers[0][2] = "Invest it";
            questions[1] = "You have the day off from school, how do you spend your day?";
            questionAnswers[1][0] = "Play video games";
            questionAnswers[1][1] = "Work out";
            questionAnswers[1][2] = "Study and do homework";
            questions[2] = "There's a long walk ahead of you to get to your destination. What route do you take?";
            questionAnswers[2][0] = "The scenic route";
            questionAnswers[2][1] = "The challenging route";
            questionAnswers[2][2] = "The shortest route";
            questions[3] = "Your car is broken down and you're stranded. Who do you call?";
            questionAnswers[3][0] = "A close friend";
            questionAnswers[3][1] = "A family member";
            questionAnswers[3][2] = "A mechanic";
            questions[4] = "You want to go to the park, but it's too far to walk. What do you use to get there?";
            questionAnswers[4][0] = "A skateboard";
            questionAnswers[4][1] = "A bike";
            questionAnswers[4][2] = "Public transport";
            questions[5] = "You're at the cinema, what movie do you watch?";
            questionAnswers[5][0] = "A wholesome comedy";
            questionAnswers[5][1] = "A blockbuster action movie";
            questionAnswers[5][2] = "An artistic drama";
            questions[6] = "You have a free ticket to a nice vacation, where do you go?";
            questionAnswers[6][0] = "A vibrant city";
            questionAnswers[6][1] = "A historical site";
            questionAnswers[6][2] = "A relaxing beach";
            questions[7] = "What souvenir do you buy before going home?";
            questionAnswers[7][0] = "A t-shirt with a cool design";
            questionAnswers[7][1] = "A unique tattoo";
            questionAnswers[7][2] = "A locally made handicraft";
            questions[8] = "You're stuck in traffic in your car and running late, what do you do?";
            questionAnswers[8][0] = "Turn up the music and relax";
            questionAnswers[8][1] = "Honk on the wheel repeatedly";
            questionAnswers[8][2] = "Plan your day around the delay";
            questions[9] = "You find a wallet on the street, what do you do?";
            questionAnswers[9][0] = "Keep the money";
            questionAnswers[9][1] = "Look for the owner";
            questionAnswers[9][2] = "Give it to the police station";
        }

        public String[] getQuestions() {
            return questions;
        }

        public String[][] getQuestionAnswers() {
            return questionAnswers;
        }
    }
}

