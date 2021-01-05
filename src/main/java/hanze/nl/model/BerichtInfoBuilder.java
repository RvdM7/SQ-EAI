package hanze.nl.model;

public class BerichtInfoBuilder {
    String lijnNaam;
    String bedrijf;
    String busID;
    int tijd;

    public BerichtInfoBuilder setLijnNaam(String lijnNaam) {
        this.lijnNaam = lijnNaam;
        return this;
    }

    public BerichtInfoBuilder setBedrijf(String bedrijf) {
        this.bedrijf = bedrijf;
        return this;
    }

    public BerichtInfoBuilder setBusID(String busID) {
        this.busID = busID;
        return this;
    }

    public BerichtInfoBuilder setTijd(int tijd) {
        this.tijd = tijd;
        return this;
    }
}
