package hanze.nl.model;

import java.util.ArrayList;

public abstract class AbstractBericht {

    public static AbstractBericht newBericht(String lijnNaam, String bedrijf, String busID, int tijd){
        return new Bericht(lijnNaam, bedrijf, busID, tijd);
    }

    abstract public String getEindpunt();
    abstract public void setEindpunt(String newEindpunt);
    abstract public ArrayList<AbstractETA> getETAs();
}
