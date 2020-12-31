package activity;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.bumptech.glide.Glide;
import com.example.mappedstore.R;

import java.util.List;

public class FavouriteAdapter extends RecyclerView.Adapter<FavouriteAdapter.FavouriteViewHolder> {
    private static final String DELETE_URL = "http://192.168.1.8:8080/android_api/favdelete.php";
    private Context mCtx;
    private List<Favourite> FavList;
    private String email;

    public FavouriteAdapter(Context mCtx, List<Favourite> favList, String email) {
        this.mCtx = mCtx;
        this.FavList = favList;
        this.email = email;
    }

    @NonNull
    @Override
    public FavouriteViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(mCtx);
        View view = inflater.inflate(R.layout.row_fav, null);
        return new FavouriteViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull FavouriteViewHolder holder, int position) {
        Favourite favourite = FavList.get(position);
        holder.shop.setText(favourite.getShop());
        holder.product.setText(favourite.getProduct());
        holder.specialOffers.setText(favourite.getSpecialOffers());
        holder.price.setText("Price: " + favourite.getPrice());
        Glide.with(mCtx).load(favourite.getImage_url()).into(holder.image);
        holder.deleteFav.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                StringRequest stringRequest = new StringRequest(Request.Method.GET, DELETE_URL + "?param1='" + email + "'&param2=" + favourite.getProduct_shop_id(), new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {

                        Toast.makeText(mCtx, "Deleted from favourites", Toast.LENGTH_SHORT).show();

                    }
                }, new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        Toast.makeText(mCtx, error.getMessage(), Toast.LENGTH_SHORT).show();
                    }
                });
                Volley.newRequestQueue(mCtx).add(stringRequest);

            }
        });

    }

    @Override
    public int getItemCount() {
        return FavList.size();
    }


    class FavouriteViewHolder extends RecyclerView.ViewHolder {
        TextView shop, product, price, specialOffers;
        ImageView image;
        Button deleteFav;

        public FavouriteViewHolder(@NonNull View itemView) {
            super(itemView);
            shop = itemView.findViewById(R.id.Shop);
            product = itemView.findViewById(R.id.Product);
            specialOffers = itemView.findViewById(R.id.SpecialOffers);
            price = itemView.findViewById(R.id.Price);
            image = itemView.findViewById(R.id.productImage);
            deleteFav = itemView.findViewById(R.id.deleteFav);
        }
    }
}


