package com.example.warehousemanager;

import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.view.View;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {
    private DatabaseHelper dbHelper;
    private RecyclerView productRecyclerView;
    private List<Product> productList;
    private ProductAdapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        dbHelper = new DatabaseHelper(this);
        productRecyclerView = findViewById(R.id.productRecyclerView);
        productList = new ArrayList<>();
        adapter = new ProductAdapter(productList);
        productRecyclerView.setLayoutManager(new LinearLayoutManager(this));
        productRecyclerView.setAdapter(adapter);

        loadProducts();

        findViewById(R.id.addProductButton).setOnClickListener(v -> {
            startActivity(new Intent(MainActivity.this, AddProductActivity.class));
        });

        findViewById(R.id.receiveProductButton).setOnClickListener(v -> {
            startActivity(new Intent(MainActivity.this, ReceiveProductActivity.class));
        });

        findViewById(R.id.shipProductButton).setOnClickListener(v -> {
            startActivity(new Intent(MainActivity.this, ShipProductActivity.class));
        });

        // Обработка нажатия на товар
        productRecyclerView.addOnItemTouchListener(new RecyclerItemClickListener(this, productRecyclerView,
                new RecyclerItemClickListener.OnItemClickListener() {
                    @Override
                    public void onItemClick(View view, int position) {
                        Product product = productList.get(position);
                        Intent intent = new Intent(MainActivity.this, EditProductActivity.class);
                        intent.putExtra("productId", product.getId());
                        intent.putExtra("productName", product.getName());
                        intent.putExtra("productQuantity", product.getQuantity());
                        intent.putExtra("productLocation", product.getLocation());
                        startActivity(intent);
                    }

                    @Override
                    public void onLongItemClick(View view, int position) {
                        // Опционально: обработка долгого нажатия
                    }
                }));
    }

    @Override
    protected void onResume() {
        super.onResume();
        loadProducts();
    }

    private void loadProducts() {
        productList.clear();
        Cursor cursor = dbHelper.getAllProducts();
        while (cursor.moveToNext()) {
            int id = cursor.getInt(cursor.getColumnIndexOrThrow("id"));
            String name = cursor.getString(cursor.getColumnIndexOrThrow("name"));
            int quantity = cursor.getInt(cursor.getColumnIndexOrThrow("quantity"));
            String location = cursor.getString(cursor.getColumnIndexOrThrow("location"));
            productList.add(new Product(id, name, quantity, location));
        }
        cursor.close();
        adapter.notifyDataSetChanged();
    }
}