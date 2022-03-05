package br.com.githubthg.cmin.visao;

import java.util.Arrays;
import java.util.Iterator;
import java.util.Scanner;

import br.com.githubthg.cmin.excecao.ExplosaoExcepction;
import br.com.githubthg.cmin.excecao.SairException;
import br.com.githubthg.cmin.modelo.Tabuleiro;

public class TabuleiroConsole {
	
	private Tabuleiro tabuleiro;
	private Scanner entrada = new Scanner(System.in);
	
	public TabuleiroConsole(Tabuleiro tabuleiro) {
		this.tabuleiro = tabuleiro;
		
		executarJogo();
	}

	private void executarJogo() {
		try {
			boolean continuar = true;
			
			
			while(continuar) {
				cicloDojogo();
				
				
				System.out.println("Outra partida? (S/n)  ");
				String resposta = entrada.nextLine();
				
				if("n".equalsIgnoreCase(resposta)) {
					continuar = false;
				} else {
					tabuleiro.reiniciar();
				}
			}
			
		} catch (SairException e) {
			System.out.println("Jogo Finalizado!");
			
		}finally {
			entrada.close();
		}
		
	}

	private void cicloDojogo() {
		/** DOCS
		 *  O objetivo foi alcançado? Se não
		 *  Mostra o tabuleiro e pega os valores das coordenadas
		 *  pega os valores e transforma em inteiro
		 *  Depois pega a informação se o cara quer abrir o campo que pegou a coordenada ou marcar ou desmarcar
		 *  se 1 abre
		 *  se 2 alterna a marcaão pegando xy
		 *  se digitar sair o jogo finalzia.
		 */
		
		
		try {
			while(!tabuleiro.objetivoAlcancado()) {
				System.out.println(tabuleiro);
				
				String digitado = capturarValorDigitado("Digite (X,Y): ");
				Iterator<Integer> xy  =Arrays.stream(digitado.split(","))
						.map(e -> Integer.parseInt(e.trim())).iterator();
				
				 digitado = capturarValorDigitado("Digite: 1 -> Abrir ou 2 - (Des)marcar: ");
				 
				 if("1".equals(digitado)) {
					 tabuleiro.abrir(xy.next(), xy.next());
				 } else if("2".equals(digitado)) {
					 tabuleiro.alternarMarcacao(xy.next(), xy.next());
				 }
				
			
			}
			
			System.out.println(tabuleiro);
			System.out.println("Voce ganhou!");
		} catch (ExplosaoExcepction e) {
			System.out.println(tabuleiro);
			System.out.println("Voce perdeu!");
		}
		
	}
	
	private String capturarValorDigitado(String texto) {
		System.out.print(texto);
		String digitado = entrada.nextLine();
		
		if("sair".toLowerCase().equalsIgnoreCase(digitado)) {
			throw new SairException();
		}
		
		return digitado;
	}

}
