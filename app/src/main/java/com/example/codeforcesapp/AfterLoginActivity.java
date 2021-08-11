package com.example.codeforcesapp;

import androidx.annotation.NonNull;
import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.Manifest;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.graphics.Color;
import android.os.Build;
import android.os.Bundle;
import android.util.Log;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.Stack;

public class AfterLoginActivity extends AppCompatActivity {
    private RecyclerView rvUpcomingContests;
    private RecyclerView rvPastConetests;
    private UpcomingContestAdaptor adaptor;
    private PastContestsAdaptor pastContestsAdaptor;
    private TextView txtTotalContests;
    private static final String TAG = "MyActivity";
    private String username;
    private TextView txtUsername;
    private TextView txtRank;
    @RequiresApi(api = Build.VERSION_CODES.M)
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_after_login);
        if(getApplicationContext().checkSelfPermission(Manifest.permission.READ_CALENDAR)== PackageManager.PERMISSION_GRANTED);
                else{
            requestPermissions(new String[]{Manifest.permission.READ_CALENDAR,Manifest.permission.WRITE_CALENDAR},1);
         }
          if(getApplicationContext().checkSelfPermission(Manifest.permission.WRITE_CALENDAR)== PackageManager.PERMISSION_GRANTED);
           else{
               requestPermissions(new String[]{Manifest.permission.WRITE_CALENDAR},2);
           }
        initialize();
        setUsernameRank();
        setUpcomingContest();
        setPastContest();
    }
    void initialize(){
        rvUpcomingContests=findViewById(R.id.rvUpcomingContests);
        txtUsername=findViewById(R.id.txtUsername);
        txtRank=findViewById(R.id.txtRank);
        rvPastConetests=findViewById(R.id.rvPastContests);
        SharedPreferences sP= getSharedPreferences("Cfa",MODE_PRIVATE);
        username=sP.getString("username",null);
        txtTotalContests=findViewById(R.id.txtTotalContests);
    }
    void setUsernameRank(){

        String url = "https://codeforces.cc/api/user.info?handles="+username;

        JsonObjectRequest jsonObjectRequest = new JsonObjectRequest
                (Request.Method.GET, url, null, new Response.Listener<JSONObject>() {

                    JSONArray userdetailsa;
                    @Override
                    public void onResponse(JSONObject response) {
                        try {
                             userdetailsa= response.getJSONArray("result");
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                        JSONObject userdetails= null;
                        try {
                            userdetails = userdetailsa.getJSONObject(0);
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                        String rank= null;
                        try {
                            rank = userdetails.getString("rank");
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                        txtUsername.setText(username);
                        txtRank.setText(rank);
                        int rating = 0;
                        try {
                            rating = userdetails.getInt("rating");
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                        String cc="#808080";
                        if(rating>=1200&&rating<1400){
                            cc="#008000";
                        }else if(rating>=1400&&rating<1600){
                            cc="#03a8a7";
                        }else if(rating>=1600&&rating<1900){
                            cc="#4982fc";
                        }else if(rating>=1900&&rating<2100){
                            cc="#ff88ff";
                        }else if(rating>=2100&&rating<2300){
                            cc="#ffcc88";
                        }else if(rating>=2300&&rating<2400){
                            cc="#ffbb55";
                        }else if(rating>=2400&&rating<2600){
                            cc="#ff7777";
                        }else if(rating>=2600&&rating<3000){
                            cc="#ff3333";
                        }else if(rating>=3000){
                            cc="#aa0000";
                        }
                        txtRank.setTextColor(Color.parseColor(cc));
                        txtUsername.setTextColor(Color.parseColor(cc));
                    }
                }, new Response.ErrorListener() {

                    @Override
                    public void onErrorResponse(VolleyError error) {
                       Toast.makeText(AfterLoginActivity.this, "Please check your internet", Toast.LENGTH_SHORT).show();

                    }
                });

// Access the RequestQueue through your singleton class.
        MySingleton.getInstance(this).addToRequestQueue(jsonObjectRequest);

    }
    void setUpcomingContest(){
        String url = "https://codeforces.cc/api/contest.list?gym=false";

        JsonObjectRequest jsonObjectRequest = new JsonObjectRequest
                (Request.Method.GET, url, null, new Response.Listener<JSONObject>() {

                    @Override
                    public void onResponse(JSONObject response) {
                        JSONArray contests= null;
                        try {
                            contests = response.getJSONArray("result");
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                        Stack<Contest> contestStack= new Stack<>();
                       int contestNumber=0;
                       while(true){
                           JSONObject contestDetails= null;
                           try {
                               contestDetails = contests.getJSONObject(contestNumber);
                               contestNumber++;
                           } catch (JSONException e) {
                               e.printStackTrace();
                           }
                           String phase= null;
                           try {
                               phase = contestDetails.getString("phase");
                           } catch (JSONException e) {
                               e.printStackTrace();
                           }
                           if(phase.equals("FINISHED"))
                               break;
                           String contestName= null;
                           try {
                               contestName = contestDetails.getString("name");
                           } catch (JSONException e) {
                               e.printStackTrace();
                           }
                           int duration= 0;
                           try {
                               duration = contestDetails.getInt("durationSeconds");
                           } catch (JSONException e) {
                               e.printStackTrace();
                           }
                           long start= 0;
                           try {
                               start = contestDetails.getInt("startTimeSeconds");
                           } catch (JSONException e) {
                               e.printStackTrace();
                           }
                           String date = new java.text.SimpleDateFormat("dd/MM/yyyy HH:mm").format(new java.util.Date (start *1000));
                           int durh=duration/3600;
                           String dur=Integer.toString(durh)+":";
                           int durm=(duration%3600)/60;
                           if(durm==0)
                               dur=dur+"00";
                           else if(durm/10==0)
                               dur=dur+"0"+Integer.toString(durm);
                           else
                               dur=dur+Integer.toString(durm);
                           contestStack.push(new Contest(contestName,date,dur));

                       }
                       ArrayList<Contest> contestArrayList =new ArrayList<>();
                       while(contestStack.isEmpty()==false){
                           contestArrayList.add(contestStack.pop());
                       }
                        adaptor=new UpcomingContestAdaptor();
                        rvUpcomingContests.setAdapter(adaptor);
                        rvUpcomingContests.setLayoutManager(new GridLayoutManager(AfterLoginActivity.this,1));
                        adaptor.setContests(contestArrayList,AfterLoginActivity.this);

                    }
                }, new Response.ErrorListener() {

                    @Override
                    public void onErrorResponse(VolleyError error) {
                       // Toast.makeText(AfterLoginActivity.this, "Please check you internet connection", Toast.LENGTH_SHORT).show();

                    }
                });

// Access the RequestQueue through your singleton class.
        MySingleton.getInstance(this).addToRequestQueue(jsonObjectRequest);

    }
    void setPastContest(){
        String url = "https://codeforces.cc/api/user.rating?handle="+username;

        JsonObjectRequest jsonObjectRequest = new JsonObjectRequest
                (Request.Method.GET, url, null, new Response.Listener<JSONObject>() {

                    @Override
                    public void onResponse(JSONObject response) {
                        Stack<PastContest> pastContestStack=new Stack<>();
                        JSONArray pastContestsJsonArray= null;
                        try {
                            pastContestsJsonArray = response.getJSONArray("result");
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                        for(int j=0;j<pastContestsJsonArray.length();j++){
                            JSONObject pastContestDetails= null;
                            try {
                                pastContestDetails = pastContestsJsonArray.getJSONObject(j);
                            } catch (JSONException e) {
                                e.printStackTrace();
                            }
                            String name= null;
                            try {
                                name = pastContestDetails.getString("contestName");
                            } catch (JSONException e) {
                                e.printStackTrace();
                            }
                            int newRatingInt= 0;
                            try {
                                newRatingInt = pastContestDetails.getInt("newRating");
                            } catch (JSONException e) {
                                e.printStackTrace();
                            }
                            int oldRatingInt= 0;
                            try {
                                oldRatingInt = pastContestDetails.getInt("oldRating");
                            } catch (JSONException e) {
                                e.printStackTrace();
                            }
                            int ratingChangeInt=newRatingInt-oldRatingInt;
                            String newRating=Integer.toString(newRatingInt);
                            String ratingChange=Integer.toString(ratingChangeInt);
                            int rankInt= 0;
                            try {
                                rankInt = pastContestDetails.getInt("rank");
                            } catch (JSONException e) {
                                e.printStackTrace();
                            }
                            String rank=Integer.toString(rankInt);
                            pastContestStack.push(new PastContest(name,rank,"0",ratingChange,newRating));
                        }
                        ArrayList<PastContest> pastContestArrayList=new ArrayList<>();
                        while(pastContestStack.isEmpty()==false)
                            pastContestArrayList.add(pastContestStack.pop());
                        String totalcontest="["+Integer.toString(pastContestArrayList.size())+"]";
                        txtTotalContests.setText(totalcontest);
                        pastContestsAdaptor=new PastContestsAdaptor();
                        rvPastConetests.setAdapter(pastContestsAdaptor);
                        rvPastConetests.setLayoutManager(new GridLayoutManager(AfterLoginActivity.this,1));
                        pastContestsAdaptor.setPastContests(pastContestArrayList);
                    }
                }, new Response.ErrorListener() {

                    @Override
                    public void onErrorResponse(VolleyError error) {
                       //Toast.makeText(AfterLoginActivity.this, "Please Check Your Internet", Toast.LENGTH_SHORT).show();

                    }
                });

// Access the RequestQueue through your singleton class.
        MySingleton.getInstance(this).addToRequestQueue(jsonObjectRequest);
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        if(requestCode==1){
            if(grantResults[0]== PackageManager.PERMISSION_GRANTED){
                Toast.makeText(this, "Granted", Toast.LENGTH_SHORT).show();
            }else{
                Toast.makeText(this, "Not Granted", Toast.LENGTH_SHORT).show();
            }
        }
    }

}