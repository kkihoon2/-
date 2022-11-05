package com.example.edistextex;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.widget.TextView;

public class SubActivity extends AppCompatActivity {
    private TextView sub;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sub);

        sub=findViewById(R.id.sub);

        Intent intent = getIntent();
        String str = intent.getStringExtra("str");

        sub.setText(str);
    }
}