package com.dbc.analyzerDat.services;

import java.util.Collections;
import java.util.Comparator;

import com.dbc.analyzerDat.entities.DadosDeVendasPorVendedorDTO;
import com.dbc.analyzerDat.entities.RelatorioDTO;

public class AnaliseDadosService {

	public String montaDocumentoTxt(RelatorioDTO relatorio) {
		
		StringBuilder sb = new StringBuilder();
		sb.append("Quantidade de clientes no arquivo de entrada: " + relatorio.getClientes().size() + "\n");
		sb.append("Quantidade de vendedores no arquivo de entrada: " + relatorio.getVendedores().size() + "\n");
		Collections.sort(relatorio.getDadosDeVendas(), new Comparator<DadosDeVendasPorVendedorDTO>() {
		    @Override
		    public int compare(DadosDeVendasPorVendedorDTO c1, DadosDeVendasPorVendedorDTO c2) {
		        return Double.compare(c1.getTotalVendasVendedor(), c2.getTotalVendasVendedor());
		    }
		});
		sb.append("ID da venda mais cara: " + relatorio.getDadosDeVendas().get( relatorio.getDadosDeVendas().size() -1).getSaleID() + "\n");
		sb.append("Pior vendedor: " + relatorio.getDadosDeVendas().get(0).getSalesName() + "\n");
		return sb.toString();
	}
}
