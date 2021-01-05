package hanze.nl.model;

import java.util.ArrayList;

public interface IBericht {

    public String getEindpunt();
    public void setEindpunt(String newEindpunt);
    public ArrayList<IETA> getETAs();
}
