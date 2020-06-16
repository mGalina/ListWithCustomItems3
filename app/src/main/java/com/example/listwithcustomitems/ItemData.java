package com.example.listwithcustomitems;

import android.graphics.drawable.Drawable;

public class ItemData {

    private Drawable image;
    private String title;
    private String subtitle;
    private String userText;

    public ItemData(Drawable image, String title, String subtitle, String userText) {
        this.image = image;
        this.title = title;
        this.subtitle = subtitle;
        this.userText = userText;
    }

    public Drawable getImage() {
        return image;
    }

    public String getTitle() {
        return title;
    }

    public String getSubtitle() {
        return subtitle;
    }

    public String getUserText() {
        return userText;
    }
}
