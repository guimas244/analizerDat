package com.dbc.analyzerDat.services;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.stream.Stream;

import org.springframework.beans.factory.annotation.Autowired;

import com.dbc.analyzerDat.entities.ArquivoDTO;
import com.dbc.analyzerDat.utils.ConstantesAnalizer;

public class LeitorDatService {
	
	@Autowired
	DiretorioServiceImpl dirService;
	
	public List<ArquivoDTO> retornaArquivosDiretorio() throws IOException {
		List<ArquivoDTO> arquivos = new ArrayList();
		buscaArquivosDat(arquivos);
		return arquivos;
	}

	private void buscaArquivosDat(List<ArquivoDTO> arquivos) throws IOException {
		// TRAS TUDO DA PASTA
		dirService.verificaSeCaminhoExiste(Paths.get(ConstantesAnalizer.DESTINO_IN));
			try (Stream<Path> paths = Files.walk(Paths.get(ConstantesAnalizer.DESTINO_IN))) {
				paths.filter(Files::isRegularFile).forEach(file -> {
					verificaSeTemExtensao(arquivos, file);
				});
			} 
		
	}

	private static void validaSeCaminhoExiste(Path path) {
		if (Files.notExists(Paths.get(ConstantesAnalizer.DESTINO_IN))){
			File theDir = new File(ConstantesAnalizer.DESTINO_IN);
			theDir.mkdirs();
		}
	}

	private static void verificaSeTemExtensao(List<ArquivoDTO> arquivos, Path file) {
		if (Objects.nonNull(file) && file.getFileName().toString().contains(".")) 
			retornaArquivosData(file, arquivos);
	}

	private static void retornaArquivosData(Path file, List<ArquivoDTO> arquivos) {
		String fileName = file.getFileName().toString();
		
		if (fileName.contains(ConstantesAnalizer.EXT_DAT_SEM_PONTO)) {
			// AQUI VIMOS QUE É SIM O UM DAT
			try (Stream<String> stream = Files.lines(Paths.get(ConstantesAnalizer.DESTINO_IN + fileName))) {

				StringBuilder sb = new StringBuilder();
				stream.forEach(linha -> sb.append(linha).append(ConstantesAnalizer.SEPARADOR_INTERNO) );
				arquivos.add(ArquivoDTO.builder().nome(extraiNomeArquivoSemExtensoes( fileName )).conteudo(sb.toString()).build());
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
	}

	private static String extraiNomeArquivoSemExtensoes(String fileNameComExtensao) {
		String[] split = fileNameComExtensao.split("\\.");
		return split[0];
	}

}
