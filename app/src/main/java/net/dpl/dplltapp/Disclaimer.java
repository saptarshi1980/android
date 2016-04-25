package net.dpl.dplltapp;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;

public class Disclaimer extends AppCompatActivity implements  View.OnClickListener {

    String conNo;
    private Button buttonProceed;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_disclaimer);
        Bundle extras = getIntent().getExtras();
        conNo = extras.getString("conNo");
        buttonProceed = (Button) findViewById(R.id.proceed);
        buttonProceed.setOnClickListener(this);

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_disclaimer, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    public void onClick(View v) {
        if(v == buttonProceed){
            Intent intent = new Intent(getApplicationContext(),Payment.class);
            intent.putExtra("conNo",conNo);
            startActivity(intent);

        }
    }
}
