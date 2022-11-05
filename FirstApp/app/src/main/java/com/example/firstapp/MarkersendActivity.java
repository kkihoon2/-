package com.example.firstapp;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import androidx.appcompat.app.AppCompatActivity;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

public class MarkersendActivity extends AppCompatActivity {
    protected void onCreate(Bundle savedInstance) {
        super.onCreate(savedInstance);
        setContentView(R.layout.activity_markersend);
        EditText poster_nickname = (EditText) findViewById(R.id.poster_nickname);
        EditText is_danger = (EditText) findViewById(R.id.is_danger);
        EditText marker_inform = (EditText) findViewById(R.id.marker_inform);
        EditText marker_latitude = (EditText) findViewById(R.id.marker_latitude);
        EditText marker_longitude = (EditText) findViewById(R.id.marker_longitude);
        EditText marker_category = (EditText) findViewById(R.id.marker_catergory);
        EditText image_url = (EditText) findViewById(R.id.image_url);
        Button starttosend = findViewById(R.id.starttosend);
        starttosend.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                JSONObject wrapObject = new JSONObject();
                JSONArray jsonArray = new JSONArray();
                try {
                    for(int i = 0 ; i<10; i++)
                    {
                        JSONObject jsonObject = new JSONObject();
                        jsonObject.put("poster_nickname",poster_nickname.getText().toString());
                        jsonObject.put("is_danger",is_danger.getText().toString());
                        jsonObject.put("marker_inform",marker_inform.getText().toString());
                        jsonObject.put("marker_latitude",marker_latitude.getText().toString());
                        jsonObject.put("marker_longitude",marker_longitude.getText().toString());
                        jsonObject.put("marker_category",marker_category.getText().toString());
                    }
                    wrapObject.put("marker",jsonArray);
                    //receiveArray(wrapObject.toString());
                }catch (JSONException e){
                    e.printStackTrace();
                }
            }
        });
    }
}

