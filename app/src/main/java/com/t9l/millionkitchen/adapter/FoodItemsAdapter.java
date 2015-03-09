package com.t9l.millionkitchen.adapter;

import android.content.Context;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.RatingBar;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.t9l.millionkitchen.R;
import com.t9l.millionkitchen.dao.FoodItem;

import java.util.List;

/**
 * Created by praneet on 11-02-2015.
 */
public class FoodItemsAdapter extends RecyclerView.Adapter<FoodItemsAdapter.FoodItemViewHolder> implements View.OnClickListener{

    private List<FoodItem> contactList;
    private Animation animation1;
    private Animation animation2;
    int animateItemPos=-1;

    private Context context;

    public FoodItemsAdapter(Context context, List<FoodItem> contactList) {
        this.context=context;
        this.contactList = contactList;
        animation1 = AnimationUtils.loadAnimation(context, R.anim.to_middle);
        animation2 = AnimationUtils.loadAnimation(context, R.anim.from_middle);
    }


    @Override
    public int getItemCount() {
        return contactList.size();
    }

    @Override
    public void onBindViewHolder(FoodItemViewHolder foodItemViewHolder,final int i) {
        FoodItem ci = contactList.get(i);
//        contactViewHolder.item_name_tv.setText(ci.name);
//        contactViewHolder.item_desc_tv.setText(ci.surname);
//        contactViewHolder.item_serves_tv.setText(ci.email);
//        contactViewHolder.item_remaining_tv.setText(ci.name + " " + ci.surname);
        foodItemViewHolder.ratingBar.setRating(2.5f);
        foodItemViewHolder.setTag(i);
        foodItemViewHolder.card_view.setTag(foodItemViewHolder);
        foodItemViewHolder.card_view.setOnClickListener(this);
        final RelativeLayout frontCard= foodItemViewHolder.cardFrontLayout;
        final RelativeLayout backCard= foodItemViewHolder.cardBackLayout;

        if(ci.isFrontShown())
        {
            frontCard.setVisibility(View.VISIBLE);
            backCard.setVisibility(View.GONE);
        }else
        {
            frontCard.setVisibility(View.GONE);
            backCard.setVisibility(View.VISIBLE);
        }
    }

    @Override
    public FoodItemViewHolder onCreateViewHolder(ViewGroup viewGroup, int i) {
        final View itemView = LayoutInflater.
                from(viewGroup.getContext()).
                inflate(R.layout.card_layout, viewGroup, false);
        return new FoodItemViewHolder(itemView);
    }

    @Override
    public void onClick(View v) {
        final FoodItemViewHolder foodItemViewHolder =(FoodItemViewHolder)v.getTag();
        final int i=(int) foodItemViewHolder.getTag();
        final CardView card= foodItemViewHolder.card_view;
        animateItemPos=i;
        card.setEnabled(false);
        card.clearAnimation();
        card.setAnimation(animation1);
        card.startAnimation(animation1);

        final RelativeLayout frontCard= foodItemViewHolder.cardFrontLayout;
        final RelativeLayout backCard= foodItemViewHolder.cardBackLayout;
        animation1.setAnimationListener(new Animation.AnimationListener() {
            @Override
            public void onAnimationStart(Animation animation) {
            }

            @Override
            public void onAnimationEnd(Animation animation) {
                if (animateItemPos == i) {
                    if (backCard.getVisibility() == View.VISIBLE) {
                        // Flip card to show its back
//				((ImageView)findViewById(R.id.imageView1)).setImageResource(R.drawable.card_front);
                        frontCard.setVisibility(View.VISIBLE);
                        backCard.setVisibility(View.GONE);
                        contactList.get(i).setFrontShown(true);
                    } else {
                        // Flip card to show its front
//				((ImageView)findViewById(R.id.imageView1)).setImageResource(R.drawable.card_back);
                        frontCard.setVisibility(View.GONE);
                        backCard.setVisibility(View.VISIBLE);
                        contactList.get(i).setFrontShown(false);
                    }
                    card.clearAnimation();
                    card.setAnimation(animation2);
                    card.startAnimation(animation2);
                }
            }

            @Override
            public void onAnimationRepeat(Animation animation) {

            }
        });

        animation2.setAnimationListener(new Animation.AnimationListener() {
            @Override
            public void onAnimationStart(Animation animation) {
            }

            @Override
            public void onAnimationEnd(Animation animation) {
                card.setEnabled(true);
                animateItemPos=-1;
            }

            @Override
            public void onAnimationRepeat(Animation animation) {

            }
        });
    }

    public static class FoodItemViewHolder extends RecyclerView.ViewHolder {

        CardView card_view;
        RelativeLayout cardFrontLayout,cardBackLayout;
        protected TextView item_name_tv;
        protected TextView item_desc_tv;
        protected TextView item_serves_tv;
        protected TextView item_remaining_tv;
        protected RatingBar ratingBar;
        Object tag;

        public FoodItemViewHolder(View v) {
            super(v);
            card_view=(CardView)v.findViewById(R.id.card_view);
            cardFrontLayout=(RelativeLayout)v.findViewById(R.id.cardFrontLayout);
            cardBackLayout=(RelativeLayout)v.findViewById(R.id.cardBackLayout);
            item_name_tv =  (TextView) v.findViewById(R.id.item_name_tv);
            item_desc_tv = (TextView)  v.findViewById(R.id.item_desc_tv);
            item_serves_tv = (TextView)  v.findViewById(R.id.item_serves_tv);
            item_remaining_tv = (TextView) v.findViewById(R.id.item_remaining_tv);
            ratingBar=(RatingBar)v.findViewById(R.id.ratingBar);
        }

        public void setTag(Object pos)
        {
            tag=pos;
        }
        public Object getTag()
        {
            return tag;
        }
    }
}
