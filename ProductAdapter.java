package com.example.warehousemanager;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

public class ProductAdapter extends RecyclerView.Adapter<ProductAdapter.ProductViewHolder> {
    private List<Product> productList;

    public ProductAdapter(List<Product> productList) {
        this.productList = productList;
    }

    @NonNull
    @Override
    public ProductViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_product, parent, false);
        return new ProductViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ProductViewHolder holder, int position) {
        Product product = productList.get(position);
        holder.productIdTextView.setText("ID: " + product.getId());
        holder.productNameTextView.setText("Название: " + product.getName());
        holder.productQuantityTextView.setText("Количество: " + product.getQuantity());
        holder.productLocationTextView.setText("Местоположение: " + product.getLocation());
    }

    @Override
    public int getItemCount() {
        return productList.size();
    }

    static class ProductViewHolder extends RecyclerView.ViewHolder {
        TextView productIdTextView, productNameTextView, productQuantityTextView, productLocationTextView;

        public ProductViewHolder(@NonNull View itemView) {
            super(itemView);
            productIdTextView = itemView.findViewById(R.id.productIdTextView);
            productNameTextView = itemView.findViewById(R.id.productNameTextView);
            productQuantityTextView = itemView.findViewById(R.id.productQuantityTextView);
            productLocationTextView = itemView.findViewById(R.id.productLocationTextView);
        }
    }
}