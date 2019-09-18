package com.example.nasadaily;

import androidx.appcompat.app.AppCompatActivity;

import android.nfc.Tag;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.DefaultRetryPolicy;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.ImageLoader;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.squareup.picasso.Picasso;

import org.json.JSONObject;

public class MainActivity extends AppCompatActivity implements ApiService.oninfoRecived{


    private ApiService apiService;
    Button btn_req;
    TextView title;
    TextView explanation;
    ImageView img_show;
    ImageLoader imageLoader;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        findview();
        apiService = new ApiService(this);

        btn_req.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                apiService.getMedia(MainActivity.this);
            }
        });
    }

    public void findview(){
        btn_req = findViewById(R.id.btn_req);
        title= findViewById(R.id.txt_title);
        explanation = findViewById(R.id.txt_explanation);
        img_show= findViewById(R.id.img_show);
    }

    @Override
    public void onRecived(NasaInfo info) {
        if(info!=null){

            title.setText(info.getTitle());
            explanation.setText(info.getDescription());
            Picasso.get()
                    .load(info.getImgUrl())
                    .into(img_show);

        } else
            Toast.makeText(this, "Error in reciving info",Toast.LENGTH_LONG).show();
    }
}
