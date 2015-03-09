package com.t9l.millionkitchen;

import android.content.Context;
import android.os.Bundle;
import android.os.Handler;
import android.support.annotation.Nullable;
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

import com.t9l.millionkitchen.SectionRecyclerView.SimpleAdapter;
import com.t9l.millionkitchen.SectionRecyclerView.SimpleSectionedRecyclerViewAdapter;
import com.t9l.millionkitchen.dao.FoodItem;
import com.t9l.millionkitchen.tools.Methods;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by praneet on 23-02-2015.
 */
public class DishesFragment extends Fragment implements View.OnClickListener{
    LinearLayoutManager llm;
    View bottomLayout;
    int mLastScroll = 0;
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setHasOptionsMenu (true);
    }
    @Override
    public void onPrepareOptionsMenu(Menu menu) {
        super.onPrepareOptionsMenu(menu);
        Methods.setMenuItems(menu, true, false, false);
        return;
    }
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_dishes, container, false);
        initFoodList(getActivity(), view);
        view.findViewById(R.id.addDishBtn).setOnClickListener(this);
        return view;
    }

    public void initFoodList(final Context context, View rootView) {
        int mLastFirstVisibleItem;
//        FoodItemsAdapter adapter;
        RecyclerView recList = (RecyclerView) rootView.findViewById(R.id.cardList);
//        recList.addItemDecoration(new DividerItemDecoration(getActivity(),LinearLayoutManager.VERTICAL));
        //Your RecyclerView.Adapter
        ArrayList<FoodItem> items=new ArrayList<FoodItem>();
        for (int i=0;i<15;i++)
        {
            FoodItem item=new FoodItem();
            item.setItemName("Item "+(i+1));
            item.setRemainingQuantity(1);

            items.add(item);
        }
        SimpleAdapter mAdapter = new SimpleAdapter(getActivity(),items);


        //This is the code to provide a sectioned list
        List<SimpleSectionedRecyclerViewAdapter.Section> sections =
                new ArrayList<SimpleSectionedRecyclerViewAdapter.Section>();

        //Sections
        sections.add(new SimpleSectionedRecyclerViewAdapter.Section(0,"Section 1"));
        sections.add(new SimpleSectionedRecyclerViewAdapter.Section(5,"Section 2"));
        sections.add(new SimpleSectionedRecyclerViewAdapter.Section(12,"Section 3"));
        sections.add(new SimpleSectionedRecyclerViewAdapter.Section(14,"Section 4"));

        //Add your adapter to the sectionAdapter
        SimpleSectionedRecyclerViewAdapter.Section[] dummy = new SimpleSectionedRecyclerViewAdapter.Section[sections.size()];
        SimpleSectionedRecyclerViewAdapter mSectionedAdapter = new
                SimpleSectionedRecyclerViewAdapter(getActivity(),R.layout.section,R.id.section_text,mAdapter);
        mSectionedAdapter.setSections(sections.toArray(dummy));

        bottomLayout = (View) rootView.findViewById(R.id.bottomLayout);
        recList.setHasFixedSize(true);
        llm = new LinearLayoutManager(context);
        llm.setOrientation(LinearLayoutManager.VERTICAL);
        recList.setLayoutManager(llm);

//        recList.setOnScrollListener(new RecyclerView.OnScrollListener() {
//            @Override
//            public void onScrollStateChanged(RecyclerView recyclerView, int newState) {
//                super.onScrollStateChanged(recyclerView, newState);
//            }
//
//            @Override
//            public void onScrolled(RecyclerView recyclerView, int dx, int dy) {
//                super.onScrolled(recyclerView, dx, dy);
//
//                // Code to hide the bottom layout on scrolling up and again show it on scrolling down like Facebook app.
//
//                if (recyclerView.getChildCount() <= 0)
//                    return;
//                View c = recyclerView.getChildAt(0);
//                int scrolly = -c.getTop() + llm.findFirstVisibleItemPosition() * c.getHeight();
//                if (mLastScroll == 0)
//                    mLastScroll = (int) (scrolly + Math.round(bottomLayout.getTranslationY() / 0.7));
//                float dif = 0;
//                if (mLastScroll - scrolly <= 0)
//                    dif = mLastScroll - scrolly;
//                else
//                    mLastScroll = scrolly;
//
//                if (dif * 0.7 < -bottomLayout.getHeight()) {
//                    dif = Math.round(-bottomLayout.getHeight() / 0.7);
//                    mLastScroll = (int) Math.round(scrolly - bottomLayout.getHeight() / 0.7);
//                }
//                long pos = Math.round(dif * 0.7);
//
//                bottomLayout.setTranslationY(-pos);
//            }
//        });
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
//        adapter = new FoodItemsAdapter(context, createList(30));
//        recList.setAdapter(adapter);

        //Apply this adapter to the RecyclerView
        recList.setAdapter(mSectionedAdapter);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.addDishBtn:
                getActivity().getSupportFragmentManager()
                        .beginTransaction()
                        .replace(R.id.container, new AddDishFragment(),
                                "AddDish").addToBackStack("AddDish")
                        .commit();
                break;
        }
    }
}
