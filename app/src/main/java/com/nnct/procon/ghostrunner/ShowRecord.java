package com.nnct.procon.ghostrunner;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;

/**
 * Created by kaito on 2017/09/12.
 */

public class ShowRecord extends Activity {
    Setting set;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.show_record);
        Intent intent = this.getIntent();
        set = (Setting)intent.getSerializableExtra("file");
    }
}
