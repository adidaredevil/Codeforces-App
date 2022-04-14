package com.example.codeforcesapp;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;

import org.json.JSONException;
import org.json.JSONObject;

public class MainActivity extends AppCompatActivity {

    private EditText edtCodeforcesUsername;
    private ImageButton ibEnterProfile;
    private TextView txtInvalidUsername;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        edtCodeforcesUsername=findViewById(R.id.edtcodeforcesUsername);
        ibEnterProfile=findViewById(R.id.ibEnterProfile);
        txtInvalidUsername=findViewById(R.id.txtInvalidUsername);
        SharedPreferences sP= getSharedPreferences("Cfa",MODE_PRIVATE);
        String already=sP.getString("username",null);
        Intent intent=new Intent(this,AfterLoginActivity.class);
        if(already!=null){
            intent.putExtra("username",already);
            startActivity(intent);
        }

        ibEnterProfile.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String codeforcesusername=edtCodeforcesUsername.getText().toString();
                String url="https://codeforces.com/api/user.info?handles="+codeforcesusername;
                JsonObjectRequest jsonObjectRequest = new JsonObjectRequest
                        (Request.Method.GET, url, null, new Response.Listener<JSONObject>() {

                            @Override
                            public void onResponse(JSONObject response) {
                                txtInvalidUsername.setVisibility(View.GONE);

                                String status=null;
                                try {
                                     status =response.getString("status");
                                } catch (JSONException e) {
                                    e.printStackTrace();
                                }
                                if(status.equals("OK")){
                                    SharedPreferences sP= getSharedPreferences("Cfa",MODE_PRIVATE);
                                    SharedPreferences.Editor ed=sP.edit();
                                    ed.putString("username",codeforcesusername);
                                    ed.apply();
                                    intent.putExtra("username",codeforcesusername);
                                    startActivity(intent);

                                }else{
                                    txtInvalidUsername.setVisibility(View.VISIBLE);
                                }

                            }
                        }, new Response.ErrorListener() {

                            @Override
                            public void onErrorResponse(VolleyError error) {
                                txtInvalidUsername.setVisibility(View.VISIBLE);

                            }
                        });

// Access the RequestQueue through your singleton class.
                MySingleton.getInstance(MainActivity.this).addToRequestQueue(jsonObjectRequest);
            }
        });

    }
}