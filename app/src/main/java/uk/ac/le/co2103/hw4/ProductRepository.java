package uk.ac.le.co2103.hw4;

import android.app.Application;

import androidx.lifecycle.LiveData;

import java.util.List;
import java.util.Objects;

public class ProductRepository {

    private ProductDao productDao;
    private LiveData<List<Product>> allProducts;

    public ProductRepository(Application application){
        ShoppingListDatabase shoppingDb = ShoppingListDatabase.getInstance(application);
        this.productDao = shoppingDb.productDao();
        this.allProducts = this.productDao.getAll();
    }

    public void insert(Product product){
        ShoppingListDatabase.databaseWriteExecutor.execute(()->productDao.insert(product));
    }

    public void delete(String productName){
        ShoppingListDatabase.databaseWriteExecutor.execute(()->productDao.delete(productName));
    }

    public void update(Product product){
        ShoppingListDatabase.databaseWriteExecutor.execute(()->productDao.update(product));
    }

    public LiveData<Product> get(String productName){
        return this.productDao.get(productName);
    }

    public LiveData<List<Product>> getAll(){return allProducts;}

    public LiveData<List<Product>> getShoppingListProducts(int listId){
        return productDao.getShoppingListProducts(listId);
    }

}
