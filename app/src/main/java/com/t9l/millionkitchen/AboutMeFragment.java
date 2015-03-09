package com.t9l.millionkitchen;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;

import com.t9l.millionkitchen.adapter.ImagesViewPagerAdapter;
import com.t9l.millionkitchen.dao.CustomImage;
import com.t9l.millionkitchen.tools.Methods;
import com.t9l.millionkitchen.tools.Vars;
import com.t9l.millionkitchen.viewpagerindicator.CirclePageIndicator;

import java.util.ArrayList;

/**
 * Created by praneet on 23-02-2015.
 */
public class AboutMeFragment extends Fragment {
    ViewPager viewPager;
    ImagesViewPagerAdapter adapter;
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setHasOptionsMenu (true);
    }
    @Override
    public void onPrepareOptionsMenu(Menu menu) {
        super.onPrepareOptionsMenu(menu);
        Methods.setMenuItems(menu, true, false, true);
        return;
    }
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case Vars.MENU_ITEM_ID_EDIT:
                getActivity().getSupportFragmentManager()
                        .beginTransaction()
                        .replace(R.id.container, new AboutMeEditFragment(),
                                "AboutMeEdit").addToBackStack("AboutMeEdit")
                        .commit();
                break;
            default:
                break;
        }
        return false;
    }
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_about_me, container, false);
        viewPager = (ViewPager) view.findViewById(R.id.pager);
        ArrayList<CustomImage> images = getImages();
        if (images != null) {
            adapter = new ImagesViewPagerAdapter(getActivity(), images,R.drawable.kitchen_wall);
            // Binds the Adapter to the ViewPager
            viewPager.setAdapter(adapter);
        }
        if (viewPager != null && adapter != null) {
            CirclePageIndicator bubbles = (CirclePageIndicator) view
                    .findViewById(R.id.bubbles);
            bubbles.setRadius(5);
            bubbles.setViewPager(viewPager);
        }
        return view;
    }

    private ArrayList<CustomImage> getImages() {
        ArrayList<CustomImage> images=new ArrayList<CustomImage>();

        images.add(new CustomImage("",false));
        images.add(new CustomImage("",false));
        images.add(new CustomImage("",false));

//        images.add(new CustomImage("http://media1.s-nbcnews.com/j/msnbc/Components/Photos/040511/040511_redrectangle_hmed_10a.grid-6x2.jpg",false));
//        images.add(new CustomImage("http://www.vanseodesign.com/blog/wp-content/uploads/2011/04/stonehenge.jpg",false));
//        images.add(new CustomImage("http://kapowartnow.com/wp-content/uploads/2013/05/golden-ratiojpg.png",false));

        return images;
    }
}
