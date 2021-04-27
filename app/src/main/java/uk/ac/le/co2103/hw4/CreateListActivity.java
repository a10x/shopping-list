package uk.ac.le.co2103.hw4;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;

import androidx.activity.result.ActivityResultCallback;
import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.res.ResourcesCompat;

import java.io.FileNotFoundException;
import java.io.InputStream;

public class CreateListActivity extends AppCompatActivity {

    private EditText editListName;
    private Button btnPickImg;
    private Button btnCreateList;
    private ImageView imgListSelected;

    private Bitmap selectedBitmap = null;

    ActivityResultLauncher<String> getImageLauncher = registerForActivityResult(new ActivityResultContracts.GetContent(),
            new ActivityResultCallback<Uri>() {
                @Override
                public void onActivityResult(Uri result) {
                    if(result != null) {
                        try {
                            InputStream inputStream = getContentResolver().openInputStream(result);
                            selectedBitmap = BitmapFactory.decodeStream(inputStream);

                            Bitmap scaledBitmap = Bitmap.createScaledBitmap(selectedBitmap, 150, 120, false);
                            imgListSelected.setImageBitmap(selectedBitmap);
                            selectedBitmap = scaledBitmap;

                            return;
                        } catch (FileNotFoundException e) {
                            imgListSelected.setImageDrawable(ResourcesCompat.getDrawable(getResources(), R.drawable.ic_shopping_cart, null));
                        }
                    }
                    imgListSelected.setImageDrawable(ResourcesCompat.getDrawable(getResources(), R.drawable.ic_shopping_cart, null));
                }
            }
        );

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_create_list);

        this.btnPickImg = findViewById(R.id.btn_pick_img);
        this.imgListSelected = findViewById(R.id.img_list_selected);
        this.btnCreateList = findViewById(R.id.btn_list_add);
        this.editListName = findViewById(R.id.edit_list_name);

        this.editListName.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {}

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {}

            @Override
            public void afterTextChanged(Editable s) {
                btnCreateList.setEnabled(ValidationUtils.ValidateShoppingListName(s.toString()));
            }
        });

        this.btnPickImg.setOnClickListener(v -> getImageLauncher.launch("image/*"));

        this.btnCreateList.setOnClickListener(view -> {
            Intent intent = new Intent(getApplicationContext(), MainActivity.class);
            String listName = this.editListName.getText().toString();
            byte[] imageData =  ImageUtils.BitmapToByteArray(selectedBitmap);

            intent.putExtra("LIST_DATA", new CreateListHolder(listName, imageData));
            setResult(Activity.RESULT_OK, intent);
            finish();
        });
    }
}
