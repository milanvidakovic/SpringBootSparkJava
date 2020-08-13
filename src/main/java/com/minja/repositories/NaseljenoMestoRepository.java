package com.minja.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.minja.entities.NaseljenoMesto;

@Repository
public interface NaseljenoMestoRepository extends JpaRepository<NaseljenoMesto, Long>{

}
