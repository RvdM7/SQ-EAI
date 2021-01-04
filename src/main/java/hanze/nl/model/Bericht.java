package hanze.nl.model;

import java.util.ArrayList;

public class Bericht {
	private String lijnNaam;
	private String eindpunt;
	private String bedrijf;
	private String busID;
	private int tijd;
	private final ArrayList<ETA> ETAs;
	
	public Bericht(String lijnNaam, String bedrijf, String busID, int tijd){
		this.lijnNaam=lijnNaam;
		this.bedrijf=bedrijf;
		this.eindpunt="";
		this.busID=busID;
		this.tijd=tijd;
		this.ETAs=new ArrayList<ETA>();
	}

	public int getTijd() {
		return tijd;
	}

	public void setTijd(int tijd) {
		this.tijd = tijd;
	}

	public String getBusID() {
		return busID;
	}

	public void setBusID(String busID) {
		this.busID = busID;
	}

	public String getBedrijf() {
		return bedrijf;
	}

	public void setBedrijf(String bedrijf) {
		this.bedrijf = bedrijf;
	}

	public String getEindpunt() {
		return eindpunt;
	}

	public void setEindpunt(String eindpunt) {
		this.eindpunt = eindpunt;
	}

	public String getLijnNaam() {
		return lijnNaam;
	}

	public void setLijnNaam(String lijnNaam) {
		this.lijnNaam = lijnNaam;
	}

	public ArrayList<ETA> getETAs() {
		return ETAs;
	}
}
