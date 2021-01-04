package hanze.nl.model;

import java.util.ArrayList;

class Bericht extends AbstractBericht {
	private String lijnNaam;
	private String eindpunt;
	private String bedrijf;
	private String busID;
	private int tijd;
	private final ArrayList<AbstractETA> ETAs;
	
	Bericht(String lijnNaam, String bedrijf, String busID, int tijd){
		this.lijnNaam=lijnNaam;
		this.bedrijf=bedrijf;
		this.eindpunt="";
		this.busID=busID;
		this.tijd=tijd;
		this.ETAs=new ArrayList<AbstractETA>();
	}

	public String getEindpunt() {
		return eindpunt;
	}

	public void setEindpunt(String newEindpunt) {
		eindpunt = newEindpunt;
	}

	public ArrayList<AbstractETA> getETAs() {
		return ETAs;
	}
}
