package com.my.cafeoder;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.TextView;

public class OrderDetailActivity extends AppCompatActivity {

    public static final String ORDER = "order";

    private TextView mTextViewOrder;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_order_create);
        mTextViewOrder = findViewById(R.id.textViewOrder);
        Intent intent = getIntent();
        if(intent.hasExtra(ORDER)) {
            String order = intent.getStringExtra(ORDER);
            mTextViewOrder.setText(order);
        } else {
            Intent backToLogin = new Intent(this, LoginActivity.class);
            startActivity(backToLogin);
        }

    }


}
