package com.nnct.procon.ghostrunner;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.TextView;

import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.android.gms.vision.text.Text;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.util.ArrayList;

/**
 * Created by kaito on 2017/08/23.
 * 09/21 ファイル読み込み&フリックイベントでファイル変動
 */

public class SetCourse extends FragmentActivity implements OnMapReadyCallback {
    private GoogleMap mMap;
    String line,logLine;
    Setting set;
    File dir ;
    int fileCount,listCount=0;
    ArrayList<String> courseList;
    BufferedReader reader = null,logReader = null;
    double lat,lng;
    String[] str;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        /*dir = new File("/data/data/" + getPackageName() + "/files");
        final File[] fileList = dir.listFiles();
        fileCount = fileList.length;*/
        courseList = new ArrayList<>();
        //ファイルの有無
        File file = new File("/data/data/"+getPackageName()+"/files/course1.dat");
        boolean isExists = file.exists();
        if (isExists == true) {
            setContentView(R.layout.map_select);
            /*try{
                reader = new BufferedReader(
                        new InputStreamReader(openFileInput("course1.dat")));
                set.count = 1;
                while((line = reader.readLine()) != null){
                    courseList.add(line);
                    listCount++;
                }
                TextView textView = (TextView)findViewById(R.id.textView);
                textView.setText(courseList.get(0));
                set.time = Long.parseLong(courseList.get(listCount - 2));
                set.dist = Double.parseDouble(courseList.get(listCount - 1));
            }catch (IOException e){
                e.printStackTrace();
            }
            finally {
                try{
                    if(reader != null){
                        reader.close();
                    }
                }catch (IOException e){
                    e.printStackTrace();
                }
            }
            try{
                logReader = new BufferedReader(
                        new InputStreamReader(openFileInput("log1.dat")));
                logLine = logReader.readLine();
                str = logLine.split(" ",0);
                lat = Double.parseDouble(str[0]);
                lng = Double.parseDouble(str[1]);
            }catch (IOException e){
                e.printStackTrace();
            }
            finally {
                try{
                    if(logReader != null){
                        logReader.close();
                    }
                }catch (IOException e){
                    e.printStackTrace();
                }
            }*/
        }else{
            setContentView(R.layout.map_unselect);
        }

        Intent i = this.getIntent();
        set = (Setting)i.getSerializableExtra("Mode");

    }

    @Override
    protected  void onStart(){
        super.onStart();

        try{
            reader = new BufferedReader(
                    new InputStreamReader(openFileInput("course1.dat")));
            set.count = 1;
            while((line = reader.readLine()) != null){
                courseList.add(line);
                listCount++;
            }
            TextView textView = (TextView)findViewById(R.id.textView);
            textView.setText(courseList.get(0));
            set.time = Long.parseLong(courseList.get(listCount - 2));
            set.dist = Double.parseDouble(courseList.get(listCount - 1));
        }catch (IOException e){
            e.printStackTrace();
        }
        finally {
            try{
                if(reader != null){
                    reader.close();
                }
            }catch (IOException e){
                e.printStackTrace();
            }
        }
        try{
            logReader = new BufferedReader(
                    new InputStreamReader(openFileInput("log1.dat")));
            logLine = logReader.readLine();
            str = logLine.split(" ",0);
            lat = Double.parseDouble(str[0]);
            lng = Double.parseDouble(str[1]);
        }catch (IOException e){
            e.printStackTrace();
        }
        finally {
            try{
                if(logReader != null){
                    logReader.close();
                }
            }catch (IOException e){
                e.printStackTrace();
            }
        }
    }

    @Override
    public void onMapReady(GoogleMap gmap){
        mMap = gmap;
        mMap.getUiSettings().setZoomControlsEnabled(true);
        LatLng nowPos = new LatLng(lat,lng);
        mMap.addMarker(new MarkerOptions().position(nowPos));
        mMap.moveCamera(CameraUpdateFactory.newLatLngZoom(nowPos,5));
    }

    void CourseEnter_onClick(View view){
        if(set.courseFile == null){
            ImageButton imgBtn = (ImageButton)findViewById(R.id.goNext);
            imgBtn.setEnabled(false);
            imgBtn.setColorFilter(0xaa808080);
        }else{
            ImageButton imgBtn = (ImageButton)findViewById(R.id.goNext);
            imgBtn.setEnabled(true);
        }
        Intent intent;
        if(set.mode.equals("vs")){
            intent = new Intent(this,com.nnct.procon.ghostrunner.VsStart.class);
        }else {
            intent = new Intent(this, com.nnct.procon.ghostrunner.MethodSelect.class);
        }
        intent.putExtra("Course",set);
        startActivity(intent);
    }

    void courseMake_onClick(View view){
        Log.d("Touch_confirm","タッチを検出しました.");
        final EditText editView = new EditText(SetCourse.this);
        AlertDialog.Builder dialog = new AlertDialog.Builder(SetCourse.this);
        dialog.setTitle("コース名を入力してください");
        dialog.setView(editView);

        dialog.setPositiveButton("OK", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                if(editView != null){
                    BufferedWriter writer = null;
                    try{
                        writer = new BufferedWriter(new OutputStreamWriter(
                                openFileOutput("course1.dat", Context.MODE_PRIVATE)));
                        writer.write(editView.getText().toString());
                        writer.newLine();
                        set.courseFile = "course1" ;
                        set.count = 1;
                    }catch (IOException e){
                        e.printStackTrace();
                    }finally {
                        try{
                            if(writer != null){
                                writer.close();

                            }
                        }catch (IOException e){
                            e.printStackTrace();
                        }
                    }
                }
            }
        });

        dialog.setNegativeButton("キャンセル", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                dialog.cancel();
            }
        });
    }

}
