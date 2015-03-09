package com.t9l.millionkitchen.adapter;

import android.content.Context;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;

import com.t9l.millionkitchen.R;
import com.t9l.millionkitchen.dao.CustomImage;
import com.t9l.millionkitchen.lazyloading.ImageLoader;
import com.t9l.millionkitchen.tools.Methods;

import java.util.ArrayList;

public class ImagesViewPagerAdapter extends PagerAdapter {
    // Declare Variables
    Context context;
    ArrayList<CustomImage> images;
    LayoutInflater inflater;
    public ImageLoader imageLoader;
    int thumbnail;

    public ImagesViewPagerAdapter(Context context, ArrayList<CustomImage> images, int thumbnail) {
        this.context = context;
        this.images = images;
        this.thumbnail = thumbnail;
        imageLoader = new ImageLoader(context.getApplicationContext());
        imageLoader.clearCache();
    }

    @Override
    public int getCount() {
        return images.size();
    }

    @Override
    public boolean isViewFromObject(View view, Object object) {
        return view == ((LinearLayout) object);
    }

    @Override
    public Object instantiateItem(ViewGroup container, final int position) {
        ImageView imageView;
        inflater = (LayoutInflater) context
                .getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View itemView = inflater.inflate(R.layout.image_viewpager_item, container,
                false);
        // Locate the ImageView in viewpager_item.xml
        imageView = (ImageView) itemView.findViewById(R.id.image);

        if (images.get(position).isLocal()) {
            String path = images.get(position).getImageLocalPath();
            if (Methods.valid(path))
                imageView.setImageBitmap(Methods.doParse(path,
                        100, 100));
            else
            imageView.setImageResource(thumbnail);
        } else {
            String path = images.get(position).getImageRemotePath();
            if (Methods.valid(path))
                imageLoader.DisplayImage(context, path, imageView, null,
                        thumbnail);
            else
                imageView.setImageResource(thumbnail);
        }


        // Add viewpager_item.xml to ViewPager
        ((ViewPager) container).addView(itemView);
//		itemView.setOnClickListener(new OnClickListener() {
//
//			@Override
//			public void onClick(View arg0) {
//				FullImageFragment fragment = new FullImageFragment();
//				Bundle bundle = new Bundle();
//				bundle.putSerializable("property", property);
//				bundle.putSerializable("selectedPos", position);
//				fragment.setArguments(bundle);
//				(((FragmentActivity) context).getSupportFragmentManager())
//						.beginTransaction()
//						.replace(R.id.frame_container, fragment)
//						.addToBackStack(null).commit();
//			}
//		});
        return itemView;
    }

    @Override
    public void destroyItem(ViewGroup container, int position, Object object) {
        // Remove viewpager_item.xml from ViewPager
        ((ViewPager) container).removeView((LinearLayout) object);

    }
}