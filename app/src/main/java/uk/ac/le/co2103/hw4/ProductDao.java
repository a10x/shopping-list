package uk.ac.le.co2103.hw4;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;
import androidx.room.Update;

import java.util.List;

@Dao
public interface ProductDao {

    @Insert
    void insert(Product product);

    @Delete
    void delete(Product product);
    @Query("DELETE FROM product WHERE name=:name")
    void delete(String name);

    @Update
    void update(Product product);

    @Query("SELECT * FROM product WHERE name=:productId")
    LiveData<Product> get(String productId);
    @Query("SELECT * FROM shopping_list WHERE listId=:listId")
    ShoppingList getShoppingList(int listId);
    @Query("SELECT * FROM product")
    LiveData<List<Product>> getAll();
    @Query("SELECT * FROM product WHERE fkShoppingList=:listId")
    LiveData<List<Product>> getShoppingListProducts(int listId);
}
