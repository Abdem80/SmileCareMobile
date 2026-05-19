package com.example.smilecaremobile.modeles;

public class RendezVous {

    public int idRdv;
    public String nomService;
    public String dateRdv;
    public String heureRdv;
    public String dentiste;

    public RendezVous(int id, String s, String dhRdv, String d) {
        idRdv = id;
        nomService = s;

        StringBuilder tempDate = new StringBuilder();
        StringBuilder tempHeure = new StringBuilder();
        int tempIndex = 0;
        for(int i = 0; i < dhRdv.length(); i++) {
            if(dhRdv.charAt(i) != ' ') {
                if(tempIndex == 0) {
                    tempDate.append(dhRdv.charAt(i));
                }
                if(tempIndex == 1) {
                    tempHeure.append(dhRdv.charAt(i));
                }
            }
            else {
                tempIndex++;
            }
        }

        dateRdv = tempDate.toString();
        heureRdv = tempHeure.toString();
        dentiste = d;
    }
    public RendezVous(int id, String s, String dRdv, String hRdv, String d) {
        idRdv = id;
        nomService = s;
        dateRdv = dRdv;
        heureRdv = hRdv;
        dentiste = d;
    }
}
