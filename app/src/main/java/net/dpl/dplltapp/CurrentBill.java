package net.dpl.dplltapp;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;

public class CurrentBill extends AppCompatActivity implements View.OnClickListener {
    String varPartyCode,conNo,name,varBillPeriod,varPrevReading,varCurrReading,varMulFactor,varMeterStatus,varUnitPerMonth,varBillPerMonth,varDueDate1,varDueDate2;
    ArrayList<Consumer> consumerList=new ArrayList<Consumer>();
    ListView listView1;
    TextView conName,billPeriod,previousReading,currentReading,mulFactor,meterStatus,unitPerMonth,billPerMonth,dueDate1,dueDate2;
    private static final String REGISTER_URL = "https://thedpl.in/billappws/billinfo/CurrBill";
    private static final String REGISTER_URL_BILL = "https://thedpl.in/billappws/billinfo/DownloadBill";

    ImageView iv1;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_current_bill);
        this.setTitle("DPL- Current Bill");
        Bundle extras=getIntent().getExtras();
        conNo=extras.getString("conNo");
        name=extras.getString("conName");
        conName=(TextView)findViewById(R.id.textView3);
        conName.setText("Consumer Name: "+name);
        iv1=(ImageView)findViewById(R.id.imageView6);
        iv1.setOnClickListener(this);
        billPeriod=(TextView)findViewById(R.id.textView5);
        previousReading=(TextView)findViewById(R.id.textView8);
        currentReading=(TextView)findViewById(R.id.textView9);
        mulFactor=(TextView)findViewById(R.id.textView10);
        meterStatus=(TextView)findViewById(R.id.textView11);
        unitPerMonth=(TextView)findViewById(R.id.textView12);
        billPerMonth=(TextView)findViewById(R.id.textView13);
        dueDate1=(TextView)findViewById(R.id.textView);
        dueDate2=(TextView)findViewById(R.id.textViewdue);
        register();
    }

    private void register() {
        class GetInformation extends AsyncTask<String, Void, String> {
            ProgressDialog loading;
            GetInfo ruc = new GetInfo();


            @Override
            protected void onPreExecute() {
                //adapter.clear();
                super.onPreExecute();
                loading = ProgressDialog.show(CurrentBill.this, "Please Wait",null, true, true);
            }

            @Override
            protected void onPostExecute(String s) {
                super.onPostExecute(s);
                loading.dismiss();
                billPeriod.setText("Bill Period: " + varBillPeriod);
                previousReading.setText("Previous Reading: " + varPrevReading);
                currentReading.setText("Current Reading: " + varCurrReading);
                mulFactor.setText("Multiplying Factor: " + varMulFactor);
                meterStatus.setText("Meter Status: " + varMeterStatus);
                unitPerMonth.setText("Units Consumed / Month: " + varUnitPerMonth);
                billPerMonth.setText("Bill Amount / Month: Rs" + varBillPerMonth);
                dueDate1.setText("1st Due Date: " + varDueDate1);
                dueDate2.setText("2nd Due Date: "+varDueDate2);

                System.out.println("Result-"+s+" / Length--"+s.length());
                System.out.println("%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%");

                if(s.length()<4){
                    Toast.makeText(getApplicationContext(), "Billing Record Does Not Exist For This Consumer Number", Toast.LENGTH_LONG).show();
                }

            }

            @Override
            protected String doInBackground(String... params) {

                HashMap<String, String> data = new HashMap<String,String>();
                data.put("conNo", conNo);
                String result = ruc.sendPostRequest(REGISTER_URL,data);
                try{
                    JSONArray contacts = new JSONArray(result);
                    for (int i = 0; i < contacts.length(); i++)
                    {

                        JSONObject c = contacts.getJSONObject(i);
                        varBillPeriod = c.getString("month");
                        varPrevReading = c.getString("prevReading");
                        varCurrReading = c.getString("currReading");
                        varMeterStatus = c.getString("meterStatus");
                        varMulFactor = c.getString("mulFactor");
                        varUnitPerMonth = c.getString("unit");
                        varBillPerMonth = c.getString("amountDue");
                        varDueDate1 = c.getString("due1");
                        varDueDate2 = c.getString("due2");

                    }

                } catch (JSONException e) {
                    e.printStackTrace();
                }

                return  result;
            }
        }

        GetInformation ru = new GetInformation();
        ru.execute();
    }

    @Override
    public void onClick(View v) {

        /*if(v == iv1){






        }

*/

        Intent intent = new Intent(getApplicationContext(),DownloadBill.class);
        intent.putExtra("conNo",conNo);
        intent.putExtra("billPrd",varBillPeriod);
        startActivity(intent);

    }



}
