package org.gumidev.springweb.services.hospital;

import org.gumidev.springweb.dao.IDoctorDao;
import org.gumidev.springweb.dao.IHospitalDao;
import org.gumidev.springweb.entities.Doctor;
import org.gumidev.springweb.entities.Hospital;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
public class HospitalServiceImplement implements IHospitalService {

    @Autowired
    private IHospitalDao hospitalDao;

    @Autowired
    private IDoctorDao doctorDao;

    @Override
    @Transactional(readOnly = true)
    public List<Hospital> findAll() {
        return (List<Hospital>) hospitalDao.findAll(Sort.by(Sort.Direction.DESC, "id"));
    }

    @Override
    @Transactional(readOnly = true)
    public Hospital findById(Long id) {
        return hospitalDao.findById(id).orElse(null);
    }

    @Override
    @Transactional
    public Hospital save(Hospital hospital) {
        return hospitalDao.save(hospital);
    }

    @Override
    @Transactional
    public void delete(Long id) {
        hospitalDao.deleteById(id);
    }

    @Override
    public List<Hospital> findAllByName(String name) {
        return hospitalDao.findAllByName(name);
    }

    @Override
    public Hospital findByDoctor(Long id) {
        return hospitalDao.findByDoctor(id);
    }
}
