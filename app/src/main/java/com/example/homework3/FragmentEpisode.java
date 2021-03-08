package com.example.homework3;

import android.annotation.SuppressLint;
import android.app.Application;
import android.app.Notification;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.content.Context;
import android.content.SharedPreferences;
import android.os.Build;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.core.app.NotificationCompat;
import androidx.core.app.NotificationManagerCompat;
import androidx.fragment.app.Fragment;

import com.squareup.picasso.Picasso;

public class FragmentEpisode extends Fragment{

    TextView episodeName;
    TextView airDate;
    ImageView char1;
    ImageView char2;
    ImageView char3;
    View v;
    SharedPreferences sharedPreferences;
    Button buttonN;
    private NotificationManagerCompat manager;
    String not;
    String URLNot;
    String CHANNEL_1_ID="channel1";

    @SuppressLint("SetTextI18n")
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        v = inflater.inflate(R.layout.fragment_episode, container, false);
        createNotificationsChannels();
        sharedPreferences = getActivity().getPreferences(Context.MODE_PRIVATE);
        buttonN = v.findViewById(R.id.button_moreInfo);
        buttonN.setOnClickListener(v1 -> sendOnChannel1(v));
        int episodeNum= sharedPreferences.getInt("episodeNum",0);
        String episodeN=  sharedPreferences.getString("Ename", "").replace(" ", "_");

        manager=NotificationManagerCompat.from(getActivity());

        not= "To read more information about Episode " + episodeNum+ " please visit:" +
                "https://rickandmorty.fandom.com/wiki/"+episodeN;
        Log.d("Notification", not);

        URLNot=  "https://rickandmorty.fandom.com/wiki/"+sharedPreferences.getString("episode", "");

        episodeName =v.findViewById(R.id.textView_episodename);
        episodeName.setText(sharedPreferences.getString("episode", "")
                + " " + sharedPreferences.getString("Ename", ""));

        airDate=v.findViewById(R.id.textView_airdate);
        airDate.setText("Aired on: " + sharedPreferences.getString("airDate",""));

        String url1=sharedPreferences.getString("ch1","");
        Log.d("url1", url1);
        String url2=sharedPreferences.getString("ch2","");
        String url3= sharedPreferences.getString("ch3","");
        char1=v.findViewById(R.id.imageViewchar1);
        char2=v.findViewById(R.id.imageViewchar2);
        char3=v.findViewById(R.id.imageViewchar3);

        Picasso.get().load(url1).into(char1);
        Picasso.get().load(url2).into(char2);
        Picasso.get().load(url3).into(char3);

        return v;
    }

    private void sendOnChannel1(View v){
        Notification notification= new NotificationCompat.Builder(getContext(),CHANNEL_1_ID)
                .setSmallIcon(R.drawable.ic_one).setContentTitle(sharedPreferences.getString("episode", "") + sharedPreferences.getString("Ename", "")).setContentText(not)
                .setPriority(NotificationCompat.PRIORITY_HIGH)
                .setCategory(NotificationCompat.CATEGORY_MESSAGE)
                .setStyle(new NotificationCompat.BigTextStyle()
                        .bigText(not))
                .build();
        manager.notify(1,notification);

    }

    private void createNotificationsChannels(){
        if(Build.VERSION.SDK_INT>=Build.VERSION_CODES.O){
            NotificationChannel channel1=new NotificationChannel
                    (CHANNEL_1_ID,
                    "Channel 1",
                    NotificationManager.IMPORTANCE_HIGH);

            channel1.setDescription("This is Channel 1");

            NotificationManager manager= (NotificationManager)getActivity().getSystemService(getActivity().NOTIFICATION_SERVICE);
            manager.createNotificationChannel(channel1);
        }
    }
}

