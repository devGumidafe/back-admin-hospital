package org.gumidev.springweb.dao;

import java.util.List;

import org.gumidev.springweb.entities.Doctor;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;

public interface IDoctorDao extends CrudRepository<Doctor, Long> {
    Object findAll(Sort id);

    @Query("select d from Doctor d where lower(d.name) like %?1%")
    List<Doctor> findAllByName(String name);
}
