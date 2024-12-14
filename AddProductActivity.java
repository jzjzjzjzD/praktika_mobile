package com.example.warehousemanager;

import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

public class AddProductActivity extends AppCompatActivity {
    private DatabaseHelper dbHelper;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_product);

        dbHelper = new DatabaseHelper(this);

        ImageView backButton = findViewById(R.id.backButton);
        EditText nameEditText = findViewById(R.id.productNameEditText);
        EditText quantityEditText = findViewById(R.id.productQuantityEditText);
        EditText locationEditText = findViewById(R.id.productLocationEditText);
        Button addButton = findViewById(R.id.addButton);

        backButton.setOnClickListener(v -> finish());

        addButton.setOnClickListener(v -> {
            String name = nameEditText.getText().toString().trim();
            String quantityStr = quantityEditText.getText().toString().trim();
            String location = locationEditText.getText().toString().trim();

            if (name.isEmpty() || quantityStr.isEmpty() || location.isEmpty()) {
                Toast.makeText(this, "Заполните все поля", Toast.LENGTH_SHORT).show();
                return;
            }

            int quantity = Integer.parseInt(quantityStr);
            if (quantity < 0) {
                Toast.makeText(this, "Количество не может быть отрицательным", Toast.LENGTH_SHORT).show();
                return;
            }

            boolean isAdded = dbHelper.addProduct(name, quantity, location);
            if (isAdded) {
                Toast.makeText(this, "Товар добавлен", Toast.LENGTH_SHORT).show();
                finish();
            } else {
                Toast.makeText(this, "Ошибка при добавлении товара", Toast.LENGTH_SHORT).show();
            }
        });
    }
}