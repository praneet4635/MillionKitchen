package com.t9l.millionkitchen.tools;

import android.content.Context;
import android.net.Uri;
import android.widget.ImageView;

import com.t9l.millionkitchen.R;
import com.t9l.millionkitchen.dao.CustomImage;
import com.t9l.millionkitchen.lazyloading.ImageLoader;

/**
 * Created by praneet on 13-02-2015.
 */
public class ImageUtil {
    Context context;

    public ImageUtil(Context con) {
        this.context = con;
    }
    public String getUniqueImageFilename() {
        return "img_" + System.currentTimeMillis() + ".jpg";
    }
    public void loadImage(ImageView v, CustomImage image) {
        if (image.isLocal())
            v.setImageURI(Uri.parse(image.getImageLocalPath()));
        else {
            ImageLoader imageLoader = new ImageLoader(context);
            imageLoader.DisplayImage(context, image.getImageRemotePath(), v, null, R.drawable.plus_img);
        }
    }

}
