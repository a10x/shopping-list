package uk.ac.le.co2103.hw4;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;

import java.util.List;

public class ShoppingListViewModel extends AndroidViewModel {

    private ShoppingListRepository sRepo;
    private LiveData<List<ShoppingList>> allShoppingLists;

    public ShoppingListViewModel(@NonNull Application application) {
        super(application);
        this.sRepo = new ShoppingListRepository(application);
        this.allShoppingLists = this.sRepo.getAll();
    }

    public void insert(ShoppingList list){
        if(ValidationUtils.ValidateShoppingList(list)){sRepo.insert(list);}
    }

    public void delete(int listId){sRepo.delete(listId);}

    public ShoppingList get(int listId){return sRepo.get(listId);}

    public LiveData<List<ShoppingList>> getAll(){return this.allShoppingLists;}

}
