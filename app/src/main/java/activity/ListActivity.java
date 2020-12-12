package activity;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.widget.Toolbar;

import com.example.mappedstore.R;


public class    ListActivity extends Activity implements AdapterView.OnItemClickListener{
    android.widget.ListView listView;

    String[] product_name = {"Huawei MatePad T8 Tablet", "Samsung Galaxy S20 Dual SIM", "Samsung Smart TV", "LG Smart TV"};
    String[] description = {"8 Inch, 16 GB, 2 GB RAM", "128GB, 8GB RAM, 4G LTE", "55 Inch 4K Ultra HD Smart LED TV with Built-in Receiver", "43 Inch Smart LED Full HD TV With Built In Receiver - 43Lm6300"};
    int[] images = {R.drawable.huawei_matepad_tablet, R.drawable.samsung_galaxy_s20, R.drawable.samsung_smart_tv, R.drawable.lg_smart_tv};


    Toolbar toolbar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_list);

        listView = findViewById(R.id.listView);
        toolbar = findViewById(R.id.productlist);

        toolbar.setTitle(R.string.products);
        MyAdapter adapter = new MyAdapter(this, product_name, description, images);
        listView.setAdapter(adapter);
        listView.setOnItemClickListener(this);

    }

    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

        Intent intent = new Intent(ListActivity.this, DetailActivity.class);

        intent.putExtra("productName", product_name[position]);
        intent.putExtra("prodcutImage", images[position]);
        intent.putExtra("description", description[position]);
        startActivity(intent);
    }


    class MyAdapter extends ArrayAdapter<String> {

        Context context;
        String[] rName;
        int[] rImgs;
        String[] rDescription;

        MyAdapter(Context c, String[] product_name, String[] description, int[] imgs) {
            super(c, R.layout.row, R.id.textView1, product_name);
            this.context = c;
            this.rName = product_name;
            this.rImgs = imgs;
            this.rDescription = description;
        }

        @NonNull
        @Override
        public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
            LayoutInflater layoutInflater = (LayoutInflater) getApplicationContext().getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            View row = layoutInflater.inflate(R.layout.row, parent, false);
            ImageView images = row.findViewById(R.id.image);
            TextView myTitle = row.findViewById(R.id.textView1);
            myTitle.setText(rName[position]);
//            TextView rDescription = row.findViewById(R.id.textView1);

            images.setImageResource(rImgs[position]);
            myTitle.setText(rName[position]);

            return row;
        }
    }

    public void onClick(View v) {

    }
}


