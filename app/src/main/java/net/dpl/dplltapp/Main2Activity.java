package net.dpl.dplltapp;

import android.app.ProgressDialog;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
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

public class Main2Activity extends AppCompatActivity implements View.OnClickListener{

    private Button buttonRegister;
    ArrayList<HashMap<String, String>> oslist = new ArrayList<HashMap<String, String>>();
    ListView list;
    TextView title;
    TextView description;
    TextView url;
    TextView id;
    ArrayList<Actors> actorsList=new ArrayList<Actors>();
    ListView listView1;
    ArrayAdapter<Actors> adapter;




    //private static final String REGISTER_URL = "http://192.168.30.3/billappws/billinfo/GetFeeds";
    private static final String REGISTER_URL = "https://thedpl.in/billappws/billinfo/GetFeeds";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main2);
        this.setTitle("Data Returned By The Server");
        listView1 = (ListView) findViewById(R.id.listView1);
        Bundle extras=getIntent().getExtras();
        String name=extras.getString("name");
        registerUser();
    }

    @Override
    public void onClick(View v) {
        if(v == buttonRegister){

            //registerUser();
        }
    }

    private void registerUser() {
        String name = "SS";
        String username = "SS";
        register(name, username);
    }

    private void register(String name, String username) {
        class GetInformation extends AsyncTask<String, Void, String> {
            ProgressDialog loading;
            GetInfo ruc = new GetInfo();


            @Override
            protected void onPreExecute() {
                //adapter.clear();
                super.onPreExecute();
                loading = ProgressDialog.show(Main2Activity.this, "Please Wait",null, true, true);
            }

            @Override
            protected void onPostExecute(String s) {
                super.onPostExecute(s);
                loading.dismiss();
                //Toast.makeText(getApplicationContext(), s, Toast.LENGTH_LONG).show();

                ArrayAdapter<Actors> adapter = new ArrayAdapter<Actors>(getApplicationContext(),R.layout.listview_row,R.id.name, actorsList);
                listView1.setAdapter(adapter);



            }

            @Override
            protected String doInBackground(String... params) {

                HashMap<String, String> data = new HashMap<String,String>();
                data.put("conNo",params[0]);
                data.put("meterNo",params[1]);


                String result = ruc.sendPostRequest(REGISTER_URL,data);

                System.out.println("Here is my data-"+result);


                try{
                    JSONArray contacts = new JSONArray(result);
                        for (int i = 0; i < contacts.length(); i++)
                            {
                                Actors actors=new Actors();
                                JSONObject c = contacts.getJSONObject(i);
                                String title = c.getString("title");
                                actors.setTitle(title);
                                String url = c.getString("url");
                                actors.setUrl(url);
                                String description = c.getString("description");
                                actors.setDescription(description);
                                String id = c.getString("id");
                                actors.setId(id);
                                actorsList.add(actors);
                                System.out.println("%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%");
                                System.out.println("Data-"+actors.getDescription());
                                System.out.println("%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%");

                                Log.e("Parsed data is", "Title:" + title + "||URL:" + url + "||Description: " + description + " ||ID: " + id);


                            }

                } catch (JSONException e) {
                    e.printStackTrace();
                }

                return  result;
            }
        }

        GetInformation ru = new GetInformation();
        ru.execute(name,username);
    }


}
