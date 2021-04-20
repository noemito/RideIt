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

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.FirebaseDatabase;

import com.example.rideit.databinding.ActivityRegisterPageBinding;

public class RegisterPage extends AppCompatActivity {
    EditText username, password, confirmPassword;
    Button submit;

    private FirebaseAuth mAuth;
    private FirebaseDatabase database;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        ActivityRegisterPageBinding activityRegisterPageBinding = ActivityRegisterPageBinding.inflate(getLayoutInflater());
        setContentView(activityRegisterPageBinding.getRoot());

        mAuth = FirebaseAuth.getInstance();
        database = FirebaseDatabase.getInstance();

        username = activityRegisterPageBinding.inputUserName;
        password = activityRegisterPageBinding.inputPassword;
        confirmPassword = activityRegisterPageBinding.inputPasswordMatch;

        submit = activityRegisterPageBinding.register;

        submit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String userEmail = username.getText().toString().trim();
                String userPassword = password.getText().toString().trim();
                String userConfirmPass = confirmPassword.getText().toString().trim();
                //TODO validate username and password
                // check if not empty and a correct email and password match

                if (!Patterns.EMAIL_ADDRESS.matcher(userEmail).matches() || TextUtils.isEmpty(userEmail)){
                    username.setError("Cant be empty and a valid email address");
                    username.requestFocus();
                    return;
                }
                if(TextUtils.isEmpty(userPassword) || userPassword.length() < 6){
                    password.setError("Cant be empty and least 6 characters");
                    password.requestFocus();
                    return;
                }
                else
                    Log.d("Userpassword", userPassword);

                if(!TextUtils.equals(userPassword,userConfirmPass)){
                    confirmPassword.setError("Must be match with password");
                    confirmPassword.requestFocus();
                    Log.d("Userpassword","UserConfirmPass: " + userConfirmPass);
                    return;
                }

                CreateUsername(userEmail, userConfirmPass);

            }
        });
    }

    public void CreateUsername(String username, String password){
        mAuth.createUserWithEmailAndPassword(username,password)
                .addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if(task.isSuccessful()){
                            Toast.makeText(RegisterPage.this,"Successful Registered Redirecting!", Toast.LENGTH_LONG).show();

                            Intent intent = new Intent(getApplicationContext(), RegisterDetailsPage.class);
                            startActivity(intent);

                        }else {
                            Toast.makeText(RegisterPage.this,"Failed to create User", Toast.LENGTH_LONG).show();
                        }
                    }
                });
    }
}