package br.com.githubthg.cmin;

import br.com.githubthg.cmin.modelo.Tabuleiro;
import br.com.githubthg.cmin.visao.TabuleiroConsole;

public class Aplicacao {
	public static void main(String[] args) {
		
		Tabuleiro tabuleiro = new Tabuleiro(3, 3, 2);
		new TabuleiroConsole(tabuleiro);
	
		
	}

}
