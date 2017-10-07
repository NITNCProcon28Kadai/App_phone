package com.nnct.procon.ghostrunner;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.util.Log;
import android.view.GestureDetector;
import android.view.MotionEvent;
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
import java.io.FilenameFilter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.util.ArrayList;

import static com.nnct.procon.ghostrunner.R.id.textView2;

/**
 * Created by kaito on 2017/08/23.
 * 09/21 ファイル読み込み&フリックイベントでファイル変動
 */

public class SetCourse extends FragmentActivity implements OnMapReadyCallback {
    private GoogleMap mMap;
    String line,logLine,logCount;
    Setting set;
    File dir;
    int fileCount,listCount=0;//ファイルの行数カウント
    ArrayList<String> courseList;
    BufferedReader reader = null,logReader = null;
    double lat,lng;
    String[] str;
    int charCount;
    File[] fileList;
    // タッチイベントを処理するためのインタフェース
    private GestureDetector mGestureDetector;
    // X軸最低スワイプ距離
    private static final int SWIPE_MIN_DISTANCE = 50;

    // X軸最低スワイプスピード
    private static final int SWIPE_THRESHOLD_VELOCITY = 200;

    private String path,fileName;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        set = new Setting();
        courseList = new ArrayList<String>();
        set.count = 0;
        //フィルタを作成する
        FilenameFilter filter = new FilenameFilter() {

            public boolean accept(File file, String str){

                //指定文字列でフィルタする
                //indexOfは指定した文字列が見つからなかったら-1を返す
                if (str.indexOf("course")  != -1){
                    return true;
                }else{
                    return false;
                }
            }
        };
        path = "data/data/" + getPackageName() +"/files";
        dir = new File(path);
        fileList = dir.listFiles(filter);
        //ファイルの有無
        if (fileList.length == 0) {
            setContentView(R.layout.map_unselect);
        }else{
            setContentView(R.layout.map_select);
        }

        Intent i = this.getIntent();
        set = (Setting)i.getSerializableExtra("Mode");

    }

    @Override
    protected void onStart(){
        super.onStart();
        if (fileList.length != 0) {
            fileCount = fileList.length;//存在するファイルの数
            charCount = fileList[set.count].toString().length();
            fileName = fileList[set.count].toString().substring(44,charCount);//パスを除いたファイル名
            logCount = path.replaceAll("[^0-9]","");//ログファイルに対応する数字
            if (!(fileList.equals(null))) {
                try {
                    reader = new BufferedReader(
                            new InputStreamReader(openFileInput(fileName)));
                    while ((line = reader.readLine()) != null) {
                        courseList.add(line);
                        listCount++;
                    }
                    TextView textView = (TextView) findViewById(R.id.textView);
                    textView.setText(courseList.get(0));
                    set.time = Long.parseLong(courseList.get(listCount - 2));
                    set.dist = Double.parseDouble(courseList.get(listCount - 1));
                } catch (IOException e) {
                    e.printStackTrace();
                } finally {
                    try {
                        if (reader != null) {
                            reader.close();
                        }
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }
                try {
                    logReader = new BufferedReader(
                            new InputStreamReader(openFileInput("log" + logCount + ".dat")));
                    logLine = logReader.readLine();
                    str = logLine.split(" ", 0);
                    lat = Double.parseDouble(str[0]);
                    lng = Double.parseDouble(str[1]);
                } catch (IOException e) {
                    e.printStackTrace();
                } finally {
                    try {
                        if (logReader != null) {
                            logReader.close();
                        }
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }
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

    void courseEnter_onClick(View view){
        /*if(set.courseFile == null){
            ImageButton imgBtn = (ImageButton)findViewById(R.id.goNext);
            imgBtn.setEnabled(false);
            imgBtn.setColorFilter(0xaa808080);
        }else{
            ImageButton imgBtn = (ImageButton)findViewById(R.id.goNext);
            imgBtn.setEnabled(true);
        }*/
        Intent intent;
        if(set.mode.equals("vs")){
            intent = new Intent(this,com.nnct.procon.ghostrunner.VsStart.class);
        }else {
            intent = new Intent(this, com.nnct.procon.ghostrunner.MethodSelect.class);
        }
        intent.putExtra("Course",set);
        startActivity(intent);
        SetCourse.this.finish();
    }

    void courseMake_onClick(View view){
        final EditText editView = new EditText(SetCourse.this);
        AlertDialog.Builder dialog = new AlertDialog.Builder(SetCourse.this);
        dialog.setTitle("コース名を入力してください");
        dialog.setView(editView);
        Log.d("Touch_confirm","タッチを検出しました.");
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
                        set.courseName = editView.getText().toString();
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
        dialog.show();
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        return mGestureDetector.onTouchEvent(event);
    }

    private final GestureDetector.SimpleOnGestureListener mOnGestureListener = new GestureDetector.SimpleOnGestureListener() {

        // フリックイベント
        @Override
        public boolean onFling(MotionEvent event1, MotionEvent event2, float velocityX, float velocityY) {

            try {

                // 移動距離・スピードを出力
                float distance_x = Math.abs((event1.getX() - event2.getX()));
                float velocity_x = Math.abs(velocityX);

                if(fileCount > 1) {
                    // 開始位置から終了位置の移動距離が指定値より大きい
                    // X軸の移動速度が指定値より大きい
                    if (event1.getX() - event2.getX() > SWIPE_MIN_DISTANCE && Math.abs(velocityX) > SWIPE_THRESHOLD_VELOCITY) {
                        if(set.count > fileCount-1){
                            set.count = 0;
                            onNewIntent(getIntent());
                        }else{
                            set.count++;
                            onNewIntent(getIntent());
                        }

                    }
                    // 終了位置から開始位置の移動距離が指定値より大きい
                    // X軸の移動速度が指定値より大きい
                    else if (event2.getX() - event1.getX() > SWIPE_MIN_DISTANCE && Math.abs(velocityX) > SWIPE_THRESHOLD_VELOCITY) {
                        if(set.count < 0){
                            set.count = fileCount - 1;
                            onNewIntent(getIntent());
                        }else{
                            set.count--;
                            onNewIntent(getIntent());
                        }
                    }
                }

            } catch (Exception e) {
                e.printStackTrace();
            }

            return false;
        }
    };


}
