package hanze.nl.model;

import java.util.ArrayList;

public interface IBericht {

//    public static AbstractBericht newBericht(String lijnNaam, String bedrijf, String busID, int tijd){
//        return new Bericht(lijnNaam, bedrijf, busID, tijd);
//    }

    public String getEindpunt();
    public void setEindpunt(String newEindpunt);
    public ArrayList<IETA> getETAs();
}
