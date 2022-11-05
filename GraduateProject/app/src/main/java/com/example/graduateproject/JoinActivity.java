package com.example.graduateproject;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;

public class JoinActivity extends AppCompatActivity {
    Button joinButton;
    Button checkId;
    Button checkNickName;
    Boolean checkIdbool;
    Boolean checkNickNamebool;
    String id , nickname , pw;
    CheckBox isChild;
    CheckBox isParent;
    int childparent;
    JwtInform jwt;
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_join);
        joinButton = findViewById(R.id.join);
        checkId=findViewById(R.id.check_Id);
        checkNickName=findViewById(R.id.check_Nickname);

        checkIdbool = true;
        checkNickNamebool = true;
        EditText member_id = (EditText) findViewById(R.id.member_id);
        EditText member_pw = (EditText) findViewById(R.id.member_pw);
        EditText member_nickname = (EditText) findViewById(R.id.member_nickname);






        checkId.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String result ="";
                String str1 = "false";
                String str2 = "true";
                id = member_id.getText().toString();
                try {
                    String checkIdUrl="http://192.168.219.101:8080/auth/memberId/"+id+"/exists";
                    URL url = new URL("http://10.210.8.130:8080/auth/memberId/"+id+"/exists");
                    HttpURLConnection connection = (HttpURLConnection) url.openConnection();
                    connection.setRequestMethod("GET"); //전송방식
                    connection.setDoOutput(false);       //데이터를 쓸 지 설정
                    connection.setDoInput(true);        //데이터를 읽어올지 설정

                    InputStream is = connection.getInputStream();
                    StringBuilder sb = new StringBuilder();
                    BufferedReader br = new BufferedReader(new InputStreamReader(is,"UTF-8"));

                    while((result = br.readLine())!=null){
                        sb.append(result+"\n");
                    }

                    result = sb.toString();
                    System.out.println("ID중복확인 get:"+result);


                } catch (MalformedURLException e) {
                    e.printStackTrace();
                } catch (IOException e) {
                    e.printStackTrace();
                }
                checkIdbool=Boolean.parseBoolean(result);
                if(checkIdbool==false)
                {
                    Toast.makeText(getApplicationContext(), "사용가능한 ID입니다.", Toast.LENGTH_LONG).show();

                }else if(checkIdbool==true)
                {
                    Toast.makeText(getApplicationContext(), "이미 존재하는 ID입니다다.", Toast.LENGTH_LONG).show();
                }else
                {
                    Toast.makeText(getApplicationContext(), "오류", Toast.LENGTH_LONG).show();
                }

            }
        });
        checkNickName.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                String result = "";
                nickname = member_nickname.getText().toString();
                try {
                    String checkIdUrl="http://192.168.219.101:8080/auth/nickname/"+nickname+"/exists";
                    URL url = new URL("http://10.210.8.130:8080/auth/nickname/"+nickname+"/exists");
                    HttpURLConnection connection = (HttpURLConnection) url.openConnection();
                    connection.setRequestMethod("GET"); //전송방식
                    connection.setDoOutput(false);       //데이터를 쓸 지 설정
                    connection.setDoInput(true);        //데이터를 읽어올지 설정

                    InputStream is = connection.getInputStream();
                    StringBuilder sb = new StringBuilder();
                    BufferedReader br = new BufferedReader(new InputStreamReader(is,"UTF-8"));

                    while((result = br.readLine())!=null){
                        sb.append(result+"\n");
                    }

                    result = sb.toString();

                } catch (MalformedURLException e) {
                    e.printStackTrace();
                } catch (IOException e) {
                    e.printStackTrace();
                }
                checkNickNamebool=Boolean.getBoolean(result);
                if(checkNickNamebool == false)
                {

                    Toast.makeText(getApplicationContext(), "사용가능한 닉네임입니다.", Toast.LENGTH_LONG).show();
                }else if(checkNickNamebool == true)
                {

                    Toast.makeText(getApplicationContext(), "이미 존재하는 닉네임입니다.", Toast.LENGTH_LONG).show();
                }



            }
        });



        joinButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // id test 한번더
                String result ="";
                String str1 = "false";
                String str2 = "true";
                id = member_id.getText().toString();
                try {
                    String checkIdUrl="http://192.168.219.101:8080/auth/memberId/"+id+"/exists";
                    URL url = new URL("http://10.210.8.130:8080/auth/memberId/"+id+"/exists");
                    HttpURLConnection connection = (HttpURLConnection) url.openConnection();
                    connection.setRequestMethod("GET"); //전송방식
                    connection.setDoOutput(false);       //데이터를 쓸 지 설정
                    connection.setDoInput(true);        //데이터를 읽어올지 설정

                    InputStream is = connection.getInputStream();
                    StringBuilder sb = new StringBuilder();
                    BufferedReader br = new BufferedReader(new InputStreamReader(is,"UTF-8"));

                    while((result = br.readLine())!=null){
                        sb.append(result+"\n");
                    }

                    result = sb.toString();
                    System.out.println("ID중복확인 get:"+result);


                } catch (MalformedURLException e) {
                    e.printStackTrace();
                } catch (IOException e) {
                    e.printStackTrace();
                }
                checkIdbool=Boolean.parseBoolean(result);
                if(checkIdbool==false)
                {
//                    Toast.makeText(getApplicationContext(), "사용가능한 ID입니다.", Toast.LENGTH_LONG).show();

                }else if(checkIdbool==true)
                {
                    Toast.makeText(getApplicationContext(), "이미 존재하는 ID입니다다.", Toast.LENGTH_LONG).show();
                }else
                {
                    Toast.makeText(getApplicationContext(), "오류", Toast.LENGTH_LONG).show();
                }
                //nickname test 한번더
                result ="";
                nickname = member_nickname.getText().toString();
                try {
                    String checkIdUrl="http://192.168.219.101:8080/auth/nickname/"+nickname+"/exists";
                    URL url = new URL("http://10.210.8.130:8080/auth/nickname/"+nickname+"/exists");
                    HttpURLConnection connection = (HttpURLConnection) url.openConnection();
                    connection.setRequestMethod("GET"); //전송방식
                    connection.setDoOutput(false);       //데이터를 쓸 지 설정
                    connection.setDoInput(true);        //데이터를 읽어올지 설정

                    InputStream is = connection.getInputStream();
                    StringBuilder sb = new StringBuilder();
                    BufferedReader br = new BufferedReader(new InputStreamReader(is,"UTF-8"));

                    while((result = br.readLine())!=null){
                        sb.append(result+"\n");
                    }

                    result = sb.toString();

                } catch (MalformedURLException e) {
                    e.printStackTrace();
                } catch (IOException e) {
                    e.printStackTrace();
                }
                checkNickNamebool=Boolean.getBoolean(result);
                if(checkNickNamebool == false)
                {

                    Toast.makeText(getApplicationContext(), "사용가능한 닉네임입니다.", Toast.LENGTH_LONG).show();
                }else if(checkNickNamebool == true)
                {

                    Toast.makeText(getApplicationContext(), "이미 존재하는 닉네임입니다.", Toast.LENGTH_LONG).show();
                }




                if(checkIdbool == false && checkNickNamebool == false) {
                    JSONObject member = new JSONObject();
                    isChild = (CheckBox) findViewById(R.id.ischild) ;
                    isParent = (CheckBox) findViewById(R.id.isparent);
                    if(isChild.isChecked()==true)
                    {
                        childparent = 1;
                    }else if (isParent.isChecked()==true)
                    {
                        childparent = 2;
                    }
                    pw = member_pw.getText().toString();
                    try {
                        member.put("memberId", id.toString());
                        member.put("password", pw.toString());
                        member.put("nickname", nickname.toString());
                        member.put("memberRole",childparent);
                        System.out.println("send" + member);


                    } catch (JSONException e) {
                        System.out.println(e.getCause());
                    }

                    try {
                        //--------------------------
                        //   URL 설정하고 접속하기
                        //--------------------------
                        URL url2 = new URL("http://10.210.8.130:8080/auth/signup");       // URL 설정
                        HttpURLConnection http = (HttpURLConnection) url2.openConnection();   // 접속
                        //--------------------------
                        //   전송 모드 설정 - 기본적인 설정이다
                        //--------------------------
                        http.setRequestMethod("POST");

                        http.setDoInput(true);                         // 서버에서 읽기 모드 지정
                        http.setDoOutput(true);                       // 서버로 쓰기 모드 지정
                        // 전송 방식은 POST

                        // 서버에게 웹에서 <Form>으로 값이 넘어온 것과 같은 방식으로 처리하라는 걸 알려준다
                        http.setRequestProperty("content-type", "application/json");
                        http.setRequestProperty("Accept", " application/json");
                        //--------------------------
                        //   서버로 값 전송
                        //--------------------------
                        BufferedWriter bw = new BufferedWriter(new OutputStreamWriter(http.getOutputStream()));
                        bw.write(member.toString());
                        bw.flush();
                        bw.close();

                        InputStreamReader tmp = new InputStreamReader(http.getInputStream(), "UTF-8");
                        BufferedReader reader = new BufferedReader(tmp);
                        StringBuilder builder = new StringBuilder();
                        String str;
                        while ((str = reader.readLine()) != null) {       // 서버에서 라인단위로 보내줄 것이므로 라인단위로 읽는다
                            builder.append(str + "\n");                     // View에 표시하기 위해 라인 구분자 추가
                        }
                        String myResult = builder.toString();                       // 전송결과를 전역 변수에 저장
                        System.out.println(myResult);
                        Toast.makeText(getApplicationContext(), "회원가입 성공", Toast.LENGTH_LONG).show();
                        Intent intent2 = new Intent(JoinActivity.this,MainActivity.class);
                        startActivity(intent2);



                    } catch (IOException ie) {
                        System.out.println("io" + ie.getCause());
                        ie.printStackTrace();
                    } catch (Exception ee) {
                        System.out.println("e" + ee.getCause());
                        ee.printStackTrace();
                    }
                }else
                {
                    Toast.makeText(getApplicationContext(), "회원가입 실패", Toast.LENGTH_LONG).show();
                }
            }
        });
    }

}