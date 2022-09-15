package org.gumidev.springweb.services.doctor;

import org.gumidev.springweb.entities.Doctor;

import java.util.List;

public interface IDoctorService {

    public List<Doctor> findAll();

    public List<Doctor> findAllByName(String name);

    public Doctor findById(Long id);

    public Doctor save(Doctor doctor);

    public void delete(Long id);
}
