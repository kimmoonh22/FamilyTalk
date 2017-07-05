package com.example.a0104.mhtalk.Services;

import android.app.Service;
import android.content.Context;
import android.content.Intent;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.IBinder;
import android.util.Log;

import com.example.a0104.mhtalk.DB.DBHelper;

import org.apache.http.HttpResponse;
import org.apache.http.NameValuePair;
import org.apache.http.client.HttpClient;
import org.apache.http.client.ResponseHandler;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.BasicResponseHandler;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.message.BasicNameValuePair;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class GPSProvider extends Service {
    LocationManager locationManager;
    double latitude, longitude;
    String id;

    public GPSProvider() {
        // background 동작 확인, 주기 조정 필요
    }

    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }
    @Override
    public void onCreate() {
        super.onCreate();
        // 서비스에서 가장 먼저 호출됨(최초에 한번만)
        Log.d("test", "서비스의 onCreate");
        DBHelper dbHelper = new DBHelper(getApplicationContext(), "UserInfo.db", null, 1);
        id = dbHelper.getResult().get("usrid");
    }
    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        // 서비스가 호출될 때마다 실행
        Log.d("test", "서비스의 onStartCommand");
        LocationManager locationManager = (LocationManager) getSystemService(Context.LOCATION_SERVICE);
        try {
            locationManager.requestLocationUpdates(LocationManager.NETWORK_PROVIDER, 5 * 60 * 1000, 0, locationListener);
            Log.d("test", "onStartCommand : requestLocationUpdates");
        }
        catch (SecurityException e) {
            Log.d("test", "onStartCommand : SecurityException");
            e.printStackTrace();
        }

        return START_STICKY;
    }

    LocationListener locationListener = new LocationListener() {
        @Override
        public void onLocationChanged(Location location) {
            SimpleDateFormat sdfNow = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
            String time = sdfNow.format(new Date(System.currentTimeMillis()));
            latitude = location.getLatitude();
            longitude = location.getLongitude();
            Log.d("test", "onLocationChanged");
            String query = "insert into GPS_INFO(id, lat, lon, timeinfo) values ('"+id+"','"+latitude+"','"+longitude+"','"+time+"')";
            new HttpUtil().execute(query);
        }

        @Override
        public void onStatusChanged(String provider, int status, Bundle extras) {
            Log.d("test", "status changed");
        }

        @Override
        public void onProviderEnabled(String provider) {
        }

        @Override
        public void onProviderDisabled(String provider) {
        }
    };

    @Override
    public void onDestroy() {
        super.onDestroy();
        // 서비스가 종료될 때 실행
        Log.d("test", "서비스의 onDestroy");
    }

    public class HttpUtil extends AsyncTask<String, Void, Void> {
        @Override
        public Void doInBackground(String... params) {

            try{
                HttpClient httpClient = new DefaultHttpClient();
                HttpPost httpPost = new HttpPost("http://52.79.131.13/MHtalk_insert.php");

                List<NameValuePair> nameValuePairs = new ArrayList<NameValuePair>();
                String query = params[0];
                nameValuePairs.add(new BasicNameValuePair("query", query));
                httpPost.setEntity(new UrlEncodedFormEntity(nameValuePairs, "utf-8"));
                HttpResponse response = httpClient.execute(httpPost);
                ResponseHandler<String> responseHandler = new BasicResponseHandler();
                Log.d("test", "doInBackground");
            }
            catch (Exception e){
                e.printStackTrace();
                Log.d("test", "httputill exception");
            }

            return null;
        }
    }
}
