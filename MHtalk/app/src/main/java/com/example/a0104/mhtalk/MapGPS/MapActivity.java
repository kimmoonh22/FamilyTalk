package com.example.a0104.mhtalk.MapGPS;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.FrameLayout;
import android.widget.Toast;

import com.example.a0104.mhtalk.R;
import com.skp.Tmap.TMapMarkerItem;
import com.skp.Tmap.TMapPoint;
import com.skp.Tmap.TMapView;
import com.tsengvn.typekit.TypekitContextWrapper;

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;

import java.io.BufferedInputStream;
import java.net.URL;
import java.util.ArrayList;

import static com.example.a0104.mhtalk.R.drawable.kjb_marker;
import static com.example.a0104.mhtalk.R.drawable.kkt_marker;
import static com.example.a0104.mhtalk.R.drawable.kmh_marker;

public class MapActivity extends AppCompatActivity {

    private String APPKEY = "2cfca2bc-7f91-3031-b69d-3c7eed12970c";
    FrameLayout mapLayout;
    Button f, d, s;
    ArrayList<GPSPoint> list;
    ArrayList<TMapPoint> points;
    Bitmap bitmap;
    View.OnClickListener listener;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_map);

        mapLayout = (FrameLayout) findViewById(R.id.maplayout);
        f = (Button) findViewById(R.id.father_map);
        d = (Button) findViewById(R.id.daughter_map);
        s = (Button) findViewById(R.id.son_map);

        final TMapView tMapView = new TMapView(this);
        tMapView.setSKPMapApiKey(APPKEY);
        tMapView.setZoomLevel(10);

        list = new ArrayList<GPSPoint>();
        points = new ArrayList<TMapPoint>();

        new Thread() {
            public void run() {
                // thread로 웹서버 접속
                getGPSlist();
                for (int i=0; i<points.size(); i++)
                {
                    TMapMarkerItem item = new TMapMarkerItem();
                    item.setTMapPoint(points.get(i));
                    item.setVisible(TMapMarkerItem.VISIBLE);
                    getIconResource(list.get(i).getId());
                    item.setIcon(bitmap);
                    item.setPosition((float) 0.5, (float) 1.0);
                    tMapView.addMarkerItem(list.get(i).getId(), item);
                }
            }
        }.start();

        mapLayout.addView(tMapView);

        f.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                TMapMarkerItem item = tMapView.getMarkerItemFromID("kjb");
                if(item!=null) tMapView.setCenterPoint(item.longitude, item.latitude);
                else Toast.makeText(v.getContext(),"위치를 알 수 없습니다.",Toast.LENGTH_SHORT).show();
            }
        });

        d.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                TMapMarkerItem item = tMapView.getMarkerItemFromID("kmh");
                if(item!=null) tMapView.setCenterPoint(item.longitude, item.latitude);
                else Toast.makeText(v.getContext(),"위치를 알 수 없습니다.",Toast.LENGTH_SHORT).show();
            }
        });

        s.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                TMapMarkerItem item = tMapView.getMarkerItemFromID("kkt");
                if(item!=null) tMapView.setCenterPoint(item.longitude, item.latitude);
                else Toast.makeText(v.getContext(),"위치를 알 수 없습니다.",Toast.LENGTH_SHORT).show();
            }
        });
    }

    public void getIconResource(String id) {
        if (id.equals("kjb")) bitmap = BitmapFactory.decodeResource(getApplicationContext().getResources(), kjb_marker);
        else if (id.equals("kmh")) bitmap = BitmapFactory.decodeResource(getApplicationContext().getResources(), kmh_marker);
        else if (id.equals("kkt")) bitmap = BitmapFactory.decodeResource(getApplicationContext().getResources(), kkt_marker);;
    }

     public void getGPSlist() {
         try{
             BufferedInputStream reader = null;
             URL url;
             StringBuffer buffer = null;

             try {
                 url = new URL("http://52.79.131.13/MHTalk_gps.php");
                 reader = new BufferedInputStream(url.openStream());
                 buffer = new StringBuffer();
                 int i;
                 byte[] b = new byte[4096];

                 while ((i = reader.read(b)) != -1) {
                     buffer.append(new String(b, 0, i));
                 }

             }

             catch (Exception e) {
                 Log.e("ERROR : ", e.getMessage());
             }

             try {
                 JSONParser jsonParser = new JSONParser();
                 JSONObject jsonObject = (JSONObject) jsonParser.parse(buffer.toString());
                 JSONArray array = (JSONArray) jsonObject.get("result");
                 String id; double lat,lon; String time;
                 for (int i = 0; i < array.size(); i++) {
                     JSONObject entity = (JSONObject) array.get(i);
                     id = entity.get("id").toString();
                     lat = Double.parseDouble(entity.get("lat").toString());
                     lon = Double.parseDouble(entity.get("lon").toString());
                     GPSPoint point = new GPSPoint(id, lat, lon);
                     list.add(i, point);
                     points.add(i, new TMapPoint(lat, lon));
                 }
             }
             catch (Exception e)
             {
                 e.printStackTrace();
             }

         }
         catch (Exception e){
             e.printStackTrace();
         }
     }

    @Override
    protected void attachBaseContext(Context newBase) { super.attachBaseContext(TypekitContextWrapper.wrap(newBase));}
}
