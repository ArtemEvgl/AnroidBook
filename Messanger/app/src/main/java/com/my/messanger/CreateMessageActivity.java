package com.my.messanger;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;

public class CreateMessageActivity extends AppCompatActivity {

    private EditText mEditeTextMessage;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_create_message);
        mEditeTextMessage = findViewById(R.id.editTextMessage);
    }

    public void onClickSendMessage(View view) {
        String message = mEditeTextMessage.getText().toString();
        Intent intent = new Intent(Intent.ACTION_SEND);
        intent.setType("text/plain");
        intent.putExtra(Intent.EXTRA_TEXT, message);
        Intent chosenIntent = Intent.createChooser(intent, getResources().getString(R.string.chooser_title));
        startActivity(chosenIntent);
    }
}
