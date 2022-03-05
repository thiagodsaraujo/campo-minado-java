package br.com.githubthg.cmin.modelo;


@FunctionalInterface
public interface CampoObservador {
	
	public void eventoOcorreu(Campo campo, CampoEvento evento);

}
