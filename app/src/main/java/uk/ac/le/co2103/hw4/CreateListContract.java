package uk.ac.le.co2103.hw4;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;

import androidx.activity.result.contract.ActivityResultContract;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

public class CreateListContract extends ActivityResultContract<Void, CreateListHolder> {
    @NonNull
    @Override
    public Intent createIntent(@NonNull Context context, Void input) {
        return new Intent(context, CreateListActivity.class);
    }

    @Override
    public CreateListHolder parseResult(int resultCode, @Nullable Intent intent) {
        if(resultCode != Activity.RESULT_OK) return null;
        if(intent == null)return null;

        return (CreateListHolder) intent.getSerializableExtra("LIST_DATA");
    }
}
