package com.example.codeforcesapp;

import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.material.card.MaterialCardView;

import java.util.ArrayList;

public class UpcomingContestAdaptor extends RecyclerView.Adapter<UpcomingContestAdaptor.ViewHolder> {

    ArrayList<Contest> contests =new ArrayList<>();
    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view= LayoutInflater.from(parent.getContext()).inflate(R.layout.contest_layout,parent,false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        holder.txtContestName.setText(contests.get(position).getContestName());
        holder.txtContestStart.setText(contests.get(position).getContestStart());
        int height=holder.txtContestName.getHeight();
        holder.txtContestStart.getLayoutParams().height=height;
        holder.txtContestLength.setText(contests.get(position).getContestLength());
        holder.txtContestRegister.setText("Register");
        holder.txtContestRegister.setTextColor(Color.parseColor("#FF0000"));
        holder.txtContestSetAlarm.setText("Set Alarm");
        holder.txtContestSetAlarm.setTextColor(Color.parseColor("#FF0000"));
        holder.txtContestRegister.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                holder.txtContestRegister.setTextColor(Color.parseColor("#00FF00"));

            }
        });
        holder.txtContestSetAlarm.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                holder.txtContestSetAlarm.setTextColor(Color.parseColor("#00FF00"));
            }
        });
    }

    @Override
    public int getItemCount() {
        return contests.size();
    }

    public void setContests(ArrayList<Contest> contests) {
        this.contests = contests;
        notifyDataSetChanged();
    }

    public class ViewHolder extends RecyclerView.ViewHolder{
        private MaterialCardView parent;
        private TextView txtContestName;
        private TextView txtContestStart;
        private TextView txtContestLength;
        private TextView txtContestRegister;
        private TextView txtContestSetAlarm;
        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            parent=itemView.findViewById(R.id.parent);
            txtContestName=itemView.findViewById(R.id.txtPastContestName);
            txtContestStart=itemView.findViewById(R.id.txtContestRank);
            txtContestLength=itemView.findViewById(R.id.txtSolved);
            txtContestRegister=itemView.findViewById(R.id.txtRatingChange);
            txtContestSetAlarm=itemView.findViewById(R.id.txtNewRating);

        }

    }
}
