package hanze.nl.bussimulator;

import com.thoughtworks.xstream.XStream;
import hanze.nl.bussimulator.Halte.Positie;
import hanze.nl.model.AbstractBericht;
import hanze.nl.model.AbstractETA;

public class Bus{

	private final Bedrijven bedrijf;
	private final Lijnen lijn;
	private int halteNummer;
	private int totVolgendeHalte;
	private final int richting;
	private boolean bijHalte;
	private String busID;
	
	Bus(Lijnen lijn, Bedrijven bedrijf, int richting){
		this.lijn=lijn;
		this.bedrijf=bedrijf;
		this.richting=richting;
		this.halteNummer = -1;
		this.totVolgendeHalte = 0;
		this.bijHalte = false;
		this.busID = "Niet gestart";
	}
	
	public void setbusID(int starttijd){
		this.busID=starttijd+lijn.name()+richting;
	}
	
	public void naarVolgendeHalte(){
		Positie volgendeHalte = lijn.getHalte(halteNummer+richting).getPositie();
		totVolgendeHalte = lijn.getHalte(halteNummer).afstand(volgendeHalte);
	}
	
	public boolean halteBereikt(){
		halteNummer+=richting;
		bijHalte=true;
		if ((halteNummer>=lijn.getLengte()-1) || (halteNummer == 0)) {
			System.out.printf("Bus %s heeft eindpunt (halte %s, richting %d) bereikt.%n", 
					lijn.name(), lijn.getHalte(halteNummer), lijn.getRichting(halteNummer));
			return true;
		}
		else {
			System.out.printf("Bus %s heeft halte %s, richting %d bereikt.%n", 
					lijn.name(), lijn.getHalte(halteNummer), lijn.getRichting(halteNummer));		
			naarVolgendeHalte();
		}		
		return false;
	}
	
	public void start() {
		halteNummer = (richting==1) ? 0 : lijn.getLengte()-1;
		System.out.printf("Bus %s is vertrokken van halte %s in richting %d.%n", 
				lijn.name(), lijn.getHalte(halteNummer), lijn.getRichting(halteNummer));		
		naarVolgendeHalte();
	}

	public boolean move(){
		boolean eindpuntBereikt = false;
		bijHalte=false;
		if (halteNummer == -1) {
			start();
		}
		else {
			totVolgendeHalte--;
			if (totVolgendeHalte==0){
				eindpuntBereikt=halteBereikt();
			}
		}
		return eindpuntBereikt;
	}
	
	public void sendETAs(int nu){
		AbstractBericht bericht = AbstractBericht.newBericht(lijn.name(), bedrijf.name(), busID, nu);
		checkETA(bericht);

		Positie eerstVolgende = lijn.getHalte(halteNummer+richting).getPositie();
		int tijdNaarHalte = totVolgendeHalte+nu;

		int i;
		for (i = halteNummer + richting; !(i >= lijn.getLengte()) && !(i < 0); i = i + richting ){
			tijdNaarHalte += lijn.getHalte(i).afstand(eerstVolgende);
			AbstractETA eta = AbstractETA.createETA(lijn.getHalte(i).name(), lijn.getRichting(i),tijdNaarHalte);
			bericht.getETAs().add(eta);
			eerstVolgende = lijn.getHalte(i).getPositie();
		}

		bericht.setEindpunt(lijn.getHalte(i-richting).name());
		sendBericht(bericht);
	}

	private void checkETA(AbstractBericht bericht){
		if (bijHalte) {
			AbstractETA eta = AbstractETA.createETA(lijn.getHalte(halteNummer).name(),lijn.getRichting(halteNummer),0);
			bericht.getETAs().add(eta);
		}
	}
	
	public void sendLastETA(int nu){
		AbstractBericht bericht = AbstractBericht.newBericht(lijn.name(),bedrijf.name(),busID,nu);
		String eindpunt = lijn.getHalte(halteNummer).name();
		AbstractETA eta = AbstractETA.createETA(eindpunt,lijn.getRichting(halteNummer),0);
		bericht.getETAs().add(eta);
		bericht.setEindpunt(eindpunt);
		sendBericht(bericht);
	}

	public void sendBericht(AbstractBericht bericht){
    	XStream xstream = new XStream();
    	xstream.alias("AbstractBericht", AbstractBericht.class);
    	xstream.alias("AbstractETA", AbstractETA.class);
    	String xml = xstream.toXML(bericht);
    	Producer producer = new Producer();
    	producer.sendBericht(xml);		
	}
}
