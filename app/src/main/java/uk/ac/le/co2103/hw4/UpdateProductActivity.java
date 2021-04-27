package uk.ac.le.co2103.hw4;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

public class UpdateProductActivity extends AppCompatActivity {

    private EditText editProductName;
    private EditText editProductQuantity;
    private Spinner spinProductUnit;
    private Button btnProductAdd;
    private Button btnQuanIncre;
    private Button btnQuanDecre;

    private boolean[] enableButton;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_update_product);

        this.enableButton = new boolean[2];
        this.enableButton[0] = true;
        this.enableButton[1] = true;

        this.editProductName = findViewById(R.id.editTextName);
        this.editProductQuantity = findViewById(R.id.editTextQuantity);
        this.spinProductUnit = findViewById(R.id.spinner);
        this.btnProductAdd = findViewById(R.id.btn_product_add);
        this.btnQuanIncre = findViewById(R.id.btn_quan_incre);
        this.btnQuanDecre = findViewById(R.id.btn_quan_decre);

        ProductHolder productHolder = (ProductHolder) this.getIntent().getSerializableExtra("OLD_PRODUCT_DATA");

        this.editProductName.setText(productHolder.getName());
        this.editProductQuantity.setText(String.valueOf(productHolder.getQuantity()));

        String[] unitLabels = getResources().getStringArray(R.array.labels_unit);
        for(int i = 0; i < unitLabels.length; ++i){
            if(unitLabels[i].equals(productHolder.getUnit().name())){
                this.spinProductUnit.setSelection(i);
                break;
            }
        }

        this.btnProductAdd.setEnabled(enableButton[0] && enableButton[1]);

        this.btnQuanIncre.setOnClickListener(click -> {
            int quantity = Integer.parseInt(this.editProductQuantity.getText().toString());
            ++quantity;
            this.editProductQuantity.setText(String.valueOf(quantity));
        });

        this.btnQuanDecre.setOnClickListener(click -> {
            int quantity = Integer.parseInt(this.editProductQuantity.getText().toString());
            --quantity;
            this.editProductQuantity.setText(String.valueOf(quantity));
        });

        this.editProductName.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
            }
            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
            }

            @Override
            public void afterTextChanged(Editable s) {
                enableButton[0] = ValidationUtils.ValidateProductName(s.toString());

                btnProductAdd.setEnabled(enableButton[0] && enableButton[1]);
            }
        });

        this.editProductQuantity.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
            }
            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
            }

            @Override
            public void afterTextChanged(Editable s) {
                enableButton[1] = ValidationUtils.ValidateProductQuantity(s.toString());

                btnProductAdd.setEnabled(enableButton[0] && enableButton[1]);
            }
        });

        this.btnProductAdd.setOnClickListener(v -> {
            Intent intent = new Intent(getApplicationContext(), ShoppingListActivity.class);

            String productName = this.editProductName.getText().toString();
            int productQuantity = Integer.parseInt(this.editProductQuantity.getText().toString());
            Unit productUnit = Unit.valueOf(this.spinProductUnit.getSelectedItem().toString().toUpperCase());

            intent.putExtra("NEW_PRODUCT_DATA", new ProductHolder(productName, productQuantity, productUnit));
            setResult(Activity.RESULT_OK, intent);
            finish();
        });

    }
}
