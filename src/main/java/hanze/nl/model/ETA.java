package hanze.nl.model;

class ETA implements IETA {
	public String halteNaam;
	public int richting;
	public int aankomsttijd;
	
	ETA(String halteNaam, int richting, int aankomsttijd){
		this.halteNaam=halteNaam;
		this.richting=richting;
		this.aankomsttijd=aankomsttijd;
	}
}
