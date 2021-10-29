package com.example.group3project8android;

/*
 *   Krzysztof Stalmach
 *   Project 8 - CMPP-264 Android
 *   Package Main Activity
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
import androidx.cardview.widget.CardView;

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

import model.Package;

public class PackageMainActivity extends AppCompatActivity
{
    CardView cvPackages;
    Button btnRefresh;
    ArrayList<Package> packages = new ArrayList<>();
    ArrayList<HashMap<String, String>> packageMap = new ArrayList<>();
    ListView lvPackages;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.package_recycler_view);

//        cvPackages = findViewById(R.id.cvPackages);
        btnRefresh = findViewById(R.id.btnRefreshPkgs);
        lvPackages = findViewById(R.id.lvPackages);

        getPackages();

        btnRefresh.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                packageMap.clear();
                packages.clear();
                getPackages();
            }
        });

//        lvPackages.setOnItemClickListener(new AdapterView.OnItemClickListener() {
//            @Override
//            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
//                Intent intent = new Intent(getApplicationContext(), PackageDetailActivity.class);
//                Package p = packages.get(i);
//                intent.putExtra("package", p);
//                startActivity(intent);
//            }
//        });
    }

    private void getPackages() {
        Executors.newSingleThreadExecutor().execute(new Runnable() {
            @Override
            public void run() {
                Log.d("Krzys", "In getPackages Request");
                String url = "http://10.0.2.2:8080/Group3REST-1.0-SNAPSHOT/api/package/getpackages";
                StringRequest request = new StringRequest(url, new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        try {
                            Log.d("Krzys", "onResponse: " + response.toString());
                            JSONArray packagesArray = new JSONArray(response);
                            Log.d("Krzys", "Packages: " + packagesArray.length());

                            for (int i = 0; i < packagesArray.length(); i++) {
                                JSONObject pacakgeOBJ = packagesArray.getJSONObject(i);
                                Log.d("Krzys", "Package: " + pacakgeOBJ.toString());

                                Integer id = pacakgeOBJ.getInt("packageId");
                                String name = pacakgeOBJ.getString("pkgName");
                                String desc = pacakgeOBJ.getString("pkgDesc");
                                String  startDate = pacakgeOBJ.getString("pkgStartDate");
                                Log.d("krzys", "startdate: " + startDate);
                                String endDate = pacakgeOBJ.getString("pkgEndDate");
                                Log.d("krzys", "enddate: " + endDate);

                                Double basePrice = pacakgeOBJ.getDouble("pkgBasePrice");
                                Double commission = pacakgeOBJ.getDouble("pkgAgencyCommission");

                                packages.add(new Package(id, name, startDate, endDate, desc, basePrice, commission));
                            }
                            Log.d("Krzys", "date: " + packages.get(1).getPkgEndDate());

                            for (Package p : packages)
                            {
                                HashMap<String, String> map = new HashMap<>();
//                                map.put("packageId", p.getId() + "");
                                map.put("pkgName", p.getPkgName() + "");
                                map.put("pkgDesc", p.getPkgDesc() + "");
                                map.put("pkgStartDate", p.getPkgStartDate() + "");
                                map.put("pkgEndDate", p.getPkgEndDate() + "");
                                map.put("pkgBasePrice", p.getPkgBasePrice() + "");
                                packageMap.add(map);
                                Log.d("Krzys", "packageMap: " + String.valueOf(packageMap));
                                Log.d("Krzys", "packageMap: " + String.valueOf(map));
                                Log.d("Krzys", "expected date: " + String.valueOf(p.getPkgStartDate()) + p.getPkgEndDate());
                            }

                            String [] from = {"pkgName", "pkgDesc","pkgStartDate", "pkgEndDate", "pkgBasePrice" };
                            int[] to = {R.id.txt_package_name,R.id.etPkgDesc,R.id.etStartDate,R.id.etEndDate,R.id.etBasePrice};
                            SimpleAdapter adapter = new SimpleAdapter(getApplicationContext(), packageMap,R.layout.package_view_item,from,to);
                            lvPackages.setAdapter(adapter);


                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }
                }, new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        Log.d("Krzys", "onErrorResponse: " + error.toString());
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
