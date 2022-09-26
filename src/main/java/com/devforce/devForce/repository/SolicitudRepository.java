package com.devforce.devForce.repository;

import com.devforce.devForce.model.entity.Solicitud;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface SolicitudRepository extends JpaRepository<Solicitud,Long> {
    //Solicitud findById(Long id);
    List<Solicitud> findByArea (String area);
}
