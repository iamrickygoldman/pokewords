package com.iamricky.pokewords;

import android.content.res.AssetManager;
import android.os.Build;
import android.provider.UserDictionary;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.Locale;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        final TextView message = (TextView) findViewById(R.id.status);
        final Button quit = (Button) findViewById(R.id.quit);
        quit.setVisibility(View.GONE);

        AssetManager am = this.getAssets();
        try {
            InputStream is = am.open("pokemon.txt");
            BufferedReader reader = new BufferedReader(new InputStreamReader(is));
            String data;
            while ((data = reader.readLine()) != null)
            {
                String name = data.toLowerCase();
                name = name.substring(0,1).toUpperCase() + name.substring(1);
                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN) {
                    UserDictionary.Words.addWord(this,name,1,null, Locale.getDefault());
                    Log.v("test1",name + "1");
                } else {
                    UserDictionary.Words.addWord(this,name,1,UserDictionary.Words.LOCALE_TYPE_CURRENT);
                    Log.v("test1",name + "2");
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        message.setText(getString(R.string.success_message));
        quit.setVisibility(View.VISIBLE);
    }

    public void exitProgram(View v) {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN) {
            finishAffinity();
        } else {
            finish();
        }
    }
}
