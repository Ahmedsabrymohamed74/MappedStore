package activity;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ListView;
import android.widget.ProgressBar;
import android.widget.Toast;

import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.example.mappedstore.R;
import com.google.android.gms.maps.model.LatLng;

import org.json.JSONArray;
import org.json.JSONObject;

import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.List;


public class DetailActivity extends AppCompatActivity {

    private ProgressBar progressBar;
    public RecyclerView recyclerView;
    public RecyclerView.LayoutManager manager;
    public RecyclerView.Adapter mAdapter;
    public List<Product> products;
    private static final String BASE_URL = "http://196.157.67.181:8080/android_api/json.php";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail);

        Toolbar mToolbar = findViewById(R.id.toolbar);
//        progressBar = findViewById(R.id.progressbar);
        setSupportActionBar(mToolbar);
        ActionBar mActionBar = getSupportActionBar();

//        recyclerView = findViewById(R.id.products_recyclerView);
        manager = new GridLayoutManager(DetailActivity.this, 2);
        recyclerView.setLayoutManager(manager);
        products = new ArrayList<>();

        getProducts();

    }

    private void getProducts() {

        StringRequest stringRequest = new StringRequest(Request.Method.GET, BASE_URL,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        try {

                            JSONArray array = new JSONArray(response);
                            for (int i = 0; i < array.length(); i++) {

                                JSONObject object = array.getJSONObject(i);

                                String shopName = object.getString("shopName");
                                String productName = object.getString("productName");
                                double price = object.getDouble("price");
                                String specialOffers = object.getString("specialOffers");

                                String rate = String.valueOf(price);
                                float newRate = Float.parseFloat(rate);

                                Product product = new Product(shopName, productName, price, specialOffers);
                                products.add(product);

                                if(shopName.equals("Carrefour Cairo Fistival City")){
                                  double carre_lat =  30.029968;
                                  double carre_long = 31.40869;

                                }

                                if(shopName.equals("RadioShack")){
                                    double radio_lat =  30.004335;
                                    double radio_long = 31.424731;
                                }

                                if(shopName.equals("B.Tech")){
                                    double btech_lat =  30.006426;
                                    double btech_long = 31.425114;
                                }

                                if(shopName.equals("2B Computer")){
                                    double twob_lat =  30.025149;
                                    double radio_long = 31.489566;
                                }

                                if(shopName.equals("Samsung Brand Shop")){
                                    double radio_lat =  30.045507;
                                    double radio_long = 31.409397;
                                }

                                if(shopName.equals("LG")){
                                    double radio_lat =  30.065566;
                                    double radio_long = 31.348285;
                                }


                            }

                        } catch (Exception e) {

                        }

                        mAdapter = new RecyclerAdapter(DetailActivity.this, products);
                        recyclerView.setAdapter(mAdapter);

                    }
                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {

                progressBar.setVisibility(View.GONE);
                Toast.makeText(DetailActivity.this, error.toString(), Toast.LENGTH_LONG).show();

            }
        });

        Volley.newRequestQueue(DetailActivity.this).add(stringRequest);

    }

    public double CalculationByDistance(LatLng StartP, LatLng EndP) {
        int Radius = 6371;  // radius of earth in Km
        double lat1 = StartP.latitude; //current user lat
        double lat2 = EndP.latitude;
        double lon1 = StartP.longitude; //current user long
        double lon2 = EndP.longitude;
        double dLat = Math.toRadians(lat2 - lat1);
        double dLon = Math.toRadians(lon2 - lon1);
        double a = Math.sin(dLat / 2) * Math.sin(dLat / 2)
                + Math.cos(Math.toRadians(lat1))
                * Math.cos(Math.toRadians(lat2)) * Math.sin(dLon / 2)
                * Math.sin(dLon / 2);
        double c = 2 * Math.asin(Math.sqrt(a));
        double valueResult = Radius * c;
        double km = valueResult / 1;
        DecimalFormat newFormat = new DecimalFormat("####");
        int kmInDec = Integer.valueOf(newFormat.format(km));
        double meter = valueResult % 1000;
        int meterInDec = Integer.valueOf(newFormat.format(meter));
        Log.i("Radius Value", "" + valueResult + "   KM  " + kmInDec
                + " Meter   " + meterInDec);

        return Radius * c;
    }

}


