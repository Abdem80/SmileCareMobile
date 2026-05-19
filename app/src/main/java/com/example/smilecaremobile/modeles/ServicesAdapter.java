package com.example.smilecaremobile.modeles;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.smilecaremobile.R;

import java.util.ArrayList;

public class ServicesAdapter extends RecyclerView.Adapter<ServicesAdapter.MyViewHolder> {
    private Context context;
    private ArrayList<Service> services = new ArrayList<Service>();

    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(context);
        View view = inflater.inflate(R.layout.services_liste, parent, false);
        return new MyViewHolder(view);
    }

    public ServicesAdapter(Context context, ArrayList<Service> services){
        this.context = context;
        this.services = services;
    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHolder holder, int position) {
        CharSequence charCategorie = "Catégorie : " + ServicesAdapter.this.services.get(position).getCategorie();
        CharSequence charDuree = "Durée : " + ServicesAdapter.this.services.get(position).getDuree() + "h";
        CharSequence charDescription;
        if (ServicesAdapter.this.services.get(position).getDescription().equals("null")){
            charDescription = "Description : Aucune description";
        }
        else {
            charDescription = "Description : " + ServicesAdapter.this.services.get(position).getDescription();
        }
        holder.name.setText(ServicesAdapter.this.services.get(position).getName());
        holder.categorie.setText(charCategorie);
        holder.duree.setText(charDuree);
        holder.description.setText(charDescription);
    }

    @Override
    public int getItemCount(){ return this.services.size(); }

    public class MyViewHolder extends RecyclerView.ViewHolder{
        TextView name;
        TextView categorie;
        TextView duree;
        TextView description;

        public MyViewHolder(View itemView){
            super(itemView);
            name = (TextView) itemView.findViewById(R.id.service_name);
            categorie = (TextView) itemView.findViewById(R.id.service_categorie);
            duree = (TextView) itemView.findViewById(R.id.service_duree);
            description = (TextView) itemView.findViewById(R.id.service_description);
        }
    }
}
