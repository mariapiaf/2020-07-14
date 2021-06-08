package it.polito.tdp.PremierLeague.model;

public class Evento {

	private Match partita;
	private Team squadra;
	private int nReporter;
	
	public Evento(Match partita, Team squadra, int nReporter) {
		super();
		this.partita = partita;
		this.squadra = squadra;
		this.nReporter = nReporter;
	}
	
	public Match getPartita() {
		return partita;
	}

	public void setPartita(Match partita) {
		this.partita = partita;
	}


	public Team getSquadra() {
		return squadra;
	}

	public void setSquadra(Team squadra) {
		this.squadra = squadra;
	}

	public int getnReporter() {
		return nReporter;
	}

	public void setnReporter(int nReporter) {
		this.nReporter = nReporter;
	}
	
	
}
