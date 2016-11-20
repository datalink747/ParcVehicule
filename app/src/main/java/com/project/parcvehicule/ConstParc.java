package com.project.parcvehicule;

import android.os.Parcel;
import android.os.Parcelable;

/**
 * Created by Soussi on 18/05/2016.
 */
public class ConstParc implements Parcelable {
    int id_individu,id_mission,id_intervention;
    String nom_individi,prenom_individi,cin_individu;
    String date_intervention,date_probléme,etat_intervention,compterendu_intervention,duree_intervention,
            probleme_intervention,nom_intervenant;

    String date_debut_mission,date_fin_mission,objet_mission,nom_demandeur_mission,matricule_mission,
            etat_mission,km_mission,qte_carburant;

    double Longitude_m,Latitude_m;

    public ConstParc() {
    }

    protected ConstParc(Parcel in) {
        id_individu = in.readInt();
        id_mission = in.readInt();
        id_intervention = in.readInt();
        nom_individi = in.readString();
        prenom_individi = in.readString();
        cin_individu = in.readString();
        date_intervention = in.readString();
        date_probléme = in.readString();
        etat_intervention = in.readString();
        compterendu_intervention = in.readString();
        duree_intervention = in.readString();
        probleme_intervention = in.readString();
        nom_intervenant = in.readString();
        date_debut_mission = in.readString();
        date_fin_mission = in.readString();
        objet_mission = in.readString();
        nom_demandeur_mission = in.readString();
        matricule_mission = in.readString();
        etat_mission = in.readString();
        km_mission = in.readString();
        qte_carburant = in.readString();
        Longitude_m = in.readDouble();
        Latitude_m = in.readDouble();
    }

    public static final Creator<ConstParc> CREATOR = new Creator<ConstParc>() {
        @Override
        public ConstParc createFromParcel(Parcel in) {
            return new ConstParc(in);
        }

        @Override
        public ConstParc[] newArray(int size) {
            return new ConstParc[size];
        }
    };

    public String getCin_individu() {
        return cin_individu;
    }

    public void setCin_individu(String cin_individu) {
        this.cin_individu = cin_individu;
    }

    public int getId_individu() {
        return id_individu;
    }

    public void setId_individu(int id_individu) {
        this.id_individu = id_individu;
    }

    public int getId_mission() {
        return id_mission;
    }

    public void setId_mission(int id_mission) {
        this.id_mission = id_mission;
    }

    public int getId_intervention() {
        return id_intervention;
    }

    public void setId_intervention(int id_intervention) {
        this.id_intervention = id_intervention;
    }

    public String getNom_individi() {
        return nom_individi;
    }

    public void setNom_individi(String nom_individi) {
        this.nom_individi = nom_individi;
    }

    public String getPrenom_individi() {
        return prenom_individi;
    }

    public void setPrenom_individi(String prenom_individi) {
        this.prenom_individi = prenom_individi;
    }

    public String getDate_intervention() {
        return date_intervention;
    }

    public void setDate_intervention(String date_intervention) {
        this.date_intervention = date_intervention;
    }

    public String getDate_probléme() {
        return date_probléme;
    }

    public void setDate_probléme(String date_probléme) {
        this.date_probléme = date_probléme;
    }

    public String getEtat_intervention() {
        return etat_intervention;
    }

    public void setEtat_intervention(String etat_intervention) {
        this.etat_intervention = etat_intervention;
    }

    public String getCompterendu_intervention() {
        return compterendu_intervention;
    }

    public void setCompterendu_intervention(String compterendu_intervention) {
        this.compterendu_intervention = compterendu_intervention;
    }

    public String getDuree_intervention() {
        return duree_intervention;
    }

    public void setDuree_intervention(String duree_intervention) {
        this.duree_intervention = duree_intervention;
    }

    public String getProbleme_intervention() {
        return probleme_intervention;
    }

    public void setProbleme_intervention(String probleme_intervention) {
        this.probleme_intervention = probleme_intervention;
    }

    public String getNom_intervenant() {
        return nom_intervenant;
    }

    public void setNom_intervenant(String nom_intervenant) {
        this.nom_intervenant = nom_intervenant;
    }

    public String getDate_debut_mission() {
        return date_debut_mission;
    }

    public void setDate_debut_mission(String date_debut_mission) {
        this.date_debut_mission = date_debut_mission;
    }

    public String getDate_fin_mission() {
        return date_fin_mission;
    }

    public void setDate_fin_mission(String date_fin_mission) {
        this.date_fin_mission = date_fin_mission;
    }

    public String getObjet_mission() {
        return objet_mission;
    }

    public void setObjet_mission(String objet_mission) {
        this.objet_mission = objet_mission;
    }

    public String getNom_demandeur_mission() {
        return nom_demandeur_mission;
    }

    public void setNom_demandeur_mission(String nom_demandeur_mission) {
        this.nom_demandeur_mission = nom_demandeur_mission;
    }

    public String getMatricule_mission() {
        return matricule_mission;
    }

    public void setMatricule_mission(String matricule_mission) {
        this.matricule_mission = matricule_mission;
    }

    public String getEtat_mission() {
        return etat_mission;
    }

    public void setEtat_mission(String etat_mission) {
        this.etat_mission = etat_mission;
    }

    public String getKm_mission() {
        return km_mission;
    }

    public void setKm_mission(String km_mission) {
        this.km_mission = km_mission;
    }

    public String getQte_carburant() {
        return qte_carburant;
    }

    public void setQte_carburant(String qte_carburant) {
        this.qte_carburant = qte_carburant;
    }

    public double getLongitude_m() {
        return Longitude_m;
    }

    public void setLongitude_m(double longitude_m) {
        Longitude_m = longitude_m;
    }

    public double getLatitude_m() {
        return Latitude_m;
    }

    public void setLatitude_m(double latitude_m) {
        Latitude_m = latitude_m;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeInt(id_individu);
        dest.writeInt(id_mission);
        dest.writeInt(id_intervention);
        dest.writeString(nom_individi);
        dest.writeString(prenom_individi);
        dest.writeString(cin_individu);
        dest.writeString(date_intervention);
        dest.writeString(date_probléme);
        dest.writeString(etat_intervention);
        dest.writeString(compterendu_intervention);
        dest.writeString(duree_intervention);
        dest.writeString(probleme_intervention);
        dest.writeString(nom_intervenant);
        dest.writeString(date_debut_mission);
        dest.writeString(date_fin_mission);
        dest.writeString(objet_mission);
        dest.writeString(nom_demandeur_mission);
        dest.writeString(matricule_mission);
        dest.writeString(etat_mission);
        dest.writeString(km_mission);
        dest.writeString(qte_carburant);
        dest.writeDouble(Longitude_m);
        dest.writeDouble(Latitude_m);
    }
}
