package hanze.nl.infobord;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;

import javax.swing.BoxLayout;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;
import java.awt.Insets;

import com.fasterxml.jackson.databind.ObjectMapper;
import hanze.nl.tijdtools.InfobordTijdFuncties;

public class InfoBord {

	private static HashMap<String,Integer> laatsteBericht = new HashMap<String,Integer>();
	private static HashMap<String,JSONBericht> infoBordRegels = new HashMap<String,JSONBericht>();
	private static InfoBord infobord;
	private static int hashValue;
	private JFrame scherm;
	private JLabel tijdregel1;
	private JLabel tijdregel2;
	private JLabel regel1;
	private JLabel regel2;
	private JLabel regel3;
	private JLabel regel4;
	
	private InfoBord(){
		this.scherm = new JFrame("InfoBord");
		initRegels();
		JPanel panel = initPanel();
		hashValue=0;
		scherm.add(panel);
		scherm.pack();
		scherm.setVisible(true);
	}

	private JPanel initPanel(){
		JPanel panel = new JPanel();
		BoxLayout boxlayout = new BoxLayout(panel, BoxLayout.Y_AXIS);
		panel.setLayout(boxlayout);
		panel.setBorder(new EmptyBorder(new Insets(10, 20, 10, 20)));
		panel.add(tijdregel1);
		panel.add(tijdregel2);
		panel.add(regel1);
		panel.add(regel2);
		panel.add(regel3);
		panel.add(regel4);
		return panel;
	}

	private void initRegels(){
		this.tijdregel1=new JLabel("Scherm voor de laatste keer bijgewerkt op:");
		this.tijdregel2=new JLabel("00:00:00");
		this.regel1=new JLabel("-regel1-");
		this.regel2=new JLabel("-regel2-");
		this.regel3=new JLabel("-regel3-");
		this.regel4=new JLabel("-regel4-");
	}
	
	public static InfoBord getInfoBord(){
		if(infobord==null){
			infobord=new InfoBord();
		}
		return infobord;
	}

	static class Pair implements Comparable<Pair>{
		public final String infoTekst;
		public final int aankomstTijd;

		Pair(String infoTekst, int aankomstTijd){
			this.infoTekst = infoTekst;
			this.aankomstTijd = aankomstTijd;
		}

		@Override
		public boolean equals(Object obj) {
			if(obj.getClass() == this.getClass()){
				Pair pair = (Pair) obj;
				return pair.aankomstTijd == this.aankomstTijd && pair.infoTekst.equals(this.infoTekst);
			}
			return false;
		}

		public int compareTo(Pair o) {
			if(this.aankomstTijd < o.aankomstTijd){
				return -1;
			}else if(this.aankomstTijd > o.aankomstTijd){
				return 1;
			}
			return 0;
		}
	}

	public void setRegels(){
		ArrayList<Pair> regels = new ArrayList<Pair>();

		if(!infoBordRegels.isEmpty()) {
			for (String busID : infoBordRegels.keySet()) {
				JSONBericht regel = infoBordRegels.get(busID);

				regels.add(new Pair(regel.getInfoRegel(), regel.getAankomsttijd()));
			}
		}
		Collections.sort(regels);

		if(checkRepaint(getAankomsttijden(regels))) {
			repaintInfoBord(getInfoTeksts(regels));
		}
	}

	private int[] getAankomsttijden(ArrayList<Pair> regels){
		int[] aankomsttijden = new int[regels.size()];

		for(int i = 0; i < regels.size(); i++){
			aankomsttijden[i] = regels.get(i).aankomstTijd;
		}

		return aankomsttijden;
	}

	private String[] getInfoTeksts(ArrayList<Pair> regels) {
		String[] infotekst = new String[regels.size()];

		for (int i = 0; i < regels.size(); i++) {
			infotekst[i] = regels.get(i).infoTekst;
		}

		return infotekst;
	}
	
	private boolean checkRepaint(int[] aankomsttijden){
		int totaalTijden=0;
		for (int j : aankomsttijden) {
			totaalTijden += j;
		}
		if(hashValue!=totaalTijden){
			hashValue=totaalTijden;
			return true;
		}
		return false;
	}

	private void repaintInfoBord(String[] infoTekst){
		InfobordTijdFuncties tijdfuncties = new InfobordTijdFuncties();
		String tijd = tijdfuncties.getCentralTime().toString();
		tijdregel2.setText(tijd);
		regel1.setText(infoTekst[0]);
		regel2.setText(infoTekst[1]);
		regel3.setText(infoTekst[2]);
		regel4.setText(infoTekst[3]);
		scherm.repaint();		
	}
	
	public static void verwerktBericht(String incoming){
        try {
			JSONBericht bericht = new ObjectMapper().readValue(incoming, JSONBericht.class);
			String busID = bericht.getBusID();
			Integer tijd = bericht.getTijd();
			if (!laatsteBericht.containsKey(busID) || laatsteBericht.get(busID)<=tijd){
				laatsteBericht.put(busID, tijd);
				handleBusID(bericht, busID);
			}
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	private static void handleBusID(JSONBericht bericht, String busID){
		if (bericht.getAankomsttijd()==0){
			infoBordRegels.remove(busID);
		} else {
			infoBordRegels.put(busID, bericht);
		}
	}
}
