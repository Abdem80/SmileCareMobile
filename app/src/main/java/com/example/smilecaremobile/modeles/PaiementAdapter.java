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

public class PaiementAdapter extends RecyclerView.Adapter<PaiementAdapter.MyViewHolder> {
    private Context context;
    private ArrayList<Paiement> paiements = new ArrayList<Paiement>();

    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(context);
        View view = inflater.inflate(R.layout.paiements_liste, parent, false);
        return new MyViewHolder(view);
    }

    public PaiementAdapter(Context context, ArrayList<Paiement> paiements){
        this.context = context;
        this.paiements = paiements;
    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHolder holder, int position) {
        CharSequence charMontant = PaiementAdapter.this.paiements.get(position).getMontant() + " $";
        CharSequence charType = "Méthode de paiement : " + PaiementAdapter.this.paiements.get(position).getType();
        CharSequence charEtat = "État du paiement : " + PaiementAdapter.this.paiements.get(position).getEtat();

        holder.date.setText(PaiementAdapter.this.paiements.get(position).getDate());
        holder.montant.setText(charMontant);
        holder.type.setText(charType);
        holder.etat.setText(charEtat);
    }

    @Override
    public int getItemCount(){ return this.paiements.size(); }

    public class MyViewHolder extends RecyclerView.ViewHolder{
        TextView date;
        TextView montant;
        TextView type;
        TextView etat;

        public MyViewHolder(View itemView){
            super(itemView);
            date = (TextView) itemView.findViewById(R.id.paiement_date);
            montant = (TextView) itemView.findViewById(R.id.paiement_montant);
            type = (TextView) itemView.findViewById(R.id.paiement_type);
            etat = (TextView) itemView.findViewById(R.id.paiement_etat);
        }
    }
}