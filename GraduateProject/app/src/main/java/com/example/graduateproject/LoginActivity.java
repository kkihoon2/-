package com.example.graduateproject;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.net.HttpURLConnection;
import java.net.URL;

public class LoginActivity extends AppCompatActivity {
    private Button join_in;
    private Button login_btn;
    String str;
    boolean isLogin;
    String id;
    String pw;

//    public void setSendJwt(JwtInform jwt)
//    {
//
//    }
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        join_in = findViewById(R.id.join_in);
        login_btn=findViewById(R.id.login_btn);


        EditText login_id = (EditText) findViewById(R.id.login_id);
        EditText login_pw = (EditText) findViewById(R.id.login_pw);

        login_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                str="";
                id = login_id.getText().toString();
                pw = login_pw.getText().toString();
                JSONObject loginInform = new JSONObject();
                try {
                    loginInform.put("memberId",id);
                    loginInform.put("password",pw);
                }catch (JSONException j) { }
                System.out.println("loginInform :" +loginInform.toString());

                String grantType1,accessToken1,tokenExpireIn1 = "";
                try {
                    JwtInform jwt = new JwtInform("",null,0);

                    //--------------------------
                    //   URL 설정하고 접속하기
                    //--------------------------
                    URL url2 = new URL("http://10.210.8.130:8080/auth/login/");       // URL 설정
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
                    bw.write(loginInform.toString());
                    bw.flush();
                    bw.close();

                    InputStreamReader tmp = new InputStreamReader(http.getInputStream(), "UTF-8");
                    BufferedReader reader = new BufferedReader(tmp);
                    StringBuilder builder = new StringBuilder();

                    while ((str = reader.readLine()) != null) {       // 서버에서 라인단위로 보내줄 것이므로 라인단위로 읽는다
                        builder.append(str + "\n");                     // View에 표시하기 위해 라인 구분자 추가
                    }
                    String myResult = builder.toString();                       // 전송결과를 전역 변수에 저장
                    System.out.println(myResult);

                    JSONObject jsonObject =new JSONObject(myResult);

                    jwt.setGrantType(jsonObject.getString("grantType"));
                    jwt.setAccessToken(jsonObject.getString("accessToken"));
                    jwt.setTokenExpiresIn(jsonObject.getLong("tokenExpiresIn"));
                    System.out.println("제발되라"+jwt.getTokenExpiresIn());

                    if(jwt.getAccessToken()==null)
                    {
                        Toast.makeText(getApplicationContext(), "아이디 비밀번호를 확인하세요.", Toast.LENGTH_LONG).show();
                    }else
                    {
                        isLogin = true;
                        Intent sendJwt = new Intent(LoginActivity.this,MainActivity.class);
                        sendJwt.putExtra("isLogin",isLogin);
                        sendJwt.putExtra("jwt",jwt);
                        LoginActivity.this.startActivity(sendJwt);
                    }

                }
                catch(NullPointerException e)
                { }
                catch (IOException ie)
                {
                    System.out.println("io"+ie.getCause());
                    ie.printStackTrace();
                }catch(Exception ee)
                {
                    System.out.println("e"+ee.getCause());
                    ee.printStackTrace();
                }


//                    jwt = new JwtInform(jsonObject.getString("grantType"),jsonObject.getString("accessToken"),jsonObject.getInt("tokenExpiresln"));











            }
        });


        join_in.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(LoginActivity.this,JoinActivity.class);
                startActivity(intent);//액티비티 이동
            }
        });
    }

}
