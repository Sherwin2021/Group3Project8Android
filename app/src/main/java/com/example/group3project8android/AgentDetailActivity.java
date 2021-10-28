package com.example.group3project8android;

/*
 *   Sherwin Chinprahat
 *   Project 8 - CMPP-264 Android
 *   Controller for agent details - uses getagent/{agentId} path of REST server
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

public class AgentDetailActivity extends AppCompatActivity {
    EditText etAgentId, etAgtFirstName, etAgtMiddleInitial, etAgtLastName, etAgtBusPhone, etAgtEmail, etAgtPosition, etAgencyId;
    Button btnSaveAgent;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_agent_detail);

        etAgentId = findViewById(R.id.etAgentId);
        etAgtFirstName = findViewById(R.id.etAgtFirstName);
        etAgtMiddleInitial = findViewById(R.id.etAgtMiddleInitial);
        etAgtLastName = findViewById(R.id.etAgtLastName);
        etAgtBusPhone = findViewById(R.id.etAgtBusPhone);
        etAgtEmail = findViewById(R.id.etAgtEmail);
        etAgtPosition = findViewById(R.id.etAgtPosition);
        etAgencyId = findViewById(R.id.etAgencyId);
        btnSaveAgent = findViewById(R.id.btnSaveAgent);

        Intent intent = getIntent();

        Agent agent = (Agent) intent.getSerializableExtra("agent");
        etAgentId.setText(agent.getAgentId() + "");
        etAgtFirstName.setText(agent.getAgtFirstName());
        etAgtMiddleInitial.setText(agent.getAgtMiddleInitial());
        etAgtLastName.setText(agent.getAgtLastName());
        etAgtBusPhone.setText(agent.getAgtBusPhone());
        etAgtEmail.setText(agent.getAgtEmail());
        etAgtPosition.setText(agent.getAgtPosition());
        etAgencyId.setText(agent.getAgencyId() + "");

        btnSaveAgent.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Executors.newSingleThreadExecutor().execute(new Runnable() {
                    @Override
                    public void run() {
                        String postUrl = "http://10.0.2.2:8080/Day4JPAEx1-1.0-SNAPSHOT/api/agents/postagent/";
                        RequestQueue requestQueue = Volley.newRequestQueue(getApplicationContext());
                        Log.d("sherwin", "after save click");

                        // get values from textboxes
                        String agentId = String.valueOf(etAgentId.getText());
                        String fname = String.valueOf(etAgtFirstName.getText());
                        String midinit = String.valueOf(etAgtMiddleInitial.getText());
                        String lname = String.valueOf(etAgtLastName.getText());
                        String phone = String.valueOf(etAgtBusPhone.getText());
                        String email = String.valueOf(etAgtEmail.getText());
                        String position = String.valueOf(etAgtPosition.getText());
                        String agencyId = String.valueOf(etAgencyId.getText());

                        // convert data into JSON object
                        JSONObject postData = new JSONObject();
                        try {
                            postData.put("id", agentId);
                            postData.put("agtFirstName", fname);
                            postData.put("agtMiddleInitial", midinit);
                            postData.put("agtLastName", lname);
                            postData.put("agtBusPhone", phone);
                            postData.put("agtEmail", email);
                            postData.put("agtPosition", position);
                            postData.put("agencyId", agencyId);
                            Log.d("sherwin", String.valueOf(postData));
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }

                        // now time to post to server!!
                        JsonObjectRequest jsonObjectRequest = new JsonObjectRequest(Request.Method.POST, postUrl, postData, new Response.Listener<JSONObject>() {
                            @Override
                            public void onResponse(JSONObject response) {
                                System.out.println(response);
                                Log.d("sherwin", "on response");
                                Log.d("sherwin", String.valueOf(response));
                                Toast.makeText(getApplicationContext(), "Update successful", Toast.LENGTH_SHORT).show();

                                // back to agents list view
                                Intent intent = new Intent(getApplicationContext(), AgentListViewActivity.class);
                                startActivity(intent);
                                Log.d("Sherwin", "Return to Agent list view");
                            }
                        }, new Response.ErrorListener() {
                            @Override
                            public void onErrorResponse(VolleyError error) {
                                error.printStackTrace();
                                Log.d("sherwin", "on response volley error");
                                Log.d("sherwin", String.valueOf(error));
                            }
                        });

                        requestQueue.add(jsonObjectRequest);
                    }

                });
            }
        });
    }
}