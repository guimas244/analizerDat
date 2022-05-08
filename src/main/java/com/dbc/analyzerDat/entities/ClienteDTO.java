package com.dbc.analyzerDat.entities;

import lombok.Builder;
import lombok.Getter;

@Builder
@Getter
public class ClienteDTO {
	private String cnpj;
	private String name;
	private String businessArea;
}
