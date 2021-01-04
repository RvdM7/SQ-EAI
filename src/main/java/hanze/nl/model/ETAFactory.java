package hanze.nl.model;

public class ETAFactory {

    public static IETA createETA(String halteNaam, int richting, int aankomsttijd){
        return new ETA(halteNaam, richting, aankomsttijd);
    }
}
