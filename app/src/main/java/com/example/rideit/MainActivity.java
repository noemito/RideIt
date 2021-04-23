package com.example.rideit;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.appcompat.widget.Toolbar;


import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.View;
import android.widget.Button;


import com.example.rideit.databinding.ActivityMainBinding;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {

    final static String TAG = "MainPage";
    ArrayList<Job> jobs;
    FirebaseAuth mAuth;
    FirebaseUser user;
    FirebaseDatabase database;
    RecyclerView mRecyclerView;
    public ActivityMainBinding mBinding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        // List of job
        jobs = new ArrayList<Job>();

        mBinding = ActivityMainBinding.inflate(getLayoutInflater());
        setContentView(mBinding.getRoot());
        //Toolbar
        Toolbar toolbar = mBinding.toolbarMain;
        setSupportActionBar(toolbar);

        // Firebase
        mAuth = FirebaseAuth.getInstance();
        user = mAuth.getCurrentUser();
        database = FirebaseDatabase.getInstance();

        // UI
        Button btnLogout = mBinding.logOut;
        Button btnCreateJob = mBinding.createJobBtn;

        mRecyclerView = findViewById(R.id.recyclerView);

        mRecyclerView.setHasFixedSize(true);
        mRecyclerView.setLayoutManager(new LinearLayoutManager(this));


        btnLogout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                SingOut();
            }
        });
        btnCreateJob.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intentCreateJob = new Intent(getApplicationContext(), CreateJobPage.class);
                startActivity(intentCreateJob);
            }
        });
    }

    public void GetJob(){
        
            database.getReference("Jobs").addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                jobs.clear();
                for(DataSnapshot dataSnapshots  : snapshot.getChildren()){
                   Job  j  = dataSnapshots.getValue(Job.class);
                   // check kong naa na sa atong data kong naa na dli na nako i add
                    if(!jobs.contains(j)) {
                        jobs.add(j);
                    }
                    Log.d("JobsListUser", "DATA: " + j.toString());
                }
                JobAdapter jobAdapter = new JobAdapter(jobs, MainActivity.this);
                mRecyclerView.setAdapter(jobAdapter);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                Log.d("JobsList", "Can't Connect database");
            }
        });
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
        GetJob();
        Log.d("JobsList", "Size: " + String.valueOf(jobs.size()));
    }

    public void SingOut(){
        mAuth.signOut();
        user = mAuth.getCurrentUser();
        Intent intent = new Intent(getApplicationContext(), LogInPage.class);
        startActivity(intent);
    }

    @Override
    public boolean onPrepareOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu, menu);
        return super.onPrepareOptionsMenu(menu);
    }
}