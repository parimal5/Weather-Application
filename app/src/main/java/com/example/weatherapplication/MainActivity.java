package com.example.weatherapplication;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

public class MainActivity extends AppCompatActivity {

    EditText getCity;
    TextView showWeather;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        getCity = findViewById(R.id.cityEditText);
        showWeather= findViewById(R.id.weatherInfoTextView);
    }

    public void getWeather(View view){
        String apiKey = "407ba9ea6987bb11201873125a59f475";
        String city = getCity.getText().toString();
        String apiUrl = "https://api.openweathermap.org/data/2.5/weather?q="+ city +"&appid=407ba9ea6987bb11201873125a59f475";

        RequestQueue queue = Volley.newRequestQueue(getApplicationContext());

        JsonObjectRequest request = new JsonObjectRequest(Request.Method.GET, apiUrl, null, new Response.Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject response) {
                try {
                    JSONArray jsonArray = response.getJSONArray("weather");
                    JSONObject object = jsonArray.getJSONObject(0);
                    String main  = object.getString("main");
                    String description = object.getString("description");

                    JSONObject jsonObject = response.getJSONObject("main");
                    String temperature = jsonObject.getString("temp");
                    showWeather.setText(main + "\n" + description + "\nTemperature " + temperature);
                } catch (JSONException e) {
                    Toast.makeText(getApplicationContext(), e.getMessage(), Toast.LENGTH_LONG).show();
                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Toast.makeText(MainActivity.this, "Try Again City Name", Toast.LENGTH_LONG).show();
            }
        });
        queue.add(request);
    }
}
