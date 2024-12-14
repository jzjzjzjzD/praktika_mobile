package com.example.warehousemanager;

import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;
import androidx.appcompat.app.AppCompatActivity;

public class ReceiveProductActivity extends AppCompatActivity {
    private DatabaseHelper dbHelper;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_receive_product);

        dbHelper = new DatabaseHelper(this);

        ImageView backButton = findViewById(R.id.backButton);
        EditText productIdEditText = findViewById(R.id.productIdEditText);
        EditText quantityEditText = findViewById(R.id.quantityEditText);
        Button receiveButton = findViewById(R.id.receiveButton);

        backButton.setOnClickListener(v -> finish());

        receiveButton.setOnClickListener(v -> {
            String productIdStr = productIdEditText.getText().toString().trim();
            String quantityStr = quantityEditText.getText().toString().trim();

            if (productIdStr.isEmpty() || quantityStr.isEmpty()) {
                Toast.makeText(this, "Заполните все поля", Toast.LENGTH_SHORT).show();
                return;
            }

            int productId = Integer.parseInt(productIdStr);
            int quantity = Integer.parseInt(quantityStr);

            if (quantity < 0) {
                Toast.makeText(this, "Количество не может быть отрицательным", Toast.LENGTH_SHORT).show();
                return;
            }

            boolean isUpdated = dbHelper.updateQuantity(productId, quantity);
            if (isUpdated) {
                Toast.makeText(this, "Товар принят", Toast.LENGTH_SHORT).show();
                finish();
            } else {
                Toast.makeText(this, "Ошибка при приёмке товара", Toast.LENGTH_SHORT).show();
            }
        });
    }
}