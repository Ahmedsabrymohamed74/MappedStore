package activity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
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
import java.util.HashMap;
import java.util.List;

import helper.SQLiteHandler;
import helper.SessionManager;

public class DetailActivity extends AppCompatActivity {
    private static final String PRODUCT_SHOP_URL = "http://192.168.1.8:8080/android_api/json.php";
    TextView productName, productDescription;
    ImageView productImage;
    List<Shop> shopList;
    RecyclerView recyclerView;
    int shop_id;
    String specialOffers;
    double price, latitude, longitude;
    String email;
    private int product_id;
    private String name;
    private String description;
    private String image_url;
    private Button btnLinkToMain;
    private SQLiteHandler db;
    private SessionManager session;


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

        btnLinkToMain = (Button) findViewById(R.id.btnLinkToMainScreen);

        btnLinkToMain.setOnClickListener(new View.OnClickListener() {

            public void onClick(View view) {
                Intent i = new Intent(getApplicationContext(),
                        MainActivity.class);
                startActivity(i);
                finish();
            }
        });

        // SqLite database handler
        db = new SQLiteHandler(getApplicationContext());

        // session manager
        session = new SessionManager(getApplicationContext());


        // Fetching user details from sqlite
        HashMap<String, String> user = db.getUserDetails();

        email = user.get("email");

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
                    ShopAdapter adapter = new ShopAdapter(DetailActivity.this, shopList, email, product_id);
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