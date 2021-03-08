package com.example.homework3;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.loopj.android.http.AsyncHttpClient;
import com.loopj.android.http.AsyncHttpResponseHandler;
import com.squareup.picasso.Picasso;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.Random;

import cz.msebera.android.httpclient.Header;

public class FragmentCharacter extends Fragment {

    private ImageView characterPic;
    private TextView name;
    private TextView status;
    private TextView species;
    private TextView gender;
    private TextView origin;
    private TextView location;
    private TextView appear;
    private View v;
    private SharedPreferences sharedPreferences;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        v = inflater.inflate(R.layout.fragment_character, container, false);
        sharedPreferences = getActivity().getPreferences(Context.MODE_PRIVATE);

        name =v.findViewById(R.id.textView_name);
        name.setText(sharedPreferences.getString("name", ""));

        status=v.findViewById(R.id.textView_status);
        status.setText("Status: " + sharedPreferences.getString("status",""));

        species=v.findViewById(R.id.textView_speices);
        species.setText("Species: " + sharedPreferences.getString("species",""));

        gender =v.findViewById(R.id.textView_gender);
        gender.setText("Gender: " + sharedPreferences.getString("gender",""));

        origin=v.findViewById(R.id.textView_origin);
        origin.setText("Origin: " + sharedPreferences.getString("origin", ""));

        location=v.findViewById(R.id.textView_location);
        location.setText("Location: " + sharedPreferences.getString("location",""));

        appear=v.findViewById(R.id.textView_appearence);
        appear.setText("Appeared in Episodes:" + sharedPreferences.getString("episodes",""));

        String url=sharedPreferences.getString("imageURL","");
        characterPic=v.findViewById(R.id.imageView);
        Picasso.get().load(url).into(characterPic);

        return v;

    }

}
