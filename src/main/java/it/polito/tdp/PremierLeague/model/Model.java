package it.polito.tdp.PremierLeague.model;

import java.util.HashMap;
import java.util.Map;

import org.jgrapht.Graph;
import org.jgrapht.Graphs;
import org.jgrapht.graph.DefaultWeightedEdge;
import org.jgrapht.graph.SimpleDirectedWeightedGraph;

import it.polito.tdp.PremierLeague.db.PremierLeagueDAO;

public class Model {
	
	private Graph<Team, DefaultWeightedEdge> grafo;
	private PremierLeagueDAO dao;
	private Map<Integer, Team> idMap;
	
	
	public Model() {
		dao = new PremierLeagueDAO();
	}
	
	
	public void creaGrafo() {
		grafo = new SimpleDirectedWeightedGraph<>(DefaultWeightedEdge.class);
		
		idMap = new HashMap<>();
		dao.listAllTeams(idMap);
		// aggiungo vertici
		Graphs.addAllVertices(this.grafo, this.idMap.values());
		this.creaClassifica();
		// aggiungo archi
		for(Team t1: idMap.values()) {
			for(Team t2: idMap.values()) {
				if(t1.getPunteggioClassifica()>t2.getPunteggioClassifica()) {
					Graphs.addEdge(this.grafo, t1, t2, (t1.getPunteggioClassifica()-t2.getPunteggioClassifica()));
				}
				else if(t2.getPunteggioClassifica()>t1.getPunteggioClassifica()) {
					Graphs.addEdge(this.grafo, t2, t1, (t2.getPunteggioClassifica()-t1.getPunteggioClassifica()));
				}
			}
		}
	}
	
	public void creaClassifica() {
		for(Match m: dao.listAllMatches()) {
			if(m.getResultOfTeamHome()==1) { // vittoria squadra di casa
				idMap.get(m.getTeamHomeID()).setPunteggioClassifica(3);
			}
			else if(m.getResultOfTeamHome()==0) { // vittoria squadra di casa
				idMap.get(m.getTeamHomeID()).setPunteggioClassifica(1);
				idMap.get(m.getTeamAwayID()).setPunteggioClassifica(1);
			}
			else if(m.getResultOfTeamHome()==(-1)) { // vittoria squadra di casa
				idMap.get(m.getTeamAwayID()).setPunteggioClassifica(3);
			}
		}
	}
	
	public int getNVertici() {
		return this.grafo.vertexSet().size();
	}
	
	public int getNArchi() {
		return this.grafo.edgeSet().size();
	}
}
