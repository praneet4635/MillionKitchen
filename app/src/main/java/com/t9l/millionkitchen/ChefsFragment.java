package com.t9l.millionkitchen;

import android.content.Context;
import android.os.Bundle;
import android.os.Handler;
import android.support.v4.app.Fragment;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.t9l.millionkitchen.adapter.FoodItemsAdapter;
import com.t9l.millionkitchen.dao.FoodItem;
import com.t9l.millionkitchen.tools.Methods;

import java.util.ArrayList;
import java.util.List;

public class ChefsFragment extends Fragment {
    LinearLayoutManager llm;
    View bottomLayout;
    int mLastScroll = 0;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setHasOptionsMenu(true);
    }

    @Override
    public void onPrepareOptionsMenu(Menu menu) {
        super.onPrepareOptionsMenu(menu);
        Methods.setMenuItems(menu, true, true, false);
        return;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Get the view from fragmenttab2.xml
        View view = inflater.inflate(R.layout.fragment_list, container, false);
        initFoodList(getActivity(), view);
        return view;
    }

    private List<FoodItem> createList(int size) {

        List<FoodItem> result = new ArrayList<FoodItem>();
        for (int i = 1; i <= size; i++) {
            FoodItem ci = new FoodItem();

            result.add(ci);
        }

        return result;
    }

    public void initFoodList(final Context context, View rootView) {
        int mLastFirstVisibleItem;
        FoodItemsAdapter adapter;
        RecyclerView recList = (RecyclerView) rootView.findViewById(R.id.cardList);
        bottomLayout = (View) rootView.findViewById(R.id.bottomLayout);
        recList.setHasFixedSize(true);
        llm = new LinearLayoutManager(context);
        llm.setOrientation(LinearLayoutManager.VERTICAL);
        recList.setLayoutManager(llm);

        recList.setOnScrollListener(new RecyclerView.OnScrollListener() {
            @Override
            public void onScrollStateChanged(RecyclerView recyclerView, int newState) {
                super.onScrollStateChanged(recyclerView, newState);
            }

            @Override
            public void onScrolled(RecyclerView recyclerView, int dx, int dy) {
                super.onScrolled(recyclerView, dx, dy);

                // Code to hide the bottom layout on scrolling up and again show it on scrolling down like Facebook app.

                if (recyclerView.getChildCount() <= 0)
                    return;
                View c = recyclerView.getChildAt(0);
                int scrolly = -c.getTop() + llm.findFirstVisibleItemPosition() * c.getHeight();
                if (mLastScroll == 0)
                    mLastScroll = (int) (scrolly + Math.round(bottomLayout.getTranslationY() / 0.7));
                float dif = 0;
                if (mLastScroll - scrolly <= 0)
                    dif = mLastScroll - scrolly;
                else
                    mLastScroll = scrolly;

                if (dif * 0.7 < -bottomLayout.getHeight()) {
                    dif = Math.round(-bottomLayout.getHeight() / 0.7);
                    mLastScroll = (int) Math.round(scrolly - bottomLayout.getHeight() / 0.7);
                }
                long pos = Math.round(dif * 0.7);

                bottomLayout.setTranslationY(-pos);
            }
        });
        final SwipeRefreshLayout swipeView = (SwipeRefreshLayout) rootView.findViewById(R.id.swipe);
        swipeView.setColorSchemeResources(android.R.color.holo_blue_dark, android.R.color.holo_blue_light, android.R.color.holo_green_light, android.R.color.holo_green_light);
        swipeView.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                swipeView.setRefreshing(true);
                Log.d("Swipe", "Refreshing Number");
                (new Handler()).postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        swipeView.setRefreshing(false);
                        double f = Math.random();
                        Toast.makeText(context, "Refreshed", Toast.LENGTH_SHORT).show();
                    }
                }, 5000);
            }
        });
        adapter = new FoodItemsAdapter(context, createList(30));
        recList.setAdapter(adapter);
    }
}
