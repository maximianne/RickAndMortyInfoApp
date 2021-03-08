package com.example.homework3;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

public class LocationAdapter extends RecyclerView.Adapter<LocationAdapter.ViewHolder>{

    private ArrayList<Location> locations;

    public LocationAdapter(ArrayList<Location> locations){
        this.locations=locations;
    }

    @NonNull
    @Override
    public LocationAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        Context context = parent.getContext();
        LayoutInflater inflater = LayoutInflater.from(context);
        //inflate the custom layout
        View item = inflater.inflate(R.layout.item_location, parent, false);
        //return a new ViewHolder
        ViewHolder viewHolder = new ViewHolder(item);

        return viewHolder;

    }

    @Override
    public void onBindViewHolder(@NonNull LocationAdapter.ViewHolder holder, int position) {
        Location curr= locations.get(position);
        holder.textView_LocationName.setText(curr.getName());
        holder.textView_LocationType.setText("Type: " + curr.getType());
        holder.textView_LocationDimension.setText("Dimension: " + curr.getDimension());
    }

    @Override
    public int getItemCount() {
        return locations.size();
    }

    class ViewHolder extends RecyclerView.ViewHolder{

        TextView textView_LocationName;
        TextView textView_LocationType;
        TextView textView_LocationDimension;

        public ViewHolder(View itemView){
            super(itemView);
            //Context context = itemView.getContext();
            textView_LocationName=itemView.findViewById(R.id.textView_LocationName);
            textView_LocationType=itemView.findViewById(R.id.textView_LocationType);
            textView_LocationDimension=itemView.findViewById(R.id.textView_LocationDimension);

        }
    }
}
