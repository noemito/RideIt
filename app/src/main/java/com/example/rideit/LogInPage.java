package com.example.rideit;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.util.Patterns;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.example.rideit.databinding.ActivityLogginPageBinding;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;


public class LogInPage extends AppCompatActivity {
    FirebaseAuth mAuth;
    FirebaseUser mUser;
    FirebaseDatabase database;
    Users databaseUser;
    ActivityLogginPageBinding mPageBinding;
    EditText email, password;
    Button signIn, signUp;

    @Override

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mPageBinding = ActivityLogginPageBinding.inflate(getLayoutInflater());
        setContentView(mPageBinding.getRoot());
        // setting up firebase
        mAuth = FirebaseAuth.getInstance();
        mUser = mAuth.getCurrentUser();
        database = FirebaseDatabase.getInstance();
        // user information
        email = mPageBinding.userEmail;
        password = mPageBinding.userPassword;
        // buttons
        signIn = mPageBinding.login;
        signUp = mPageBinding.registerPage;

        signUp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getApplicationContext(), RegisterPage.class);
                startActivity(intent);
            }
        });
        //
        signIn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // EditText to String

                //TODO check if the email is an email input and not empty
                // check if the password is valid and have a length of more that 6 digit of string
                String _email = email.getText().toString().trim();
                String _password = password.getText().toString().trim();

                if (!Patterns.EMAIL_ADDRESS.matcher(_email).matches() || TextUtils.isEmpty(_email)){
                    email.setError("Cant be empty and a valid email address");
                    email.requestFocus();
                    return;
                }
                if (TextUtils.isEmpty(_password) || _password.length() < 6){
                    password.setError("Cant be empty and least 6 characters");
                    password.requestFocus();
                    return;
                }
                // connecting to firebase database and checking if the user is in our database

                SignInToDatabase(_email, _password);
            }
        });

    }

    public void SignInToDatabase(String _email, String _password){
        mAuth.signInWithEmailAndPassword(_email,_password).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
            @Override
            public void onComplete(@NonNull Task<AuthResult> task) {
                if(task.isSuccessful()){
                    //TODO get the user id and user information
                    mUser = mAuth.getCurrentUser();
                    Intent intent = new Intent(getApplicationContext(), MainActivity.class);
                    // Redirecting to Main page!
                    startActivity(intent);

                }else{
                    Toast.makeText(getApplicationContext(), "You are not registered yet!", Toast.LENGTH_LONG).show();
                    //TODO maybe? Redirecting the user to Register Page??
                    signIn.setClickable(true);
                }
            }
        });
    }


    @Override
    protected void onStart() {
        super.onStart();

        if(mUser != null) {
            String userID = mUser.getUid().toString();
            Log.d("LoginPage", "Current Userid:" + userID);

            database.getReference("User").child(mUser.getUid()).addValueEventListener(new ValueEventListener() {
                @Override
                public void onDataChange(@NonNull DataSnapshot snapshot) {
                    if(snapshot != null){
                       databaseUser = snapshot.getValue(Users.class);
                       if (databaseUser == null){
                           Intent intentRegisterPage = new Intent(getApplicationContext(), RegisterDetailsPage.class);
                           startActivity(intentRegisterPage);
                           finish();
                       }
                       else {
                           Log.d("LoginPage", databaseUser.userId);
                           Intent intentMainPage = new Intent(getApplicationContext(),MainActivity.class);
                           startActivity(intentMainPage);
                           finish();
                       }
                    }
                }

                @Override
                public void onCancelled(@NonNull DatabaseError error) {

                }
            });

        }
    }
}
