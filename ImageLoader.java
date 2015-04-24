package com.example.majisha.ebayshopping;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.AsyncTask;
import android.widget.ImageView;

import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;

/**
 * Created by Majisha on 4/20/2015.
 */
public class ImageLoader extends AsyncTask<Void, Void, Bitmap> {

    private String imgURL;
    private ImageView imgView;

    public ImageLoader(String url, ImageView imgView){
        this.imgURL = url;
        this.imgView = imgView;
    }


    @Override
    protected Bitmap doInBackground(Void... params) {
        try {
            URL urlConnection = new URL(imgURL);
            HttpURLConnection connection = (HttpURLConnection) urlConnection.openConnection();
            connection.setDoInput(true);
            connection.connect();
            InputStream is = connection.getInputStream();
            Bitmap imgBMP = BitmapFactory.decodeStream(is);
            return imgBMP;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    @Override
    protected void onPostExecute(Bitmap bmp) {
        super.onPostExecute(bmp);
        imgView.setImageBitmap(bmp);
    }
}
