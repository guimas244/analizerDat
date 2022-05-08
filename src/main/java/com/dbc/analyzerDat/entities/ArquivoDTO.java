package com.dbc.analyzerDat.entities;

import lombok.Builder;
import lombok.Getter;

@Builder
@Getter
public class ArquivoDTO {
	private String nome;
	private String conteudo;
}
