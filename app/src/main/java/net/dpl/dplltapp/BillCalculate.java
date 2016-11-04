package net.dpl.dplltapp;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import java.util.HashMap;

public class BillCalculate extends AppCompatActivity implements View.OnClickListener {
    String name, conNo, amt;
    TextView tv1, tv2, tv3, tv4,tv5,tv6,tv7,tv8;
    EditText unit;
    Button submit;
    String energyCharge, meterRent, fixedCharge, mvca, duty,totalBill;
    private static final String REGISTER_URL = "https://thedpl.in/billappws/billinfo/CalculateBill";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_bill_calculate);
        Bundle extras = getIntent().getExtras();
        name = extras.getString("name");
        conNo = extras.getString("conNo");
        tv1 = (TextView) findViewById(R.id.textView14);
        tv2 = (TextView) findViewById(R.id.textView15);
        tv3 = (TextView) findViewById(R.id.textView16);
        tv4 = (TextView) findViewById(R.id.textView17);
        tv5 = (TextView) findViewById(R.id.textView18);
        tv6 = (TextView) findViewById(R.id.textView19);
        tv7 = (TextView) findViewById(R.id.textView20);
        tv8 = (TextView) findViewById(R.id.textView21);
        tv1.setText("Consumer Name:" + name + ", Consumer Number:" + conNo);
        unit = (EditText) findViewById(R.id.editText4);
        submit = (Button) findViewById(R.id.button3);
        submit.setOnClickListener(this);


    }

    @Override
    public void onClick(View v) {
        if (v == submit) {
            try{
                double value=Double.parseDouble(unit.getText().toString());
                registerUser(conNo, unit.getText().toString());
            }catch(NumberFormatException ex){
                Toast.makeText(getApplicationContext(), "Invalid Input!", Toast.LENGTH_LONG).show();
            }

        }
        v.requestFocusFromTouch();
        tv1.requestFocus();

    }


    private void registerUser(String consumerNo, String unit) {
        register(consumerNo, unit);
    }

    private void register(String consumerNo, String unit) {
        class GetInformation extends AsyncTask<String, Void, String> {
            ProgressDialog loading;
            GetInfo ruc = new GetInfo();


            @Override
            protected void onPreExecute() {
                //adapter.clear();
                super.onPreExecute();
                loading = ProgressDialog.show(BillCalculate.this, "Please Wait", null, true, true);
            }

            @Override
            protected void onPostExecute(String s) {


                //finish();
                loading.dismiss();
                super.onPostExecute(s);
                //tv3.setText("Approximate Bill Amount for given Consumption Unit:- Rs." + s + "/-" + " (Including Energy Charge, Fixed Charge, Meter Rent & MVCA)");
                tv2.setText("Approximate Bill break-up are as under:");
                extractString(s);
                tv3.setText("Energy Charge-Rs." + energyCharge + "/-");
                tv4.setText("Meter Rent-Rs."+meterRent+"/-");
                tv5.setText("Fixed Charge-Rs."+fixedCharge+"/-");
                tv6.setText("MVCA-Rs."+mvca+"/-");
                tv7.setText("Total Amount-Rs."+totalBill+"/-");
                tv8.setText("Govt Duty-Rs."+duty+"/-");
                InputMethodManager imm = (InputMethodManager) getSystemService(Activity.INPUT_METHOD_SERVICE);
                imm.toggleSoftInput(InputMethodManager.HIDE_IMPLICIT_ONLY, 0);


       }

            @Override
            protected String doInBackground(String... params) {

                HashMap<String, String> data = new HashMap<String, String>();
                data.put("conNo", params[0]);
                data.put("unit", params[1]);
                String consumerName;
                String consumerNo = params[0];
                String meterNo = params[1];
                String result = ruc.sendPostRequest(REGISTER_URL, data);
                System.out.println("Here is my data from consumer authentication-" + result);
                return result;
            }
        }

        GetInformation ru = new GetInformation();
        ru.execute(consumerNo, unit);
    }

    private boolean isNetworkAvailable() {
        ConnectivityManager connectivityManager
                = (ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo activeNetworkInfo = connectivityManager.getActiveNetworkInfo();
        return activeNetworkInfo != null && activeNetworkInfo.isConnected();
    }


    public void extractString(String msg) {

        int i = 0;
        String[] value_split = msg.split("\\|");
        for (String string : value_split) {

            System.out.println("Response:" + string);

        }
        for (i = 0; i < value_split.length; i++) {

            if (i == 0) {
                BillCalculate.this.totalBill = value_split[i];
            } else if (i == 1) {
                BillCalculate.this.energyCharge = value_split[i];
            } else if (i == 2) {
                BillCalculate.this.meterRent = value_split[i];
            } else if (i == 3) {
                BillCalculate.this.fixedCharge = value_split[i];
            } else if (i == 4) {
                BillCalculate.this.mvca = value_split[i];
            }
            else if (i == 5) {
                BillCalculate.this.duty = value_split[i];
            }

        }

    }
}
