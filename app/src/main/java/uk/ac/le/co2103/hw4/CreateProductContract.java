package uk.ac.le.co2103.hw4;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;

import androidx.activity.result.contract.ActivityResultContract;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

public class CreateProductContract extends ActivityResultContract<Void, ProductHolder> {

    @NonNull
    @Override
    public Intent createIntent(@NonNull Context context, Void input) {
        return new Intent(context, AddProductActivity.class);
    }

    @Override
    public ProductHolder parseResult(int resultCode, @Nullable Intent intent) {
        if(resultCode != Activity.RESULT_OK) return null;
        if(intent == null)return null;

        return (ProductHolder) intent.getSerializableExtra("PRODUCT_DATA");
    }
}
