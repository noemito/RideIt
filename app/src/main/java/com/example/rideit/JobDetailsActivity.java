package com.example.rideit;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;

import android.os.Bundle;
import android.widget.LinearLayout;
import android.widget.TextView;

public class JobDetailsActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_job_details);

        Job j = (Job) getIntent().getSerializableExtra("Job_Details");

        TextView userText = findViewById(R.id.user_name_text);
        TextView jobDescription = findViewById(R.id.job_full_description);
        TextView jobDestination = findViewById(R.id.job_full_destination);

        userText.setText(j.getUserName());
        jobDescription.setText(j.getJobDescription());
        jobDestination.setText(j.getUserAddress());
    }
}