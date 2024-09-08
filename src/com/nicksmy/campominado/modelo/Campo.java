package com.nicksmy.campominado.modelo;

import java.util.ArrayList;
import java.util.List;

public class Campo {

	private final Integer x; // coluna
	private final Integer y; // linha
	
	private Boolean aberto = false;
	private Boolean minado = false;
	private Boolean marcado = false;
	
	private List<Campo> vizinhos = new ArrayList<>();
	
	Campo(Integer x, Integer y) {
		this.x = x;
		this.y = y;
	}
	
	public Boolean adicionarVizinho(Campo vizinho) {
		Boolean yDif = y != vizinho.y;
		Boolean xDif = x != vizinho.x;
		Boolean diagonal = yDif && xDif;
		
		Integer deltaY = Math.abs(y - vizinho.y);
		Integer deltaX = Math.abs(x - vizinho.x);
		Integer deltaGeral = deltaX + deltaY;
		
		if(deltaGeral == 1 && !diagonal) {
			vizinhos.add(vizinho);
			return true;
		}
		
		if (deltaGeral == 2 && diagonal) {
			vizinhos.add(vizinho);
			return true;
		}
		
		return false;
	}
}
