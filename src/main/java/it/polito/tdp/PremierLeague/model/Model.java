package it.polito.tdp.PremierLeague.model;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.jgrapht.Graph;
import org.jgrapht.Graphs;
import org.jgrapht.graph.DefaultWeightedEdge;
import org.jgrapht.graph.SimpleDirectedWeightedGraph;

import it.polito.tdp.PremierLeague.db.PremierLeagueDAO;

public class Model {
	
	private Graph<Team, DefaultWeightedEdge> grafo;
	private PremierLeagueDAO dao;
	private Map<Integer, Team> idMap;
	private Map<Team, Integer> classifica;
	private List<Arco> diff;
	
	
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
		diff = new LinkedList<>();
		
		// aggiungo archi
		for(Team t1: idMap.values()) {
			for(Team t2: idMap.values()) {
				if(t1.getPunteggioClassifica()>t2.getPunteggioClassifica()) {
					Graphs.addEdge(this.grafo, t1, t2, (t1.getPunteggioClassifica()-t2.getPunteggioClassifica()));
					diff.add(new Arco(t1, t2, (t1.getPunteggioClassifica()-t2.getPunteggioClassifica())));
				}
				else if(t2.getPunteggioClassifica()>t1.getPunteggioClassifica()) {
					Graphs.addEdge(this.grafo, t2, t1, (t2.getPunteggioClassifica()-t1.getPunteggioClassifica()));
					diff.add(new Arco(t2, t1, (t2.getPunteggioClassifica()-t1.getPunteggioClassifica())));
				}
			}
		}
	}
	
	public void creaClassifica() {
		classifica = new HashMap<>();
		for(Match m: dao.listAllMatches()) {
			if(m.getResultOfTeamHome()==1) { // vittoria squadra di casa
				idMap.get(m.getTeamHomeID()).setPunteggioClassifica(3);
				classifica.put(idMap.get(m.getTeamHomeID()), idMap.get(m.getTeamHomeID()).getPunteggioClassifica());
				classifica.put(idMap.get(m.getTeamAwayID()), idMap.get(m.getTeamAwayID()).getPunteggioClassifica());
				
			}
			else if(m.getResultOfTeamHome()==0) { // vittoria squadra di casa
				idMap.get(m.getTeamHomeID()).setPunteggioClassifica(1);
				classifica.put(idMap.get(m.getTeamHomeID()), idMap.get(m.getTeamHomeID()).getPunteggioClassifica());
				idMap.get(m.getTeamAwayID()).setPunteggioClassifica(1);
				classifica.put(idMap.get(m.getTeamAwayID()), idMap.get(m.getTeamAwayID()).getPunteggioClassifica());
			}
			else if(m.getResultOfTeamHome()==(-1)) { // vittoria squadra di casa
				idMap.get(m.getTeamAwayID()).setPunteggioClassifica(3);
				classifica.put(idMap.get(m.getTeamAwayID()), idMap.get(m.getTeamAwayID()).getPunteggioClassifica());
				classifica.put(idMap.get(m.getTeamHomeID()), idMap.get(m.getTeamHomeID()).getPunteggioClassifica());
			}
		}
	}
	
	public int getNVertici() {
		return this.grafo.vertexSet().size();
	}
	
	public int getNArchi() {
		return this.grafo.edgeSet().size();
	}
	
	public Set<Team> getVertici(){
		return this.grafo.vertexSet();
	}
	
	public List<Arco> getSquadreBattuteDa(Team squadra){
		//Map<Team, Integer> result = new LinkedHashMap<>();
		List<Arco> risultato = new ArrayList<>();
		for(DefaultWeightedEdge e: this.grafo.outgoingEdgesOf(squadra)) {
			risultato.add(new Arco(this.grafo.getEdgeSource(e), this.grafo.getEdgeTarget(e), (int) this.grafo.getEdgeWeight(e)));
			//result.put(Graphs.getOppositeVertex(this.grafo, e, squadra), (int) this.grafo.getEdgeWeight(e));
		}
		Collections.sort(risultato);
		return risultato;
	}
	
	public List<Arco> getSquadreCheHannoBattuto(Team squadra){
		
		//Map<Team, Integer> result = new LinkedHashMap<>();
		List<Arco> risultato = new ArrayList<>();
		
		for(DefaultWeightedEdge e: this.grafo.incomingEdgesOf(squadra)) {
			risultato.add(new Arco(this.grafo.getEdgeSource(e), this.grafo.getEdgeTarget(e), (int) this.grafo.getEdgeWeight(e)));
			//result.put(Graphs.getOppositeVertex(this.grafo, e, squadra), (int) this.grafo.getEdgeWeight(e));
		}
		
		Collections.sort(risultato);
		
		return risultato;
	}
	
	public List<Match> getAllMatches(){
		return this.dao.listAllMatches();
	}
	
	public List<Team> squadrePiuBlasonate(Team squadra){
		List<Team> result = new LinkedList<>();
		for(Arco a: this.getSquadreCheHannoBattuto(squadra)) {
    		result.add(a.getT1());
    	}
		return result;
	}
	
	public List<Team> squadreMenoBlasonate(Team squadra){
		List<Team> result = new LinkedList<>();
		for(Arco a: this.getSquadreBattuteDa(squadra)) {
    		result.add(a.getT2());
    	}
		return result;
	}
}
