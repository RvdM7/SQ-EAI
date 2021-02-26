package hanze.nl.bussimulator;

public class Bus{

	private Bedrijven bedrijf;
	private Lijnen lijn;
	private int halteNummer;
	private int totVolgendeHalte;
	private int richting;
	private boolean bijHalte;
	private String busID;
	
	Bus(Lijnen lijn, Bedrijven bedrijf, int richting){
		this.lijn=lijn;
		this.setBedrijf(bedrijf);
		this.richting=richting;
		this.setHalteNummer(-1);
		this.setTotVolgendeHalte(0);
		this.setBijHalte(false);
		this.setBusID("Niet gestart");
	}
	
	public void setbusID(int starttijd){
		this.setBusID(starttijd+lijn.name()+richting);
	}

	public Bedrijven getBedrijf() {
		return bedrijf;
	}

	public void setBedrijf(Bedrijven bedrijf) {
		this.bedrijf = bedrijf;
	}
	
	public Lijnen getLijn() {
		return lijn;
	}

	public int getHalteNummer() {
		return halteNummer;
	}

	public void setHalteNummer(int halteNummer) {
		this.halteNummer = halteNummer;
	}

	public int getTotVolgendeHalte() {
		return totVolgendeHalte;
	}

	public void setTotVolgendeHalte(int totVolgendeHalte) {
		this.totVolgendeHalte = totVolgendeHalte;
	}

	public boolean isBijHalte() {
		return bijHalte;
	}

	public void setBijHalte(boolean bijHalte) {
		this.bijHalte = bijHalte;
	}

	public String getBusID() {
		return busID;
	}

	public void setBusID(String busID) {
		this.busID = busID;
	}
	
	public int getRichting() {
		return richting;
	}
	
	
}
