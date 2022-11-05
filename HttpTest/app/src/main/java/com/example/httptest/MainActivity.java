package com.example.httptest;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.util.Log;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);


                RequestQueue queue = Volley.newRequestQueue(MainActivity.this);
                JsonObjectRequest jsonObjectRequest = new JsonObjectRequest(Request.Method.GET, "http://localhost:8080/api/marker", null, new Response.Listener<JSONObject>() {
                    @Override
                    public void onResponse(JSONObject response) {
                        try {
                            JSONArray marker = new JSONArray();
                            for (int i=0;i<=1;i++)
                            {
                                JSONObject jsonObject = marker.getJSONObject(i);
                                String marker_id = jsonObject.getString("markerId");
                                String marker_category = jsonObject.getString("markerCategory");
                                String marker_inform = jsonObject.getString("markerInform");
                                String is_danger = jsonObject.getString("isDanger");
                                String image_url = jsonObject.getString("imageUrl");
                                String marker_longitude = jsonObject.getString("markerLongitude");
                                String marker_latitude = jsonObject.getString("markerLatitude");
                                String posterNickName = jsonObject.getString("posterNickName");

                            }
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }
                }, new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        Log.e("error","전송실패");
                    }
                }
                );
                queue.add(jsonObjectRequest);
            }
    }
