package pl.aib.fortfrem.data.dao;

import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.Query;

import java.util.List;

import pl.aib.fortfrem.data.entity.Offer;

@Dao
public interface OfferDao {
    @Query("select * from offer where endTime > :now")
    List<Offer> findActive(long now);

    @Query("select count(*) from offer where endTime > :now")
    int countOfActive(long now);

    @Query("select distinct(category) from offer")
    List<String> findCategories();

    @Query("select distinct(subcategory) from offer where category = :category")
    List<String> findSubcategoriesForCategor(String category);

    @Query("select * from offer where storeName = :storeName")
    List<Offer> findForStore(String storeName);

    @Query("select * from offer where category = :category")
    List<Offer> findForCategory(String category);

    @Query("select * from offer where category = :subcategory")
    List<Offer> findForSubcategory(String subcategory);

    @Query("select * from offer where category = :category and storeName = :storeName")
    List<Offer> findForStoreAndCategory(String storeName, String category);

    @Query("select * from offer where category = :subcategory and storeName = :storeName")
    List<Offer> findForStoreSubcategory(String storeName, String subcategory);

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    void insert(Offer... offers);

    @Delete
    void delete(Offer... offers);

    @Query("delete from offer")
    void clear();
}
