package com.example.smilecaremobile.modeles;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.smilecaremobile.R;
import com.example.smilecaremobile.api.API;

import org.json.JSONException;

import java.util.ArrayList;

public class RendezVousAdapter extends RecyclerView.Adapter<RendezVousAdapter.MyViewHolder> {
    private Context context;
    private ArrayList<RendezVous> rendezVous = new ArrayList<RendezVous>();

    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(context);
        View view = inflater.inflate(R.layout.rendezvous_list, parent, false);
        return new MyViewHolder(view);
    }

    public RendezVousAdapter(Context context, ArrayList<RendezVous> rendezVous){
        this.context = context;
        this.rendezVous = rendezVous;
    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHolder holder, int position) {
        holder.name.setText(RendezVousAdapter.this.rendezVous.get(position).nomService);
        holder.date.setText(RendezVousAdapter.this.rendezVous.get(position).dateRdv);
        holder.heure.setText(RendezVousAdapter.this.rendezVous.get(position).heureRdv);
        holder.dentiste.setText("avec " + RendezVousAdapter.this.rendezVous.get(position).dentiste);
    }

    @Override
    public int getItemCount(){ return this.rendezVous.size(); }

    public class MyViewHolder extends RecyclerView.ViewHolder{
        TextView name;
        TextView date;
        TextView heure;
        TextView dentiste;

        public MyViewHolder(View itemView){
            super(itemView);
            name = (TextView) itemView.findViewById(R.id.nomService);
            date = (TextView) itemView.findViewById(R.id.dateRdv);
            heure = (TextView) itemView.findViewById(R.id.heureRdv);
            dentiste = (TextView) itemView.findViewById(R.id.dentitseRdv);
        }
    }
}
