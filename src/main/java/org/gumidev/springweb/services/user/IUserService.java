package org.gumidev.springweb.services.user;

import org.gumidev.springweb.entities.User;

import java.util.List;

public interface IUserService {

    public List<User> findAll();

    public List<User> findAllByName(String name);

    public User findById(Long id);

    public User save(User user);

    public void delete(Long id);

    User findByEmail(String email);

    User findByEmailIgnoreCaseAndPassword(String email, String password);

}



