package com.my.cafeoder;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Adapter;
import android.widget.ArrayAdapter;
import android.widget.CheckBox;
import android.widget.RadioButton;
import android.widget.Spinner;
import android.widget.TextView;

public class CreateOrderActivity extends AppCompatActivity {

    public static final String LOGIN = "login";
    public static final String PASSWORD = "pass";
    public static final String ORDER = "order";

    private TextView mTextViewHello;
    private TextView mTextViewAdditions;
    private CheckBox mCheckBoxSugar;
    private CheckBox mCheckBoxLemon;
    private CheckBox mCheckBoxMilk;
    private Spinner mSpinnerTea;
    private Spinner mSpinnerCoffee;

    private String drink;
    private String login;
    private String pass;
    private StringBuilder builderAdditions;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_create_order);
        Intent intent = getIntent();
        login = intent.getStringExtra(LOGIN);
        pass = intent.getStringExtra(PASSWORD);
        drink = getString(R.string.tea);
        if(!intent.hasExtra(LOGIN) || !intent.hasExtra(PASSWORD)) {
            login = getString(R.string.default_login);
            pass = getString(R.string.default_password);
        }

        mTextViewAdditions = findViewById(R.id.textViewAdditions);
        String additions = String.format(getString(R.string.additions), drink);
        mTextViewAdditions.setText(additions);

        mTextViewHello = findViewById(R.id.textViewHello);
        String hello = String.format(getString(R.string.hello_user), login);
        mTextViewHello.setText(hello);

        mCheckBoxLemon = findViewById(R.id.checkboxLemon);
        mCheckBoxMilk = findViewById(R.id.checkboxMilk);
        mCheckBoxSugar = findViewById(R.id.checkboxSugar);
        mSpinnerCoffee = findViewById(R.id.spinnerOfCoffee);
        mSpinnerTea = findViewById(R.id.spinnerOfTea);
        ArrayAdapter<?> adapterTea = ArrayAdapter.createFromResource(this, R.array.options_of_tea, R.layout.spinner_item);
        ArrayAdapter<?> adapterCoffee = ArrayAdapter.createFromResource(this, R.array.options_of_coffee, R.layout.spinner_item);
        mSpinnerCoffee.setAdapter(adapterCoffee);
        mSpinnerTea.setAdapter(adapterTea);
        adapterTea.setDropDownViewResource(R.layout.spinner_dropdown_item);
        adapterCoffee.setDropDownViewResource(R.layout.spinner_dropdown_item);
        builderAdditions = new StringBuilder();
    }

    public void onClickChangeDrink(View view) {
        RadioButton radioButton = (RadioButton) view;
        int id = radioButton.getId();
        if(id == R.id.radioButtonTea) {
            drink = getString(R.string.tea);
            mSpinnerTea.setVisibility(View.VISIBLE);
            mSpinnerCoffee.setVisibility(View.INVISIBLE);
            mCheckBoxLemon.setVisibility(View.VISIBLE);
        } else if (id == R.id.radioButtonCoffee) {
            drink = getString(R.string.coffee);
            mSpinnerTea.setVisibility(View.INVISIBLE);
            mSpinnerCoffee.setVisibility(View.VISIBLE);
            mCheckBoxLemon.setVisibility(View.INVISIBLE);
        }
        String additions = String.format(getString(R.string.additions), drink);
        mTextViewAdditions.setText(additions);

    }

    public void onClickSetOrder(View view) {
        builderAdditions.setLength(0);
        if (mCheckBoxLemon.isChecked() && drink.equals(getString(R.string.tea))) {
            builderAdditions.append(getString(R.string.lemon)).append(" ");
        }
        if (mCheckBoxMilk.isChecked()) {
            builderAdditions.append(getString(R.string.milk)).append(" ");
        }
        if (mCheckBoxSugar.isChecked()) {
            builderAdditions.append(getString(R.string.sugar)).append(" ");
        }
        String optionOfDrink = "";
        if(drink.equals(getString(R.string.tea))) {
            optionOfDrink = mSpinnerTea.getSelectedItem().toString();
        } else if (drink.equals(getString(R.string.coffee))) {
            optionOfDrink = mSpinnerCoffee.getSelectedItem().toString();
        }
        String order = String.format(getString(R.string.order), login, pass, drink, optionOfDrink);
        String additions = "";
        if(builderAdditions.length() > 0) {
            additions = "\n" + getString(R.string.need_additions) + builderAdditions;
        }
        String fullOrder = order + additions;
        Intent intent = new Intent(this, OrderDetailActivity.class);
        intent.putExtra(ORDER, fullOrder);
        startActivity(intent);
    }
}
