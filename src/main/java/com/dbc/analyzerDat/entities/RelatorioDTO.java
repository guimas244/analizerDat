package com.dbc.analyzerDat.entities;

import java.util.List;

import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
public class RelatorioDTO {
	private List<ClienteDTO> clientes;
	private List<VendedorDTO> vendedores;
	private List<DadosDeVendasPorVendedorDTO> dadosDeVendas;
	

}
