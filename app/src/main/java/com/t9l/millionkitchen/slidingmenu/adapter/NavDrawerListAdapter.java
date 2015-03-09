package com.t9l.millionkitchen.slidingmenu.adapter;

import android.app.Activity;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.t9l.millionkitchen.R;
import com.t9l.millionkitchen.lazyloading.ImageLoader;
import com.t9l.millionkitchen.slidingmenu.model.NavDrawerItem;
import com.t9l.millionkitchen.tools.Methods;

import java.util.ArrayList;

public class NavDrawerListAdapter extends BaseAdapter {

	private Context context;
	private ArrayList<NavDrawerItem> navDrawerItems;

	public ImageLoader imageLoader;

	public NavDrawerListAdapter(Context context,
			ArrayList<NavDrawerItem> navDrawerItems) {
		this.context = context;
		this.navDrawerItems = navDrawerItems;
		imageLoader = new ImageLoader(context);
	}

	@Override
	public int getCount() {
		return navDrawerItems.size();
	}

	@Override
	public Object getItem(int position) {
		return navDrawerItems.get(position);
	}

	@Override
	public long getItemId(int position) {
		return position;
	}

	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		TextView txtTitle, txtSubTitle = null;
		if (convertView == null) {
			LayoutInflater mInflater = (LayoutInflater) context
					.getSystemService(Activity.LAYOUT_INFLATER_SERVICE);
			if (position == 0) {
				convertView = mInflater.inflate(
						R.layout.drawer_list_item_profile, null);
			} else
				convertView = mInflater
						.inflate(R.layout.drawer_list_item, null);
		}

		ImageView imgIcon = (ImageView) convertView.findViewById(R.id.icon);

		if (position == 0) {
			String userImage = navDrawerItems.get(position).getUserImage();
			if (Methods.valid(userImage))
				imageLoader.DisplayImage(context, userImage, imgIcon, null,
						R.drawable.default_profile_pic);
			txtSubTitle = (TextView) convertView.findViewById(R.id.subTitle);
			txtSubTitle.setText(navDrawerItems.get(position).getSubTitle());
		} else
			imgIcon.setImageResource(navDrawerItems.get(position).getIcon());
		txtTitle = (TextView) convertView.findViewById(R.id.title);
		txtTitle.setText(navDrawerItems.get(position).getTitle());
		return convertView;
	}
}