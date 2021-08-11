package com.example.codeforcesapp;

import android.Manifest;
import android.app.Activity;
import android.app.AlertDialog;
import android.app.TimePickerDialog;
import android.content.ActivityNotFoundException;
import android.content.ComponentName;
import android.content.ContentResolver;
import android.content.ContentValues;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.graphics.Color;
import android.net.Uri;
import android.os.Build;
import android.provider.AlarmClock;
import android.provider.CalendarContract;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.TimePicker;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.RequiresApi;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.material.card.MaterialCardView;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;
import java.util.TimeZone;

public class UpcomingContestAdaptor extends RecyclerView.Adapter<UpcomingContestAdaptor.ViewHolder> {

    ArrayList<Contest> contests =new ArrayList<>();
    private Activity context;
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
        SharedPreferences sP= context.getSharedPreferences("Cfa",context.MODE_PRIVATE);
        String alreadyr=sP.getString(contests.get(position).getContestName()+"r",null);
        if(alreadyr==null)
        holder.txtContestRegister.setTextColor(Color.parseColor("#FF0000"));
        else
            holder.txtContestRegister.setTextColor(Color.parseColor("#7fab5b"));
        holder.txtContestSetAlarm.setText("Set Alert");
        String alreadya=sP.getString(contests.get(position).getContestName()+"a",null);
        if(alreadya==null)
            holder.txtContestSetAlarm.setTextColor(Color.parseColor("#FF0000"));
        else
            holder.txtContestSetAlarm.setTextColor(Color.parseColor("#7fab5b"));
        holder.txtContestRegister.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                SharedPreferences sP= context.getSharedPreferences("Cfa",context.MODE_PRIVATE);
                holder.txtContestRegister.setTextColor(Color.parseColor("#7fab5b"));
                SharedPreferences.Editor ed=sP.edit();
                ed.putString(contests.get(holder.getAdapterPosition()).getContestName()+"r","1");
                ed.apply();
                String url = "https://codeforces.com/contests";
                try {
                    Intent i = new Intent("android.intent.action.MAIN");
                    i.setComponent(ComponentName.unflattenFromString("com.android.chrome/com.android.chrome.Main"));
                    i.addCategory("android.intent.category.LAUNCHER");
                    i.setData(Uri.parse(url));
                    context.startActivity(i);
                }
                catch(ActivityNotFoundException e) {
                    // Chrome is not installed
                    Intent i = new Intent(Intent.ACTION_VIEW, Uri.parse(url));
                    context.startActivity(i);
                }

            }
        });
        int day=Integer.parseInt(contests.get(holder.getAdapterPosition()).getContestStart().substring(0,2));
        int mon=Integer.parseInt(contests.get(holder.getAdapterPosition()).getContestStart().substring(3,5));
        int yer=Integer.parseInt(contests.get(holder.getAdapterPosition()).getContestStart().substring(6,10));
        int min=Integer.parseInt(contests.get(holder.getAdapterPosition()).getContestStart().substring(14));
        int hour=Integer.parseInt(contests.get(holder.getAdapterPosition()).getContestStart().substring(11,13));
        holder.txtContestSetAlarm.setOnClickListener(new View.OnClickListener() {
            @RequiresApi(api = Build.VERSION_CODES.M)
            @Override
            public void onClick(View view) {
                long epoch = 0;
                try {
                    epoch = new java.text.SimpleDateFormat("dd/MM/yyyy HH:mm").parse(contests.get(holder.getAdapterPosition()).getContestStart()).getTime();
                } catch (ParseException e) {
                    e.printStackTrace();
                }


                Long duration=Long.parseLong(contests.get(holder.getAdapterPosition()).getContestLength().substring(0,1))*60*60+Long.parseLong(contests.get(holder.getAdapterPosition()).getContestLength().substring(2,3))*60*10+Long.parseLong(contests.get(holder.getAdapterPosition()).getContestLength().substring(3))*60;
                //Attempt 1
//                Intent intent = new Intent(Intent.ACTION_INSERT);
//                intent.setData(CalendarContract.Events.CONTENT_URI);
//                intent.putExtra(CalendarContract.Events.TITLE, contests.get(holder.getAdapterPosition()).getContestName());
//                intent.putExtra(CalendarContract.EXTRA_EVENT_BEGIN_TIME,epoch);
//                intent.putExtra(CalendarContract.EXTRA_EVENT_END_TIME,epoch+duration*1000);
//                if(intent.resolveActivity(context.getPackageManager()) != null){
//                    context.startActivity(intent);
//                }else{
//                    Toast.makeText(context, "There is no app that support this action", Toast.LENGTH_SHORT).show();
//                }
                //Attempt 2
//                Calendar calendarEvent = Calendar.getInstance();
//                Intent i = new Intent(Intent.ACTION_EDIT);
//                i.setType("vnd.android.cursor.item/event");
//                i.putExtra("beginTime", epoch);
//                i.putExtra("allDay", false);
//                i.putExtra("rule", "FREQ=YEARLY");
//                Long duration=Long.parseLong(contests.get(holder.getAdapterPosition()).getContestLength().substring(0,1))*60*60+Long.parseLong(contests.get(holder.getAdapterPosition()).getContestLength().substring(2,3))*60*10+Long.parseLong(contests.get(holder.getAdapterPosition()).getContestLength().substring(3))*60;
//                i.putExtra("endTime", epoch+duration*1000);
//                i.putExtra("title", contests.get(holder.getAdapterPosition()).getContestName());
//                context.startActivity(i);

                // Attempt 3

                Calendar cal = Calendar.getInstance();
                Uri EVENTS_URI = Uri.parse(getCalendarUriBase(true) + "events");
                ContentResolver cr = context.getContentResolver();
                TimeZone timeZone = TimeZone.getDefault();

                /** Inserting an event in calendar. */
                ContentValues values = new ContentValues();
                values.put(CalendarContract.Events.CALENDAR_ID, 1);
                values.put(CalendarContract.Events.TITLE, contests.get(holder.getAdapterPosition()).getContestName());
                values.put(CalendarContract.Events.DESCRIPTION, "CodeForces Contest");
                values.put(CalendarContract.Events.ALL_DAY, 0);
                // event starts at 11 minutes from now
                values.put(CalendarContract.Events.DTSTART, epoch);
                // ends 60 minutes from now
                values.put(CalendarContract.Events.DTEND, epoch+duration);
                values.put(CalendarContract.Events.EVENT_TIMEZONE, timeZone.getID());
                values.put(CalendarContract.Events.HAS_ALARM, 1);
                Uri event = cr.insert(EVENTS_URI, values);

                // Display event id
                Toast.makeText(context, "Reminder Added for 30 mins before the contest", Toast.LENGTH_SHORT).show();

                /** Adding reminder for event added. */
                Uri REMINDERS_URI = Uri.parse(getCalendarUriBase(true) + "reminders");
                values = new ContentValues();
                values.put(CalendarContract.Reminders.EVENT_ID, Long.parseLong(event.getLastPathSegment()));
                 values.put(CalendarContract.Reminders.METHOD,CalendarContract.Reminders.METHOD_ALERT);
                values.put(CalendarContract.Reminders.MINUTES, 30);
                cr.insert(REMINDERS_URI, values);
                SharedPreferences sP= context.getSharedPreferences("Cfa",context.MODE_PRIVATE);
                holder.txtContestSetAlarm.setTextColor(Color.parseColor("#7fab5b"));
                SharedPreferences.Editor ed=sP.edit();
                ed.putString(contests.get(holder.getAdapterPosition()).getContestName()+"a","1");
                ed.apply();
            }
        });
    }

    @Override
    public int getItemCount() {
        return contests.size();
    }

    public void setContests(ArrayList<Contest> contests, Activity context) {
        this.context=context;
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
    private String getCalendarUriBase(boolean eventUri) {
        Uri calendarURI = null;
        try {
            if (android.os.Build.VERSION.SDK_INT <= 7) {
                calendarURI = (eventUri) ? Uri.parse("content://calendar/") : Uri.parse("content://calendar/calendars");
            } else {
                calendarURI = (eventUri) ? Uri.parse("content://com.android.calendar/") : Uri
                        .parse("content://com.android.calendar/calendars");
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return calendarURI.toString();
    }

}
