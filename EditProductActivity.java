package com.example.warehousemanager;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

public class EditProductActivity extends AppCompatActivity {
    private DatabaseHelper dbHelper;
    private int productId;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_product);

        dbHelper = new DatabaseHelper(this);

        // Получение данных о товаре из Intent
        Intent intent = getIntent();
        productId = intent.getIntExtra("productId", -1);
        String productName = intent.getStringExtra("productName");
        int productQuantity = intent.getIntExtra("productQuantity", 0);
        String productLocation = intent.getStringExtra("productLocation");

        // Инициализация UI
        ImageView backButton = findViewById(R.id.backButton);
        EditText nameEditText = findViewById(R.id.productNameEditText);
        EditText quantityEditText = findViewById(R.id.productQuantityEditText);
        EditText locationEditText = findViewById(R.id.productLocationEditText);
        Button saveButton = findViewById(R.id.saveButton);
        Button deleteButton = findViewById(R.id.deleteButton); // Кнопка "Удалить"

        // Установка данных в поля ввода
        nameEditText.setText(productName);
        quantityEditText.setText(String.valueOf(productQuantity));
        locationEditText.setText(productLocation);

        // Обработка кнопки "Назад"
        backButton.setOnClickListener(v -> finish());

        // Обработка кнопки "Сохранить"
        saveButton.setOnClickListener(v -> {
            String newName = nameEditText.getText().toString().trim();
            String newQuantityStr = quantityEditText.getText().toString().trim();
            String newLocation = locationEditText.getText().toString().trim();

            if (newName.isEmpty() || newQuantityStr.isEmpty() || newLocation.isEmpty()) {
                Toast.makeText(this, "Заполните все поля", Toast.LENGTH_SHORT).show();
                return;
            }

            int newQuantity = Integer.parseInt(newQuantityStr);
            if (newQuantity < 0) {
                Toast.makeText(this, "Количество не может быть отрицательным", Toast.LENGTH_SHORT).show();
                return;
            }

            // Обновление данных в базе данных
            boolean isUpdated = dbHelper.updateProduct(productId, newName, newQuantity, newLocation);
            if (isUpdated) {
                Toast.makeText(this, "Товар обновлён", Toast.LENGTH_SHORT).show();
                finish();
            } else {
                Toast.makeText(this, "Ошибка при обновлении товара", Toast.LENGTH_SHORT).show();
            }
        });

        // Обработка кнопки "Удалить"
        deleteButton.setOnClickListener(v -> {
            boolean isDeleted = dbHelper.deleteProduct(productId);
            if (isDeleted) {
                Toast.makeText(this, "Товар удалён", Toast.LENGTH_SHORT).show();
                finish();
            } else {
                Toast.makeText(this, "Ошибка при удалении товара", Toast.LENGTH_SHORT).show();
            }
        });
    }
}