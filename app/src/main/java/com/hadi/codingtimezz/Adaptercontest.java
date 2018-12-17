package com.hadi.codingtimezz;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.net.Uri;
import android.provider.CalendarContract;
import android.support.annotation.NonNull;
import android.support.v4.content.ContextCompat;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;


public class Adaptercontest extends RecyclerView.Adapter<Adaptercontest.ViewHolder> {

    private Context context;
    private LayoutInflater inflater;
    List<Datalist> data= new ArrayList<Datalist>();
    Datalist current;
    int currentPos=0;

    public Adaptercontest(Context context, List<Datalist> data) {
        this.context=context;
        inflater= LayoutInflater.from(context);
        this.data=data;
    }


    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view=LayoutInflater.from(parent.getContext()).inflate(R.layout.container_list,parent,false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull final ViewHolder holder, int position) {

        final Datalist list=data.get(position);
        holder.compename.setText(list.getName());

        if (list.getPlatform().equalsIgnoreCase("codechef")){
            Glide.with(context).load(R.drawable.codechef).into(holder.imageView);
        }
        else if (list.getPlatform().equalsIgnoreCase("hackerearth"))
        {
            Glide.with(context).load(R.drawable.hackerearth_logo).into(holder.imageView);
        }
        else if (list.getPlatform().equalsIgnoreCase("hackerrank"))
        {
            Glide.with(context).load(R.drawable.hackerrank_logo).into(holder.imageView);
        }
        else if (list.getPlatform().equalsIgnoreCase("topcoder"))
        {
            Glide.with(context).load(R.drawable.topcoder).into(holder.imageView);
        }
        else if (list.getPlatform().equalsIgnoreCase("codeforces"))
        {
            Glide.with(context).load(R.drawable.codeforces).into(holder.imageView);
        }
        else if(list.getPlatform().equalsIgnoreCase("other"))
        {
            Glide.with(context).load(R.drawable.other).into(holder.imageView);
        }

        holder.startdate.setText(list.getStartTime());
        holder.duration.setText(list.getDuration());
        holder.compelink.setOnClickListener(new View.OnClickListener() {
                                                @Override
                                                public void onClick(View view) {

                                                    DialogInterface.OnClickListener dialogClickListener = new DialogInterface.OnClickListener() {
                                                        @Override
                                                        public void onClick(DialogInterface dialog, int which) {
                                                            switch (which) {
                                                                case DialogInterface.BUTTON_POSITIVE:
                                                                    Intent browserIntent = new Intent(Intent.ACTION_VIEW, Uri.parse(list.Urls));
                                                                    context.startActivity(browserIntent);
                                                                    break;

                                                                case DialogInterface.BUTTON_NEGATIVE:
                                                                    Toast.makeText(context, "\u2639", Toast.LENGTH_SHORT).show();
                                                                    break;
                                                            }
                                                        }
                                                    };

                                                    AlertDialog.Builder builder = new AlertDialog.Builder(context);
                                                    builder.setMessage("Are you sure you want to open browser ?").setPositiveButton("Yes", dialogClickListener)
                                                            .setNegativeButton("No", dialogClickListener).show();
                                                }
                                            });
        holder.sharebutton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent myIntent = new Intent(Intent.ACTION_SEND);
                myIntent.setType("text/plain");
                String  shareBody = (list.getName());
                String shareSub = ("Hey, check out this coding contest:" + list.getName() +"\n"+"Link:"+list.getUrls()+"\n"+list.getStartTime());
                myIntent.putExtra(Intent.EXTRA_SUBJECT,shareBody);
                myIntent.putExtra(Intent.EXTRA_TEXT,shareSub);
                context.startActivity(Intent.createChooser(myIntent,"Share Please!! :))"));
            }
        });
        holder.compenoti.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                int hours=0;
                int year = 0;
                int month=0;
                int minutes=0;
                int date=0;
                try
                {
                    //this part is given by Harshit Sahai
                HashMap<String, Integer> map = new HashMap<>();
                map.put("Jan", 0);
                map.put("Feb", 1);
                map.put("Mar", 2);
                map.put("Apr", 3);
                map.put("May", 4);
                map.put("June", 5);
                map.put("July", 6);
                map.put("Aug", 7);
                map.put("Sep", 8);
                map.put("Oct", 9);
                map.put("Nov", 10);
                map.put("Dec", 11);
                String s=list.getStartTime();
                s=s.replace(':', ' ');
                String s1[]=s.split(" ");
                int l=s1.length-3;
                String s2[]=new String[l];
                int coll[]=new int[l];
                int c=0;
                for(int i=0;i<s1.length;i++)
                {
                    if(i<=2)
                        continue;
                    else
                        s2[c++]=s1[i];
                }
                for(int i=0;i<s2.length;i++)
                {
                    if(i==1)
                    {
                         month=map.get(s2[1]);
                        //System.out.println(month);
                    }
                    else if(i==0)
                    {
                          date=Integer.parseInt(s2[0]);
                        //System.out.println(date);
                    }
                    else if(i==2)
                    {
                          year=Integer.parseInt(s2[2]);
                        //System.out.println(year);
                    }
                    else if(i==3)
                    {
                          hours=Integer.parseInt(s2[3]);
                        //System.out.println(hours);
                    }
                    else if(i==4)
                    {
                          minutes=Integer.parseInt(s2[4]);
                        //System.out.println(minutes);
                    }//till here
                }}
                catch(Exception e){
                    e.printStackTrace();
                }

                Intent calIntent = new Intent(Intent.ACTION_INSERT);
                calIntent.setData(CalendarContract.Events.CONTENT_URI);
                calIntent.putExtra(CalendarContract.Events.TITLE, list.getName());
                //calIntent.putExtra(CalendarContract.Events.EVENT_LOCATION, "The W Hotel Bar on Third Street");
                //calIntent.putExtra(CalendarContract.Events.DESCRIPTION, "Hang out after Google IO for a drink and geeky conversation.");
                Calendar startTime = Calendar.getInstance();
                startTime.set(year,month,date,hours,minutes);
                calIntent.putExtra(CalendarContract.EXTRA_EVENT_BEGIN_TIME,
                        startTime.getTimeInMillis());
                context.startActivity(calIntent);
            }
        });
    }
    @Override
    public int getItemCount() {
        return data.size();
    }

    class  ViewHolder extends RecyclerView.ViewHolder{

        TextView compename;
        TextView startdate;
        TextView duration;
        Button compelink;
        Button sharebutton;
        ImageView imageView;
        Button compenoti;

        public ViewHolder(View itemView) {
            super(itemView);
            compename=(TextView)itemView.findViewById(R.id.compename);
            startdate=(TextView)itemView.findViewById(R.id.startdate);
            duration=(TextView)itemView.findViewById(R.id.duration);
            compelink=(Button)itemView.findViewById(R.id.compelink);
            sharebutton=(Button)itemView.findViewById(R.id.sharebutton);
            imageView=itemView.findViewById(R.id.compelogo);
            compenoti=(Button)itemView.findViewById(R.id.compenoti);
        }
    }
}