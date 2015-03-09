package com.t9l.millionkitchen.dao;

/**
 * Created by praneet on 13-02-2015.
 */
public class CustomImage {
    private String imageLocalPath;
    private String imageRemotePath;
    private boolean isLocal;

    public CustomImage(String imagePath, boolean isLocal) {
        if (isLocal)
            this.imageLocalPath = imagePath;
        else
            this.imageRemotePath = imagePath;
        this.isLocal = isLocal;
    }

    public String getImageLocalPath() {
        return imageLocalPath;
    }

    public boolean isLocal() {
        return isLocal;
    }

    public String getImageRemotePath() {

        return imageRemotePath;
    }

    public void setLocal(boolean isLocal) {
        this.isLocal = isLocal;
    }

    public void setImageRemotePath(String imageRemotePath) {

        this.imageRemotePath = imageRemotePath;
    }

    public void setImageLocalPath(String imageLocalPath) {

        this.imageLocalPath = imageLocalPath;
    }
}
