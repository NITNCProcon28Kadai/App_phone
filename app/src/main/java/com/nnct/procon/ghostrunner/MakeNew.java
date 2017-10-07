package com.nnct.procon.ghostrunner;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageButton;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FilenameFilter;
import java.io.IOException;
import java.io.OutputStreamWriter;

/**
 * Created by kaito on 2017/10/01.
 */

public class MakeNew extends Activity {
    private int count;
    Setting set;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.make_new);
        set = new Setting();

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
        File dir = new File("data/data/" + getPackageName() + "/files");
        File[] files = dir.listFiles(filter);
        if (files.length == 0){
            count = 1;
        }else {
            count = files.length;
        }
    }

    void courseEnter_onClick(View view){
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
        final EditText editView = new EditText(MakeNew.this);
        AlertDialog.Builder dialog = new AlertDialog.Builder(MakeNew.this);
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
                                openFileOutput("course" + count+1 +".dat", Context.MODE_PRIVATE)));
                        writer.write(editView.getText().toString());
                        writer.newLine();
                        set.courseName = editView.getText().toString();
                        set.courseFile = "course" + count+1 ;
                        set.count = count;
                        set.mode = "vs";
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
}
