package com.my.toolsshop;

import android.content.Context;
import android.content.Intent;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.ImageView;
import android.widget.TextView;

public class DrillDetailActivity extends AppCompatActivity {

    private static final String TITLE = "title";
    private static final String INFO = "info";
    private static final String RESID = "resid";

    private TextView mTitleTextView;
    private TextView mInfoTextView;
    private ImageView mLogoImageView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_drill_detail);
        ActionBar actionBar = getSupportActionBar();
        if(actionBar != null) {
            actionBar.hide();
        }
        mTitleTextView = findViewById(R.id.titleTextView);
        mInfoTextView = findViewById(R.id.infoTextView);
        mLogoImageView = findViewById(R.id.logoImageView);
        if(getIntent().hasExtra(TITLE) && getIntent().hasExtra(RESID) && getIntent().hasExtra(INFO)) {
            mLogoImageView.setImageResource(getIntent().getIntExtra(RESID, 0));
            mTitleTextView.setText(getIntent().getStringExtra(TITLE));
            mInfoTextView.setText(getIntent().getStringExtra(INFO));
        } else {
            Intent intent = new Intent(this, DrillCategoryActivity.class);
            startActivity(intent);
        }
    }

    public static Intent newIntent(Context context, String title, String info, int resId) {
        Intent intent =  new Intent(context, DrillDetailActivity.class);
        intent.putExtra(TITLE, title);
        intent.putExtra(INFO, info);
        intent.putExtra(RESID, resId);
        return intent;
    }
}
