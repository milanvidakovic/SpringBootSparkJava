package com.minja.entities;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name="naseljenomesto")
public class NaseljenoMesto {
	
	@Id
	private long id;
	private String naziv;
	
	public NaseljenoMesto() {}

	public NaseljenoMesto(long id, String naziv) {
		this();
		this.id = id;
		this.naziv = naziv;
	}

	public long getId() {
		return id;
	}

	public void setId(long id) {
		this.id = id;
	}

	public String getNaziv() {
		return naziv;
	}

	public void setNaziv(String naziv) {
		this.naziv = naziv;
	}
	
	

}
