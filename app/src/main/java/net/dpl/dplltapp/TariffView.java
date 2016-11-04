package net.dpl.dplltapp;

import android.app.ProgressDialog;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.ListView;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;

public class TariffView extends AppCompatActivity {


    public static final String FIRST_COLUMN = "First";
    public static final String SECOND_COLUMN = "Second";
    public static final String THIRD_COLUMN = "Third";
    public static final String FOURTH_COLUMN = "Fourth";
    private static final String REGISTER_URL = "https://thedpl.in/billappws/billinfo/GetTariff";
    String conNo, conName;


    public ArrayList<HashMap<String, String>> list = new ArrayList<HashMap<String, String>>();
    ListView listView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_tariff_view);
        listView = (ListView) findViewById(R.id.listView1);
        Bundle extras = getIntent().getExtras();
        conNo = extras.getString("conNo");
        //conName = extras.getString("conName");
        this.setTitle("Tariff Details-Consumer No:" + conNo);
        //list.clear();
        populateList();
        //System.out.println("Array List Size-");
        //System.out.println("Array List Size-" + this.list.size());


    }

    private void populateList() {
        // TODO Auto-generated method stub
        class GetInformation extends AsyncTask<String, Void, String> {
            ProgressDialog loading;
            GetInfo ruc = new GetInfo();


            @Override
            protected void onPreExecute() {
                super.onPreExecute();
                loading = ProgressDialog.show(TariffView.this, "Please Wait", null, true, true);
            }

            @Override
            protected void onPostExecute(String s) {
                super.onPostExecute(s);
                ListViewAdapter adapter = new ListViewAdapter(TariffView.this, list);
                listView.setAdapter(adapter);
                loading.dismiss();

            }

            @Override
            protected String doInBackground(String... params) {

                HashMap<String, String> data = new HashMap<String, String>();
                data.put("conNo", conNo);
                //ArrayList<HashMap<String, String>> list=new ArrayList<HashMap<String, String>>() ;


                String result = ruc.sendPostRequest(REGISTER_URL, data);

                System.out.println("Here is my data-" + result);


                try {
                    JSONArray contacts = new JSONArray(result);
                    for (int i = 0; i < contacts.length(); i++) {

                        HashMap<String, String> temp = new HashMap<String, String>();
                        JSONObject c = contacts.getJSONObject(i);
                        String head = c.getString("head");
                        temp.put(FIRST_COLUMN, head);
                        String consumption = c.getString("consumption");
                        temp.put(SECOND_COLUMN, consumption);
                        String rate = c.getString("rate");
                        temp.put(THIRD_COLUMN, rate);
                        TariffView.this.list.add(temp);
                    }

                } catch (JSONException e) {
                    e.printStackTrace();
                }

                return result;
            }
        }

        GetInformation ru = new GetInformation();
        ru.execute();

    }
}
