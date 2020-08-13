package com.minja.controllers;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.minja.services.NaseljenoMestoService;
import com.minja.spark.ISpark;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;

import spark.Spark;

@Component  
@Order(value=1)
public class NaseljenoMestoController implements ISpark {
	
	@Autowired
	NaseljenoMestoService naseljenoMestoService;

	@Autowired
	ObjectMapper om;

	@Override
    public void register() {
		Spark.get("/rest/mesto/getall", (req, res) -> {return om.writeValueAsString(naseljenoMestoService.getMesta());});
	}

}
