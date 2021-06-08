package it.polito.tdp.PremierLeague.model;

import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.PriorityQueue;
import java.util.Random;

import org.jgrapht.Graph;
import org.jgrapht.graph.DefaultWeightedEdge;

import it.polito.tdp.PremierLeague.model.Evento;


public class Simulatore {

	private Model model;
	
	private PriorityQueue<Evento> queue;
	
	private Graph<Team, DefaultWeightedEdge> grafo;
	
	int nReporter;
	int soglia;
	
	Map<Team, Integer> reporterPerSquadra;
	
	public void init(int nReporter, int soglia, Graph<Team, DefaultWeightedEdge> grafo, Map<Integer, Team> idMap) {
		this.nReporter = nReporter;
		this.soglia = soglia;
		this.grafo = grafo;
		
		for(Team t: this.grafo.vertexSet()) {
			reporterPerSquadra.put(t, nReporter);
		}
		
		this.queue = new PriorityQueue<>();
		for(Match m: model.getAllMatches()) {
			this.queue.add(new Evento(m, idMap.get(m.getTeamHomeID()), nReporter));
			this.queue.add(new Evento(m, idMap.get(m.getTeamAwayID()), nReporter));
		}
		
		
	}
	
	
	public void run(Map<Integer, Team> idMap) {
		while(!this.queue.isEmpty()) {
			Evento ev = queue.poll();
			Team squadraVincente = null;
			if(ev.getPartita().getResultOfTeamHome()==1) {
				squadraVincente = idMap.get(ev.getPartita().getTeamHomeID());
			}
			else if(ev.getPartita().getResultOfTeamHome()==(-1)) {
				squadraVincente = idMap.get(ev.getPartita().getTeamAwayID());
			}
			List<Team> squadrePiuBlasonate = new LinkedList<>(model.squadrePiuBlasonate(squadraVincente));
//			if(squadrePiuBlasonate == null) {
//				// il reporter rimane alla squadra precedente
//			}
			
			Random random = new Random();
			Team squadraRandom = squadrePiuBlasonate.get(random.nextInt());
			
		}
	}
}
