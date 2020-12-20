package activity;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.bumptech.glide.Glide;
import com.example.mappedstore.R;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

public class DetailActivity extends AppCompatActivity {
    private static final String PRODUCT_SHOP_URL = "http://192.168.1.13:8080/android_api/json.php";
    TextView productName, productDescription;
    Button sortPriceBtn, sortDistanceBtn;
    ImageView productImage;
    List<Shop> shopList;
    RecyclerView recyclerView;
    int shop_id;
    String shopName, specialOffers;
    double price, latitude, longitude;
    private int product_id;
    private String name;
    private String description;
    private String image_url;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail);
        Intent intent = getIntent();
        product_id = intent.getExtras().getInt("product_id");
        name = intent.getExtras().getString("productName");
        description = intent.getExtras().getString("productDescription");
        image_url = intent.getExtras().getString("image_url");

        productName = findViewById(R.id.productName);
        productDescription = findViewById(R.id.productDescription);
        productImage = findViewById(R.id.productImage);

        productName.setText(name);
        productDescription.setText(description);
        Glide.with(getApplicationContext()).load(image_url).into(productImage);

        recyclerView = (RecyclerView) findViewById(R.id.recyclerView2);
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        shopList = new ArrayList<>();
        loadShops();


    }

    private void loadShops() {
        StringRequest stringRequest = new StringRequest(Request.Method.GET, PRODUCT_SHOP_URL + "?param1=" + product_id, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                try {
                    JSONArray productShops = new JSONArray(response);

                    for (int i = 0; i < productShops.length(); i++) {
                        JSONObject productShop = productShops.getJSONObject(i);
                        shop_id = productShop.getInt("shop_id");
                        price = productShop.getDouble("price");
                        specialOffers = productShop.getString("specialOffers");
                        name = productShop.getString("name");
                        latitude = productShop.getDouble("latitude");
                        longitude = productShop.getDouble("longitude");

                        Shop shopFinal = new Shop(shop_id, name, price, specialOffers, latitude, longitude);
                        shopList.add(shopFinal);
                    }
                    ShopAdapter adapter = new ShopAdapter(DetailActivity.this, shopList);
                    recyclerView.setAdapter(adapter);

                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Toast.makeText(DetailActivity.this, error.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });
        Volley.newRequestQueue(this).add(stringRequest);
    }

}