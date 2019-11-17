package com.my.geoquiz;

import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.content.Context;
import android.content.Intent;
import android.os.Build;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.view.ViewAnimationUtils;
import android.widget.Button;
import android.widget.TextView;

public class CheatActivity extends AppCompatActivity {

    public static final String EXTRA_ANSWER_IS_TRUE = "answer_is_true";
    private static final String EXTRA_ANSWER_SHOWN = "answer_shown";
    private static final String EXTRA_QUANTITY_HINT = "quantity_hint";

    private boolean mAnswerIsTrue;
    private boolean mAnswerIsShown;
    private int mQuantityHint;

    private TextView mAnswerTextView;
    private TextView mVersionTextView;
    private Button mShowAnswerButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cheat);
        mAnswerIsTrue = getIntent().getBooleanExtra(EXTRA_ANSWER_IS_TRUE, false);
        mQuantityHint = getIntent().getIntExtra(EXTRA_QUANTITY_HINT, 3);
        mAnswerTextView = findViewById(R.id.answer_text_view);
        mShowAnswerButton = findViewById(R.id.show_answer_button);
        mVersionTextView = findViewById(R.id.version_text_view);
        mVersionTextView.setText(String.format("API level: %d", Build.VERSION.SDK_INT));

        if(savedInstanceState == null) {
            mAnswerIsShown = false;
        } else {
            mAnswerIsShown = savedInstanceState.getBoolean(EXTRA_ANSWER_SHOWN);
            if(mAnswerIsShown == true) {
                setAnwer();
            }
        }

        mShowAnswerButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mAnswerIsShown = true;
                mQuantityHint--;
                setAnwer();
                if(Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
                    int cx = mShowAnswerButton.getWidth() / 2;
                    int cy = mShowAnswerButton.getHeight() / 2;
                    float radius = mShowAnswerButton.getWidth();
                    Animator anim = ViewAnimationUtils.createCircularReveal(mShowAnswerButton, cx, cy, radius, 0);
                    anim.addListener(new AnimatorListenerAdapter() {
                        @Override
                        public void onAnimationEnd(Animator animation) {
                            super.onAnimationEnd(animation);
                            mShowAnswerButton.setVisibility(View.INVISIBLE);
                        }
                    });
                    anim.start();
                } else {
                    mShowAnswerButton.setVisibility(View.INVISIBLE);
                }
            }
        });
    }

    public static Intent newIntent(Context context, boolean isAnswerIsTrue, int mQuanityHint) {
        Intent intent = new Intent(context, CheatActivity.class);
        intent.putExtra(EXTRA_ANSWER_IS_TRUE, isAnswerIsTrue);
        intent.putExtra(EXTRA_QUANTITY_HINT, mQuanityHint);
        return intent;
    }

    public void setAnswerShownResult(boolean oiAnswerShown) {
        Intent data = new Intent();
        data.putExtra(EXTRA_ANSWER_SHOWN, true);
        data.putExtra(EXTRA_QUANTITY_HINT, mQuantityHint);
        setResult(RESULT_OK, data);
    }

    public static boolean wasAnswerShown(Intent result) {
        return result.getBooleanExtra(EXTRA_ANSWER_SHOWN, false);
    }

    public static int getQuantityHint(Intent intent) {
        return intent.getIntExtra(EXTRA_QUANTITY_HINT, 3);
    }

    @Override
    protected void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        outState.putBoolean(EXTRA_ANSWER_SHOWN, mAnswerIsShown);
    }

    private void setAnwer() {
        if(mAnswerIsTrue) {
            mAnswerTextView.setText(R.string.true_button);
        } else {
            mAnswerTextView.setText(R.string.false_button);
        }
        setAnswerShownResult(true);
    }
}
