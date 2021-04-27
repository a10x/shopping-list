package uk.ac.le.co2103.hw4;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;

import java.util.List;

@Dao
public interface ShoppingListDao {

    @Insert
    long insert(ShoppingList list);

    @Delete
    void delete(ShoppingList list);
    @Query("DELETE FROM shopping_list WHERE listId=:listId")
    void delete(int listId);

    @Query("SELECT * FROM shopping_list")
    LiveData<List<ShoppingList>> getAll();
    @Query("SELECT * FROM shopping_list WHERE listId=:listId")
    ShoppingList get(int listId);
}
