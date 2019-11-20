package com.my.stringadvanced;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
//        String geometria = "Геометрия";
//        String metr = geometria.substring(3, 7);
//        Log.i("Name", metr);
          String river = "Mississipi";
          Pattern pattern = Pattern.compile("Mi(.*?)pi");
          Matcher matcher = pattern.matcher(river);
          while(matcher.find()) {
              Log.i("name", matcher.group(1));
          }

          String url = "<div class=\"image\">\n" +
                  "\t\t\t\t\t\t<img src=\"http://cdn.posh24.se/images/:profile/25438cc5f0c3fdce720fe88b03705ea7a\" alt=\"Margaux Dietz\"/>\n" +
                  "\t\t\t\t\t</div>";
          Pattern pattern1 = Pattern.compile("img src=\"(.*?)\"");
          Pattern pattern2 = Pattern.compile("alt=\"(.*?)\"");
          Matcher matcher1 = pattern1.matcher(url);
          Matcher matcher2 = pattern2.matcher(url);
          while(matcher1.find()) {
              Log.i("name", matcher1.group(1));
          }
          while(matcher2.find()) {
              Log.i("name", matcher2.group(1));
          }
    }
}
