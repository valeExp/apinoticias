package com.midasconsultores.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.midasconsultores.models.LogNoticias;

@Repository
public interface LogNoticiasRepository extends JpaRepository<LogNoticias, Long> {

}
