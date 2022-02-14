package com.alex.helpdesk.repositories;

import org.springframework.data.jpa.repository.JpaRepository;

import com.alex.helpdesk.domain.Tecnico;

public interface TecnicoRepository extends JpaRepository<Tecnico, Integer> {

}
