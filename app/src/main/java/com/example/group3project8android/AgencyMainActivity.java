package com.example.group3project8android;

/*
 *   Krzysztof Stalmach
 *   Project 8 - CMPP-264 Android
 *   Agency Main Activity
 */
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.ListView;
import android.widget.SimpleAdapter;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.android.volley.DefaultRetryPolicy;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.concurrent.Executors;

import model.Agency;

public class AgencyMainActivity extends AppCompatActivity {
    Button btnRefresh;
    ListView lvAgencies;
    ArrayList<Agency> agencies = new ArrayList<>();
    ArrayList<HashMap<String, String>> agenciesData = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main_agency);

        btnRefresh = findViewById(R.id.btnRefresh);
        lvAgencies = findViewById(R.id.lvAgencies);

        btnRefresh.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                agenciesData.clear();
                agencies.clear();
                getAgencies();
            }
        });

        getAgencies();

        lvAgencies.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                Intent intent = new Intent(getApplicationContext(), com.example.group3project8android.AgencyDetailActivity.class);
                Agency agncy = agencies.get(i);
                intent.putExtra("agency", agncy);
                startActivity(intent);
            }
        });
    }

    private void getAgencies() {
        Executors.newSingleThreadExecutor().execute(new Runnable() {
            @Override
            public void run() {
                Log.d("Krzys", "In getAgencies Request");
//                String url = "http://10.0.2.2:8080/Group3REST-1.0-SNAPSHOT/api/agency/getagencies";
                String url = "http://10.0.2.2:8080/Group3REST-1.0-SNAPSHOT/api/agency/getagencies";

                StringRequest request = new StringRequest(url, new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        try {
                            JSONArray agencyArray = new JSONArray(response);

                            for (int i = 0; i < agencyArray.length(); i++) {
                                JSONObject agencyObject = agencyArray.getJSONObject(i);
                                Integer id = agencyObject.getInt("agencyId");
                                String address = agencyObject.getString("agncyAddress");
                                String city = agencyObject.getString("agncyCity");
                                String prov = agencyObject.getString("agncyProv");
                                String post = agencyObject.getString("agncyPostal");
                                String country = agencyObject.getString("agncyCountry");
                                String phone = agencyObject.getString("agncyPhone");
                                String fax = agencyObject.getString("agncyFax");

                                agencies.add(new Agency(id, address, city, prov, post, country, phone, fax));
                                Log.d("Krzys", "Agency:" + String.valueOf(agencies.get(i)));
                            }

                            Log.d("Krzys", "hashmap");
                            for (Agency a : agencies) {
                                HashMap<String, String> map = new HashMap<>();
                                map.put("agencyId", a.getAgencyId() + "");
                                map.put("agncyCity", a.getAgncyCity());
                                agenciesData.add(map);
                                Log.d("Krzys", "agenciesDataMap: " + String.valueOf(agenciesData));
                            }

                            String[] from = {"agencyId", "agncyCity"};
                            int[] to = {R.id.tvAgencyId, R.id.tvAgncyCity};
                            SimpleAdapter adapter = new SimpleAdapter(getApplicationContext(), agenciesData, R.layout.listview_item_agency, from, to);
                            lvAgencies.setAdapter(adapter);
                            Log.d("krzys", "set agency adapter");
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }
                }, new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        Log.d("ERROR-VOLLEY", "Volley Error: " + error.toString());
                        Toast.makeText(getApplicationContext(), "Volley comms error", Toast.LENGTH_LONG).show();
                    }
                });
                request.setRetryPolicy(new DefaultRetryPolicy(60000, DefaultRetryPolicy.DEFAULT_MAX_RETRIES, DefaultRetryPolicy.DEFAULT_BACKOFF_MULT));
                RequestQueue requestQueue = Volley.newRequestQueue(getApplicationContext());
                requestQueue.add(request);
            }
        });
    }
}