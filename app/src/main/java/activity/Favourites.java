package activity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.example.mappedstore.R;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import helper.SQLiteHandler;
import helper.SessionManager;

public class Favourites extends AppCompatActivity {
    private static final String FAV_URL = "http://192.168.1.8:8080/android_api/fav.php";
    List<Favourite> favList;
    RecyclerView recyclerView;
    SQLiteHandler db;
    SessionManager session;
    String email;
    String result = null;
    private Button btnLinkToMain;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_favourites);
        btnLinkToMain = findViewById(R.id.btnLinkToMainScreen);

        recyclerView = (RecyclerView) findViewById(R.id.recyclerView);
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        favList = new ArrayList<>();

        // SqLite database handler
        db = new SQLiteHandler(getApplicationContext());

        // session manager
        session = new SessionManager(getApplicationContext());


        // Fetching user details from sqlite
        HashMap<String, String> user = db.getUserDetails();
        email = user.get("email");
//        Log.i("tagconvertstr", "[" + result + "]");
        loadFavourites();

        btnLinkToMain.setOnClickListener(new View.OnClickListener() {

            public void onClick(View view) {
                Intent i = new Intent(getApplicationContext(),
                        MainActivity.class);
                startActivity(i);
                finish();
            }
        });

    }

    private void loadFavourites() {
        StringRequest stringRequest = new StringRequest(Request.Method.GET, FAV_URL + "?param1='" + email + "'", new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                try {
//                    Log.i("tagconvertstr", "[" + result + "]");
                    JSONArray favourites = new JSONArray(response);
                    for (int i = 0; i < favourites.length(); i++) {
                        JSONObject favouriteObject = favourites.getJSONObject(i);
                        int id = favouriteObject.getInt("id");
                        String shop = favouriteObject.getString("shop_name");
                        String product = favouriteObject.getString("product_name");
                        double price = favouriteObject.getDouble("price");
                        String specialOffers = favouriteObject.getString("specialOffers");
                        String image_url = favouriteObject.getString("image_url");
                        Favourite favourite = new Favourite(id, shop, product, price, specialOffers, image_url);
                        favList.add(favourite);

                    }
                    FavouriteAdapter adapter = new FavouriteAdapter(Favourites.this, favList, email);
                    recyclerView.setAdapter(adapter);
                    adapter.notifyDataSetChanged();
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Toast.makeText(Favourites.this, error.getMessage(), Toast.LENGTH_SHORT).show();

            }
        });
        Volley.newRequestQueue(this).add(stringRequest);
    }
}