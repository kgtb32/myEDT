package com.kgtb32.myedt.model;

import android.os.AsyncTask;
import android.os.Environment;
import android.util.Log;

import java.io.File;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.net.URL;
import java.net.URLConnection;

public class Fetcher extends AsyncTask <Void, Void, Void> {
    private String URL;
    private EdtHelper helper;

    public Fetcher(String URL, EdtHelper helper){
        super();
        this.URL = URL;
        this.helper = helper;
    }

    @Override
    protected Void doInBackground(Void... voids){
        try{
            Log.v("MyEDT [Fetcher.class]", "downloading file from URL");

            this.helper.getFrag().getAct().runOnUiThread(new Thread(new Runnable() {
                @Override
                public void run() {
                    Fetcher.this.helper.getFrag().getPreLoadingDialog().resetDialog();
                    Fetcher.this.helper.getFrag().getPreLoadingDialog().show();
                }
            }));

            URL url  = new URL(this.URL);
            URLConnection connection = url.openConnection();
            connection.connect();

            InputStream is = url.openStream();

            File testDirectory = new File(Environment.getExternalStorageDirectory()+"/.myEDT/");
            if(!testDirectory.exists()){
                testDirectory.mkdir();
            }

            FileOutputStream fos = new FileOutputStream(testDirectory+"/cacheCalendar.ics");

            byte data[] = new byte[1024];


            long total = 0;
            int lenghtOfFile = connection.getContentLength();
            Log.e("le",lenghtOfFile+"");

            int count;
            while ((count=is.read(data)) != -1)
            {
                total+=count;

                fos.write(data, 0, count);
                final int progress = (int) (total*100)/lenghtOfFile;
                this.helper.getFrag().getAct().runOnUiThread(new Thread(new Runnable() {
                    @Override
                    public void run() {
                        Fetcher.this.helper.getFrag().getPreLoadingDialog().setProgress(progress);
                    }
                }));
            }

            is.close();
            fos.close();

        }catch(Exception e){
            Log.v("MyEDT [Fetcher.class]","download Error couldn't receive the file from URL");
            e.printStackTrace();
            this.helper.getFrag().getAct().runOnUiThread(new Thread(new Runnable() {
                @Override
                public void run() {
                    Fetcher.this.helper.getFrag().getPreLoadingDialog().resetDialog();
                    Fetcher.this.helper.getFrag().getPreLoadingDialog().close();
                    Fetcher.this.helper.notifyError();
                }
            }));
            this.cancel(true);
        }
        return null;
    }

    @Override
    protected void onProgressUpdate(Void... values) {
        super.onProgressUpdate(values);
        Log.e("DWProgress",values.toString());
    }

    @Override
    protected void onPostExecute(Void aVoid) {
        super.onPostExecute(aVoid);
        Log.v("MyEDT [Fetcher.class]","file downloaded from URL");
        this.helper.getFrag().getPreLoadingDialog().resetDialog();
        this.helper.getFrag().getPreLoadingDialog().close();
        this.helper.updateEdtList();
        this.helper.getFrag().setIsFirstStart(false);
        this.cancel(true);

    }
}
