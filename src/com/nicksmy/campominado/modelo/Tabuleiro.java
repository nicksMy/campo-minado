package com.nicksmy.campominado.modelo;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.function.Predicate;

import com.nicksmy.campominado.execao.ExplosaoException;

public class Tabuleiro {

	private Integer qtdX; // colunas
	private Integer qtdY; // linhas
	private Integer minas;

	private final List<Campo> campos = new ArrayList<>();

	public Tabuleiro(Integer quantidadeX, Integer quantidadeY, Integer minas) {
		super();
		this.qtdX = quantidadeX;
		this.qtdY = quantidadeY;
		this.minas = minas;

		gerarCampos();
		associarVizinhos();
		sortearMinas();
	}

	public void abrir(Integer x, Integer y) {
		try {
			campos.parallelStream().filter(c -> c.getX() == x && c.getY() == y).findFirst().ifPresent(c -> c.abrir());
		} catch (ExplosaoException e) {
			campos.forEach(c -> c.setAberto(true));
			throw e;
		}
	}

	public void marcar(Integer x, Integer y) {
		campos.parallelStream().filter(c -> c.getX() == x && c.getY() == y).findFirst()
				.ifPresent(c -> c.alterarMarcacao());
	}

	private void gerarCampos() {
		for (int x = 0; x < qtdX; x++) {
			for (int y = 0; y < qtdX; y++) {
				campos.add(new Campo(x, y));
			}
		}
	}

	private void associarVizinhos() {
		for (Campo c1 : campos) {
			for (Campo c2 : campos) {
				c1.adicionarVizinho(c2);
			}
		}
	}

	private void sortearMinas() {
		Long minasArmadas = 0L;
		Predicate<Campo> minado = c -> c.isMinado();

		do {
			int aleatorio = (int) (Math.random() * campos.size());
			campos.get(aleatorio).minar();
			minasArmadas = campos.stream().filter(minado).count();
		} while (minasArmadas < minas);
	}

	public Boolean objetivoAlcancado() {
		return campos.stream().allMatch(c -> c.objetivoAlcancado());
	}

	public void reiniciar() {
		campos.stream().forEach(c -> c.reiniciar());
		sortearMinas();
	}

	public String toString() {
		StringBuilder sb = new StringBuilder();

		sb.append("  ");

		for (Integer c = 0; c < qtdX; c++) {
			sb.append(" ");
			sb.append(c);
			sb.append(" ");
		}
		
		sb.append("\n");
		
		int i = 0;
		for (var y = 0; y < qtdY; y++) {
			sb.append(y);
			sb.append(" ");
			for (var x = 0; x < qtdX; x++) {
				sb.append(" ");
				sb.append(campos.get(i));
				sb.append(" ");
				i++;
			}
			sb.append("\n");
		}

		return sb.toString();
	}
}
