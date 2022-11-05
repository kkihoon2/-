package com.example.graduateproject;
import java.net.HttpURLConnection;

import java.net.URL;

import android.content.Intent;
import android.os.Bundle;
import android.provider.MediaStore;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;
import java.io.*;
import java.net.*;

import android.app.*;
import android.os.*;
import android.util.*;
import android.view.*;
import android.widget.*;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;


import androidx.appcompat.app.AppCompatActivity;

import org.apache.http.params.HttpConnectionParams;
import org.apache.http.params.HttpParams;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.HashMap;
import java.util.Map;

public class MarkersendActivity extends AppCompatActivity {

    Button starttosend;
    marker sendmarker;
    JwtInform jwt;
    String json;
    String parsingNickName;
    boolean isLogin;
    public String setParsingNickName(String json)
    {
        try{
            JSONObject jsonObject =new JSONObject(json);

            String parsingNickName = jsonObject.getString("nickname");
            System.out.println(parsingNickName);
            return parsingNickName;

        }catch (JSONException e)
        {
            e.printStackTrace();
        }
        return "setParsingNickName error";
    }

    public String setJwt(JwtInform jwt)
    {
        HttpURLConnection hc;
        String result="";

        try {
            String authorization = "Bearer "+jwt.getAccessToken();
            URL url = new URL("http://10.210.8.130:8080/member/me");
            HttpURLConnection connection = (HttpURLConnection) url.openConnection();
            connection.setRequestMethod("GET"); //전송방식
            connection.setDoOutput(false);       //데이터를 쓸 지 설정
            connection.setDoInput(true);

            connection.setRequestProperty("Authorization", authorization);//데이터를 읽어올지 설정

            InputStream is = connection.getInputStream();
            StringBuilder sb = new StringBuilder();
            BufferedReader br = new BufferedReader(new InputStreamReader(is,"UTF-8"));

            while((result = br.readLine())!=null){
                sb.append(result+"\n");
            }

            result = sb.toString();

        }catch(MalformedURLException m){
            Log.e("MalformedURLException에러","MalformedURLException에러");
        }
        catch(IOException e)
        {
            Log.e("IOException에러","IOException에러");
            e.printStackTrace();
        }catch(NullPointerException e)
        {
            Log.e("NullPointerException에러","NullPointerException에러");
        }
        System.out.println("tokensendresult2"+result);
        return result;
    }
    protected void onCreate(Bundle savedInstance) {
        super.onCreate(savedInstance);

        setContentView(R.layout.activity_markersend);
        TextView poster_nickname = (TextView) findViewById(R.id.poster_nickname);
       // EditText is_danger = (EditText) findViewById(R.id.is_danger);

        Spinner categorySpinner = (Spinner)findViewById(R.id.categorySpinner);

        RadioButton safeButton = (RadioButton)findViewById(R.id.safeButton);
        RadioButton dangerButton = (RadioButton)findViewById(R.id.dangerButton);
        EditText marker_inform = (EditText) findViewById(R.id.marker_inform);

       // TextView  marker_latitude = (TextView) findViewById(R.id.marker_latitude);
       // TextView marker_longitude = (TextView) findViewById(R.id.marker_longitude);
       // EditText marker_category = (EditText) findViewById(R.id.marker_catergory);
        EditText image_url = (EditText) findViewById(R.id.image_url);

        starttosend = findViewById(R.id.starttosend);
        Intent intent = getIntent();
        JwtInform jwt = (JwtInform)intent.getSerializableExtra("jwt");
        Double intent_latitude = intent.getDoubleExtra("latitude", 0);
        Double intent_longitude = intent.getDoubleExtra("longitude", 0);
        boolean ismarkersend = true;

        json = setJwt(jwt);
        parsingNickName=setParsingNickName(json);
        System.out.println("json: "+json);
        poster_nickname.setText(parsingNickName);
        starttosend.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String nickname = poster_nickname.getText().toString();
                String category=categorySpinner.getSelectedItem().toString();
                String inform = marker_inform.getText().toString();
                String url = image_url.getText().toString();
                int isDanger = -1;
                if(dangerButton.isChecked()==true)
                {
                    isDanger = 1;
                }
                else if(safeButton.isChecked()==true)
                {
                    isDanger=0;
                }
                else{
                    Toast.makeText(MarkersendActivity.this,"하나 클릭하세요",Toast.LENGTH_SHORT).show();
                }
//                sendmarker.setMarkerInform(inform);
//                sendmarker.setImageUrl(url);
//                sendmarker.setMarkerLatitude(intent_latitude);
//                sendmarker.setMarkerLongitude(intent_longitude);
//                sendmarker.setPosterNickName(nickname);
//                sendmarker.setMarkerCategory(category);
                sendmarker = new marker(isDanger,inform,url,intent_latitude,intent_longitude,parsingNickName,category);
               Intent intent2 = new Intent(MarkersendActivity.this,MainActivity.class);
               //intent2.putExtra("sendmarker",sendmarker);
               //intent2.putExtra("ismarkersend",ismarkersend);

               Map<String,Integer>categoryMap = new HashMap<>();
               categoryMap.put("공사장",1);
               categoryMap.put("보행사고다발지역",2);
               categoryMap.put("스쿨존사고주의",3);
               categoryMap.put("어린이보호구역",4);
               categoryMap.put("범죄주의구간",5);
               categoryMap.put("파출소",6);
               categoryMap.put("치안센터",7);
               categoryMap.put("경찰서",8);
                categoryMap.put("지구대",9);
                categoryMap.put("안심벨",10);
                JSONObject mark = new JSONObject();

               try{
                   mark.put("markerCategory",categoryMap.get(sendmarker.getMarkerCategory().toString()));
                   mark.put("markerInform",sendmarker.getMarkerInform());
                   mark.put("isDanger",sendmarker.getIsDanger().toString());
                   mark.put("imageUrl",sendmarker.getImageUrl());
                   mark.put("markerLongitude",intent_longitude.toString());
                   mark.put("markerLatitude",intent_latitude.toString());
                   mark.put("posterNickName",sendmarker.getPosterNickName());

               }catch (JSONException e)
               {
                    System.out.println(e.getCause());
               }

               System.out.println("send"+mark);

                try {
                    //--------------------------
                    //   URL 설정하고 접속하기
                    //--------------------------
                    URL url2 = new URL("http://10.210.8.130:8080/api/marker/");       // URL 설정
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
                     bw.write(mark.toString());
                     bw.flush();
                     bw.close();

                    InputStreamReader tmp = new InputStreamReader(http.getInputStream(), "EUC-KR");
                    BufferedReader reader = new BufferedReader(tmp);
                    StringBuilder builder = new StringBuilder();
                    String str;
                    while ((str = reader.readLine()) != null) {       // 서버에서 라인단위로 보내줄 것이므로 라인단위로 읽는다
                        builder.append(str + "\n");                     // View에 표시하기 위해 라인 구분자 추가
                    }
                    String myResult = builder.toString();                       // 전송결과를 전역 변수에 저장
                    System.out.println(myResult);


                }
                catch (IOException ie)
                {
                    System.out.println("io"+ie.getCause());
                    ie.printStackTrace();
                }catch(Exception ee)
                {
                    System.out.println("e"+ee.getCause());
                    ee.printStackTrace();
                }
                isLogin=true;
                intent2.putExtra("isLogin",isLogin);
                intent2.putExtra("jwt",jwt);
                startActivity(intent2);

            }

        });









    }
}

