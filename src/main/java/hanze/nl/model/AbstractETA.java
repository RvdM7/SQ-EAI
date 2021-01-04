package hanze.nl.model;

public abstract class AbstractETA {

    public static AbstractETA createETA(String halteNaam, int richting, int aankomsttijd){
        return new ETA(halteNaam, richting, aankomsttijd);
    }
}
