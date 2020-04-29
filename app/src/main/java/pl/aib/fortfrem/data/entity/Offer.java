package pl.aib.fortfrem.data.entity;


import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

import java.util.Locale;

@Entity
public class Offer {
    @PrimaryKey
    @NonNull
    private String oid;

    @NonNull
    private String title;

    @NonNull
    private String storeName;

    @NonNull
    private String storeLogoUrl;

    @NonNull
    private String category;

    @NonNull
    private String subcategory;

    @NonNull
    private int price;

    @NonNull
    private int oldPrice;

    @NonNull
    private String imageUrl;

    @NonNull
    private long endTime;

    @NonNull
    private long startTime;


    public String getOid() {
        return oid;
    }

    public void setOid(String oid) {
        this.oid = oid;
    }

    @NonNull
    public String getTitle() {
        return title;
    }

    public void setTitle(@NonNull String title) {
        this.title = title;
    }

    @NonNull
    public String getStoreName() {
        return storeName;
    }

    public void setStoreName(@NonNull String storeName) {
        this.storeName = storeName;
    }

    @NonNull
    public String getStoreLogoUrl() {
        return storeLogoUrl;
    }

    public void setStoreLogoUrl(@NonNull String storeLogoUrl) {
        this.storeLogoUrl = storeLogoUrl;
    }

    @NonNull
    public String getCategory() {
        return category;
    }

    public void setCategory(@NonNull String category) {
        this.category = category;
    }

    @NonNull
    public String getSubcategory() {
        return subcategory;
    }

    public void setSubcategory(@NonNull String subcategory) {
        this.subcategory = subcategory;
    }

    public int getPrice() {
        return price;
    }

    public void setPrice(int price) {
        this.price = price;
    }

    public int getOldPrice() {
        return oldPrice;
    }

    public void setOldPrice(int oldPrice) {
        this.oldPrice = oldPrice;
    }

    @NonNull
    public String getImageUrl() {
        return imageUrl;
    }

    public void setImageUrl(@NonNull String imageUrl) {
        this.imageUrl = imageUrl;
    }

    @NonNull
    public long getEndTime() {
        return endTime;
    }

    public void setEndTime(@NonNull long endTime) {
        this.endTime = endTime;
    }

    public long getStartTime() {
        return startTime;
    }

    public void setStartTime(long startTime) {
        this.startTime = startTime;
    }

    public String getPricePLNString() {
        int pln = this.price / 100;
        int gr = this.price % 100;
        return String.format(Locale.getDefault(),"%d.%02d PLN", pln, gr);
    }

    public String getDiscountPercentageString() {
        double difference = this.oldPrice - this.price;
        double percentage = (difference / this.oldPrice) * 100;
        return String.format(Locale.getDefault(), "%.2f%%", percentage);
    }
}
