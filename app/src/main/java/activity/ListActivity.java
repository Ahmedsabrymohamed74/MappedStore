package activity;

import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Toast;

import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.android.volley.Request;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.example.mappedstore.R;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;


public class ListActivity extends Activity implements AdapterView.OnItemClickListener {
    private static final String PRODUCT_URL = "http://192.168.1.5:8080/android_api/jsonlist.php";

    List<Product> productList;
    RecyclerView recyclerView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_list);

        recyclerView = findViewById(R.id.recyclerView);
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        productList = new ArrayList<>();
        loadProducts();

    }

    private void loadProducts() {
        StringRequest stringRequest = new StringRequest(Request.Method.GET, PRODUCT_URL, response -> {
            try {
                JSONArray products = new JSONArray(response);

                for (int i = 0; i < products.length(); i++) {
                    JSONObject productObject = products.getJSONObject(i);
                    String name = productObject.getString("name");
                    String description = productObject.getString("description");
                    String image_url = productObject.getString("image_url");
                    Product product = new Product(name, description, image_url);
                    productList.add(product);
                }
                ProductAdapter adapter = new ProductAdapter(ListActivity.this, productList);
                recyclerView.setAdapter(adapter);

            } catch (JSONException e) {
                e.printStackTrace();
            }
        }, error -> Toast.makeText(ListActivity.this, error.getMessage(), Toast.LENGTH_SHORT).show());
        Volley.newRequestQueue(this).add(stringRequest);
    }

    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

    }
}


