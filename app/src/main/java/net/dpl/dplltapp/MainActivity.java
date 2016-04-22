/**
 * Created by Saptarshi Sanyal on 9/18/2015.
 */

package net.dpl.dplltapp;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        final Handler handler = new Handler();
        handler.postDelayed(new Runnable() {
            public void run() {
                // TODO: Your application init goes here.
                Intent mInHome = new Intent(MainActivity.this, Login.class);
                MainActivity.this.startActivity(mInHome);
                finish();
            }
        }, 2000);
    }
}

