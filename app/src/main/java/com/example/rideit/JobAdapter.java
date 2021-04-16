package com.example.rideit;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

public class JobAdapter extends RecyclerView.Adapter<JobAdapter.ViewHolder>{

    ArrayList<Job> jobs;
    Context context;

    public JobAdapter(ArrayList<Job> jobs, MainActivity activity) {
        this.jobs = jobs;
        this.context = activity;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater layoutInflater = LayoutInflater.from(parent.getContext());
        View view = layoutInflater.inflate(R.layout.card_view_layout, parent,false);
        ViewHolder viewHolder = new ViewHolder(view);

        return viewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        final Job job = jobs.get(position);
        holder.textUserName.setText(job.getUserName());
        holder.textDescription.setText(job.getJobDescription());
        holder.textLocation.setText(job.getUserAddress());

        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(context, job.getUserName(),Toast.LENGTH_SHORT).show();
                Intent intent = new Intent(context, JobDetailsActivity.class);
                intent.putExtra("Job_Details", job);
                context.startActivity(intent);
            }
        });

    }

    @Override
    public int getItemCount() {
        return jobs.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder{
        TextView textUserName;
        TextView textDescription;
        TextView textLocation;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            textUserName = itemView.findViewById(R.id.textUserName);
            textDescription = itemView.findViewById(R.id.textDescription);
            textLocation = itemView.findViewById(R.id.textLocation);
        }
    }
}
