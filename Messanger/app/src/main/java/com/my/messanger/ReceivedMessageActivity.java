package com.my.messanger;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.TextView;

public class ReceivedMessageActivity extends AppCompatActivity {

    private TextView mTextViewMessage;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_received_message);
        mTextViewMessage = findViewById(R.id.textViewReceivedMessage);
        Intent intent = getIntent();
        String msg = intent.getStringExtra("msg");
        mTextViewMessage.setText(msg);
    }


}
