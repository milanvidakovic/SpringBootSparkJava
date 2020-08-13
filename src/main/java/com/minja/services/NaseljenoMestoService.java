package com.minja.services;

import java.util.Collection;

import com.minja.entities.NaseljenoMesto;
import com.minja.repositories.NaseljenoMestoRepository;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class NaseljenoMestoService {

	@Autowired
	NaseljenoMestoRepository naseljenoMestoRepository;

	public Collection<NaseljenoMesto> getMesta() {
		return naseljenoMestoRepository.findAll();
	}
	
}
