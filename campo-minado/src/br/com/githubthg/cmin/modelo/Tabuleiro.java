package br.com.githubthg.cmin.modelo;

import java.util.ArrayList;
import java.util.List;
import java.util.function.Predicate;

import br.com.githubthg.cmin.excecao.ExplosaoExcepction;

public class Tabuleiro {
	
	private int linhas;
	private int colunas;
	private int minas;
	
	private final List<Campo> campos = new ArrayList<>();

	public Tabuleiro(int linhas, int colunas, int minas) {
		this.linhas = linhas;
		this.colunas = colunas;
		this.minas = minas;
		
		gerarCampos();
		associarVizinhos();
		sortearMinas();
		
	}
	
	// esse método vai procurar na lista qual o campo
	public void abrir(int linha, int coluna) {
		try {
			campos.parallelStream()
			.filter(campo -> campo.getLinha() == linha && campo.getColuna() == coluna)
			.findFirst()
			.ifPresent(campo -> campo.abrir());
			
		} catch(ExplosaoExcepction e) {
			campos.forEach(campo -> campo.setAberto(true)); // vai abrir todos os campos
			throw e;
		}
		
		
	}
	
	public void alternarMarcacao(int linha, int coluna) {
		campos.parallelStream()
			.filter(campo -> campo.getLinha() == linha && campo.getColuna() == coluna)
			.findFirst()
			.ifPresent(campo -> campo.alternarMarcacao());
		
		
	}
	

	private void gerarCampos() {
		for (int linha = 0; linha < linhas; linha++) {
			for (int coluna = 0; coluna < colunas; coluna++) {
				campos.add(new Campo(linha, coluna));
				
			}
			
		}
	}

	private void associarVizinhos() {
		for( Campo c1: campos) {
			for (Campo c2: campos){
				c1.adicionarVizinho(c2);
			}
		}

		
	}
	
	private void sortearMinas() {
		long minasArmadas = 0;
		Predicate<Campo> minado = campo -> campo.isMinado();
		
		// O do while vai continuar sendo executado enquanto a quantidade de minas armadas vai ser menor do que requisito
		do {
			// o cast tem prioridade do que a multiplicação, tem que colocar em parentese
			int aleatorio = (int) (Math.random() * campos.size()); // vai gerar um numero aleatório e multiplicar pelo tamanhhoda lista
			campos.get(aleatorio).minar();
			minasArmadas = campos.stream().filter(minado).count();
		} while (minasArmadas < minas);
		
		
	}
	
	public boolean objetivoAlcancado() {
		return campos.stream().allMatch(campo -> campo.objetivoAlcancado());
		
	}
	
	public void reiniciar() {
		campos.stream().forEach(campo -> campo.reiniciar());
		sortearMinas();
	}
	
	public String toString() {
		StringBuilder sb = new StringBuilder();
		
		sb.append("   ");
		for (int coluna = 0; coluna < colunas; coluna++) {
			sb.append(" ");
			sb.append(coluna);
			sb.append(" ");
			
		}
		sb.append("\n");
		
		int indice = 0;
		for (int linha = 0; linha < linhas; linha++) {
			sb.append(" ");
			sb.append(linha);
			sb.append(" ");
			
			for (int coluna = 0; coluna < colunas; coluna++) {
				sb.append(" ");
				sb.append(campos.get(indice));
				sb.append(" ");
				indice++;
				
			}
			sb.append("\n");
			
		}
		
		return sb.toString();
		
	}

}
