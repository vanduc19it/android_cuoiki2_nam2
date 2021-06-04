package vku.phungduc.myapplication;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.os.AsyncTask;
import android.text.Html;
import android.util.Base64;
import android.view.View;

import java.io.InputStream;
import java.net.URL;

public class URLImageParser implements Html.ImageGetter {
    Context context;
    View container;

    public URLImageParser(View container, Context context) {
        this.context = context;
        this.container = container;
    }

    public Drawable getDrawable(String source) {
        if(source.matches("data:image.*base64.*")) {
            String base_64_source = source.replaceAll("data:image.*base64", "");
            byte[] data = Base64.decode(base_64_source, Base64.DEFAULT);
            Bitmap bitmap = BitmapFactory.decodeByteArray(data, 0, data.length);
            Drawable image = new BitmapDrawable(context.getResources(), bitmap);
            image.setBounds(0, 80, 0 + image.getIntrinsicWidth()+100, 0 + image.getIntrinsicHeight()+80);
            return image;
        } else {
            URLDrawable urlDrawable = new URLDrawable();
            ImageGetterAsyncTask asyncTask = new ImageGetterAsyncTask(urlDrawable);
            asyncTask.execute(source);
            return urlDrawable; //return reference to URLDrawable where We will change with actual image from the src tag
        }
    }

    public class ImageGetterAsyncTask extends AsyncTask<String, Void, Drawable> {
        URLDrawable urlDrawable;

        public ImageGetterAsyncTask(URLDrawable d) {
            this.urlDrawable = d;
        }

        @Override
        protected Drawable doInBackground(String... params) {
            String source = params[0];
            return fetchDrawable(source);
        }

        @Override
        protected void onPostExecute(Drawable result) {
            urlDrawable.setBounds(0, 80, 0 + result.getIntrinsicWidth()+100, 0 + result.getIntrinsicHeight()+80);
            urlDrawable.drawable = result;
            URLImageParser.this.container.invalidate();
        }

        public Drawable fetchDrawable(String urlString) {
            try {
                InputStream is = (InputStream) new URL(urlString).getContent();
                Drawable drawable = Drawable.createFromStream(is, "src");
                drawable.setBounds(0, 80, 0 + drawable.getIntrinsicWidth()+100, 0 + drawable.getIntrinsicHeight()+80);
                return drawable;
            } catch (Exception e) {
                return null;
            }
        }
    }
}
class URLDrawable extends BitmapDrawable {
    // the drawable that you need to set, you could set the initial drawing
    // with the loading image if you need to
    protected Drawable drawable;

    @Override
    public void draw(Canvas canvas) {
        // override the draw to facilitate refresh function later
        if(drawable != null) {
            drawable.draw(canvas);
        }
    }
}