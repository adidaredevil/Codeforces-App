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

public class PastContestsAdaptor extends RecyclerView.Adapter<PastContestsAdaptor.ViewHolder>{

    ArrayList<PastContest> pastContests = new ArrayList<>();
    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view= LayoutInflater.from(parent.getContext()).inflate(R.layout.past_contest_layout,parent,false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        holder.txtPastContestName.setText(pastContests.get(position).getName());
        holder.txtContestRank.setText(pastContests.get(position).getRank());
        holder.txtSolved.setText(pastContests.get(position).getSolved());
        holder.txtRatingChange.setText(pastContests.get(position).getRatingchange());
        int ratingchange=Integer.parseInt(pastContests.get(position).getRatingchange());
        if(ratingchange>0)
            holder.txtRatingChange.setTextColor(Color.parseColor("#7fab5b"));
        else if(ratingchange<0)
            holder.txtRatingChange.setTextColor(Color.parseColor("#FF0000"));
        holder.txtNewRating.setText(pastContests.get(position).getNewrating());
        int rating=Integer.parseInt(pastContests.get(position).getNewrating());
        String cc="#cccccc";
        if(rating>=1200&&rating<1400){
            cc="#008000";
        }else if(rating>=1400&&rating<1600){
            cc="#03a8a7";
        }else if(rating>=1600&&rating<1900){
            cc="#4982fc";
        }else if(rating>=1900&&rating<2100){
            cc="#ff88ff";
        }else if(rating>=2100&&rating<2300){
            cc="#ffcc88";
        }else if(rating>=2300&&rating<2400){
            cc="#ffbb55";
        }else if(rating>=2400&&rating<2600){
            cc="#ff7777";
        }else if(rating>=2600&&rating<3000){
            cc="#ff3333";
        }else if(rating>=3000){
            cc="#aa0000";
        }
        holder.txtNewRating.setTextColor(Color.parseColor(cc));
    }

    @Override
    public int getItemCount() {
        return pastContests.size();
    }

    public void setPastContests(ArrayList<PastContest> pastContests) {
        this.pastContests = pastContests;
        notifyDataSetChanged();
    }

    public class ViewHolder extends RecyclerView.ViewHolder{


        private MaterialCardView parent;
        private TextView txtPastContestName;
        private TextView txtContestRank;
        private TextView txtSolved;
        private TextView txtRatingChange;
        private TextView txtNewRating;
        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            parent=itemView.findViewById(R.id.parent);
            txtPastContestName=itemView.findViewById(R.id.txtPastContestName);
            txtContestRank=itemView.findViewById(R.id.txtContestRank);
            txtSolved=itemView.findViewById(R.id.txtSolved);
            txtRatingChange=itemView.findViewById(R.id.txtRatingChange);
            txtNewRating=itemView.findViewById(R.id.txtNewRating);
        }
    }
}
