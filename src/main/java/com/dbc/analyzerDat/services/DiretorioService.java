package com.dbc.analyzerDat.services;

import java.io.File;
import java.nio.file.Files;
import java.nio.file.Path;

public class DiretorioService {
	public void verificaSeCaminhoExiste(Path path) {
		if (Files.notExists(path)){
			File theDir = new File(path.toString());
			theDir.mkdirs();
		}
	}
}
