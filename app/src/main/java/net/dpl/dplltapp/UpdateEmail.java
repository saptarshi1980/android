package net.dpl.dplltapp;

import android.app.ProgressDialog;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import java.util.HashMap;

public class UpdateEmail extends AppCompatActivity implements View.OnClickListener{
    EditText et;
    String emailId,conNo,existingmail;
    TextView tv1;
    Button submit;
    private static final String REGISTER_URL = "http://thedpl.in/billappws/billinfo/UpdateEmail";
    private static final String REGISTER_URL_EMAIL = "http://thedpl.in/billappws/billinfo/FetchEmail";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_update_email);
        et=(EditText)findViewById(R.id.editText3);
        tv1=(TextView)findViewById(R.id.textView1);
        Bundle extras=getIntent().getExtras();
        conNo=extras.getString("conNo");
        this.setTitle("Update Email..");
        emailId=et.getText().toString();
        submit=(Button)findViewById(R.id.button2);
        submit.setOnClickListener(this);
        fetchEmail(conNo);


    }

    @Override
    public void onClick(View v) {
        if(v == submit){
            if (new EmailValidator().validate(emailId=et.getText().toString())){
                emailId=et.getText().toString();
                register(conNo, emailId);
            }

            else{
                Toast.makeText(getApplicationContext(), "Invalid Email id", Toast.LENGTH_LONG).show();

            }


        }
    }

    private void register(String consumerNo,String emailId) {
        class GetInformation extends AsyncTask<String, Void, String> {
            ProgressDialog loading;
            GetInfo ruc = new GetInfo();


            @Override
            protected void onPreExecute() {
                //adapter.clear();
                super.onPreExecute();
                loading = ProgressDialog.show(UpdateEmail.this, "Please Wait",null, true, true);
            }

            @Override
            protected void onPostExecute(String s) {


                if(s.matches("SUCCESS"))
                {
                    Toast.makeText(getApplicationContext(), "Email Id Updated !!!!!", Toast.LENGTH_LONG).show();
                    et.setText("");

                }

                else
                {
                    Toast.makeText(getApplicationContext(), "Internal Server Error", Toast.LENGTH_LONG).show();

                }
                loading.dismiss();
                super.onPostExecute(s);

            }

            @Override
            protected String doInBackground(String... params) {

                HashMap<String, String> data = new HashMap<String,String>();
                data.put("conNo",params[0]);
                data.put("emailId",params[1]);
                String result = ruc.sendPostRequest(REGISTER_URL,data);
                System.out.println("Here is my data from consumer authentication-"+result);
                return  result;
            }
        }

        GetInformation ru = new GetInformation();
        ru.execute(consumerNo,emailId);
    }




    private void fetchEmail(String consumerNo) {
        class GetInformation extends AsyncTask<String, Void, String> {
            ProgressDialog loading;
            GetInfo ruc = new GetInfo();


            @Override
            protected void onPreExecute() {
                //adapter.clear();
                super.onPreExecute();
                loading = ProgressDialog.show(UpdateEmail.this, "Please Wait",null, true, true);
            }

            @Override
            protected void onPostExecute(String s) {


                if(s.matches("NA"))
                {

                    tv1.setText("Currently No Email Id is registered!!");

                }

                else
                {
                    tv1.setText("Registered Email Id is:-"+s);

                }
                loading.dismiss();
                super.onPostExecute(s);

            }

            @Override
            protected String doInBackground(String... params) {

                HashMap<String, String> data = new HashMap<String,String>();
                data.put("conNo", params[0]);
                String result = ruc.sendPostRequest(REGISTER_URL_EMAIL,data);
                return  result;
            }
        }

        GetInformation ru = new GetInformation();
        ru.execute(consumerNo);
    }






}
