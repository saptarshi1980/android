package net.dpl.dplltapp;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.ActivityNotFoundException;
import android.content.Intent;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Environment;
import android.view.Menu;
import android.view.View;
import android.widget.Toast;
import java.util.concurrent.TimeUnit;
import java.io.File;
import java.io.IOException;

public class DownloadBill extends Activity {

    String varPartyCode,conNo;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_download_bill);
        Bundle extras=getIntent().getExtras();
        conNo=extras.getString("conNo");
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.main, menu);
        return true;
    }

    public void download(View v) throws InterruptedException {
        new DownloadFile().execute("https://thedpl.in/billreport/" + conNo + ".pdf", conNo + ".pdf");
        TimeUnit.SECONDS.sleep(10);
        Toast.makeText(DownloadBill.this, "Bill has been downloaded, click on View", Toast.LENGTH_LONG).show();

    }

    public void view(View v)
    {
        File pdfFile = new File(Environment.getExternalStorageDirectory() + "/testthreepdf/" + conNo+".pdf");  // -> filename = maven.pdf
        Uri path = Uri.fromFile(pdfFile);
        Intent pdfIntent = new Intent(Intent.ACTION_VIEW);
        pdfIntent.setDataAndType(path, "application/pdf");
        pdfIntent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);

        try{
            startActivity(pdfIntent);
        }catch(ActivityNotFoundException e){
            Toast.makeText(DownloadBill.this, "No Application available to view PDF", Toast.LENGTH_SHORT).show();
        }
    }

    private class DownloadFile extends AsyncTask<String, Void, Void>{

        ProgressDialog loading;
        @Override
        protected void onPreExecute() {
            //adapter.clear();
            super.onPreExecute();

        }

        @Override
        protected Void doInBackground(String... strings) {

            String fileUrl = strings[0];   // -> http://maven.apache.org/maven-1.x/maven.pdf
            String fileName = strings[1];  // -> maven.pdf
            String extStorageDirectory = Environment.getExternalStorageDirectory().toString();
            File folder = new File(extStorageDirectory, "testthreepdf");
            folder.mkdir();

            File pdfFile = new File(folder, fileName);

            try{
                pdfFile.createNewFile();

            }catch (IOException e){
                e.printStackTrace();
            }
            FileDownloader.downloadFile(fileUrl, pdfFile);
            return null;
        }
    }



}