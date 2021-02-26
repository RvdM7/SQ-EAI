package hanze.nl.bussimulator;

import com.thoughtworks.xstream.XStream;
import hanze.nl.bussimulator.Halte.Positie;

public class BusMovement {
	public static void naarVolgendeHalte(Bus bus){
		Lijnen lijn = bus.getLijn();
		int halteNummer = bus.getHalteNummer();
		int richting = bus.getRichting();
		Positie volgendeHalte = lijn.getHalte(halteNummer+richting).getPositie();
		int totVolgendeHalte = lijn.getHalte(halteNummer).afstand(volgendeHalte);
		bus.setTotVolgendeHalte(totVolgendeHalte);
	}
	
	public static boolean halteBereikt(Bus bus){
		int halteNummer = bus.getHalteNummer();
		int richting = bus.getRichting();
		Lijnen lijn = bus.getLijn();
		halteNummer+=richting;
		bus.setBijHalte(true);
		if ((halteNummer>=lijn.getLengte()-1) || (halteNummer == 0)) {
			System.out.printf("Bus %s heeft eindpunt (halte %s, richting %d) bereikt.%n", 
					lijn.name(), lijn.getHalte(halteNummer), lijn.getRichting(halteNummer));
			return true;
		}
		else {
			System.out.printf("Bus %s heeft halte %s, richting %d bereikt.%n", 
					lijn.name(), lijn.getHalte(halteNummer), lijn.getRichting(halteNummer));		
			naarVolgendeHalte(bus);
		}		
		return false;
	}
	
	public static void start(Bus bus) {
		Lijnen lijn = bus.getLijn();
		int halteNummer = bus.getHalteNummer();
		halteNummer = (bus.getRichting()==1) ? 0 : lijn.getLengte()-1;
		bus.setHalteNummer(halteNummer);
		System.out.printf("Bus %s is vertrokken van halte %s in richting %d.%n", 
				lijn.name(), lijn.getHalte(halteNummer), lijn.getRichting(halteNummer));		
		naarVolgendeHalte(bus);
	}

	public static boolean move(Bus bus){
		boolean eindpuntBereikt = false;
		bus.setBijHalte(false);
		if (bus.getHalteNummer() == -1) {
			start(bus);
		}
		else {
			int totVolgendeHalte = bus.getTotVolgendeHalte();
			totVolgendeHalte--;
			bus.setTotVolgendeHalte(totVolgendeHalte);
			if (totVolgendeHalte==0){
				eindpuntBereikt=halteBereikt(bus);
			}
		}
		return eindpuntBereikt;
	}
	
	public static void sendETAs(Bus bus, int nu){
		int halteNummer = bus.getHalteNummer();
		int richting = bus.getRichting();
		Lijnen lijn = bus.getLijn();
		int i=0;
		Bericht bericht = new Bericht(lijn.name(), bus.getBedrijf().name(), bus.getBusID(), nu);
		if (bus.isBijHalte()) {
			ETA eta = new ETA(lijn.getHalte(halteNummer).name(),lijn.getRichting(halteNummer),0);
			bericht.ETAs.add(eta);
		}
		Positie eerstVolgende=lijn.getHalte(halteNummer+richting).getPositie();
		int tijdNaarHalte=bus.getTotVolgendeHalte()+nu;
		for (i = halteNummer+richting ; !(i>=lijn.getLengte()) && !(i < 0); i=i+richting ){
			tijdNaarHalte+= lijn.getHalte(i).afstand(eerstVolgende);
			ETA eta = new ETA(lijn.getHalte(i).name(), lijn.getRichting(i),tijdNaarHalte);
			bericht.ETAs.add(eta);
			eerstVolgende=lijn.getHalte(i).getPositie();
		}
		bericht.eindpunt=lijn.getHalte(i-richting).name();
		sendBericht(bericht);
	}
	
	public static Bericht createLastETA(Bus bus, int nu) {
		Lijnen lijn = bus.getLijn();
		int halteNummer = bus.getHalteNummer();
		Bericht bericht = new Bericht(lijn.name() ,bus.getBedrijf().name(), bus.getBusID() ,nu);
		String eindpunt = lijn.getHalte(halteNummer).name();
		ETA eta = new ETA(eindpunt,lijn.getRichting(halteNummer),0);
		bericht.ETAs.add(eta);
		bericht.eindpunt = eindpunt;
		return bericht;
	}

	public static void sendBericht(Bericht bericht){
    	XStream xstream = new XStream();
    	xstream.alias("Bericht", Bericht.class);
    	xstream.alias("ETA", ETA.class);
    	String xml = xstream.toXML(bericht);
    	Producer producer = new Producer();
    	producer.sendBericht(xml);		
	}
}
