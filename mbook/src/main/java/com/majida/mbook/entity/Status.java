package com.majida.mbook.entity;

public enum  Status {

    EnCours("Prêt en cours"),
    EnRetard("Prêt en retard"),
    Terminate("Prêt terminé"),
    Renouvele("Prêt renouvelé"),

    Waiting("Réservation en attente"),
    finish("Réservation terminé"),
    AnnuleP("Réservation annulée utilisateur"),
    AnnuleS("Réservation annulé server");

    public String stateName;

    private Status(String stateName) {
        this.stateName = stateName;
    }
}
