package com.alex.helpdesk.repositories;

import org.springframework.data.jpa.repository.JpaRepository;

import com.alex.helpdesk.domain.Cliente;

public interface ClienteRepository extends JpaRepository<Cliente, Integer> {

}
