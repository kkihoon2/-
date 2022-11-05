package com.example.graduateproject;
import com.example.graduateproject.marker;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.NotificationCompat;

import android.app.Notification;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.graphics.drawable.BitmapDrawable;
import android.location.Location;
import android.location.LocationManager;
import android.os.Build;
import android.os.Bundle;
import android.os.Parcelable;
import android.os.StrictMode;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.google.android.gms.common.api.GoogleApi;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.CircleOptions;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.gson.JsonElement;
import com.google.gson.JsonParser;
import com.google.gson.Gson;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.Serializable;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.List;
import java.util.Map;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class MainActivity extends AppCompatActivity implements OnMapReadyCallback, GoogleMap.OnMarkerClickListener {

    private GoogleMap mMap;
    private boolean isLogin;
    private Button loginbtn;
    private Button logoutbtn;
    private Button markersend;
    private Button markerget;
    private Button showallmarker;
    long expireInCompare;
    private boolean ismarkersend = false;

    JwtInform jwt;
//    double mLatitude , mLongitude;
//    GPSListener gpsListener;
//    LocationManager manager;


    ArrayList<marker> markerList= new ArrayList<marker>();

        public String run() {
            String result = "";
            try {
                URL url = new URL("http://10.210.8.130:8080/api/marker/");
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
            return result;
        }


    private void jsonParsing(String json)
    {
        try{
            JSONArray markerArray = new JSONArray(json);
            //JSONObject jsonObject = new JSONObject(json);
            Log.e("d","여기다여기야22222");
            //JSONArray markerArray = jsonObject.getJSONArray(json);

            for(int i=0; i<markerArray.length(); i++)
            {
                JSONObject markerObject = markerArray.getJSONObject(i);

                marker marker = new marker();

                marker.setMarkerInform(markerObject.getString("markerInform"));
                marker.setMarkerLongitude(markerObject.getDouble("markerLongitude"));
                marker.setMarkerLatitude(markerObject.getDouble("markerLatitude"));
                marker.setIsDanger(markerObject.getInt("isDanger"));
                marker.setPosterNickName(markerObject.getString("posterNickName"));
                marker.setImageUrl(markerObject.getString("imageUrl"));
                marker.setMarkerCategory(markerObject.getString("markerCategory"));
                marker.setMarkerId(markerObject.getInt("markerId"));
                markerList.add(marker);
            }
        }catch (JSONException e) {
            e.printStackTrace();
        }
    }



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Intent getJwt = getIntent();

        isLogin = getIntent().getBooleanExtra("isLogin",false);
        jwt = (JwtInform) getJwt.getSerializableExtra("jwt");
        try {
            System.out.println("main jwt 확인"+jwt.getAccessToken());
        }catch(NullPointerException e)
        {
            System.out.println("main jwt token null");
        }
        long now = System.currentTimeMillis();
        Calendar cal = new GregorianCalendar();
        System.out.println(now);
        System.out.println(cal.getTimeInMillis());
        if(isLogin == true)
        {

            expireInCompare=jwt.getTokenExpiresIn();

        }
        System.out.println(cal.getTimeInMillis()+"vs"+expireInCompare);


        if(now>expireInCompare)
        {
            isLogin=false;
        }
        //markerget = findViewById(R.id.markerget);
        //markersend= findViewById(R.id.markersend);
        if (android.os.Build.VERSION.SDK_INT > 9) {
            StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder().permitAll().build();
            StrictMode.setThreadPolicy(policy);
        }

        //jsonParsing(getJsonString());
        //getJsonString();

        jsonParsing(run());


        loginbtn = findViewById(R.id.loginbtn);
        logoutbtn = findViewById(R.id.logoutbtn);
//        showallmarker=findViewById(R.id.showallmarker);
        if(isLogin == true){
            loginbtn.setVisibility(View.GONE);
            logoutbtn.setVisibility(View.VISIBLE);
        }else{
            logoutbtn.setVisibility(View.GONE);
            loginbtn.setVisibility(View.VISIBLE);
        }
        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager()
                .findFragmentById(R.id.map);
        mapFragment.getMapAsync(this);

        Intent intent = getIntent();
        ismarkersend=intent.getBooleanExtra("ismarkersend",ismarkersend);
        if(ismarkersend==true){

            marker sendmarker = (marker)intent.getSerializableExtra("sendmarker");
            markerList.add(sendmarker);
            ismarkersend=false;
        }



        /*if(ismarkersend == true){
            //marker 배열에 넣는 코드
        }*/
        loginbtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainActivity.this,LoginActivity.class);

                startActivity(intent);//액티비티 이동

            }

        });
        logoutbtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                jwt.setAccessToken(null);
                jwt.setGrantType(null);
                jwt.setTokenExpiresIn(0);
                isLogin = false;
                finish();//인텐트 종료
                overridePendingTransition(0, 0);//인텐트 효과 없애기
                Intent intent = getIntent(); //인텐트
                intent.putExtra("isLogin",isLogin);
                startActivity(intent); //액티비티 열기
                overridePendingTransition(0, 0);//인텐트 효과 없애기
            }

        });


    }


    @Override
    public void onMapReady(final GoogleMap googleMap) {
        mMap = googleMap;
//마커 생성 기본 for문

        BitmapDrawable bitmapdraw=(BitmapDrawable)getResources().getDrawable(R.drawable.safemarker);
        Bitmap b=bitmapdraw.getBitmap();
        Bitmap safemarker = Bitmap.createScaledBitmap(b, 80, 80, false);
        BitmapDrawable bitmapdraw1=(BitmapDrawable)getResources().getDrawable(R.drawable.dangermarker);
        Bitmap b1=bitmapdraw1.getBitmap();
        Bitmap dangermarker = Bitmap.createScaledBitmap(b1, 80, 80, false);
        BitmapDrawable bitmapdraw2=(BitmapDrawable)getResources().getDrawable(R.drawable.mymarker);
        Bitmap b2=bitmapdraw2.getBitmap();
        Bitmap mymarker = Bitmap.createScaledBitmap(b2, 50, 50, false);
        for (int idx = 0; idx < markerList.size() ; idx++) {
            // 1.마커 옵션 설정 (만드는 과정)
            MarkerOptions makerOptions = new MarkerOptions();
            if(markerList.get(idx).getIsDanger()==1)
            {
                makerOptions // LatLng에 대한 어레이를 만들어서 이용할 수도 있다.
                        .position(new LatLng(markerList.get(idx).getMarkerLatitude(), markerList.get(idx).getMarkerLongitude()))
                        .title("마커" + idx).icon(BitmapDescriptorFactory.fromBitmap(dangermarker)); //타이틀.
            }else{
                makerOptions // LatLng에 대한 어레이를 만들어서 이용할 수도 있다.
                        .position(new LatLng(markerList.get(idx).getMarkerLatitude(), markerList.get(idx).getMarkerLongitude()))
                        .title("마커" + idx).icon(BitmapDescriptorFactory.fromBitmap(safemarker)); //타이틀.
            }


            // 2.마커 생성 (마커를 나타냄)
            mMap.addMarker(makerOptions);
        }
        //맵 터치시 마커 생성 이벤트 구현 //
        mMap.setOnMapLongClickListener(new GoogleMap.OnMapLongClickListener() {
            @Override
            public void onMapLongClick(@NonNull LatLng point) {
                if(isLogin==true)
                {
                    MarkerOptions mOptions = new MarkerOptions();
//마커 타이틀
                    mOptions.title("마커 좌표");
                    Double latitude = point.latitude;//위도
                    Double longitude = point.longitude;//경도

                    Intent intent = new Intent(MainActivity.this,MarkersendActivity.class);
                    intent.putExtra("jwt",jwt);
                    intent.putExtra("latitude",latitude);
                    intent.putExtra("longitude",longitude);
                    intent.putExtra("marker",markerList);
                    startActivity(intent);//액티비티 이동
                }else
                {
                    Toast.makeText(getApplicationContext(), "마커생성을 위해 로그인이 필요합니다.", Toast.LENGTH_LONG).show();
                }
            }
        });
        MarkerOptions myMarker = new MarkerOptions();

        LatLng suwonUniv = new LatLng(37.2087029980, 126.9760273437);
        myMarker.position(suwonUniv).title("my").icon(BitmapDescriptorFactory.fromBitmap(mymarker));
        mMap.addMarker(myMarker);
        CircleOptions circle500 = new CircleOptions().center(suwonUniv).radius(100).strokeWidth(4f).strokeColor(Color.parseColor("#6699FF"));
        mMap.addCircle(circle500);

/* for (int i = 0; i<mark.length;i++) {
            LatLng mark2dddd = new LatLng((double)mark[i].getMarkerLatitude(),(double)mark[i].getMarkerLongitude());
            if(mark[i].getIsDanger()==1) {
                mMap.addMarker(new MarkerOptions().position(mark2dddd));
            }else{
                mMap.addMarker(new MarkerOptions().position(mark2dddd).icon(BitmapDescriptorFactory.defaultMarker(BitmapDescriptorFactory.HUE_GREEN)));
            }
        }*/



        mMap.moveCamera(CameraUpdateFactory.newLatLngZoom(suwonUniv,15));
        for(int i=0;i<markerList.size();i++)
        {
            marker markDistance = (marker)markerList.get(i);
            double distance=getDistanceByDegree(37.2087029980,126.9760273437,markDistance.getMarkerLatitude(),markDistance.getMarkerLongitude());
            if(distance<100&&markDistance.getIsDanger()==1)
            {
                alarm();

                System.out.println("알람"+distance);
            }
        }
        mMap.setOnMarkerClickListener(this);

    }

    @Override
    public boolean onMarkerClick(Marker markerClick ) {


        int count = 0;

        Intent intent = new Intent(MainActivity.this, MarkergetActivity.class);
        Double latitude = markerClick.getPosition().latitude;
        Double longitude = markerClick.getPosition().longitude;
        for (marker marker : markerList) {
            if (marker.getMarkerLongitude() == longitude && marker.getMarkerLatitude() == latitude) {
                intent.putExtra("marker", marker);
            } else {
                count++;
            }

        }
        intent.putExtra("latitude", latitude);
        intent.putExtra("longitude", longitude);
        intent.putExtra("jwt",jwt);
/*for (int i = 0; i<mark.length;i++) {
            if(mark[i].getMarkerLatitude()==latitude&&mark[i].getMarkerLongitude()==longitude)
            {intent.putExtra("mark", mark[i]);}
        }*/
        startActivity(intent);//액티비티 이동
        return true;
    }
    public double getDistanceByDegree(double latitude1,double longitude1, double latitude2, double longitude2)
    {
        Location startpos = new Location("PointA");
        Location endpos = new Location("PointA");
        startpos.setLatitude(latitude1);
        startpos.setLongitude(longitude1);
        endpos.setLatitude(latitude2);
        endpos.setLongitude(longitude2);

        double distance = startpos.distanceTo(endpos);
        return distance;

    }
    public void alarm()
    {
        NotificationManager notificationManager=(NotificationManager) getSystemService(Context.NOTIFICATION_SERVICE);

        //Notification 객체를 생성해주는 건축가객체 생성(AlertDialog 와 비슷)
        NotificationCompat.Builder builder= null;

        //Oreo 버전(API26 버전)이상에서는 알림시에 NotificationChannel 이라는 개념이 필수 구성요소가 됨.
        if(Build.VERSION.SDK_INT >= Build.VERSION_CODES.O){

            String channelID="channel_01"; //알림채널 식별자
            String channelName="MyChannel01"; //알림채널의 이름(별명)

            //알림채널 객체 만들기
            NotificationChannel channel= new NotificationChannel(channelID,channelName,NotificationManager.IMPORTANCE_DEFAULT);

            //알림매니저에게 채널 객체의 생성을 요청
            notificationManager.createNotificationChannel(channel);

            //알림건축가 객체 생성
            builder=new NotificationCompat.Builder(this, channelID);


        }else{
            //알림 건축가 객체 생성
//            builder= new NotificationCompat.Builder(this, null);
            System.out.println("실패 왜지?");
        }

        //건축가에게 원하는 알림의 설정작업
        builder.setSmallIcon(android.R.drawable.ic_menu_view);

        //상태바를 드래그하여 아래로 내리면 보이는
        //알림창(확장 상태바)의 설정
        Bitmap bm= BitmapFactory.decodeResource(getResources(),R.drawable.brand1);
        builder.setLargeIcon(bm);//매개변수가 Bitmap을 줘야한다.
        builder.setContentTitle("어린이 안전 지도");//알림창 제목
        builder.setContentText("위험지역 진입 앱에서 확인하세요");//알림창 내용

        //알림창의 큰 이미지
//        Bitmap bm= BitmapFactory.decodeResource(getResources(),R.drawable.gametitle_09);
//        builder.setLargeIcon(bm);//매개변수가 Bitmap을 줘야한다.

        //건축가에게 알림 객체 생성하도록
        Notification notification=builder.build();

        //알림매니저에게 알림(Notify) 요청
        notificationManager.notify(1, notification);

        //알림 요청시에 사용한 번호를 알림제거 할 수 있음.
        //notificationManager.cancel(1);

    }


}
