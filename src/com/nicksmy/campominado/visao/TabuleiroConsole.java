package com.nicksmy.campominado.visao;

import java.util.Arrays;
import java.util.Iterator;
import java.util.Scanner;

import com.nicksmy.campominado.execao.ExplosaoException;
import com.nicksmy.campominado.execao.SairException;
import com.nicksmy.campominado.modelo.Tabuleiro;

public class TabuleiroConsole {

	private Tabuleiro tabuleiro;
	private Scanner entrada = new Scanner(System.in);

	public TabuleiroConsole(Tabuleiro tabuleiro) {
		this.tabuleiro = tabuleiro;
		executarJogo();
	}

	private void executarJogo() {
		try {
			Boolean continuar = true;

			while (continuar) {
				cicloDoJogo();

				System.out.println("Outra partida? (S/n) ");

				if ("n".equalsIgnoreCase(entrada.nextLine())) {
					continuar = false;
				} else {
					tabuleiro.reiniciar();
				}
			}
		} catch (SairException e) {
			System.out.println("Até logo...");
		} finally {
			entrada.close();
		}
	}

	private void cicloDoJogo() {
		try {
			while (!tabuleiro.objetivoAlcancado()) {
				System.out.println("\n" + tabuleiro);
				String digitado = capturarValorDigitado("Digite (x, y): ");

				Iterator<Integer> xy = Arrays.stream(digitado.split(",")).map(e -> Integer.parseInt(e.trim()))
						.iterator();

				digitado = capturarValorDigitado("1 - Abrir, 2 - (Des)Marcar: ");
				if ("1".equalsIgnoreCase(digitado)) {
					tabuleiro.abrir(xy.next(), xy.next());
				} else if ("2".equalsIgnoreCase(digitado)) {
					tabuleiro.marcar(xy.next(), xy.next());
				}
			}
			System.out.println("\n" + tabuleiro);
			System.out.println("Você ganhou!");
		} catch (ExplosaoException e) {
			System.out.println("\n" + tabuleiro);
			System.out.println("Você perdeu!");
		}
	}

	private String capturarValorDigitado(String texto) {
		System.out.print(texto);
		String digitado = entrada.nextLine();

		if ("sair".equalsIgnoreCase(digitado)) {
			throw new SairException();
		}
		return digitado;
	}
}
