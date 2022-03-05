package br.com.githubthg.cmin.modelo;

import java.util.ArrayList;
import java.util.List;

import br.com.githubthg.cmin.excecao.ExplosaoExcepction;

public class Campo {
	
	
	private final int linha;
	private final int coluna;
	
	
	private boolean aberto = false;
	private boolean minado = false;
	private boolean marcado = false;
	
	
	// Criar uma lista de autorelacionamento, relacionamento de 1 para 1.
	// s� vai aceitar itens do tipo campo.
	private List<Campo> vizinhos = new ArrayList<Campo>();
	
	
	Campo(int linha, int coluna){
		this.linha = linha;
		this.coluna = coluna;
	}
	
	boolean adicionarVizinho(Campo vizinho) {
		boolean linhaDiferente = linha != vizinho.linha; 
		boolean colunaDiferente = coluna != vizinho.coluna;
		// se os dois acima forem diferentes vai ser uma diagnoal
		boolean diagonal = linhaDiferente && colunaDiferente;
		
		/* CALCULO/L�GICA - DIST�NCIA ENTRE OS CAMPOS
		 * IMAGINANDO O CAMPO MINADO COMO UMA MATRIZ(3x3) onde o primeiro campo � (2,2) e o ultimo � (4,4)
		 * SE A DIFEREN�A ENTRE OS CAMPOS FOR IGUAL A *1* == ELES S�O VIZINHOS DE LINHA OU DE COLUNA, VEJAMOS:
		 * EXEMPLO A DIFEREN�A ENTRE -=> (3,3) - (3,4) IGUAL A 3-3 = 0 / 3 - 4 = 1 / RESULTADO IGUAL A 1 + 0 = 1 em numeros absolutos(positivos)
		 * SE A DIFEREN�A ENTRE OS CAMPOS FOR IGUAL A *2* == ELES S�O VIZINHOS DE DIAGONAL, VEJAMOS:
		 * EXEMPLO A DIFEREN�A ENTRE -=> (3,3) - (4,2) IGUAL A 4-3 = 1 / 3- 2 = 1 / RESULTADO IGUAL A 1  + 1 = 2 ''''
		 */
		
		int deltaLinha = Math.abs(linha - vizinho.linha);
		int deltaColuna = Math.abs(coluna - vizinho.coluna);
		int deltaGeral = deltaColuna + deltaLinha;
		
		
		
		if(deltaGeral == 1 && !diagonal) {
			vizinhos.add(vizinho);
			return true;
		}else if (deltaGeral == 2 && diagonal) {
			vizinhos.add(vizinho);
			return true;
		}else {
			return false;
		}
		
		}
	
	// eu s� posso alternar a marca��o de algo que est� fechado
			
	void alternarMarcacao() 
	{
		if(!aberto) 
		{
			marcado = !marcado;	
		}
		
	}
	

	boolean abrir() {
		
		if(!aberto && !marcado) { // s� vai abrir se o campo n�o tiver marcado e nao tiver sido aberto
			aberto = true;
			
			if(minado) {
				throw new ExplosaoExcepction();
			}
			
			// vai continuar abrindo( vai chamar o abrir recursivamente)
			// vai abrir todos os vizinhos desde que a vizinhan�a seja segura at� chegar em um false
			if(vizinhanceSegura()) {
				vizinhos.forEach(vizinho -> vizinho.abrir());
			}
			return true;
		}else {
			return false; // signfica que voce nao  abriu o campo e se o campo for marcado vc nao vai arbri
		}
		
	}
	
	// nenhum vizinho ao redor estar minado, se der false algum dos vizinhos est� minado
	boolean vizinhanceSegura() {
		return vizinhos.stream().noneMatch(vizinho -> vizinho.minado);
		
	}
	
	public boolean isMarcado() {
		return marcado;
	}

	void minar() {
		minado = true;
		
	}
	
	public boolean isMinado() {
		return minado;
	}
	
	
	
	void setAberto(boolean aberto) {
		this.aberto = aberto;
	}

	public boolean isAberto() {
		return aberto;
	}
	
	
	
	public boolean isFechado() {
		return !isAberto();
	}

	public int getLinha() {
		return linha;
	}

	public int getColuna() {
		return coluna;
	}
	
	// vai dizer se naquele campo o objetivo foi alcan�ado
	// S�o dois objetivos:
	// 1� Abrir um campo que nao est� minado e sao campos seguros que foram abertos
	// 2� Marcar um campo que tem bomba e para finalizar o jogo � preciso completar todo o tabuleiro
	boolean objetivoAlcancado() {
		
		boolean desvendado = !minado && aberto;
		boolean protegido = minado && marcado;
		
		return desvendado || protegido;
	}
	
	long minasNaVizinhanca() {
		return vizinhos.stream().filter(vizinho -> vizinho.minado).count(); // vai saber a quantidade de minas na vizinhanca
	}
	
	
	// reiniciar o jogo, setando false nas 3 variaveis
	void reiniciar() {
		aberto = false;
		minado = false;
		marcado = false;
	}
	
	public String toString() {
		if(marcado) {
			return "x";
		}else if (aberto && minado) {
			return "*";
		}else if(aberto && minasNaVizinhanca() > 0 ) {
			return Long.toString(minasNaVizinhanca()); // vai mostrar quantas minas tem na vizinhan�a
		} else if(aberto) { // a quantidade de minas � zero nessa situa�~~ao
			return " ";
		} else {
			return "?";
		}
	}
	
}
