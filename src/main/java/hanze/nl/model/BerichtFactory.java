package hanze.nl.model;

public class BerichtFactory {

    public static IBericht createBericht(String lijnNaam, String bedrijf, String busID, int tijd){
        return new Bericht(lijnNaam, bedrijf, busID, tijd);
    }
}
