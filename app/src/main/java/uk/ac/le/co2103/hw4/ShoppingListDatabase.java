package uk.ac.le.co2103.hw4;

import android.content.Context;

import androidx.room.Database;
import androidx.room.Room;
import androidx.room.RoomDatabase;
import androidx.room.TypeConverters;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

@Database(entities={ShoppingList.class, Product.class}, version=4, exportSchema=false)
@TypeConverters({Converters.class})
public abstract class ShoppingListDatabase extends RoomDatabase {

    private static volatile ShoppingListDatabase instance;
    private static final int NUM_THREADS = 4;
    public static final ExecutorService databaseWriteExecutor = Executors.newFixedThreadPool(NUM_THREADS);

    public abstract ShoppingListDao listDao();
    public abstract ProductDao productDao();

    public static synchronized ShoppingListDatabase getInstance(final Context context){
        if(instance == null){
            instance = Room.databaseBuilder(context.getApplicationContext(),
                    ShoppingListDatabase.class, "ShoppingListDB")
                    .fallbackToDestructiveMigration()
                    .build();
        }
        return instance;
    }

}
