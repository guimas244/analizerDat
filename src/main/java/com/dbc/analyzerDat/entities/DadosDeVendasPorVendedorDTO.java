package com.dbc.analyzerDat.entities;

import java.util.List;

import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
public class DadosDeVendasPorVendedorDTO {
	private String saleID;
	private List<ItemVendaDTO> itemsVenda;
	private String salesName;
	private Double totalVendasVendedor;

}
