package com.nnct.procon.ghostrunner;

import android.app.Activity;
import android.app.AlertDialog;
import android.bluetooth.BluetoothAdapter;
import android.bluetooth.BluetoothDevice;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.KeyEvent;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import java.util.Set;

/**
 * Created by kaito on 2017/09/15.
 */

public class VsStart extends Activity {
    Setting set;
    TextView txtView;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.vs_start);
        Intent i = this.getIntent();
        set = (Setting)i.getSerializableExtra("Course");
        txtView = (TextView)findViewById(R.id.textView2);
        txtView.setText(set.courseName);
    }


    void vsStart_onClick(View view){

        Intent intent = new Intent(this,com.nnct.procon.ghostrunner.RunRecorder.class);
        intent.putExtra("file",set);
        startActivity(intent);
        VsStart.this.finish();
    }

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event){
        if(keyCode == KeyEvent.KEYCODE_BACK){
            AlertDialog.Builder dialog = new AlertDialog.Builder(VsStart.this);
            dialog.setTitle("ホームに戻りますか?");
            dialog.setPositiveButton("はい", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int which) {
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
