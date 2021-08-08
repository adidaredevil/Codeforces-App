package com.example.codeforcesapp;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;

import java.util.ArrayList;

public class AfterLoginActivity extends AppCompatActivity {
    private RecyclerView rvUpcomingContests;
    private UpcomingContestAdaptor adaptor;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_after_login);
        rvUpcomingContests=findViewById(R.id.rvUpcomingContests);
        adaptor=new UpcomingContestAdaptor();
        rvUpcomingContests.setAdapter(adaptor);
        rvUpcomingContests.setLayoutManager(new GridLayoutManager(this,1));
        ArrayList<Contest> contests=new ArrayList<>();
        String date = new java.text.SimpleDateFormat("MM/dd/yyyy HH:mm").format(new java.util.Date (1630247700L *1000));
        int durh=8100/3600;
        String dur=Integer.toString(durh)+":";
        int durm=(8100%3600)/60;
        if(durm==0)
            dur=dur+"00";
        else if(durm%10==0)
            dur=dur+"0"+Integer.toString(durm);
        else
            dur=dur+Integer.toString(durm);

        contests.add(new Contest("Deltix Round, Summer 2021 (open for everyone, rated, Div. 1 + Div. 2)",
                                    date,dur));
         date = new java.text.SimpleDateFormat("MM/dd/yyyy HH:mm").format(new java.util.Date (1629815700L *1000));
         durh=10800/3600;
         dur=Integer.toString(durh)+":";
         durm=(10800%3600)/60;
        if(durm==0)
            dur=dur+"00";
        else if(durm%10==0)
            dur=dur+"0"+Integer.toString(durm);
        else
            dur=dur+Integer.toString(durm);
        contests.add(new Contest("Codeforces Round #739 (Div. 1 + Div. 2, based on VK Cup 2021 - Final (Engine))",
                                   date,dur));
        adaptor.setContests(contests);
    }
}