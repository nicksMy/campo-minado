package com.nicksmy.campominado.modelo;

import java.util.ArrayList;
import java.util.List;

import com.nicksmy.campominado.execao.ExplosaoException;

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

		if (deltaGeral == 1 && !diagonal) {
			vizinhos.add(vizinho);
			return true;
		}

		if (deltaGeral == 2 && diagonal) {
			vizinhos.add(vizinho);
			return true;
		}

		return false;
	}

	public void alterarMarcacao() {
		if (!aberto) {
			marcado = !marcado;
		}
	}

	public Boolean abrir() {
		if (!aberto && !marcado) {
			aberto = true;

			if (minado) {
				throw new ExplosaoException();
			}

			if (isVizinhosSeguro()) {
				vizinhos.forEach(v -> v.abrir());
			}

			return true;
		}

		return false;
	}

	public void minar() {
		minado = true;
	}

	public Boolean isVizinhosSeguro() {
		return vizinhos.stream().noneMatch(v -> v.minado);
	}

	void setAberto(Boolean aberto) {
		this.aberto = aberto;
	}
	
	public Boolean isAberto() {
		return this.aberto;
	}

	public Boolean isMinado() {
		return this.minado;
	}

	public Boolean isMarcado() {
		return this.marcado;
	}

	public Boolean isFechado() {
		return !this.isAberto();
	}

	public Integer getX() {
		return x;
	}

	public Integer getY() {
		return y;
	}

	public Boolean objetivoAlcancado() {
		return (!minado && aberto) || (minado && marcado);
	}

	public Long minasNosVizinhos() {
		return vizinhos.stream().filter(v -> v.minado).count();
	}

	public void reiniciar() {
		aberto = false;
		minado = false;
		marcado = false;
	}

	public String toString() {
		if (marcado) {
			return "X";
		} else if (aberto && minado) {
			return "*";
		} else if (aberto && minasNosVizinhos() > 0) {
			return Long.toString(minasNosVizinhos());
		} else if (aberto) {
			return " ";
		} else {
			return "?";
		}
	}
}
