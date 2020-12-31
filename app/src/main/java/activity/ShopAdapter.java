package activity;

import android.Manifest;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.location.Location;
import android.net.Uri;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.core.app.ActivityCompat;
import androidx.recyclerview.widget.RecyclerView;

import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.example.mappedstore.R;
import com.google.android.gms.location.FusedLocationProviderClient;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;

import java.util.List;

public class ShopAdapter extends RecyclerView.Adapter<ShopAdapter.ShopViewHolder> {
    private static final int REQUEST_CODE = 101;
    private static final String INSERTION_URL = "http://192.168.1.8:8080/android_api/favinsert.php";
    private static final String LOCATION_URL = "http://192.168.1.8:8080/android_api/shop.php";
    private Context mCtx;
    private List<Shop> shopList;
    private Location currentLocation;
    private FusedLocationProviderClient fusedLocationProviderClient;
    private double distance;
    private String email;
    private int product_id;

    public ShopAdapter(Context mCtx, List<Shop> shopList, String email, int product_id) {
        this.mCtx = mCtx;
        this.shopList = shopList;
        this.currentLocation = new Location("");
        this.fusedLocationProviderClient = LocationServices.getFusedLocationProviderClient(mCtx);
        this.distance = 0;
        this.email = email;
        this.product_id = product_id;
    }

    @NonNull
    @Override
    public ShopViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(mCtx);
        View view = inflater.inflate(R.layout.row_det, null);
        return new ShopViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ShopViewHolder holder, int position) {
        Shop shop = shopList.get(position);
        holder.shopName.setText(shop.getName());
        holder.price_distance.setText("Price: " + shop.getPrice() + ", Distance: " + getDistance(currentLocation, shop.getLatitude(), shop.getLongitude()) + "Km");
        holder.specialOffers.setText(shop.getSpecialOffers());
        holder.getDirection.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(android.content.Intent.ACTION_VIEW, Uri.parse("http://maps.google.com/maps?daddr=" + shop.getLatitude() + "," + shop.getLongitude()));
                mCtx.startActivity(intent);


            }
        });
        holder.addToFav.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                StringRequest stringRequest = new StringRequest(Request.Method.GET, INSERTION_URL + "?param1='" + email + "'&param2=" + shop.getId() + "&param3=" + product_id, new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        Toast.makeText(mCtx, "Added Shop to Favourites", Toast.LENGTH_SHORT).show();

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
        return shopList.size();
    }

    private void fetchLastLocation() {

        if (ActivityCompat.checkSelfPermission(mCtx, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(mCtx, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            return;
        }

        Task<Location> task = fusedLocationProviderClient.getLastLocation();
        task.addOnSuccessListener(new OnSuccessListener<Location>() {
            @Override
            public void onSuccess(Location location) {
                if (location != null) {
                    currentLocation = location;
                }
            }
        });


    }

    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        switch (requestCode) {
            case REQUEST_CODE:
                if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                    fetchLastLocation();
                } else {
                    Toast.makeText(mCtx, "Call Permission Not Granted!", Toast.LENGTH_SHORT).show();

                }
                break;
        }
    }

    public double getDistance(Location current, double shopLat, double shopLong) {
        Location shopLocation = new Location("");
        shopLocation.setLatitude(shopLat);
        shopLocation.setLongitude(shopLong);
        distance = current.distanceTo(shopLocation);
        distance = distance / 1000;
        return distance;

    }

    class ShopViewHolder extends RecyclerView.ViewHolder {
        TextView shopName, price_distance, specialOffers;
        Button addToFav;
        Button getDirection;

        public ShopViewHolder(@NonNull View itemView) {
            super(itemView);
            shopName = itemView.findViewById(R.id.shopName);
            price_distance = itemView.findViewById(R.id.price_distance);
            specialOffers = itemView.findViewById(R.id.specialOffers);
            addToFav = itemView.findViewById(R.id.addtoFav);
            getDirection = itemView.findViewById(R.id.getDirection);

        }
    }
}
