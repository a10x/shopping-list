package uk.ac.le.co2103.hw4;

import androidx.activity.result.ActivityResultLauncher;
import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.DefaultItemAnimator;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.app.AlertDialog;
import android.content.Intent;
import android.os.Bundle;

import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.util.ArrayList;
import java.util.Objects;

public class MainActivity extends AppCompatActivity {

    private static final String TAG = MainActivity.class.getSimpleName();
    private ArrayList<ShoppingList> shoppingLists;
    private RecyclerView recyclerView;

    private ShoppingListViewModel shoppingListViewModel;
    private ShoppingListAdapter shoppingListAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        this.recyclerView = findViewById(R.id.recyclerview);

        this.shoppingListViewModel = new ViewModelProvider(this, ViewModelProvider.AndroidViewModelFactory.getInstance(getApplication())).get(ShoppingListViewModel.class);
        this.shoppingListViewModel.getAll().observe(this, shoppingLists -> {
            this.shoppingListAdapter.setShoppingLists((ArrayList<ShoppingList>) shoppingLists);
            Objects.requireNonNull(this.recyclerView.getAdapter()).notifyDataSetChanged();
        });

        final FloatingActionButton button = findViewById(R.id.fab);
        button.setOnClickListener(view -> {
            addShoppingListLauncher.launch(null);
        });

        this.setAdapter();
    }

    private void setAdapter(){
        this.shoppingListAdapter = new ShoppingListAdapter((ArrayList<ShoppingList>) this.shoppingListViewModel.getAll().getValue());
        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(getApplicationContext());
        this.recyclerView.setLayoutManager(layoutManager);
        this.recyclerView.setItemAnimator(new DefaultItemAnimator());
        this.recyclerView.setAdapter(this.shoppingListAdapter);

        this.shoppingListAdapter.setShoppingListClickListener(position -> {
            int id = Objects.requireNonNull(this.shoppingListViewModel.getAll().getValue()).get(position).getListId();
            Intent intent = new Intent(getApplication(), ShoppingListActivity.class);
            intent.putExtra("LIST_ID", id);

            startActivity(intent);
        });

        this.shoppingListAdapter.setShoppingListLongPressListener(position -> {
            ShoppingList shoppingList = Objects.requireNonNull(this.shoppingListViewModel.getAll().getValue()).get(position);

            AlertDialog.Builder alert = new AlertDialog.Builder(this);
            alert.setTitle("Delete Shopping List");
            alert.setMessage("Are you sure you want to delete " + shoppingList.getName() + "?");
            alert.setPositiveButton("Delete", (dialog, which) -> {
                this.shoppingListViewModel.delete(shoppingList.getListId());
            });

            alert.setNegativeButton("No", ((dialog, which) -> {
            }));

            alert.create().show();
        });
    }

    private final ActivityResultLauncher<Void> addShoppingListLauncher = registerForActivityResult(
            new CreateListContract(), result -> {
                if(result == null) return;
                ShoppingList s = new ShoppingList(result.getListName(), result.getImageArray());
                shoppingListViewModel.insert(s);
            });
}