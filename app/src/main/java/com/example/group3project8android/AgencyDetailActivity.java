package com.example.group3project8android;

/*
 *   Krzysztof Stalmach
 *   Project 8 - CMPP-264 Android
 *   Agency Detail Activity
 */

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.concurrent.Executors;

import model.Agency;

public class AgencyDetailActivity extends AppCompatActivity {
    EditText etAgencyId, etAgncyAddress, etAgncyCity, etAgncyProv, etAgncyPostal, etAgncyCountry, etAgncyPhone, etAgncyFax;
    Button btnSaveAgency, btnCancel;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.agencies_detail);
        // initialize fields
        etAgencyId = findViewById(R.id.etDetailAgencyId);
        etAgencyId.setEnabled(false);
        etAgncyAddress = findViewById(R.id.etAgncyAddress);
        etAgncyCity = findViewById(R.id.etAgncyCity);
        etAgncyProv = findViewById(R.id.etAgncyProv);
        etAgncyPostal = findViewById(R.id.etAgncyPostal);
        etAgncyCountry = findViewById(R.id.etAgncyCountry);
        etAgncyPhone = findViewById(R.id.etAgncyPhone);
        etAgncyFax = findViewById(R.id.etAgncyFax);


        // initialize buttons
        btnSaveAgency = findViewById(R.id.btnSaveAgency);
        btnCancel = findViewById(R.id.btnCancel);
        // get our agency object and load it into the fields:
        Agency agency = (Agency) getIntent().getSerializableExtra("agency");

        populateFields(agency);

        btnCancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                populateFields(agency);
            }
        });

        btnSaveAgency.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Executors.newSingleThreadExecutor().execute(new Runnable() {
                    @Override
                    public void run() {
//                        String url = "http://10.0.2.2:8080/Group3REST-1.0-SNAPSHOT/api/agency/postagency";
                        String url = "http://10.0.2.2:8080/Group3REST-1.0-SNAPSHOT/api/agency/postagency";
                        RequestQueue requestQueue = Volley.newRequestQueue(getApplicationContext());
                        String agencyId = String.valueOf(etAgencyId.getText());
                        String address = String.valueOf(etAgncyAddress.getText());
                        String city = String.valueOf(etAgncyCity.getText());
                        String prov = String.valueOf(etAgncyProv.getText());
                        String postal = String.valueOf(etAgncyPostal.getText());
                        String country = String.valueOf(etAgncyCountry.getText());
                        String phone = String.valueOf(etAgncyPhone.getText());
                        String fax = String.valueOf(etAgncyFax.getText());

                        JSONObject post = new JSONObject();
                        try {
                            post.put("agencyId", agencyId);
                            post.put("agncyAddress", address);
                            post.put("agncyCity", city);
                            post.put("agncyProv", prov);
                            post.put("agncyPostal", postal);
                            post.put("agncyCountry", country);
                            post.put("agncyPhone", phone);
                            post.put("agncyFax", fax);
                        }
                        catch (JSONException e)
                        {
                            e.printStackTrace();
                        }

                        JsonObjectRequest jsonObjectRequest = new JsonObjectRequest(Request.Method.POST, url, post, new Response.Listener<JSONObject>() {
                            @Override
                            public void onResponse(JSONObject response) {
                                Log.d("POST RESPONSE", "onResponse: " + response.toString());
                                Toast.makeText(getApplicationContext(), "Response successful: " + response.toString(), Toast.LENGTH_LONG).show();
                                Intent intent = new Intent(getApplicationContext(),AgencyMainActivity.class);
                                startActivity(intent);
                            }
                        }, new Response.ErrorListener() {
                            @Override
                            public void onErrorResponse(VolleyError error) {
                                error.printStackTrace();
                                Log.d("POST ERROR", "onErrorResponse: "  + error.toString());
                            }
                        });
                        requestQueue.add(jsonObjectRequest);
                    }
                });
            }
        });
    }

    private void populateFields(Agency agency) {
        etAgencyId.setText(agency.getAgencyId() + "");
        etAgncyAddress.setText(agency.getAgncyAddress());
        etAgncyCity.setText(agency.getAgncyCity());
        etAgncyProv.setText(agency.getAgncyProv());
        etAgncyPostal.setText(agency.getAgncyPostal());
        etAgncyCountry.setText(agency.getAgncyCountry());
        etAgncyPhone.setText(agency.getAgncyPhone());
        etAgncyFax.setText(agency.getAgncyFax());
    }
}