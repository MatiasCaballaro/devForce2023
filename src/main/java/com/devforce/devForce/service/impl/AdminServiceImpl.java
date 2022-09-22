package com.devforce.devForce.service.impl;

import com.devforce.devForce.model.dto.LicenciaDTO;
import com.devforce.devForce.model.entity.Licencia;
import com.devforce.devForce.repository.LicenciaRepository;
import com.devforce.devForce.service.AdminService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class AdminServiceImpl implements AdminService {

    @Autowired
    LicenciaRepository licenciaRepository;

    public List<Licencia> getLicencias(){
        return licenciaRepository.findAll();
    }

    public List<LicenciaDTO> getLicenciasDTO(){
        return licenciaRepository.findAll().stream().map(licencia -> createLicenciaDTO(licencia)).collect(Collectors.toList());
    }


    private LicenciaDTO createLicenciaDTO(Licencia licencia){
        LicenciaDTO licenciaDTO = new LicenciaDTO(licencia.getId(), licencia.getSerie(), licencia.getEstado(),
                licencia.getVencimiento(), licencia.getPlataforma(), licencia.getSolicitudes());
        return licenciaDTO;
    }


}
