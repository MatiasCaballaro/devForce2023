package com.devforce.devForce.repository;

import com.devforce.devForce.model.entity.Solicitud;
import org.springframework.data.jpa.repository.JpaRepository;

public interface SolicitudRepository extends JpaRepository<Solicitud,Long> {
}
