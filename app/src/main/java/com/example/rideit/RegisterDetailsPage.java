package com.example.rideit;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.example.rideit.databinding.ActivityRegisterDetailsPageBinding;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.FirebaseDatabase;


public class RegisterDetailsPage extends AppCompatActivity {

    ActivityRegisterDetailsPageBinding pageBinding;
    private FirebaseAuth mAuth;
    FirebaseUser mUser;
    private FirebaseDatabase database;
    private EditText name, age, email;
    private Button btnSubmit;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        pageBinding = ActivityRegisterDetailsPageBinding.inflate(getLayoutInflater());
        setContentView(pageBinding.getRoot());
        // Firebase Init
        mAuth = FirebaseAuth.getInstance();
        // get current user
        mUser = mAuth.getCurrentUser();
        database = FirebaseDatabase.getInstance();
        // init widget
        name = pageBinding.name;
        age = pageBinding.age;
        email = pageBinding.email;
        btnSubmit = pageBinding.button;

        // check if the user is not null
        if(mUser == null)
        {
            Toast.makeText(RegisterDetailsPage.this, "You're not Login.",Toast.LENGTH_LONG).show();
            //TODO: Redirect user to Login page!
            Intent intent = new Intent(getApplicationContext(), LogInPage.class);
            finish();
        }

        btnSubmit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                String _name = name.getText().toString().trim();
                String _age = age.getText().toString().trim();
                String _email = email.getText().toString().trim();

                Users user = new Users(mUser.getUid().toString(),_name,_age,_email);
                CreateUserDetails(user);

            }
        });
    }
    public void CreateUserDetails(Users user){
        database.getReference("User").child(mAuth.getCurrentUser().getUid()).setValue(user)
                .addOnCompleteListener(new OnCompleteListener<Void>() {
                    @Override
                    public void onComplete(@NonNull Task<Void> task) {
                        if(task.isSuccessful()){
                            Toast.makeText(RegisterDetailsPage.this, "Successfully Registereed Data Redirecting....",Toast.LENGTH_LONG).show();
                            Intent intent = new Intent(getApplicationContext(), MainActivity.class);
                            startActivity(intent);
                            finish();
                        }else{
                            Toast.makeText(RegisterDetailsPage.this, "Can't Connect to database!",Toast.LENGTH_LONG).show();
                        }
                    }
                });
    }
}