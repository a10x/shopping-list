package uk.ac.le.co2103.hw4;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Toast;

import androidx.activity.result.ActivityResultLauncher;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.DefaultItemAnimator;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.util.List;

public class ShoppingListActivity extends AppCompatActivity {

    private static final String TAG = ShoppingListActivity.class.getSimpleName();
    private ProductAdapter productAdapter;
    private RecyclerView productRecycler;

    private ProductViewModel productViewModel;

    private int listId;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_show_list);
        this.productRecycler = findViewById(R.id.recycler_products);
        FloatingActionButton floatingActionButton = findViewById(R.id.fabAddProduct);

        this.listId = getIntent().getIntExtra("LIST_ID", 1);

        this.productViewModel = new ViewModelProvider(this, ViewModelProvider.AndroidViewModelFactory.getInstance(getApplication())).get(ProductViewModel.class);
        this.productViewModel.getShoppingListProducts(this.listId).observe(this, products -> {
            this.productAdapter.setProducts(products);
            this.productAdapter.notifyDataSetChanged();
        });

        floatingActionButton.setOnClickListener(view ->{
            addProductLauncher.launch(null);
        });

        this.setAdapter();
    }

    private void setAdapter(){
        this.productAdapter = new ProductAdapter(this.productViewModel.getShoppingListProducts(this.listId).getValue());
        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(getApplicationContext());
        this.productRecycler.setLayoutManager(layoutManager);
        this.productRecycler.setItemAnimator(new DefaultItemAnimator());
        this.productRecycler.setAdapter(this.productAdapter);

        this.productAdapter.setProductClickListener(position -> {

            new AlertDialog.Builder(ShoppingListActivity.this)
                    .setTitle("Edit or Delete")
                    .setItems(R.array.edit_delete, (dialog, which) -> {
                        LiveData<List<Product>> liveProducts = productViewModel.getShoppingListProducts(listId);

                        Observer<List<Product>> productsObserver = new Observer<List<Product>>() {
                            @Override
                            public void onChanged(List<Product> products) {
                                Product product = products.get(position);
                                if(which == 1){
                                    productViewModel.delete(product.getName());
                                }else if(which == 0){
                                    ProductHolder ph = new ProductHolder(product.getName(), product.getQuantity(), product.getUnit());
                                    updateProductLauncher.launch(ph);
                                }

                                liveProducts.removeObserver(this);
                            }
                        };
                        liveProducts.observe(this, productsObserver);

            }).create().show();
        });
    }

    private final ActivityResultLauncher<Void> addProductLauncher = registerForActivityResult(
            new CreateProductContract(), result -> {
                if(result == null) return;
                Product product = new Product(result.getName(), result.getQuantity(), result.getUnit(), this.listId);

                LiveData<Product> liveProduct = this.productViewModel.getProduct(product.getName());

                Observer<Product> productObserver = new Observer<Product>() {
                    @Override
                    public void onChanged(Product existProduct) {
                        if(existProduct == null){
                           productViewModel.insert(product);
                        }else{
                            Toast.makeText(ShoppingListActivity.this, "Product already exists", Toast.LENGTH_SHORT).show();
                        }
                        liveProduct.removeObserver(this);
                    }
                };

                liveProduct.observe(this, productObserver);
            });

    private final ActivityResultLauncher<ProductHolder> updateProductLauncher = registerForActivityResult(
            new UpdateProductContract(), result -> {
                if(result == null) return;
                Product product = new Product(result.getName(), result.getQuantity(), result.getUnit(), this.listId);

                this.productViewModel.update(product);
            }
    );
}
