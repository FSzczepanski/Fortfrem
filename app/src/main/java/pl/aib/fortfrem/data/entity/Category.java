package pl.aib.fortfrem.data.entity;

import android.widget.ImageView;

public class Category {
    private int id;
    private String title;
    private int img;
    public Category(String t,int images){
        title = t;
        img = images;
    }

    public int getId() {
        return id;
    }

    public int getImg() {
        return img;
    }

    public String getTitle() {
        return title;
    }
}
