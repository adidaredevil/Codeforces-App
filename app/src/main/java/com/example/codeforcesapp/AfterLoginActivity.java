package com.example.codeforcesapp;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;

public class AfterLoginActivity extends AppCompatActivity {
    private RecyclerView rvUpcomingContests;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_after_login);
        rvUpcomingContests=findViewById(R.id.rvUpcomingContests);
    }
}