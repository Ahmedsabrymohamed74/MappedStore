package activity;


import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.example.mappedstore.*;
import com.example.mappedstore.R;

import java.util.ArrayList;
import java.util.List;

public class RecyclerAdapter extends RecyclerView.Adapter<RecyclerAdapter.MyViewHolder> {

    private final Context mContext;
    private List<Product> products = new ArrayList<>();


    public RecyclerAdapter(Context context, List<Product> products) {
        this.mContext = context;
        this.products = products;
    }

    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

        View view = LayoutInflater.from(mContext).inflate(R.layout.activity_detail, parent, false);
        return new MyViewHolder(view);
    }


    @Override
    public int getItemCount() {
        return 0;
    }

    @SuppressLint("SetTextI18n")
    @Override
    public void onBindViewHolder(@NonNull MyViewHolder holder, int position) {

        final Product product = products.get(position);

        holder.mPrice.setText("EGP " + product.getPrice());
//        holder.mPrice.setPrice(product.getPrice());
        holder.mShopName.setText(product.getShopName());
//        Glide.with(mContext).load(product.getProductName()).into(holder.mproductNameView);

        holder.mContainer.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent intent = new Intent(mContext, DetailActivity.class);

                intent.putExtra("shopName", product.getShopName());
                intent.putExtra("productName", product.getProductName());
                intent.putExtra("price", product.getPrice());
                intent.putExtra("specialOffers", product.getSpecialOffers());

                mContext.startActivity(intent);

            }
        });
    }

    public class MyViewHolder extends RecyclerView.ViewHolder {

        public TextView mShopName;
        public TextView mproductNameView;
        public TextView mPrice;
        public TextView mSpecialOffers;
        public LinearLayout mContainer;


        public MyViewHolder(View view) {
            super(view);

            mShopName = view.findViewById(R.id.shop_name);
            mproductNameView = view.findViewById(R.id.product_name);
            mPrice = view.findViewById(R.id.price);
            mSpecialOffers = view.findViewById(R.id.special_offers);
//            mContainer = view.findViewById(R.id.product_container);
        }
    }

//    @Override
//    public int getItemCount() {
//        return products.size();
//    }
}