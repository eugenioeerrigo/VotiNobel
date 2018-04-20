package it.polito.tdp.model;

import java.util.*;

import it.polito.tdp.dao.EsameDAO;

public class Model {

	private List<Esame> esami;
	private EsameDAO edao;
	private List<Esame> soluzione;
	private double bestAvg;
	
	public Model() {
		edao = new EsameDAO();
		esami = edao.getTuttiEsami();
	}
	
	public List<Esame> calcolaSottoinsiemeEsami(int numeroCrediti) {
		
		///inizializzazione
		soluzione = new ArrayList<Esame>();
		bestAvg = 0.0;
		
		List<Esame> parziale = new ArrayList<>();
		recursive(0, parziale, numeroCrediti);
		
		return soluzione;
	}

	private void recursive(int step, List<Esame> parziale, int numeroCrediti) {      //void: se cerco la migliore soluzione possibile - boolean:se cerco una soluzione in particolare
		
		//Debug
//		for(Esame e : parziale)
//			System.out.print(e.getCodins()+" ");
//		System.out.println(" ");
		
		//Condizione di terminazione
		if(totCrediti(parziale) > numeroCrediti) {
			return;
		}
		if(totCrediti(parziale) == numeroCrediti) {
			if(avg(parziale) > bestAvg) {
				soluzione = new ArrayList<>(parziale);
				bestAvg = avg(parziale);
			}
		}
		
		//Generazione di una soluzione parziale
		for(Esame esame : esami) {
			if(!parziale.contains(esame)) {
				parziale.add(esame);
				recursive(step+1, parziale, numeroCrediti);
				parziale.remove(esame);
			}
		}
	}

	private Double avg(List<Esame> parziale) {
		double avg = 0.0;
		
		for(Esame e : parziale)
			avg += e.getCrediti()*e.getVoto();
		
		return avg /= totCrediti(parziale);
	}

	private int totCrediti(List<Esame> parziale) {
		int sum = 0;
		
		for(Esame e : parziale)
			sum += e.getCrediti();
		
		return sum;
	}
}
