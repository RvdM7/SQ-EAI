package hanze.nl.model;

import java.util.ArrayList;

class Bericht implements IBericht {
	private String lijnNaam;
	private String eindpunt;
	private String bedrijf;
	private String busID;
	private int tijd;
	private final ArrayList<IETA> ETAs;
	
	Bericht(String lijnNaam, String bedrijf, String busID, int tijd){
		this.lijnNaam=lijnNaam;
		this.bedrijf=bedrijf;
		this.eindpunt="";
		this.busID=busID;
		this.tijd=tijd;
		this.ETAs=new ArrayList<IETA>();
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
