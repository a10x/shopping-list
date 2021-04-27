package uk.ac.le.co2103.hw4;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;

import androidx.activity.result.contract.ActivityResultContract;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

public class UpdateProductContract extends ActivityResultContract<ProductHolder, ProductHolder> {

    @NonNull
    @Override
    public Intent createIntent(@NonNull Context context, ProductHolder input) {
        Intent intent = new Intent(context, UpdateProductActivity.class);
        intent.putExtra("OLD_PRODUCT_DATA", input);

        return intent;
    }

    @Override
    public ProductHolder parseResult(int resultCode, @Nullable Intent intent) {
        if(resultCode != Activity.RESULT_OK) return null;
        if(intent == null) return null;

        return (ProductHolder) intent.getSerializableExtra("NEW_PRODUCT_DATA");
    }
}
