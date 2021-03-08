package com.example.homework3;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.loopj.android.http.AsyncHttpClient;
import com.loopj.android.http.AsyncHttpResponseHandler;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.Set;

import cz.msebera.android.httpclient.Header;

public class FragmentLocation extends Fragment {

    private RecyclerView recyclerView;
    String api_urlLp1="https://rickandmortyapi.com/api/location?page=1";
    private static AsyncHttpClient client = new AsyncHttpClient();
    ArrayList<Location>locations;
    View v;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        v = inflater.inflate(R.layout.fragment_location, container, false);
        recyclerView = v.findViewById(R.id.recycler_view_Loc);
        locations=new ArrayList<>();

        client.addHeader("accept", "application/json");
        client.get(api_urlLp1, new AsyncHttpResponseHandler(){
            @Override
            public void onSuccess(int statusCode, Header[] headers, byte[] responseBody) {
                try{
                    JSONObject json= new JSONObject((new String(responseBody)));
                    JSONArray array= json.getJSONArray("results");

                    for(int i=0; i<array.length();i++){
                        Location loc= new Location(array.getJSONObject(i).get("name").toString(),
                                array.getJSONObject(i).get("type").toString(),
                                array.getJSONObject(i).get("dimension").toString());
                        Log.d("location", loc.getName());
                        Log.d("type", loc.getType());
                        Log.d("dimension", loc.getDimension());
                        locations.add(loc);
                    }
                }
                catch (JSONException e) {
                    e.printStackTrace();
                }

                LocationAdapter adapter = new LocationAdapter(locations);
                recyclerView.setAdapter(adapter);
                recyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
            }
            @Override
            public void onFailure(int statusCode, Header[] headers, byte[] responseBody, Throwable error) {
                Log.e("api error", new String(responseBody));
            }
        });
        return v;
      }
}
