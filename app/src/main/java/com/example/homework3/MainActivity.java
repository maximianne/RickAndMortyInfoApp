package com.example.homework3;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.widget.Button;

import com.loopj.android.http.AsyncHttpClient;
import com.loopj.android.http.AsyncHttpResponseHandler;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Random;
import java.util.Set;

import cz.msebera.android.httpclient.Header;

public class MainActivity extends AppCompatActivity {
    private Button button_charaters;
    private Button button_location;
    private Button button_episodes;
    private int rand;

    String api_urlC="https://rickandmortyapi.com/api/character/";
    String api_urlE="https://rickandmortyapi.com/api/episode/";
    private static AsyncHttpClient client = new AsyncHttpClient();
    SharedPreferences sharedPreferences;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        sharedPreferences=this.getPreferences(Context.MODE_PRIVATE);
        characterInfo();
        episodeInfo();

        button_charaters=findViewById(R.id.button);
        button_episodes=findViewById(R.id.button2);
        button_location=findViewById(R.id.button3);

        button_charaters.setOnClickListener(v -> loadFragment(new FragmentCharacter()));
        button_location.setOnClickListener(v -> loadFragment(new FragmentLocation()));
        button_episodes.setOnClickListener(v -> loadFragment(new FragmentEpisode()));
    }

    public void loadFragment(Fragment fragment){
        FragmentManager fragmentManager=getSupportFragmentManager();
        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
        fragmentTransaction.replace(R.id.fragmentContainerView,fragment);
        fragmentTransaction.commit();
    }

    public void characterInfo(){
        Random random=new Random();
        ArrayList <String> episodeInfo=new ArrayList<>();
        int charId=random.nextInt(671);
        api_urlC=api_urlC+charId;

        client.addHeader("accept", "application/json");
        client.get(api_urlC, new AsyncHttpResponseHandler(){
            @Override
            public void onSuccess(int statusCode, Header[] headers, byte[] responseBody) {
                try{
                    JSONObject json= new JSONObject((new String(responseBody)));
                    SharedPreferences.Editor editor =sharedPreferences.edit();
                    editor.putString("name", json.getString("name"));
                    editor.putString("status", json.getString("status"));
                    editor.putString("species", json.getString("species"));
                    editor.putString("type",json.getString("type"));
                    editor.putString("gender", json.getString("gender"));
                    editor.putString("origin", json.getJSONObject("origin").getString("name"));
                    editor.putString("location",json.getJSONObject("location").getString("name"));
                    editor.putString("imageURL", json.getString("image"));

                    JSONArray array= json.getJSONArray("episode");
                    ArrayList <String> ep = filterEpisodes(array);
                    String episodes=ep.toString();
                    editor.putString("episodes", episodes);
                    editor.apply(); //DONT FORGET TO APPLY !!!

                }
                catch (JSONException e) {
                    e.printStackTrace();
                }
            }
            @Override
            public void onFailure(int statusCode, Header[] headers, byte[] responseBody, Throwable error) {
                Log.e("api error", new String(responseBody));
            }
        });
    }
    public void episodeInfo(){
        Random random=new Random();
        int epiId=random.nextInt(41);
        api_urlE=api_urlE+epiId;

        client.addHeader("accept", "application/json");
        client.get(api_urlE, new AsyncHttpResponseHandler(){
            @Override
            public void onSuccess(int statusCode, Header[] headers, byte[] responseBody) {
                try{
                    JSONObject json= new JSONObject((new String(responseBody)));
                    SharedPreferences.Editor editor =sharedPreferences.edit();
                    editor.putString("Ename", json.getString("name"));
                    //Log.d("episodename",json.getString("name") );
                    editor.putString("episode", json.getString("episode"));
                    //Log.d("epiepisode",json.getString("episode") );
                    editor.putString("airDate", json.getString("air_date"));
                   //Log.d("episodeairDate",json.getString("air_date") );
                    editor.putInt("episodeNum", epiId);
                    JSONArray array =json.getJSONArray("characters");
                    //Log.d("characters", array.toString());

                    //every array has a URl
                    characterURL(array.get(0).toString(),1);
                    characterURL(array.get(1).toString(),2);
                    characterURL(array.get(2).toString(),3);
                    editor.apply(); //DONT FORGET TO APPLY !!!

                }
                catch (JSONException e) {
                    e.printStackTrace();
                }
            }
            @Override
            public void onFailure(int statusCode, Header[] headers, byte[] responseBody, Throwable error) {
                Log.e("api error", new String(responseBody));
            }
        });
    }


    public void characterURL(String URL, int x){

        client.addHeader("accept", "application/json");
        client.get(URL, new AsyncHttpResponseHandler(){
            @Override
            public void onSuccess(int statusCode, Header[] headers, byte[] responseBody) {
                try{
                    JSONObject json= new JSONObject((new String(responseBody)));
                    SharedPreferences.Editor editor =sharedPreferences.edit();
                    String imageURL=json.getString("image");
                    editor.putString("ch"+x, imageURL);
                    Log.d("tests", imageURL);
                    editor.apply();
                }
                catch (JSONException e) {
                    e.printStackTrace();
                }
            }
            @Override
            public void onFailure(int statusCode, Header[] headers, byte[] responseBody, Throwable error) {
                Log.e("api error", new String(responseBody));
            }
        });
    }


    //FIX THIS
    public ArrayList<String> filterEpisodes(JSONArray array) throws JSONException {
        ArrayList<Integer> toReturn= new ArrayList<>();
        ArrayList<String> filter=new ArrayList<>();
        Log.d("JSON ARRAYYYYYY", array.toString());
        Log.d("JSON item", array.get(0).toString());
        Log.d("size",String.valueOf(array.length()));
        for(int i=0; i<array.length();i++){
            String temp=array.get(i).toString();
            String [] temp2=temp.split("/");
            Log.d("value", temp2.toString());
            String val=temp2[temp2.length-1];
            filter.add(val);
        }
        Log.d("filtered json array", filter.toString());
        return filter;
    }

}