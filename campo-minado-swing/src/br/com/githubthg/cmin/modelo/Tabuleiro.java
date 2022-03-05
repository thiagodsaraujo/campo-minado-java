package br.com.githubthg.cmin.modelo;

import java.util.ArrayList;
import java.util.List;
import java.util.function.Consumer;
import java.util.function.Predicate;

public class Tabuleiro implements CampoObservador {
	
	private final int linhas;
	private final int colunas;
	private final int minas;
	
	private final List<Campo> campos = new ArrayList<>();
	private final List<Consumer<ResultadoEvento>> observadores = 
			new ArrayList<>();	

	public Tabuleiro(int linhas, int colunas, int minas) {
		this.linhas = linhas;
		this.colunas = colunas;
		this.minas = minas;
		
		gerarCampos();
		associarVizinhos();
		sortearMinas();
		
	}
	
	public void paraCadaCampo(Consumer<Campo> funcao) {
		campos.forEach(funcao);
	}
	
	public void registrarObservador(Consumer<ResultadoEvento> observador) {
		observadores.add(observador);
	}
	
	private void notificarObservadores(boolean resultado) {
		observadores.stream()
		.forEach(observador -> observador.accept(new ResultadoEvento(resultado)));
	}
	
	// esse método vai procurar na lista qual o campo
	public void abrir(int linha, int coluna) {
		campos.parallelStream()
		.filter(campo -> campo.getLinha() == linha && campo.getColuna() == coluna)
		.findFirst()
		.ifPresent(campo -> campo.abrir());
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
				Campo campo = new Campo(linha, coluna);
				campo.registrarObservador(this); // a classe atual(tabuleiro) implementa o observador de campo, pode passar a instancia atual como
				// pode passar a instancia atual como um observador para todos os campos que estão criando aqui.
				campos.add(campo);
				
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
	
	
	
	public int getLinhas() {
		return linhas;
	}

	public int getColunas() {
		return colunas;
	}

	@Override
	public void eventoOcorreu(Campo campo, CampoEvento evento) {
		if (evento == CampoEvento.EXPLODIR) {
			mostrarMinas();
			notificarObservadores(false);
		}else if (objetivoAlcancado()) {
			notificarObservadores(true);
		
	}
		
	}
	
	private void mostrarMinas() {
		campos.stream()
		.filter(campo -> campo.isMinado())
		.filter(campo -> !campo.isMarcado())
		.forEach(campo -> campo.setAberto(true));
			
	}
}

