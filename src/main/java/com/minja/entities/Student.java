package com.minja.entities;


import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

@Entity
public class Student {
	
	@Id
	private long id;	
	
	@Size(min=1, max=16, message="{ime.length}")
	@NotNull(message = "{ime.notnull}")
	private String ime;
	
	@Size(min=1, max=16, message="{prezime.length}")
	@NotNull(message = "{prezime.notnull}")
	private String prezime;
	
	@ManyToOne
	@JoinColumn(name="mesto_rodjenja_id")
	private NaseljenoMesto mestoRodjenja;
	
	public Student() {}
	
	
	public Student(long id, String ime, String prezime, NaseljenoMesto mestoRodjenja) {
		this();
		this.id = id;
		this.ime = ime;
		this.prezime = prezime;
		this.mestoRodjenja = mestoRodjenja;
	}


	public long getId() {
		return id;
	}
	public void setId(long id) {
		this.id = id;
	}
	public String getIme() {
		return ime;
	}
	public void setIme(String ime) {
		this.ime = ime;
	}
	public String getPrezime() {
		return prezime;
	}
	public void setPrezime(String prezime) {
		this.prezime = prezime;
	}

	public NaseljenoMesto getMestoRodjenja() {
		return mestoRodjenja;
	}

	public void setMestoRodjenja(NaseljenoMesto mestoRodjenja) {
		this.mestoRodjenja = mestoRodjenja;
	}

	@Override
	public String toString() {
		return "Student [id=" + id + ", ime=" + ime + ", mestoRodjenja=" + mestoRodjenja + ", prezime=" + prezime + "]";
	}
	
}
