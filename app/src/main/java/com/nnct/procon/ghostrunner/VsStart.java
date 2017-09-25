package com.nnct.procon.ghostrunner;

import android.app.Activity;
import android.bluetooth.BluetoothAdapter;
import android.bluetooth.BluetoothDevice;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;

import java.util.Set;

/**
 * Created by kaito on 2017/09/15.
 */

public class VsStart extends Activity {
    final private int REQUEST_ENABLE_BT = 1;
    Setting set;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.vs_start);
        Intent i = this.getIntent();
        set = (Setting)i.getSerializableExtra("Course");
    }


    void vsStart_onClick(View view){

        Intent intent = new Intent(this,com.nnct.procon.ghostrunner.RunRecorder.class);
        intent.putExtra("file",set);
        startActivity(intent);
    }
}
