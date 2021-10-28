package com.example.group3project8android;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;

import java.util.concurrent.Executors;

public class MainActivity extends AppCompatActivity {
    Button btnAgent, btnAgencies;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        btnAgent = findViewById(R.id.btnAgents);

        btnAgent.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Executors.newSingleThreadExecutor().execute(new Runnable() {
                    @Override
                    public void run() {
                        Intent intent = new Intent(getApplicationContext(), AgentListViewActivity.class);
                        startActivity(intent);
                        Log.d("Sherwin", "Agents list view button clicked");
                    }
                });
            }
        });


    }


}