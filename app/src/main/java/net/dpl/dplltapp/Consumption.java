package net.dpl.dplltapp;

import android.app.ProgressDialog;
import android.graphics.Color;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.TextView;

import com.jjoe64.graphview.GraphView;
import com.jjoe64.graphview.helper.StaticLabelsFormatter;
import com.jjoe64.graphview.series.BarGraphSeries;
import com.jjoe64.graphview.series.DataPoint;

import java.util.HashMap;

public class Consumption extends AppCompatActivity {
    String name,conNo,firstUnit,secondUnit,thirdUnit,firstMonth,secondMonth,thirdMonth;


    private static final String REGISTER_URL = "https://thedpl.in/billappws/billinfo/ConPattern";
    GraphView graph;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_consumption);
        graph = (GraphView) findViewById(R.id.graph);
        TextView tv=(TextView)findViewById(R.id.textView8);
        Bundle extras=getIntent().getExtras();
        conNo=extras.getString("conNo");
        conNo=extras.getString("conNo");
        tv.setText("Consumption Pattern for Consumer No-" + conNo);
        this.setTitle("Consumption Pattern");
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
                loading = ProgressDialog.show(Consumption.this, "Please Wait",null, true, true);
            }

            @Override
            protected void onPostExecute(String s) {

                System.out.println("Response from Post Execute-"+s);
                extractString(s);
                BarGraphSeries<DataPoint> series = new BarGraphSeries<DataPoint>(new DataPoint[] {
                        new DataPoint(1,Integer.parseInt(thirdUnit)),
                        new DataPoint(2, Integer.parseInt(secondUnit)),
                        new DataPoint(3, Integer.parseInt(firstUnit)),

                });
                StaticLabelsFormatter staticLabelsFormatter = new StaticLabelsFormatter(graph);
                staticLabelsFormatter.setHorizontalLabels(new String[]{thirdMonth, secondMonth, firstMonth});
                graph.getGridLabelRenderer().setLabelFormatter(staticLabelsFormatter);
                graph.addSeries(series);
                series.setSpacing(10);
                series.setDrawValuesOnTop(true);
                series.setValuesOnTopColor(Color.GREEN);
                loading.dismiss();
                super.onPostExecute(s);

            }

            @Override
            protected String doInBackground(String... params) {

                HashMap<String, String> data = new HashMap<String,String>();
                data.put("conNo",conNo);
                String result = ruc.sendPostRequest(REGISTER_URL,data);
                System.out.println("PATTERN DATA-"+result);
                return  result;
            }
        }

        GetInformation ru = new GetInformation();
        ru.execute();
    }
    public void extractString(String msg){

        int i=0;
        String[] value_split = msg.split("\\|");
        for (String string : value_split) {

            System.out.println("Response:"+string);

        }
        for(i=0;i<value_split.length;i++){

            if(i==0){
                Consumption.this.firstMonth=value_split[i];
            }
            else if(i==1){
                Consumption.this.firstUnit=value_split[i];
            }
            else if(i==2){
                Consumption.this.secondMonth=value_split[i];
            }

            else if(i==3){
                Consumption.this.secondUnit=value_split[i];
            }
            else if(i==4){
                Consumption.this.thirdMonth=value_split[i];
            }
            else if(i==5){
                Consumption.this.thirdUnit=value_split[i];
            }

        }


        if(Consumption.this.firstMonth==null || Consumption.this.firstUnit==null ){
            Consumption.this.firstMonth="N/A";
            Consumption.this.firstUnit="0";
        }
        else if(Consumption.this.secondMonth==null || Consumption.this.secondUnit==null ){
            Consumption.this.secondMonth="N/A";
            Consumption.this.secondUnit="0";
        }
        else if(Consumption.this.thirdMonth==null || Consumption.this.thirdUnit==null ){
            Consumption.this.thirdMonth="N/A";
            Consumption.this.thirdUnit="0";
        }






    }



}
