package com.nicksmy.campominado.modelo;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import com.nicksmy.campominado.execao.ExplosaoException;

public class CampoTeste {

	private Campo campo;

	@BeforeEach
	void iniciarCampo() {
		campo = new Campo(3, 3);
	}

	@Test
	void testeVizinhoDistancia1Esquerda() {
		Campo vizinho = new Campo(3, 2);
		Boolean resultado = campo.adicionarVizinho(vizinho);
		assertTrue(resultado);
	}

	@Test
	void testeVizinhoDistancia1Direita() {
		Campo vizinho = new Campo(3, 4);
		Boolean resultado = campo.adicionarVizinho(vizinho);
		assertTrue(resultado);
	}

	@Test
	void testeVizinhoDistancia1Cima() {
		Campo vizinho = new Campo(3, 2);
		Boolean resultado = campo.adicionarVizinho(vizinho);
		assertTrue(resultado);
	}

	@Test
	void testeVizinhoDistancia1Baixo() {
		Campo vizinho = new Campo(3, 4);
		Boolean resultado = campo.adicionarVizinho(vizinho);
		assertTrue(resultado);
	}

	@Test
	void testeVizinhoDiagonal() {
		Campo vizinho = new Campo(2, 2);
		Boolean resultado = campo.adicionarVizinho(vizinho);
		assertTrue(resultado);
	}

	@Test
	void testeNaoVizinho() {
		Campo vizinho = new Campo(1, 1);
		Boolean resultado = campo.adicionarVizinho(vizinho);
		assertFalse(resultado);
	}

	@Test
	void testeValorPadraoMarcacao() {
		assertFalse(campo.isMarcado());
	}

	@Test
	void testeAlterarMarcacao() {
		campo.alterarMarcacao();
		assertTrue(campo.isMarcado());
	}

	@Test
	void testeAlterarMarcacaoDuasChamadas() {
		campo.alterarMarcacao();
		campo.alterarMarcacao();
		assertFalse(campo.isMarcado());
	}

	@Test
	void testeAbrirNaoMinadoNaoMarcado() {
		assertTrue(campo.abrir());
	}

	@Test
	void testeAbrirNaoMinadoMarcado() {
		campo.alterarMarcacao();
		assertFalse(campo.abrir());
	}

	@Test
	void testeAbrirMinadoMarcado() {
		campo.alterarMarcacao();
		campo.minar();
		assertFalse(campo.abrir());
	}

	@Test
	void testeAbrirMinadoNaoMarcado() {
		campo.minar();
		assertThrows(ExplosaoException.class, () -> {
			campo.abrir();
		});
	}

	@Test
	void testeAbrirComVizinhos01() {
		Campo campo11 = new Campo(1, 1);
		Campo campo22 = new Campo(2, 2);
		campo22.adicionarVizinho(campo11);

		campo.adicionarVizinho(campo22);
		campo.abrir();

		assertTrue(campo22.isAberto() && campo11.isAberto());
	}

	@Test
	void testeAbrirComVizinhos02() {
		Campo campo11 = new Campo(1, 1);
		Campo campo12 = new Campo(1, 1);
		campo12.minar();

		Campo campo22 = new Campo(2, 2);
		campo22.adicionarVizinho(campo11);
		campo22.adicionarVizinho(campo12);

		campo.adicionarVizinho(campo22);
		campo.abrir();

		assertTrue(campo22.isAberto() && campo11.isFechado());
	}
}
