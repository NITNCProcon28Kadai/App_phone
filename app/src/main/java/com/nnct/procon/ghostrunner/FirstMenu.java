package com.nnct.procon.ghostrunner;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;

/**
 * Created by kaito on 2017/08/16.
 */

public class FirstMenu extends Activity{
    @Override
    protected void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
        setContentView(R.layout.first_select);
    }

    void runMenu_onClick(View v){
        Intent runIntent = new Intent(this,com.nnct.procon.ghostrunner.ModeSelect.class);
        startActivity(runIntent);
    }

    void editMenu_onClick(View v){
        Intent editMenu = new Intent(this,com.nnct.procon.ghostrunner.CourseEdit.class);
        startActivity(editMenu);
    }

    void recordMenu_onClick(View v){
        Intent lookRecord = new Intent(this,com.nnct.procon.ghostrunner.Record.class);
        startActivity(lookRecord);
    }

}
