package org.gumidev.springweb.dao;

import java.util.List;

import org.gumidev.springweb.entities.User;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;

public interface IUserDao extends CrudRepository<User, Long> {
    Object findAll(Sort id);

    User findByEmail(String email);

    User findByEmailIgnoreCaseAndPassword(String email, String password);

    @Query("select u from User u where lower(u.name) like %?1%")
    List<User> findAllByName(String name);
}
