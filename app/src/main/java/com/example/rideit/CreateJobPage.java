package com.example.rideit;

import androidx.activity.ComponentActivity;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import com.example.rideit.databinding.ActivityCreateJobPageBinding;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class CreateJobPage extends AppCompatActivity {
    EditText jobDescription, jobAddress;
    ActivityCreateJobPageBinding pageBinding;

    FirebaseDatabase firebaseDatabase;
    FirebaseAuth firebaseAuth;
    FirebaseUser firebaseUser;
    Users currentUser;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        pageBinding = ActivityCreateJobPageBinding.inflate(getLayoutInflater());
        setContentView(pageBinding.getRoot());

        //UI
        jobDescription = pageBinding.jobDescription;
        jobAddress = pageBinding.jobAddress;
        // Firebase
        firebaseAuth = FirebaseAuth.getInstance();
        firebaseUser = firebaseAuth.getCurrentUser();
        firebaseDatabase = FirebaseDatabase.getInstance();

        GetCurrentUser();
    }

    @Override
    protected void onStart() {
        super.onStart();

        Button btnCreate = pageBinding.btnCreate;
        btnCreate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String jobDes = jobDescription.getText().toString().trim();
                String jobAdd = jobAddress.getText().toString().trim();
                // database init
                Job j = new Job(firebaseUser.getUid(), currentUser.name, jobDes, jobAdd);
                String key = firebaseDatabase.getReference("Jobs").push().getKey();
                firebaseDatabase.getReference("Jobs").child(key).setValue(j).addOnCompleteListener(new OnCompleteListener<Void>() {
                    @Override
                    public void onComplete(@NonNull Task<Void> task) {
                        if(task.isSuccessful()){
                            Log.d("CreateJobPage1" , "Success <3");
                        }
                    }
                });


            }
        });

    }
    private void GetCurrentUser(){
        firebaseDatabase.getReference("User").child(firebaseUser.getUid()).addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                if(currentUser == null){
                    currentUser = snapshot.getValue(Users.class);
                    if(currentUser != null){
                        Log.d("CreateJobPage1", "Username:" + currentUser.name);
                    }
                }
            }
            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                Log.d("CreateJob1", "Error database");
            }
        });
    }
    private void CreateJob(String username){

    }
}