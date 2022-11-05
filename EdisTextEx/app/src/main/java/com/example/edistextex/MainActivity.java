package com.example.edistextex;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

public class MainActivity extends AppCompatActivity {

    private Button btn_move;
    private EditText test_;
    private String str;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        test_=findViewById(R.id.test_);


        btn_move = findViewById(R.id.btn_move);//객체 생성
        btn_move.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                str = test_.getText().toString();//입력시키는 객체 생성
                Intent intent = new Intent(MainActivity.this,SubActivity.class);
                intent.putExtra("str",str);
                startActivity(intent);//액티비티 이동
            }
        });

    }
}