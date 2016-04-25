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

public class UpdateMobile extends AppCompatActivity implements View.OnClickListener{
        EditText et;
        String mobile,conNo;
        Button submit;
        TextView tv1;

        private static final String REGISTER_URL = "http://thedpl.in/billappws/billinfo/UpdateMobile";
        private static final String REGISTER_URL_MOBILE = "http://thedpl.in/billappws/billinfo/FetchMobile";

        @Override
        protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_update_mobile);
        et=(EditText)findViewById(R.id.editText3);
        tv1=(TextView)findViewById(R.id.textView1);
        Bundle extras=getIntent().getExtras();
        conNo=extras.getString("conNo");
        this.setTitle("Update Mobile..");
        mobile=et.getText().toString();
        submit=(Button)findViewById(R.id.button2);
        submit.setOnClickListener(this);
            fetchMobile(conNo);

        }

@Override
public void onClick(View v) {
        if(v == submit){
            try{
                //int mobileInt=et.getText().toString();
                if(et.getText().toString().length()==10){
                    mobile=et.getText().toString();
                    register(conNo, mobile);
                }
                else{
                    Toast.makeText(getApplicationContext(), "Invalid Mobile Number, must be of 10 digit", Toast.LENGTH_LONG).show();
                }

            }catch(NumberFormatException ex){
                Toast.makeText(getApplicationContext(), "Invalid Mobile Number", Toast.LENGTH_LONG).show();
            }





        }
        }

private void register(String consumerNo,String mobile) {
class GetInformation extends AsyncTask<String, Void, String> {
    ProgressDialog loading;
    GetInfo ruc = new GetInfo();


    @Override
    protected void onPreExecute() {
        //adapter.clear();
        super.onPreExecute();
        loading = ProgressDialog.show(UpdateMobile.this, "Please Wait",null, true, true);
    }

    @Override
    protected void onPostExecute(String s) {


        if(s.matches("SUCCESS"))
        {
            Toast.makeText(getApplicationContext(), "Mobile Number Updated !!!!!", Toast.LENGTH_LONG).show();
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
        data.put("mobile",params[1]);
        String result = ruc.sendPostRequest(REGISTER_URL,data);
        System.out.println("Here is my data from consumer authentication-"+result);
        return  result;
    }
}

    GetInformation ru = new GetInformation();
    ru.execute(consumerNo,mobile);
        }


    private void fetchMobile(String consumerNo) {
        class GetInformation extends AsyncTask<String, Void, String> {
            ProgressDialog loading;
            GetInfo ruc = new GetInfo();


            @Override
            protected void onPreExecute() {
                //adapter.clear();
                super.onPreExecute();
                loading = ProgressDialog.show(UpdateMobile.this, "Please Wait",null, true, true);
            }

            @Override
            protected void onPostExecute(String s) {


                if(s.matches("NA"))
                {

                    tv1.setText("Currently No Mobile is registered!!");

                }

                else
                {
                    tv1.setText("Registered Mobile is:-" + s);

                }
                loading.dismiss();
                super.onPostExecute(s);

            }

            @Override
            protected String doInBackground(String... params) {

                HashMap<String, String> data = new HashMap<String,String>();
                data.put("conNo", params[0]);
                String result = ruc.sendPostRequest(REGISTER_URL_MOBILE,data);
                return  result;
            }
        }

        GetInformation ru = new GetInformation();
        ru.execute(consumerNo);
    }


}
