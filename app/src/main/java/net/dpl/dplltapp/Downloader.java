package net.dpl.dplltapp;

import java.io.File;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;

/**
 * Created by dpl 1 on 11/3/2016.
 */
public class Downloader {

    public static void DownloadFile(String fileURL, File directory,String conNo,String billPrd) {
        try {


            System.out.println("++++++++++++++++++++++++++++Inside Downloader+++++++++++++++++++++++=");

            FileOutputStream f = new FileOutputStream(directory);
            URL u = new URL(fileURL);

            HttpURLConnection c = (HttpURLConnection) u.openConnection();
            c.setRequestMethod("POST");
            c.setDoInput(true);
            c.setDoOutput(true);
            c.connect();
           /* Uri.Builder builder = new Uri.Builder()
                    .appendQueryParameter("conNo", conNo)
                    .appendQueryParameter("billPrd", billPrd);
            String query = builder.build().getEncodedQuery();

            OutputStream os = c.getOutputStream();
            BufferedWriter writer = new BufferedWriter(
                    new OutputStreamWriter(os, "UTF-8"));
            writer.write(query);
            writer.flush();
            writer.close();
            os.close();*/


       InputStream in = c.getInputStream();

            byte[] buffer = new byte[1024];
            int len1 = 0;
            while ((len1 = in.read(buffer)) > 0) {
                f.write(buffer, 0, len1);
            }
            f.close();
        } catch (Exception e) {
            e.printStackTrace();
        }

    }
}