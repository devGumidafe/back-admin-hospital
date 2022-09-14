package org.gumidev.springweb.services.doctor;

import org.gumidev.springweb.dao.IDoctorDao;
import org.gumidev.springweb.entities.Doctor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
public class DoctorServiceImplement implements IDoctorService {

    @Autowired
    private IDoctorDao doctorDao;

    @Override
    @Transactional(readOnly = true)
    public List<Doctor> findAll() {
        return (List<Doctor>) doctorDao.findAll(Sort.by(Sort.Direction.DESC, "id"));
    }

    @Override
    @Transactional(readOnly = true)
    public Doctor findById(Long id) {
        return doctorDao.findById(id).orElse(null);
    }

    @Override
    @Transactional
    public Doctor save(Doctor doctor) {
        return doctorDao.save(doctor);
    }

    @Override
    @Transactional
    public void delete(Long id) {
        doctorDao.deleteById(id);
    }

    @Override
    public List<Doctor> findAllByName(String name) {
        return doctorDao.findAllByName(name);
    }
}
