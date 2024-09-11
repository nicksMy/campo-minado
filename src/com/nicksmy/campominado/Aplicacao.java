package com.nicksmy.campominado;

import com.nicksmy.campominado.modelo.Tabuleiro;
import com.nicksmy.campominado.visao.TabuleiroConsole;

public class Aplicacao {

	public static void main(String[] args) {

		Tabuleiro tabuleiro = new Tabuleiro(9, 9, 9);
		new TabuleiroConsole(tabuleiro);
	}
}
