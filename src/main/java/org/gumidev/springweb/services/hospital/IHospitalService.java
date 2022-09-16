package org.gumidev.springweb.services.hospital;

import org.gumidev.springweb.entities.Hospital;

import java.util.List;

public interface IHospitalService {

    public List<Hospital> findAll();

    public List<Hospital> findAllByName(String name);

    public Hospital findById(Long id);

    public Hospital save(Hospital hospital);

    public void delete(Long id);

    public Hospital findByDoctor(Long id);
}


