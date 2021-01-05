package hanze.nl.model;

public class BerichtFactory {
    
    public static IBericht createBericht(BerichtInfoBuilder berichtInfoBuilder){
        return new Bericht(berichtInfoBuilder);
    }
}
