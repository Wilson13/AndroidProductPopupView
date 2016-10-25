package sg.com.bitwave.shoppingsystem.asynctask;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.AsyncTask;
import android.util.Log;

import java.io.BufferedInputStream;
import java.io.InputStream;
import java.net.URL;

import sg.com.bitwave.shoppingsystem.ProductListingActivity;

public class DownloadAsync extends AsyncTask<String, Integer, Bitmap[]> {

    private final String TAG ="DownloadAsync";
    private Context context;

    public DownloadAsync(Context context) {
        this.context = context;
    }

    @Override
    protected Bitmap[] doInBackground(String... urls) {
        try {
            Bitmap[] bitmapArray = new Bitmap[10];
            BufferedInputStream imageBuffer;
            InputStream imageInputStream;
            URL url;

            for (int i = 0; i < urls.length; i++ ) {

                Log.i(TAG, "url: " + urls[i]);
                url = new URL(urls[i]); //("http://iclear-digital.com/clearlink_shopping/test_image_9.jpg");

                imageInputStream = url.openStream();
                imageBuffer = new BufferedInputStream(imageInputStream);

                // Convert the BufferedInputStream to a Bitmap
                bitmapArray[i] = BitmapFactory.decodeStream(imageBuffer);

                if (imageInputStream != null) {
                    imageInputStream.close();
                }
                if (imageBuffer != null) {
                    imageBuffer.close();
                }
            }

            return bitmapArray;

        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    protected void onPostExecute(Bitmap downloadBm[])
    {
        ((ProductListingActivity)context).displayHorizontalCardAlert(downloadBm);
    }

}
