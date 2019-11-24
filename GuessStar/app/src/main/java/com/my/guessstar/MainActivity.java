package com.my.guessstar;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.Toast;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;
import java.util.concurrent.ExecutionException;

public class MainActivity extends AppCompatActivity {

    private ImageView mImageStar;
    private Button mButtonVar0;
    private Button mButtonVar1;
    private Button mButtonVar2;
    private Button mButtonVar3;

    private ArrayList<String> names;
    private ArrayList<Bitmap> images;
    private ArrayList<Button> mButtons;

    private int numberOfQuestion;
    private int numberOfRightQuestionOnButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        mButtonVar0 = findViewById(R.id.button_var0);
        mButtonVar1 = findViewById(R.id.button_var1);
        mButtonVar2 = findViewById(R.id.button_var2);
        mButtonVar3 = findViewById(R.id.button_var3);
        mButtons = new ArrayList<>();
        mButtons.add(mButtonVar0);
        mButtons.add(mButtonVar1);
        mButtons.add(mButtonVar2);
        mButtons.add(mButtonVar3);
        mImageStar = findViewById(R.id.imageStar);
        getContent();
        playGame();
    }

    private void playGame() {
        generateQuestion();
        mImageStar.setImageBitmap(images.get(numberOfQuestion));
        for(int i = 0; i < mButtons.size(); i++) {
            if(i == numberOfRightQuestionOnButton) {
                mButtons.get(i).setText(names.get(numberOfQuestion));
            } else {
                int wrongAnswer = generateWrongAnswer();
                mButtons.get(i).setText(names.get(wrongAnswer));
            }
        }
    }

    private void generateQuestion() {
         numberOfQuestion = (int) (Math.random() * names.size());
         numberOfRightQuestionOnButton = (int) (Math.random() * mButtons.size());
    }

    private int generateWrongAnswer() {
        return (int) (Math.random() * names.size());
    }

    private void getContent() {
        LoadImage loadImage = new LoadImage();
        LoadDescriptions loadDescriptions = new LoadDescriptions();
        try {
            images = loadImage.execute().get();
            names = loadDescriptions.execute().get();
            Log.i("TAG", names.get(10));
        } catch (ExecutionException e) {
            e.printStackTrace();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    public void onClickAnswer(View view) {
        playGame();
        Button button = (Button) view;
        int tag = Integer.parseInt(button.getTag().toString());
        if(tag == numberOfRightQuestionOnButton) {
            Toast.makeText(this, "Correct", Toast.LENGTH_SHORT).show();
        } else {
            Toast.makeText(this, "Uncorrect, correct is " + names.get(numberOfQuestion), Toast.LENGTH_SHORT).show();
        }
    }

    static class LoadImage extends AsyncTask<Void, Void, ArrayList<Bitmap>> {
        /*
        Получаем ссылки на изображение и на имя звезды
        при помощи библиотеки для парсинга html страницы jsoup
         */
        @Override
        protected ArrayList<Bitmap> doInBackground(Void... voids) {
            ArrayList<Bitmap> bitmaps = new ArrayList<>();
            HttpURLConnection urlConnection = null;
            InputStream inputStream = null;
            Document doc = null;
            try {
                doc = Jsoup.connect("http://posh24.se/kandisar").get();
            } catch (IOException e) {
                e.printStackTrace();
            }
//            Element el = doc.select("img").first();
//            String img = el.absUrl("src");
//            String img2 = el.attr("alt");
            Elements images = doc.select("div.image img");
            Element image = null;
            URL url = null;
            for(int i = 0; i < images.size(); i++) {
                image = images.get(i);
                String img = image.attr("src");
                try {
                    url = new URL(img);
                    urlConnection = (HttpURLConnection) url.openConnection();
                    inputStream = urlConnection.getInputStream();
                    Bitmap bitmap = BitmapFactory.decodeStream(inputStream);
                    bitmaps.add(bitmap);
                    } catch (MalformedURLException e) {
                        e.printStackTrace();
                    } catch (IOException e) {
                        e.printStackTrace();
                    }

                }
            if(urlConnection != null) {
                urlConnection.disconnect();
            }
            return bitmaps;
        }
    }

    class LoadDescriptions extends AsyncTask<Void, Void, ArrayList<String>> {
        @Override
        protected ArrayList<String> doInBackground(Void... voids) {
            Document doc = null;
            ArrayList<String> resultDescriptions = new ArrayList<>();
            try {
                doc = Jsoup.connect("http://posh24.se/kandisar").get();
            } catch (IOException e) {
                e.printStackTrace();
            }
//            Element el = doc.select("img").first();
//            String img = el.absUrl("src");
//            String img2 = el.attr("alt");
            Elements descriptions = doc.select("div.image img");
            Element descreption = null;
            for(int i = 0; i < descriptions.size(); i++) {
                descreption = descriptions.get(i);
                String result = descreption.attr("alt");
                resultDescriptions.add(result);
            }
            return resultDescriptions;
        }
    }
}
