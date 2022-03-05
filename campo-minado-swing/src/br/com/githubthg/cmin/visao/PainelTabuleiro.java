package br.com.githubthg.cmin.visao;

import java.awt.GridLayout;

import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.SwingUtilities;

import br.com.githubthg.cmin.modelo.Tabuleiro;

@SuppressWarnings("serial")
public class PainelTabuleiro extends JPanel{
	
	public PainelTabuleiro(Tabuleiro tabuleiro) {
		
		setLayout(new GridLayout(tabuleiro.getLinhas(),
				tabuleiro.getColunas()));
		
		tabuleiro.paraCadaCampo(campo -> add(new BotaoCampo(campo)));
		
		tabuleiro.registrarObservador(evento -> {
			SwingUtilities.invokeLater(() ->{
				
			if(evento.isGanhou()) {
				JOptionPane.showMessageDialog(this, "PARABÉNS!!! Você Ganhou!! :)");
			}else {
				JOptionPane.showMessageDialog(this, "Ihhh! Você Perdeu :(");
			}
			
			tabuleiro.reiniciar();
			
			});
		});
				
		}

	}
