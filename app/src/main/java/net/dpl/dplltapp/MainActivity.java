/**
 * Created by Saptarshi Sanyal on 9/18/2015.
 */

package net.dpl.dplltapp;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import java.util.HashMap;

public class MainActivity extends AppCompatActivity implements View.OnClickListener{

    private EditText editTextName;
    private EditText editTextUsername;
    private EditText editTextPassword;
    private EditText editTextEmail;

    private Button buttonRegister;

    private static final String REGISTER_URL = "http://192.168.30.3/billappws/billinfo/post";


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        editTextName = (EditText) findViewById(R.id.editText);
        editTextUsername = (EditText) findViewById(R.id.editText2);
        //buttonRegister = (Button) findViewById(R.id.button);

        //buttonRegister.setOnClickListener(this);
        final Handler handler = new Handler();
        handler.postDelayed(new Runnable() {
            public void run() {
                // TODO: Your application init goes here.
                Intent mInHome = new Intent(MainActivity.this, Login.class);
                MainActivity.this.startActivity(mInHome);
                finish();
            }
        }, 3000);
    }

    @Override
    public void onClick(View v) {
        /*if(v == buttonRegister){
            //registerUser();

            Intent intent = new Intent(getApplicationContext(), Login.class);
            //intent.putExtra("name","saptarshi");
            startActivity(intent);
        }*/
    }

    private void registerUser() {
        String name = editTextName.getText().toString().trim().toLowerCase();
        String username = editTextUsername.getText().toString().trim().toLowerCase();
        register(name, username);
    }

    private void register(String name, String username) {
        class RegisterUser extends AsyncTask<String, Void, String>{
            ProgressDialog loading;
            RegisterUserClass ruc = new RegisterUserClass();


            @Override
            protected void onPreExecute() {
                super.onPreExecute();
                loading = ProgressDialog.show(MainActivity.this, "Please Wait",null, true, true);
            }

            @Override
            protected void onPostExecute(String s) {
                super.onPostExecute(s);
                loading.dismiss();

                Toast.makeText(getApplicationContext(),s,Toast.LENGTH_LONG).show();
            }

            @Override
            protected String doInBackground(String... params) {

                HashMap<String, String> data = new HashMap<String,String>();
                data.put("conNo",params[0]);
                data.put("meterNo",params[1]);


                String result = ruc.sendPostRequest(REGISTER_URL,data);

                return  result;
            }
        }

        RegisterUser ru = new RegisterUser();
        ru.execute(name,username);
    }
}