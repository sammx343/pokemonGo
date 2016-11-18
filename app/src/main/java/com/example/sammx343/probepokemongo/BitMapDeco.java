package com.example.sammx343.probepokemongo;

/**
 * Created by Sammx343 on 16/11/2016.
 */

import android.app.ProgressDialog;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.AsyncTask;
import android.util.Log;

import java.io.IOException;
import java.io.InputStream;
import java.net.URL;
import java.net.URLConnection;
import java.util.List;

/**
 * Created by admin on 26/10/16.
 */

public class BitMapDeco extends AsyncTask<Void,Void,Bitmap> {
    private String TAG = "Img";
    private String URL;
    private Bitmap img;
    ProgressDialog pDialog;

    public BitMapDeco(ProgressDialog pDialog, String URL) {
        this.URL = URL;
        this.pDialog = pDialog;


    }

    @Override
    protected Bitmap doInBackground(Void... voids) {
        Bitmap data = getData(URL);
        if(data!=null){
            return data;

        }
        else{
            Log.d(TAG, "Error cargando Imagen");
            return null;
        }
    }

    @Override
    protected void onPreExecute() {
        super.onPreExecute();
        pDialog.setMessage("Wait a minute, please....");
        pDialog.setCancelable(false);
        pDialog.show();

    }

    @Override
    protected void onPostExecute(Bitmap bitmap) {
        super.onPostExecute(bitmap);
    }

    private static Bitmap getData(String URL){
        try {
            java.net.URL url = new URL(URL);
            URLConnection uConnect = url.openConnection();
            InputStream input = uConnect.getInputStream();
            Bitmap myBitmap = BitmapFactory.decodeStream(input);
            return myBitmap;
        } catch (IOException e) {
            e.printStackTrace();
            return null;
        }
    }
}
