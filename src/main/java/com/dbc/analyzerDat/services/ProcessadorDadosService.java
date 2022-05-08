package com.dbc.analyzerDat.services;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;

import com.dbc.analyzerDat.entities.ArquivoDTO;
import com.dbc.analyzerDat.entities.ClienteDTO;
import com.dbc.analyzerDat.entities.DadoComErroDatDTO;
import com.dbc.analyzerDat.entities.DadosDeVendasPorVendedorDTO;
import com.dbc.analyzerDat.entities.ItemVendaDTO;
import com.dbc.analyzerDat.entities.RelatorioDTO;
import com.dbc.analyzerDat.entities.VendedorDTO;
import com.dbc.analyzerDat.utils.ConstantesAnalizer;

public class ProcessadorDadosService {

	@Autowired
	LeitorDatServiceImpl leitorService;

	@Autowired
	RelatorioServiceImpl relatorioService;
	
	public void executar() throws IOException {

		List<ArquivoDTO> arquivos = leitorService.retornaArquivosDiretorio();
		arquivos.forEach(arquivo -> {

			ArrayList<VendedorDTO> vendedores = new ArrayList<>();
			ArrayList<ClienteDTO> clientes = new ArrayList<>();
			ArrayList<DadosDeVendasPorVendedorDTO> dadosVendas = new ArrayList<>();

			//TODO pre implantado tratamento de quando houve alguma linha com erro, nao realizei a implantacao para nao sair do scopo do projeto
			ArrayList<DadoComErroDatDTO> dadoComErro = new ArrayList<>();

			convertSplitParaArrayList(arquivo.getConteudo().toString().split(ConstantesAnalizer.SEPARADOR_INTERNO)).forEach(linha -> {
				ArrayList<String> linhaQuebrada = convertSplitParaArrayList(
						linha.split(ConstantesAnalizer.SEPARADOR_DO_ARQUIVO_DAT));

				if (linhaQuebrada.size() == 4) {
					switch (linhaQuebrada.get(0)) {
					case ConstantesAnalizer.ID_001:
						vendedores.add(VendedorDTO.builder().cpf(linhaQuebrada.get(1)).name(linhaQuebrada.get(2)).salary(Double.parseDouble(linhaQuebrada.get(3))).build());
						break;
					case ConstantesAnalizer.ID_002:
						clientes.add(ClienteDTO.builder().cnpj(linhaQuebrada.get(1)).name(linhaQuebrada.get(2)).businessArea(linhaQuebrada.get(3)).build());
						break;
					case ConstantesAnalizer.ID_003:
						buildItensVendaPorVendedor(dadosVendas, linhaQuebrada);
						break;
					default:
						dadoComErro.add(DadoComErroDatDTO.builder().dadoComErro(linha).build());
						break;
					}
				} else {
					dadoComErro.add(DadoComErroDatDTO.builder().dadoComErro(linha).build());

				}

			});
			
			finalizaRelatorio(vendedores, clientes, dadosVendas, arquivo.getNome());
		});

	}


	private void finalizaRelatorio(ArrayList<VendedorDTO> vendedores, ArrayList<ClienteDTO> clientes,
			ArrayList<DadosDeVendasPorVendedorDTO> dadosVendas, String fileName) {
		try {
			relatorioService.imprimirRelatorio(RelatorioDTO.builder().vendedores(vendedores).clientes(clientes).dadosDeVendas(dadosVendas).build() ,  fileName);
		} catch (IOException e) {
			e.printStackTrace();
		}
	}


	private void buildItensVendaPorVendedor(ArrayList<DadosDeVendasPorVendedorDTO> dadosVendas, ArrayList<String> linhaQuebrada) {
		String linhaQuebradaTratada = linhaQuebrada.get(2).replace("[","").replace("]","");
		ArrayList<String> itemsVendaQuebrado = convertSplitParaArrayList(linhaQuebradaTratada .split(ConstantesAnalizer.SEPARADOR_ITENS_VENDA));
		ArrayList<ItemVendaDTO> items = new ArrayList<>();
		Double totalVendasVendedor = 0d;
		
		for(String iv: itemsVendaQuebrado) {
			ArrayList<String> unidadeItemQuebrado = convertSplitParaArrayList(iv.split(ConstantesAnalizer.SEPARADOR_ITENS_UNIDADE));
			if(unidadeItemQuebrado.size() == 3) {
				ItemVendaDTO ivDTO = ItemVendaDTO.builder().id(unidadeItemQuebrado.get(0))
						.quantity(Integer.parseInt(unidadeItemQuebrado.get(1))).price(Double.parseDouble(unidadeItemQuebrado.get(2)) ) .build();
				items.add(ivDTO);
				totalVendasVendedor = totalVendasVendedor + (ivDTO.getPrice() * ivDTO.getQuantity());
			}
		}

		dadosVendas.add( DadosDeVendasPorVendedorDTO.builder().saleID(linhaQuebrada.get(1)).itemsVenda(items).salesName(linhaQuebrada.get(3)).totalVendasVendedor(totalVendasVendedor).build());
	}


	private static ArrayList<String> convertSplitParaArrayList(String[] split) {
		return new ArrayList<>(Arrays.asList(split));
	}

}
