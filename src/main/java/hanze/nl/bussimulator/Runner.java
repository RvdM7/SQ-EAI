package hanze.nl.bussimulator;
import java.util.*;

import hanze.nl.tijdtools.TijdFuncties;

public class Runner {

	private static HashMap<Integer,ArrayList<Bus>> busStart = new HashMap<Integer,ArrayList<Bus>>();
	private static ArrayList<Bus> actieveBussen = new ArrayList<Bus>();
	private static int interval=1000;
	private static int syncInterval=5;
	private static final ArrayList<Lijnen> flixBusLijnen = new ArrayList<Lijnen>(Arrays.asList(Lijnen.LIJN5));
	
	private static void addBus(int starttijd, Bus bus){
		ArrayList<Bus> bussen = new ArrayList<Bus>();
		if (busStart.containsKey(starttijd)) {
			bussen = busStart.get(starttijd);
		}
		bussen.add(bus);
		busStart.put(starttijd,bussen);
		bus.setbusID(starttijd);
	}
	
	private static int startBussen(int tijd){
		for (Bus bus : busStart.get(tijd)){
			actieveBussen.add(bus);
		}
		busStart.remove(tijd);
		return (!busStart.isEmpty()) ? Collections.min(busStart.keySet()) : -1;
	}
	
	public static void moveBussen(int nu){
		Iterator<Bus> itr = actieveBussen.iterator();
		while (itr.hasNext()) {
			Bus bus = itr.next();
			boolean eindpuntBereikt = bus.move();
			if (eindpuntBereikt) {
				bus.sendLastETA(nu);
				itr.remove();
			}
		}		
	}

	public static void sendETAs(int nu){
		Iterator<Bus> itr = actieveBussen.iterator();
		while (itr.hasNext()) {
			Bus bus = itr.next();
			bus.sendETAs(nu);
		}				
	}
	
	public static int initBussen(){
		int[] startTijdenLijst = {3, 5, 4, 6, 3, 5, 4, 6, 12, 10};

		for(int richting = -1; richting < 2; richting += 2){
			for(int lijnNr = 0; lijnNr < Lijnen.values().length; lijnNr++){

				Lijnen lijn = Lijnen.values()[lijnNr];
				Bus bus;

				if(flixBusLijnen.contains(lijn))
					bus = new Bus(lijn, Bedrijven.FLIXBUS, richting);
				else
					bus = new Bus(lijn, Bedrijven.ARRIVA, richting);

				addBus(startTijdenLijst[lijnNr], bus);
			}
		}

		return Collections.min(busStart.keySet());
	}
	
	public static void main(String[] args) throws InterruptedException {
		TijdFuncties tijdFuncties = new TijdFuncties();
		tijdFuncties.initSimulatorTijden(interval,syncInterval);
		int volgende = initBussen();

		while ((volgende>=0) || !actieveBussen.isEmpty()) {
			int counter=tijdFuncties.getCounter();
			int tijd=tijdFuncties.getTijdCounter();
			System.out.println("De tijd is:" + tijdFuncties.getSimulatorWeergaveTijd());
			volgende = (counter==volgende) ? startBussen(counter) : volgende;
			moveBussen(tijd);
			sendETAs(tijd);
			tijdFuncties.simulatorStep();
		}
	}
}
