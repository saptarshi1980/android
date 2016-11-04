package net.dpl.dplltapp;

import android.app.ProgressDialog;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;


/**
 * Created by dpl 1 on 9/19/2015.
 *
 *
 */

public class BillHistory extends AppCompatActivity implements View.OnClickListener{

    private Button buttonRegister;
    ArrayList<HashMap<String, String>> oslist = new ArrayList<HashMap<String, String>>();
    ListView list;
    TextView title;
    TextView description;
    TextView url;
    TextView id;
    ArrayList<ModelBillHistory> actorsList=new ArrayList<>();
    ListView listView1;
    ArrayAdapter<Actors> adapter;
    TextView tv;




    //private static final String REGISTER_URL = "http://192.168.30.3/billappws/billinfo/GetFeeds";
    private static final String REGISTER_URL = "https://thedpl.in/billappws/billinfo/BillHistory";
    String conNo,name;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_bill_history);
        listView1 = (ListView) findViewById(R.id.listView1);
        tv=(TextView)findViewById(R.id.textView6);
        Bundle extras=getIntent().getExtras();
        conNo=extras.getString("conNo");
        name=extras.getString("name");

        this.setTitle("Bill History-Con No:"+conNo);
        tv.setText("Consumer Name: "+name);
        registerUser();
    }

    @Override
    public void onClick(View v) {
        if(v == buttonRegister){

            //registerUser();
        }
    }

    private void registerUser() {
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
                loading = ProgressDialog.show(BillHistory.this, "Please Wait",null, true, true);
            }

            @Override
            protected void onPostExecute(String s) {
                super.onPostExecute(s);
                loading.dismiss();
                //Toast.makeText(getApplicationContext(), s, Toast.LENGTH_LONG).show();

                ArrayAdapter<ModelBillHistory> adapter = new ArrayAdapter<ModelBillHistory>(getApplicationContext(),R.layout.listview_row,R.id.name, actorsList);
                listView1.setAdapter(adapter);



            }

            @Override
            protected String doInBackground(String... params) {

                HashMap<String, String> data = new HashMap<String,String>();
                data.put("conNo",conNo);



                String result = ruc.sendPostRequest(REGISTER_URL,data);

                System.out.println("Here is my data-"+result);


                try{
                    JSONArray contacts = new JSONArray(result);
                    for (int i = 0; i < contacts.length(); i++)
                    {
                        ModelBillHistory modelBillHistory=new ModelBillHistory();
                        JSONObject c = contacts.getJSONObject(i);
                        String conNo = c.getString("conNo");
                        modelBillHistory.setConNo(conNo);
                        String partyCode = c.getString("partyCode");
                        modelBillHistory.setPartyCode(partyCode);
                        String name = c.getString("name");
                        modelBillHistory.setName(name);
                        String month = c.getString("billMonth");
                        modelBillHistory.setBillMonth(month);
                        String lastRead = c.getString("lastRead");
                        modelBillHistory.setLastRead(lastRead);
                        String currRead = c.getString("currRead");
                        modelBillHistory.setCurrRead(currRead);
                        String mf = c.getString("mf");
                        modelBillHistory.setCurrRead(currRead);
                        String unit = c.getString("unit");
                        modelBillHistory.setUnit(unit);
                        String meterStatus = c.getString("meterStatus");
                        modelBillHistory.setMeterStatus(meterStatus);
                        String billAmount = c.getString("billAmount");
                        modelBillHistory.setBillAmount(billAmount);
                        String dueDate1 = c.getString("dueDate1");
                        modelBillHistory.setDueDate1(dueDate1);
                        String dueDate2 = c.getString("dueDate2");
                        modelBillHistory.setDueDate2(dueDate2);
                        actorsList.add(modelBillHistory);
                        System.out.println("%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%");
                        //System.out.println("Data-"+actors.getDescription());
                        System.out.println("%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%");

                        //Log.e("Parsed data is", "Title:" + title + "||URL:" + url + "||Description: " + description + " ||ID: " + id);


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
