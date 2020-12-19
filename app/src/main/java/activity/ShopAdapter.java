package activity;

import android.content.Context;
import android.location.Location;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.mappedstore.R;
import com.google.android.gms.location.FusedLocationProviderClient;
import com.google.android.gms.location.LocationServices;

import java.util.List;

public class ShopAdapter extends RecyclerView.Adapter<ShopAdapter.ShopViewHolder> {
    //    private static final int REQUEST_CODE = 101;
    private final Context mCtx;
    private final List<Shop> shopList;
    private final Location currentLocation;
    private double distance;

    public ShopAdapter(Context mCtx, List<Shop> shopList) {
        this.mCtx = mCtx;
        this.shopList = shopList;
        this.currentLocation = new Location("");
        FusedLocationProviderClient fusedLocationProviderClient = LocationServices.getFusedLocationProviderClient(mCtx);
        this.distance = 0;
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

    }

    @Override
    public int getItemCount() {
        return shopList.size();
    }

//    private void fetchLastLocation() {
//
//        if (ActivityCompat.checkSelfPermission(mCtx, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(mCtx, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
//            return;
//        }
//
//        Task<Location> task = fusedLocationProviderClient.getLastLocation();
//        task.addOnSuccessListener(location -> {
//            if (location != null) {
//                currentLocation = location;
//            }
//        });
//
//    }

//    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
//        if (requestCode == REQUEST_CODE) {
//            if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
//                fetchLastLocation();
//            } else {
//                Toast.makeText(mCtx, "Call Permission Not Granted!", Toast.LENGTH_SHORT).show();
//
//            }
//        }
//    }

    public double getDistance(Location current, double shopLat, double shopLong) {
        Location shopLocation = new Location("");
        shopLocation.setLatitude(shopLat);
        shopLocation.setLongitude(shopLong);
        distance = current.distanceTo(shopLocation);
        distance = distance / 1000;
        return distance;

    }

    static class ShopViewHolder extends RecyclerView.ViewHolder {
        TextView shopName, price_distance, specialOffers;

        public ShopViewHolder(@NonNull View itemView) {
            super(itemView);
            shopName = itemView.findViewById(R.id.shopName);
            price_distance = itemView.findViewById(R.id.price_distance);
            specialOffers = itemView.findViewById(R.id.specialOffers);
        }
    }
}
