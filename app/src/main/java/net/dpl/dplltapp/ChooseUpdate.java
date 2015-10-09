package net.dpl.dplltapp;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.RadioButton;

public class ChooseUpdate extends AppCompatActivity implements View.OnClickListener {

    RadioButton rb1;
    RadioButton rb2;
    String conNo;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_choose_update);
        Bundle extras=getIntent().getExtras();
        rb1=(RadioButton)findViewById(R.id.radioButton);
        rb2=(RadioButton)findViewById(R.id.radioButton2);
        this.setTitle("Update Email & Mobile");
        conNo = extras.getString("conNo");
        rb1.setOnClickListener(this);
        rb2.setOnClickListener(this);

    }

    @Override
    public void onClick(View v) {

        if(v == rb1){
            Intent intent = new Intent(getApplicationContext(),UpdateMobile.class);
            intent.putExtra("conNo",conNo);
            startActivity(intent);
        }
        if(v == rb2){
            Intent intent = new Intent(getApplicationContext(),UpdateEmail.class);
            intent.putExtra("conNo",conNo);
            startActivity(intent);
        }

    }
}
