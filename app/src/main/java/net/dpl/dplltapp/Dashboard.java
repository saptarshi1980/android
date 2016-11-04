package net.dpl.dplltapp;

import android.app.ProgressDialog;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.TextView;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;

public class Dashboard extends AppCompatActivity {


    TextView consumerName,partyCode,category,tariff,phase,demand,mf,reference,address;

    private static final String REGISTER_URL = "https://thedpl.in/billappws/billinfo/Dashboard";

    String varConsumerName ;
    String varPartyCode;
    String varCategory;
    String varTariff;
    String varPhase ;
    String varDemand;
    String varMf;
    String varReference;
    String varAddress;
    String conNo;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_dashboard);
        consumerName=(TextView) findViewById(R.id.textView3);
        partyCode=(TextView) findViewById(R.id.textView5);
        category=(TextView) findViewById(R.id.textView8);
        tariff=(TextView) findViewById(R.id.textView9);
        phase=(TextView) findViewById(R.id.textView10);
        demand=(TextView) findViewById(R.id.textView11);
        mf=(TextView) findViewById(R.id.textView12);
        reference=(TextView) findViewById(R.id.textView13);
        address=(TextView) findViewById(R.id.textView);
        Bundle extras=getIntent().getExtras();
        conNo=extras.getString("conNo");
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
                loading = ProgressDialog.show(Dashboard.this, "Please Wait",null, true, true);
            }

            @Override
            protected void onPostExecute(String s) {
                super.onPostExecute(s);
                loading.dismiss();
                consumerName.setText("Consumer Name: " + varConsumerName);
                partyCode.setText("Party Code: "+varPartyCode);
                category.setText("Category: " + varCategory);
                tariff.setText("Tariff Type: "+varTariff);
                phase.setText("Phase: " + varPhase);
                demand.setText("Contract Demand: " + varDemand);
                mf.setText("Multiplying factor: " + varMf);
                reference.setText("Reference: " + varReference);
                address.setText("Address: "+varAddress);
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
                        varConsumerName= c.getString("consumerName");
                        varPartyCode = c.getString("partyCode");
                        varCategory = c.getString("category");
                        varTariff = c.getString("tariffType");
                        varPhase = c.getString("phase");
                        varDemand = c.getString("contractDemand");
                        varMf = c.getString("mulFactor");
                        varReference = c.getString("reference");
                        varAddress = c.getString("address");
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
}
