package com.dbc.analyzerDat.entities;

import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
public class VendedorDTO  {
	private String cpf;
	private String name;
	private Double salary;

}
