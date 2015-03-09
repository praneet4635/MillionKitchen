package com.t9l.millionkitchen.dao;

/**
 * Created by praneet on 11-02-2015.
 */
public class FoodItem {
    private String userImage;
    private String itemImage;
    private String userName;
    private String itemName;
    private float itemRating;
    private int itemPrice;
    private String itemDescription;
    private int servesPeople;
    private int remainingQuantity;
    private int selectedQuantity=0;
    private boolean isFrontShown=true;

    public boolean isFrontShown() {
        return isFrontShown;
    }

    public void setFrontShown(boolean isFrontShown) {
        this.isFrontShown = isFrontShown;
    }

    public int getSelectedQuantity() {
        return selectedQuantity;
    }

    public void setSelectedQuantity(int selectedQuantity) {
        this.selectedQuantity = selectedQuantity;
    }

    public int getRemainingQuantity() {

        return remainingQuantity;
    }

    public void setRemainingQuantity(int remainingQuantity) {
        this.remainingQuantity = remainingQuantity;
    }

    public int getServesPeople() {

        return servesPeople;
    }

    public void setServesPeople(int servesPeople) {
        this.servesPeople = servesPeople;
    }

    public String getItemDescription() {

        return itemDescription;
    }

    public void setItemDescription(String itemDescription) {
        this.itemDescription = itemDescription;
    }

    public int getItemPrice() {

        return itemPrice;
    }

    public void setItemPrice(int itemPrice) {
        this.itemPrice = itemPrice;
    }

    public float getItemRating() {

        return itemRating;
    }

    public void setItemRating(float itemRating) {
        this.itemRating = itemRating;
    }

    public String getItemName() {

        return itemName;
    }

    public void setItemName(String itemName) {
        this.itemName = itemName;
    }

    public String getUserName() {

        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getItemImage() {

        return itemImage;
    }

    public void setItemImage(String itemImage) {
        this.itemImage = itemImage;
    }

    public String getUserImage() {

        return userImage;
    }

    public void setUserImage(String userImage) {
        this.userImage = userImage;
    }
}
