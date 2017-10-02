package com.nnct.procon.ghostrunner;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;

/**
 * Created by kaito on 2017/10/01.
 */

public class CourseEdit extends Activity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.course_edit);
    }

    void new_onClick(View v){
        Intent makeIntent = new Intent(this,com.nnct.procon.ghostrunner.MakeNew.class);
        startActivity(makeIntent);
    }

    void delete_onClick(View v){
        Intent deleteIntent = new Intent(this,com.nnct.procon.ghostrunner.DeleteCourse.class);
        startActivity(deleteIntent);
    }
}
