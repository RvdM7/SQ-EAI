package hanze.nl.model;

import java.util.ArrayList;

public interface IBericht {

    String getEindpunt();
    void setEindpunt(String newEindpunt);
    ArrayList<IETA> getETAs();
}
