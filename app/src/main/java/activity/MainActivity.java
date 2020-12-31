package activity;


import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.example.mappedstore.R;

import java.util.HashMap;

import helper.SQLiteHandler;
import helper.SessionManager;

//import com.vishnusivadas.advanced_httpurlconnection.PutData;

public class MainActivity extends Activity {

    private TextView txtName;
    private TextView txtEmail;
    private Button btnLogout;
    private Button btnProducts;
    private Button btnMaps;
    private Button btnFavourites;
    private SQLiteHandler db;
    private SessionManager session;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        // Intent intent = getIntent();
        // email = intent.getExtras().getString("email");

        txtName = (TextView) findViewById(R.id.name);
        txtEmail = (TextView) findViewById(R.id.email);
        btnLogout = (Button) findViewById(R.id.btnLogout);
        btnProducts = (Button) findViewById(R.id.btn_products);
        btnMaps = (Button) findViewById(R.id.btn_maps);
        btnFavourites = findViewById(R.id.btnfavourites);



        // SqLite database handler
        db = new SQLiteHandler(getApplicationContext());

        // session manager
        session = new SessionManager(getApplicationContext());

        if (!session.isLoggedIn()) {
            logoutUser();
        }

        // Fetching user details from sqlite
        HashMap<String, String> user = db.getUserDetails();

        String name = user.get("name");
        String email = user.get("email");


        // Displaying the user details on the screen
        txtName.setText(name);
        txtEmail.setText(email);

        // Logout button click event
        btnLogout.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                logoutUser();
            }
        });

//        ToProducts Activity
        btnProducts.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                toProductList();
            }
        });

        //  Maps Activity
        btnMaps.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                toMaps();
            }
        });

        //  Favourites Activity
        btnFavourites.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                toFavs();
            }
        });


    }

    /**
     * Logging out the user. Will set isLoggedIn flag to false in shared
     * preferences Clears the user data from sqlite users table
     */
    private void logoutUser() {
        session.setLogin(false);

        db.deleteUsers();

        // Launching the login activity
        Intent intent = new Intent(MainActivity.this, LoginActivity.class);
        startActivity(intent);
        finish();
    }


    private void toProductList() {

        //Launching List activity
        Intent intent = new Intent(MainActivity.this, ListActivity.class);
        startActivity(intent);
        finish();

    }

    private void toMaps() {

        //Launching Maps activity
        Intent intent = new Intent(MainActivity.this, MapsActivity.class);
        startActivity(intent);
        finish();

    }

    private void toFavs() {

        //Launching Favourites activity
        Intent intent = new Intent(MainActivity.this, Favourites.class);
        startActivity(intent);
        finish();

    }


}