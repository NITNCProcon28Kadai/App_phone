package com.nnct.procon.ghostrunner;

import android.Manifest;
import android.app.Activity;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.location.Location;
import android.os.Bundle;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.FragmentActivity;
import android.support.v7.app.AppCompatActivity;
import android.view.KeyEvent;
import android.view.View;

import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.location.FusedLocationProviderApi;
import com.google.android.gms.location.LocationListener;
import com.google.android.gms.location.LocationRequest;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.PolylineOptions;

import java.io.BufferedWriter;
import java.io.IOException;
import java.io.OutputStreamWriter;
import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * Created by kaito on 2017/09/09.
 */

public class RunRecorder extends FragmentActivity implements OnMapReadyCallback,
    GoogleApiClient.ConnectionCallbacks,GoogleApiClient.OnConnectionFailedListener,LocationListener{
    private GoogleApiClient client;
    private LocationRequest request;
    private FusedLocationProviderApi api;
    private GoogleMap mMap;
    PolylineOptions polyline = new PolylineOptions();
    int timeCount = 0;
    int counter = 0;
    recordLatLng timePoint = new recordLatLng();
    float[] dist = new float[3];
    float totalDist = 0;
    double firstLat,firstLng,lat,lng;
    long start,end,totalTime;
    Setting set;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.running_recording);
        Intent intent = this.getIntent();
        set=(Setting)intent.getSerializableExtra("file");

        if(ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED){
            ActivityCompat.requestPermissions(this,new String[]{Manifest.permission.ACCESS_FINE_LOCATION},1);
        }

        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager().findFragmentById(R.id.runningMap);
        mapFragment.getMapAsync(this);

        request = LocationRequest.create().setPriority(LocationRequest.PRIORITY_HIGH_ACCURACY).setInterval(1000).setFastestInterval(15);
        api = LocationServices.FusedLocationApi;
        client = new GoogleApiClient.Builder(this).addApi(LocationServices.API).addConnectionCallbacks(this).addOnConnectionFailedListener(this).build();

        start = System.currentTimeMillis();
    }

    @Override
    public void onMapReady(GoogleMap googleMap){
        mMap = googleMap;
        LatLng current = new LatLng(36.678106,138.232719);

    }

    @Override
    protected void onResume(){
        super.onResume();
        if(client != null){
            client.connect();
        }
    }

    @Override
    protected void onPause(){
        super.onPause();
        if(client != null && client.isConnected()){
            api.removeLocationUpdates(client,this);
        }
        client.disconnect();
        BufferedWriter writer = null;
        BufferedWriter allDist = null;
        BufferedWriter aveDist = null;
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy年MM月dd日");

        //今まで走った距離をまとめる
        try{
            allDist = new BufferedWriter(
                    new OutputStreamWriter(
                            openFileOutput("allDistance.dat",Context.MODE_APPEND)));
            allDist.write(Double.toString(totalDist));
            allDist.newLine();
        }catch (IOException e){
            e.printStackTrace();
        }
        finally {
            try{
                if(allDist != null){
                    allDist.close();
                }
            }catch (IOException e){
                e.printStackTrace();
            }
        }
        //日付の情報を保存
        try{
            aveDist = new BufferedWriter(
                    new OutputStreamWriter(
                            openFileOutput("dateLog.dat",Context.MODE_APPEND)));
            aveDist.write(sdf.format(new Date()));
            aveDist.newLine();
        }catch (IOException e){
            e.printStackTrace();
        }
        finally {
            try{
                if(aveDist != null){
                    aveDist.close();
                }
            }catch (IOException e){
                e.printStackTrace();
            }
        }
        //記録保存
        try{
            writer = new BufferedWriter(
                    new OutputStreamWriter(
                            openFileOutput(set.courseFile + ".dat", Context.MODE_APPEND)));
            writer.write(Long.toString(totalTime));//時間
            writer.newLine();

        }catch (IOException e){
            e.printStackTrace();
        }
        finally {
            try{
                if(writer != null){
                    writer.close();
                }
            }catch (IOException e){
                e.printStackTrace();
            }
        }
        //経路保存
        try{
            writer = new BufferedWriter(
                    new OutputStreamWriter(
                            openFileOutput("log" + set.count + ".dat",Context.MODE_PRIVATE)));
            for(int i=0;i<counter;i++){ //緯度経度を記録
                lat = timePoint.getLat(i);
                writer.write(Double.toString(lat) + " ");
                lng = timePoint.getLng(i);
                writer.write(Double.toString(lng));
                writer.newLine();
            }
        }catch (IOException e){
            e.printStackTrace();
        }
        finally {
            try{
                if(writer != null){
                    writer.close();
                }
            }catch (IOException e){
                e.printStackTrace();
            }
        }
    }



    @Override
    public void onConnected(Bundle bundle){
        if(ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED){
            return;
        }
        api.requestLocationUpdates(client,request,this);
    }

    @Override
    public void onConnectionSuspended(int i){

    }

    @Override
    public void onConnectionFailed(ConnectionResult connectionResult){

    }

    @Override
    public void onLocationChanged(Location location){
        if(mMap == null){
            return ;
        }
        LatLng current = new LatLng(
                location.getLatitude(),location.getLongitude());
        polyline.add(current);
        timeCount++;
        if(timeCount % 5 == 0){
            timePoint.setLat(location.getLatitude(),counter);
            timePoint.setLng(location.getLongitude(),counter);

            if(counter > 0 ){
                Location.distanceBetween(timePoint.getLat(counter-1),timePoint.getLng(counter-1),
                        timePoint.getLat(counter),timePoint.getLng(counter),dist);
                totalDist += dist[0];
            }else{
                firstLat = location.getLatitude();
                firstLng = location.getLongitude();
            }
            counter++;
        }



        mMap.animateCamera(CameraUpdateFactory.newLatLngZoom(current,1.6f));
    }

    void runFinish_onClick(View view){
        Intent intent = new Intent(this,com.nnct.procon.ghostrunner.ShowRecord.class);
        end = System.currentTimeMillis();
        totalTime = (end - start)*1000 /60;
        set.dist = totalDist;
        set.time = totalTime;
        intent.putExtra("file",set);
        startActivity(intent);
        //RunRecorder.this.finish();
    }

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event){
        if(keyCode == KeyEvent.KEYCODE_BACK){
            AlertDialog.Builder dialog = new AlertDialog.Builder(RunRecorder.this);
            dialog.setTitle("終了しますか?");
            dialog.setPositiveButton("はい", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int which) {
                    deleteFile(set.courseFile + ".dat");
                    deleteFile("log" + set.count + ".dat");
                    finish();
                }});
            dialog.setNegativeButton("キャンセル", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int which) {
                    dialog.cancel();
                }
            });
            dialog.show();

            return true;
        }
        return false;
    }

}
