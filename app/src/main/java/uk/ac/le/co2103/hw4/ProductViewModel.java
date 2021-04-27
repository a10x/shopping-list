package uk.ac.le.co2103.hw4;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;

import java.util.List;

public class ProductViewModel extends AndroidViewModel {

    private ProductRepository pRepo;
    private LiveData<List<Product>> allProducts;

    public ProductViewModel(@NonNull Application application) {
        super(application);
        this.pRepo = new ProductRepository(application);
        this.allProducts = this.pRepo.getAll();
    }

    public void insert(Product product){
        pRepo.insert(product);
    }

    public void delete(String productName){pRepo.delete(productName);}

    public void update(Product product){ pRepo.update(product); }

    public LiveData<List<Product>> getShoppingListProducts(int listId){
        return pRepo.getShoppingListProducts(listId);
    }

    public LiveData<Product> get(String productName){
        return pRepo.get(productName);
    }


    @Deprecated
    public LiveData<Product> getProduct(String productName){
        return pRepo.get(productName);
    }
}
