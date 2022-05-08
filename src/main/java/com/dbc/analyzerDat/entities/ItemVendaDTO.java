package com.dbc.analyzerDat.entities;

import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
public class ItemVendaDTO {

	private String id;
	private Integer quantity;
	private Double price;
}
