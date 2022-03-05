package br.com.githubthg.cmin.visao;

import javax.swing.JFrame;

import br.com.githubthg.cmin.modelo.Tabuleiro;

@SuppressWarnings("serial")
public class MainTelaPrincipal extends JFrame { // � uma heran�a, logo a Tela tamb�m � um Jframe
	
	public MainTelaPrincipal() {
		Tabuleiro tabuleiro = new Tabuleiro(16,30,20);
		
		add(new PainelTabuleiro(tabuleiro));
		
		
		setTitle("Campo Minado");
		setSize(690, 438);
		setLocationRelativeTo(null);
		setDefaultCloseOperation(DISPOSE_ON_CLOSE);
		setVisible(true);
		
	}
	
	
	public static void main(String[] args) {
		new MainTelaPrincipal();
	}

}
