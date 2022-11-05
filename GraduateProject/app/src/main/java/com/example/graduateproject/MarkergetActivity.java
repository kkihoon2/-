package com.example.graduateproject;

import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;
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
import java.util.HashMap;
import java.util.Map;

public class MarkergetActivity extends AppCompatActivity {
    TextView poster_nickname;
    TextView is_danger;
    TextView marker_inform;
    TextView marker_latitude;
    TextView  marker_longitude;
    TextView marker_category;
    TextView image_url;
    TextView topbartext;
    Button marker_delete;
    JwtInform jwt;
    Button marker_fix;
    String json;
    String parsingNickName;
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
        setContentView(R.layout.activity_markerget);
        LinearLayout backColor;
        backColor = (LinearLayout) findViewById(R.id.backColor);
        marker_delete = findViewById(R.id.marker_delete);
        marker_fix = findViewById(R.id.marker_fix);
        LinearLayout topbar;
        topbar = (LinearLayout) findViewById(R.id.topbar);
        topbartext=(TextView) findViewById(R.id.topbartext);
        poster_nickname = (TextView) findViewById(R.id.poster_nickname);
        is_danger = (TextView)findViewById(R.id.is_danger);
        marker_inform = (TextView)findViewById(R.id.marker_inform);
        // marker_latitude = (TextView)findViewById(R.id.marker_latitude);
        // marker_longitude = (TextView)findViewById(R.id.marker_longitude);
        marker_category = (TextView)findViewById(R.id.marker_category);
        image_url = (TextView)findViewById(R.id.image_url);
        Intent intent = getIntent();
        Intent intent2 = new Intent(MarkergetActivity.this,MainActivity.class);
        Intent intent3 = new Intent(MarkergetActivity.this,MarkerfixActivity.class);
        marker mark = (marker)intent.getSerializableExtra("marker");
        Double intent_latitude = intent.getDoubleExtra("latitude",0);
        Double intent_longitude = intent.getDoubleExtra("longitude",0);
        //Drawable dagnertopbar = getResources().getDrawable(R.drawable.dangertopbar);
        //Drawable safetopbar = getResources().getDrawable(R.drawable.safetopbar);
        Map<String,String> categoryMap = new HashMap<>();
        categoryMap.put("1","공사장");
        categoryMap.put("2","보행사고다발지역");
        categoryMap.put("3","스쿨존사고주의");
        categoryMap.put("4","어린이보호구역");
        categoryMap.put("5","범죄주의구간");
        categoryMap.put("6","파출소");
        categoryMap.put("7","치안센터");
        categoryMap.put("8","경찰서");
        categoryMap.put("9","지구대");
        categoryMap.put("10","안심벨");

        Intent intentGet = getIntent();
        JwtInform jwt = (JwtInform)intentGet.getSerializableExtra("jwt");
        json = setJwt(jwt);
        parsingNickName=setParsingNickName(json);


        String showCategory = categoryMap.get(mark.getMarkerCategory());
        try {
            if(mark.getIsDanger()==1)
            {
                backColor.setBackgroundColor(Color.parseColor("#F5CBCB"));
                //topbar.setBackgroundColor(Color.parseColor("#de6868"));
                topbar.setBackground(getResources().getDrawable(R.drawable.dangertopbar));

                is_danger.setTextColor(Color.parseColor("#D41E1E"));
                is_danger.setText("조심하세요!\n 위험지역입니다!");
                topbartext.setText("위험해요!");
                marker_category.setTextColor(Color.parseColor("#D41E1E"));
                marker_category.setText(String.valueOf("카테고리  "+showCategory));
                poster_nickname.setText(String.valueOf(mark.getPosterNickName()+"님의 제보"));
//        is_danger.setText(String.valueOf(mark.getIsDanger()));
                marker_inform.setText(String.valueOf(mark.getMarkerInform()));

                image_url.setText(String.valueOf(mark.getImageUrl()));
            }else
            {
                backColor.setBackgroundColor(Color.parseColor("#CEF5C6"));
                //topbar.setBackgroundColor(Color.parseColor("#AFCB3D"));
                topbar.setBackground(getResources().getDrawable(R.drawable.safetopbar));
                is_danger.setTextColor(Color.parseColor("#AFCB3D"));
                is_danger.setText("안심하세요!\n 안전지역입니다!");
                topbartext.setText("안전해요!");
                poster_nickname.setText(String.valueOf(mark.getPosterNickName()+"님의 제보"));
//        is_danger.setText(String.valueOf(mark.getIsDanger()));
                marker_inform.setText(String.valueOf(mark.getMarkerInform()));

                image_url.setText(String.valueOf(mark.getImageUrl()));
                marker_category.setTextColor(Color.parseColor("#1E9615"));
                marker_category.setText(String.valueOf("카테고리  "+categoryMap.get(mark.getMarkerCategory())));
            }
        }catch(NullPointerException e){}

        marker_fix.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(parsingNickName.equals(mark.getPosterNickName())==true)
                {
                    intent3.putExtra("jwt",jwt);
                    intent3.putExtra("mark",mark);
                    startActivity(intent3);
                }else
                {
                    Toast.makeText(MarkergetActivity.this,"권한이 없습니다.",Toast.LENGTH_SHORT).show();
                }


            }
        });



//        marker_latitude.setText(String.valueOf(intent_latitude));
//       marker_longitude.setText(String.valueOf(intent_longitude));
        marker_delete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(parsingNickName.equals(mark.getPosterNickName())==true)
                {
                    JSONObject id = new JSONObject();
                    try {
                        id.put("markerId",mark.getMarkerId());
                    }catch (JSONException e){}
                    String urlString = "http://10.210.8.130:8080/api/marker/"+mark.getMarkerId();

                    try{
                        URL url = new URL(urlString);
                        HttpURLConnection connection = (HttpURLConnection) url.openConnection();
                        connection.setRequestMethod("DELETE");
                        int responseCode = connection.getResponseCode();
                        System.out.println(responseCode);
                        if(responseCode==200)
                        {
                            Toast.makeText(getApplicationContext(), "삭제완료!", Toast.LENGTH_LONG).show();
                        }else
                        {
                            Toast.makeText(getApplicationContext(), "삭제실패", Toast.LENGTH_LONG).show();
                        }
                    }
                    catch (IOException e)
                    {

                    }
                    System.out.println(mark.getMarkerId());

//

                    startActivity(intent2);

                }else
                {
                    Toast.makeText(MarkergetActivity.this,"권한이 없습니다.",Toast.LENGTH_SHORT).show();
                }


            }
        });
    }
}
