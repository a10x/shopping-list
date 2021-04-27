package uk.ac.le.co2103.hw4;

import android.app.Application;

import androidx.lifecycle.LiveData;

import java.util.List;

public class ShoppingListRepository {

    private ShoppingListDao shoppingListDao;
    private LiveData<List<ShoppingList>> allShoppingLists;

    public static long LAST_INSERTED = -1L;

    public ShoppingListRepository(Application application){
        ShoppingListDatabase shoppingDb = ShoppingListDatabase.getInstance(application);
        shoppingListDao = shoppingDb.listDao();
        allShoppingLists = shoppingListDao.getAll();
    }

    public void insert(ShoppingList list){
        ShoppingListDatabase.databaseWriteExecutor.execute(()->{
            ShoppingListRepository.LAST_INSERTED = shoppingListDao.insert(list);
        });
    }

    public void delete(int listId){
        ShoppingListDatabase.databaseWriteExecutor.execute(()->shoppingListDao.delete(listId));
    }

    public ShoppingList get(int listId){
        if(allShoppingLists.getValue() == null) return null;

        for(ShoppingList shoppingList : allShoppingLists.getValue()){
            if(shoppingList.getListId() == listId)return shoppingList;
        }
        return null;
    }

    public LiveData<List<ShoppingList>> getAll(){
        return allShoppingLists;
    }
}
