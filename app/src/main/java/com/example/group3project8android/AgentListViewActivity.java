package com.example.group3project8android;

/*
 *   Sherwin Chinprahat
 *   Project 8 - CMPP-264 Android
 *   Controller for agents list - uses getagent path of REST server
 */

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.SimpleAdapter;
import android.widget.Toast;

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

import model.Agent;

public class AgentListViewActivity extends AppCompatActivity {

    ListView lvAgents;
    ArrayList<Agent> agents = new ArrayList<>();
    ArrayList<HashMap<String, String>> data = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_agent_list_view);

        lvAgents = findViewById(R.id.lvAgents);

        getAgentsFromServer();

        lvAgents.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int position, long l) {
                Intent intent = new Intent(getApplicationContext(), AgentDetailActivity.class);
                Agent agt = (Agent) agents.get(position);
                intent.putExtra("agent", agt);
                startActivity(intent);
            }
        });
    }

    private void getAgentsFromServer() {
        Executors.newSingleThreadExecutor().execute(new Runnable() {
            @Override
            public void run() {
                Log.d("sherwin", "entered getAgentsFromServer()");
//                String url = "http://10.0.2.2:8080/Group3REST-1.0-SNAPSHOT/api/agent/getagents";
                String url = "http://10.0.2.2:8080/Group3REST-1.0-SNAPSHOT/api/agent/getagents";

                StringRequest request = new StringRequest(url, new Response.Listener<String>() {
                    @Override
                    //public void onResponse(JSONObject response) {
                    public void onResponse(String response) {
                        try {
                            Log.d("sherwin", "after response");
                            Log.d("sherwin", String.valueOf(response));
                            // add each record to agents array
                            JSONArray jsonArray = new JSONArray(response);

                            for(int i = 0; i < jsonArray.length(); i++){
                                JSONObject jsonObject = jsonArray.getJSONObject(i);
                                Integer id = jsonObject.getInt("id");
                                String fname = jsonObject.getString("agtFirstName");
                                String midinit = jsonObject.getString("agtMiddleInitial");
                                String lname = jsonObject.getString("agtLastName");
                                String phone = jsonObject.getString("agtBusPhone");
                                String email = jsonObject.getString("agtEmail");
                                String position = jsonObject.getString("agtPosition");
                                Integer agencyid = jsonObject.getInt("agencyId");

                                agents.add(new Agent(id, fname, midinit, lname, phone, email, position, agencyid));
                                Log.d("sherwin", String.valueOf(agents));
                            }

                            Log.d("sherwin", "create mapping");
                            for (Agent c : agents) {
                                HashMap<String, String> map = new HashMap<>();
                                map.put("agentId", c.getAgentId() + "");
                                map.put("agtFirstName", c.getAgtFirstName());
                                map.put("agtLastName", c.getAgtLastName());
                                map.put("agtPosition", c.getAgtPosition());
                                data.add(map);
                                Log.d("sherwin", String.valueOf(data));
                            }

                            String [] from = {"agentId", "agtFirstName", "agtLastName", "agtPosition"};
                            int [] to = {R.id.tvAgentId, R.id.tvAgtFirstName, R.id.tvAgtLastName, R.id.tvAgtPosition};
                            SimpleAdapter adapter = new SimpleAdapter(getApplicationContext(), data, R.layout.agent_listview, from, to);
                            lvAgents.setAdapter(adapter);
                            Log.d("sherwin", "lvAgents set adapter");

                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }
                }, new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        Log.d("sherwin", "volley error: " + error.toString());
                        Toast.makeText(getApplicationContext(), "Volley communication failed", Toast.LENGTH_LONG).show();
                    }
                });

                request.setRetryPolicy(new DefaultRetryPolicy(60000, DefaultRetryPolicy.DEFAULT_MAX_RETRIES, DefaultRetryPolicy.DEFAULT_BACKOFF_MULT));
                RequestQueue requestQueue = Volley.newRequestQueue(getApplicationContext());
                requestQueue.add(request);
            }
        });

    }
}