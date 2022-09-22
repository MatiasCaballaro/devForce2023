package com.devforce.devForce.service;

import com.devforce.devForce.model.dto.LicenciaDTO;
import com.devforce.devForce.model.entity.Licencia;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;


public interface AdminService {
    public List<LicenciaDTO> getLicenciasDTO();
    public List<Licencia> getLicencias();
}
