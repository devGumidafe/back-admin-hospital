package org.gumidev.springweb.dao;

import java.util.List;

import org.gumidev.springweb.entities.Hospital;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;

public interface IHospitalDao extends CrudRepository<Hospital, Long> {
    Object findAll(Sort id);

    @Query("select h from Hospital h where lower(h.name) like %?1%")
    List<Hospital> findAllByName(String name);

    @Query("select h from Hospital h join fetch h.doctorList d where d.id = ?1")
    Hospital findByDoctor(Long id);
}
