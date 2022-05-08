package com.dbc.analyzerDat;

import java.io.IOException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.annotation.Scheduled;

import com.dbc.analyzerDat.services.ProcessadorDadosServiceImpl;

@SpringBootApplication
@EnableScheduling
public class AnalyzerDatApplication {

	@Autowired 
	ProcessadorDadosServiceImpl service;
	
	public static void main(String[] args) {
		SpringApplication.run(AnalyzerDatApplication.class, args);
	}

	@Scheduled(fixedDelay = 10000)
	public void executar() throws IOException {
		System.out.println("INICIANDO JOB");
		
		service.executar();
	}
	
	
}
