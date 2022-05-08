package com.dbc.analyzerDat.services;

import java.io.FileWriter;
import java.io.IOException;
import java.nio.file.Paths;

import org.springframework.beans.factory.annotation.Autowired;

import com.dbc.analyzerDat.entities.RelatorioDTO;
import com.dbc.analyzerDat.utils.ConstantesAnalizer;

public class RelatorioService {

	@Autowired
	AnaliseDadosServiceImpl service;
	
	@Autowired
	DiretorioServiceImpl dirService;
	
	public void imprimirRelatorio(RelatorioDTO relatorio, String fileName) throws IOException {

		String textoRelatorio = service.montaDocumentoTxt(relatorio);
		dirService.verificaSeCaminhoExiste(Paths.get(ConstantesAnalizer.DESTINO_OUT));

	    try ( FileWriter myWriter = new FileWriter(ConstantesAnalizer.DESTINO_OUT + fileName +ConstantesAnalizer.DONE_DAT) ){
	    	
			
	        myWriter.write(textoRelatorio);
	        myWriter.close();
	        System.out.println("Arquivo impresso com sucesso");
	      } catch (IOException e) {
	        System.out.println("Ocorreu um erro");
	        e.printStackTrace();
	      }
	}

}
