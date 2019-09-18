package com.example.nasadaily;

import android.content.Context;
import android.util.JsonReader;
import android.util.Log;

import com.android.volley.DefaultRetryPolicy;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.UnsupportedEncodingException;
import java.net.URL;
import java.net.URLDecoder;

public class ApiService {
    private static final String TAG = "NasaDailyActivity";
    private Context context;

    public ApiService (Context context){
        this.context=context;
    }

    public void getMedia(final oninfoRecived oninfoRecived)
    {
        JsonObjectRequest request;
        request = new JsonObjectRequest(Request.Method.GET,
                "https://api.nasa.gov/planetary/apod?api_key=tetcL7A07j1LjXZX2tfHXi2ET4Bmd3QaRk9l1r15",
                null, new Response.Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject response) {
                Log.i(TAG, "on Response:" + response.toString());
                oninfoRecived.onRecived(parseResponseToNasaInfo(response));

            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Log.e(TAG, "onErrorResponse: " + error.toString());
                oninfoRecived.onRecived(null);
            }
        });

        request.setRetryPolicy(new DefaultRetryPolicy(DefaultRetryPolicy.DEFAULT_TIMEOUT_MS * 2, DefaultRetryPolicy.DEFAULT_MAX_RETRIES, DefaultRetryPolicy.DEFAULT_BACKOFF_MULT));
        RequestQueue requestQueue = Volley.newRequestQueue(context);
        requestQueue.add(request);
    }

    public interface oninfoRecived  { void onRecived(NasaInfo info);}

    private NasaInfo parseResponseToNasaInfo(JSONObject response)
    {
        NasaInfo nasaInfo = new NasaInfo();
        try {
            String jsonTitle = response.getString("title");
            nasaInfo.setTitle(jsonTitle);

            String Explanation = response.getString("explanation");
            nasaInfo.setDescription(Explanation);

            String imgUrl = response.getString("url");
            nasaInfo.setImgUrl(imgUrl);
            //Log.i("EXP", Explanation);

            return nasaInfo;
        } catch (JSONException e) {
            e.printStackTrace();
            return null;
        }

    }
}
