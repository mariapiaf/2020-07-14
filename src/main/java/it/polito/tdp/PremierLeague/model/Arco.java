package it.polito.tdp.PremierLeague.model;

import java.util.Comparator;

public class Arco implements Comparable<Arco>{

	Team t1;
	Team t2;
	int differenzaPunteggioClassifica;
	
	public Arco(Team t1, Team t2, int differenzaPunteggioClassifica) {
		super();
		this.t1 = t1;
		this.t2 = t2;
		this.differenzaPunteggioClassifica = differenzaPunteggioClassifica;
	}

	public Team getT1() {
		return t1;
	}

	public void setT1(Team t1) {
		this.t1 = t1;
	}

	public Team getT2() {
		return t2;
	}

	public void setT2(Team t2) {
		this.t2 = t2;
	}

	public int getDifferenzaPunteggioClassifica() {
		return differenzaPunteggioClassifica;
	}

	public void setDifferenzaPunteggioClassifica(int differenzaPunteggioClassifica) {
		this.differenzaPunteggioClassifica = differenzaPunteggioClassifica;
	}

	@Override
	public int compareTo(Arco o) {
		return this.differenzaPunteggioClassifica-o.getDifferenzaPunteggioClassifica();
	}


}
