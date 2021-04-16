package com.example.rideit;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.view.ContextThemeWrapper;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.example.rideit.databinding.ActivityJobDetailsBinding;
import com.example.rideit.databinding.ActivityMainBinding;
import com.google.android.gms.common.internal.Objects;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {

    final static String TAG = "MainPage";
    ArrayList<Job> Jobs = new ArrayList<>();
    FirebaseAuth mAuth;
    FirebaseUser user;

    private ActivityMainBinding mBinding;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        mBinding = ActivityMainBinding.inflate(getLayoutInflater());

        setContentView(mBinding.getRoot());

        mAuth = FirebaseAuth.getInstance();
        user = mAuth.getCurrentUser();


        Button btnLogout = mBinding.logOut;
        RecyclerView mRecyclerView = findViewById(R.id.recyclerView);

        mRecyclerView.setHasFixedSize(true);
        mRecyclerView.setLayoutManager(new LinearLayoutManager(this));

        Job noemitoJob = new Job("Noemito John, Lacanaria", "Food Delivery", "Tagoloan Misamis Oriental");
        Job sweetJob = new Job("Mercy Sweet, Suarin", "Food Delivery", "Bukidnon");

        Jobs.add(noemitoJob);
        Jobs.add(sweetJob);

        JobAdapter jobAdapter = new JobAdapter(Jobs, MainActivity.this);
        mRecyclerView.setAdapter(jobAdapter);

        btnLogout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mAuth.signOut();
                user = mAuth.getCurrentUser();
                Intent intent = new Intent(getApplicationContext(), LogInPage.class);
                startActivity(intent);
            }
        });
        Log.d(TAG, "OnCreate");
    }

    @Override
    protected void onStart() {
        super.onStart();
        Log.d(TAG, "onStart");
        if(user == null){
            Intent intent = new Intent(getApplicationContext(), LogInPage.class);
            startActivity(intent);
            finish();
        }
    }

    @Override
    protected void onResume() {
        super.onResume();
        Log.d(TAG, "onResume");

    }

    @Override
    protected void onStop() {
        super.onStop();
        Log.d(TAG, "onStop");
        if (user == null){
            Log.d(TAG, "onStop: No user!");
        }else{
            Log.d(TAG, "onStop: Have a User!");
        }

    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        Log.d(TAG, "onDestroy");

    }
}