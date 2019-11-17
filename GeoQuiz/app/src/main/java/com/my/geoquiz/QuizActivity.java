package com.my.geoquiz;

import android.app.Activity;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.PersistableBundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.Gravity;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.Locale;

public class QuizActivity extends AppCompatActivity {

    private static final String TAG = "QuizActivity";
    private static final String KEY_INDEX = "index";
    private static final String CHEAT = "cheating";
    private static final String QUANTITY_ANSWER_QUESTION = "quantity_answer";
    private static final String QUANTITY_HINT = "quantity_hint";
    public static final String APP_PREFERENCES = "mysettings";
    private static final int REQUEST_CODE_CHEAT = 0;

    private Button mTrueButton;
    private Button mFalseButton;
    private Button mNextButton;
    private Button mPrevButton;
    private Button mCheatButton;
    private TextView mQuestionTextView;
    private TextView mQuantityHintTextView;


    private Question[] mQuestions = {new Question(R.string.question_africa, false),
            new Question(R.string.question_australia, true),
            new Question(R.string.question_oceans, true),
            new Question(R.string.question_mideast, false),
            new Question(R.string.question_americas, true),
            new Question(R.string.question_asia, true)};

    ArrayList<Integer> mAnsweredQuestions = new ArrayList<>();


    private int mCurrentIndex = 0;
    private int correctAnswer = 0;
    private int uncorrectAnswer = 0;
    private int mQuantityHint = 3;
    private boolean mIsCheater;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if(savedInstanceState != null) {
            mCurrentIndex = savedInstanceState.getInt(KEY_INDEX);
            mIsCheater = savedInstanceState.getBoolean(CHEAT, false);
            mAnsweredQuestions = savedInstanceState.getIntegerArrayList(QUANTITY_ANSWER_QUESTION);
            mQuantityHint = savedInstanceState.getInt(QUANTITY_HINT, 3);
        }
        Log.d(TAG, "onCreate(Bundle) create");
        setContentView(R.layout.activity_quiz);

        mQuantityHintTextView = findViewById(R.id.quantity_hint_text_view);
        mQuantityHintTextView.setText(String.format("Количество подсказок: %d", mQuantityHint));
        mTrueButton = findViewById(R.id.true_button);
        mFalseButton = findViewById(R.id.false_button);
        mNextButton = findViewById(R.id.button_next);
        mPrevButton = findViewById(R.id.button_prev);
        mCheatButton = findViewById(R.id.cheat_button);
        mQuestionTextView = findViewById(R.id.question_text_view);
        updateQuestion();

        if(mQuantityHint == 0) {
            mCheatButton.setEnabled(false);
        } else {
            mCheatButton.setEnabled(true);
        }

        mCheatButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = CheatActivity.newIntent(QuizActivity.this, mQuestions[mCurrentIndex].isAnswerTrue(), mQuantityHint);
                startActivityForResult(intent, REQUEST_CODE_CHEAT);
            }
        });

        mQuestionTextView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mCurrentIndex = (mCurrentIndex + 1) % mQuestions.length;
                updateQuestion();
            }
        });

        mNextButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mCurrentIndex = (mCurrentIndex + 1) % mQuestions.length;
                mIsCheater = false;
                updateQuestion();
                unblockButtons();
                if(uncorrectAnswer + correctAnswer == mQuestions.length) {
                    Toast.makeText(QuizActivity.this, getPercentCorrectAnsw(correctAnswer), Toast.LENGTH_LONG).show();
                    mAnsweredQuestions.clear();
                    correctAnswer = 0;
                    uncorrectAnswer = 0;
                    mQuantityHint = 3;
                    updateQuantityTextView(mQuantityHint);
                    updateQuestion();
                }
            }
        });

        mPrevButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mCurrentIndex = (mCurrentIndex - 1 + mQuestions.length) % mQuestions.length;
                updateQuestion();
                unblockButtons();
            }
        });

        mTrueButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
            checkAnswer(true);
            mAnsweredQuestions.add(mCurrentIndex);
            blockButtons();
            }
        });
        mFalseButton.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
            checkAnswer(false);
            mAnsweredQuestions.add(mCurrentIndex);
            blockButtons();
            }
        });
    }

    private void updateQuestion() {
        mQuestionTextView.setText(mQuestions[mCurrentIndex].getTextResId());
        if(mAnsweredQuestions.contains(mCurrentIndex)) {
            mTrueButton.setVisibility(View.INVISIBLE);
            mFalseButton.setVisibility(View.INVISIBLE);
        } else {
            mTrueButton.setVisibility(View.VISIBLE);
            mFalseButton.setVisibility(View.VISIBLE);
        }
    }

    private void checkAnswer(boolean userChoice) {
        boolean result = mQuestions[mCurrentIndex].isAnswerTrue();
        int resMessage = R.string.incorrect_toast;

        if (userChoice == result) {
            resMessage = R.string.correct_toast;
            correctAnswer++;
        } else {
            uncorrectAnswer++;
        }
        if(mIsCheater) {
            resMessage = R.string.jugment_toast;
        }
        Toast.makeText(this, resMessage, Toast.LENGTH_LONG).show();
    }

    @Override
    protected void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        Log.i(TAG, "onSaveInstanceState() call");
        outState.putInt(KEY_INDEX, mCurrentIndex);
        outState.putBoolean(CHEAT, mIsCheater);
        outState.putIntegerArrayList(QUANTITY_ANSWER_QUESTION, mAnsweredQuestions);
        outState.putInt(QUANTITY_HINT, mQuantityHint);
    }

    @Override
    protected void onStart() {
        super.onStart();
        Log.d(TAG, "onStart() called");
    }

    @Override
    protected void onStop() {
        super.onStop();
        Log.d(TAG, "onStop() called");
    }

    @Override
    protected void onPause() {
        super.onPause();
        Log.d(TAG, "onPause() called");
    }

    @Override
    protected void onResume() {
        super.onResume();
        Log.d(TAG, "onResume() called");
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        Log.d(TAG, "onDestroy() called");
    }

    public void blockButtons() {
        mFalseButton.setEnabled(false);
        mTrueButton.setEnabled(false);
    }

    public void unblockButtons() {
        mFalseButton.setEnabled(true);
        mTrueButton.setEnabled(true);
    }

    public String getPercentCorrectAnsw(int correctAnswer) {
        return String.format(Locale.getDefault(), "%.2f%s выполнено", (double)correctAnswer / (double) mQuestions.length * 100, "%");
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        if(resultCode != Activity.RESULT_OK) {
            return;
        }
        if(requestCode == REQUEST_CODE_CHEAT) {
            if(data == null) {
                return;
            }
            mIsCheater = CheatActivity.wasAnswerShown(data);
            mQuantityHint = CheatActivity.getQuantityHint(data);
            updateQuantityTextView(mQuantityHint);
        }
    }

    private void updateQuantityTextView(int mQuantityHint) {
        mQuantityHintTextView.setText(String.format("Количество подсказок: %d", mQuantityHint));
        if(mQuantityHint == 0) {
            mCheatButton.setEnabled(false);
        } else {
            mCheatButton.setEnabled(true);
        }
    }
}
