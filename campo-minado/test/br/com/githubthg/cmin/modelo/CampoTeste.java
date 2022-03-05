package br.com.githubthg.cmin.modelo;

import static org.junit.jupiter.api.Assertions.*; // no asterisco vai importar o assert true e false

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import br.com.githubthg.cmin.excecao.ExplosaoExcepction;

public class CampoTeste {
	
	// Vamos testar se o vizinho foi ou não incluido, devendo retornar true.
	
	private Campo campo;
	
	@BeforeEach
	void iniciarCampo() {
		campo  = new Campo(3,3);
	}
	
	@Test
	void testeVizinhoRealDistancia1Esquerda() {
		Campo vizinho = new Campo(3, 2); // vai ser um vizinho real na distancia 1 a esquerda
		boolean resultado = campo.adicionarVizinho(vizinho);
		assertTrue(resultado); // se for true o teste vai ser bem sucedido.
		
	}
	
		
	@Test
	void testeVizinhoDistancia1Direita() {
		
		Campo vizinho = new Campo(3, 4); // vai ser um vizinho real na distancia 1 a Direita
		boolean resultado = campo.adicionarVizinho(vizinho);
		assertTrue(resultado); // se for true o teste vai ser bem sucedido.
		
	}
	
	@Test
	void testeVizinhoDistancia1EmCima() {
		Campo vizinho = new Campo(2, 3); 
		boolean resultado = campo.adicionarVizinho(vizinho);
		assertTrue(resultado); // se for true o teste vai ser bem sucedido.
		
	}
	
	@Test
	void testeVizinhoDistancia1EmBaixo() {
		Campo vizinho = new Campo(4, 3); 
		boolean resultado = campo.adicionarVizinho(vizinho);
		assertTrue(resultado); // se for true o teste vai ser bem sucedido.
		
	}
	
	@Test
	void testeVizinhoDistancia2Diagonal() {
		Campo vizinho = new Campo(2, 2); // vai ser um vizinho real na distancia 2 na Diagonal
		boolean resultado = campo.adicionarVizinho(vizinho);
		assertTrue(resultado); // se for true o teste vai ser bem sucedido.
		
	}
	
	@Test
	void testeNaoVizinho() {
		Campo vizinho = new Campo(1, 1); // vai testar se não é vizinho
		boolean resultado = campo.adicionarVizinho(vizinho);
		assertFalse(resultado); // se for FALSE teste vai ser bem sucedido.
		
	}
	
	@Test
	void testeValorPadraoAtributoMarcado() {
		assertFalse(campo.isMarcado());
		
	}
	
	@Test
	void testealternarMarcacao() {
		campo.alternarMarcacao();
		assertTrue(campo.isMarcado());
		
		
	}
	@Test
	void testealternarMarcacaoDuasChamadas() {
		campo.alternarMarcacao();
		campo.alternarMarcacao();
		assertFalse(campo.isMarcado());
		
	}
	
	@Test
	void testeAbrirNaoMinadoNaoMarcado() {
		assertTrue(campo.abrir());
		
	}
	
	@Test
	void testeAbrirNaoMinadoMarcado() {
		campo.alternarMarcacao();
		assertFalse(campo.abrir());
		
	}
	
	@Test
	void testeAbrirMinadoMarcado() {
		campo.alternarMarcacao();
		campo.minar();
		assertFalse(campo.abrir());
		
	}
	
	
	@Test // aqui nesse teste o resultado é uma explosao!
	void testeAbrirMinadoNaoMarcado() {
		campo.minar();
		
		// vai testar o tipo de excecao
		assertThrows(ExplosaoExcepction.class, () -> {
			campo.abrir(); // se gerou a excecao, e se a excecao gerada foi a esperada(explosao)
		
		}); 
		
	}
	
	@Test
	void testeAbrirComVizinhos1() {
		
		Campo campo11 = new Campo(1,1);
		
		Campo campo22 = new Campo(2,2);
		campo22.adicionarVizinho(campo11);

		campo.adicionarVizinho(campo22);

		campo.abrir();
		
		assertTrue(campo22.isAberto() && campo11.isAberto());
		
	}
	
	
	@Test
	void testeAbrirComVizinhos2() {
		
		Campo campo11 = new Campo(1,1);	
		Campo campo12 = new Campo(1,1);
		campo12.minar();
		
		Campo campo22 = new Campo(2,2);
		
		campo22.adicionarVizinho(campo11);
		campo22.adicionarVizinho(campo12);
		
		campo.adicionarVizinho(campo22);
		
		campo.abrir();
		
		assertTrue(campo22.isAberto() && campo11.isFechado());
		
	}
	
	
	

}
