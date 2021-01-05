package hanze.nl.model;

import java.util.ArrayList;

class Bericht implements IBericht {
	private String lijnNaam;
	private String eindpunt = "";
	private String bedrijf;
	private String busID;
	private int tijd;
	private final ArrayList<IETA> ETAs = new ArrayList<IETA>();

	Bericht(BerichtInfoBuilder berichtInfoBuilder){
		this.lijnNaam = berichtInfoBuilder.lijnNaam;
		this.bedrijf = berichtInfoBuilder.bedrijf;
		this.busID = berichtInfoBuilder.busID;
		this.tijd = berichtInfoBuilder.tijd;
	}

	public String getEindpunt() {
		return eindpunt;
	}

	public void setEindpunt(String newEindpunt) {
		eindpunt = newEindpunt;
	}

	public ArrayList<IETA> getETAs() {
		return ETAs;
	}
}
