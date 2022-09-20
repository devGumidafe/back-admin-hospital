package org.gumidev.springweb.controllers;

import org.gumidev.springweb.entities.Login;
import org.gumidev.springweb.entities.User;
import org.gumidev.springweb.model.ImageURL;
import org.gumidev.springweb.services.user.IUserService;
import org.gumidev.springweb.utils.JwtUtil;
import org.gumidev.springweb.utils.MenuFrontend;
import org.gumidev.springweb.utils.Validators;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@CrossOrigin(origins = "http://localhost:4200")
@RestController
@RequestMapping("/api")
public class UserRestController {

    String id = "id";
    @Autowired
    private IUserService userService;

    @Autowired
    private JwtUtil jwtUtil;

    @GetMapping("/users")
    public List<User> index() {
        return userService.findAll();
    }

    @GetMapping("/users/{id}")
    public User show(@PathVariable Long id) {
        return userService.findById(id);
    }

    @GetMapping("/users/name/{name}")
    public List<User> getUserByName(@PathVariable String name) {
        return userService.findAllByName(name);
    }

    @PostMapping("/users")
    public ResponseEntity<?> create(@Valid @RequestBody User user, BindingResult result) {
        User newUser = null;
        Map<String, Object> response = new HashMap<>();

        ResponseEntity<Map<String, Object>> responseValidator = Validators.getValidatorError(result, response);
        if (responseValidator != null)
            return responseValidator;

        try {
            newUser = userService.save(user);

        } catch (DataAccessException e) {
            response.put("message", "Error al realizar el insert en la base de datos");
            response.put("error", e.getMessage().concat(": ").concat(e.getMostSpecificCause().getMessage()));
            return new ResponseEntity<Map<String, Object>>(response, HttpStatus.INTERNAL_SERVER_ERROR);
        }

        response.put("message", "El usuario ha sido creado con exito!");
        response.put("user", newUser);
        response.put("token", jwtUtil.generateToken(newUser));
        return new ResponseEntity<Map<String, Object>>(response, HttpStatus.CREATED);
    }

    @PutMapping("/users/{id}")
    public ResponseEntity<?> update(@RequestBody User user, @PathVariable Long id) {
        User currentUser = userService.findById(id);
        User updatedUser = null;
        Map<String, Object> response = new HashMap<>();

        if (currentUser == null) {
            response.put("message", "Error: no se pudo editar, el usuario ID: "
                    .concat(id.toString().concat(" no existe en la base de datos")));
            return new ResponseEntity<Map<String, Object>>(response, HttpStatus.NOT_FOUND);
        }
        try {

            if (user.getName() != null || !user.getName().isEmpty())
                currentUser.setName(user.getName());
            if (user.getLastName() != null || !user.getLastName().isEmpty())
                currentUser.setLastName(user.getLastName());
            if (user.getEmail() != null || !user.getEmail().isEmpty())
                currentUser.setEmail(user.getEmail());

            updatedUser = userService.save(currentUser);

        } catch (DataAccessException e) {
            response.put("message", "Error al realizar el update en la base de datos");
            response.put("error", e.getMessage().concat(": ").concat(e.getMostSpecificCause().getMessage()));
            return new ResponseEntity<Map<String, Object>>(response, HttpStatus.INTERNAL_SERVER_ERROR);
        }

        response.put("message", "El usuario ha sido actualizado con exito!");
        response.put("user", updatedUser);
        return new ResponseEntity<Map<String, Object>>(response, HttpStatus.CREATED);
    }

    @PutMapping("/users/change-role/{id}")
    public ResponseEntity<?> changeRole(@RequestBody String newRole, @PathVariable Long id) {
        User currentUser = userService.findById(id);
        Map<String, Object> response = new HashMap<>();

        if (currentUser == null) {
            response.put("message", "Error: no se pudo cambiar el rol del usuario ID: "
                    .concat(id.toString().concat(" no existe en la base de datos")));
            return new ResponseEntity<Map<String, Object>>(response, HttpStatus.NOT_FOUND);
        }

        try {
            if (newRole != null)
                currentUser.setRole(newRole);

            userService.save(currentUser);

        } catch (DataAccessException e) {
            response.put("message", "Error al realizar el update en la base de datos");
            response.put("error", e.getMessage().concat(": ").concat(e.getMostSpecificCause().getMessage()));
            return new ResponseEntity<Map<String, Object>>(response, HttpStatus.INTERNAL_SERVER_ERROR);
        }

        response.put("message", "El rol ha sido actualizado con exito!");
        response.put("user", currentUser);
        return new ResponseEntity<Map<String, Object>>(response, HttpStatus.CREATED);
    }

    @DeleteMapping("/users/{id}")
    public ResponseEntity<?> delete(@PathVariable Long id) {
        Map<String, Object> response = new HashMap<>();

        try {
            userService.delete(id);
        } catch (DataAccessException e) {
            response.put("message", "Error al realizar el delete en la base de datos");
            response.put("error", e.getMessage().concat(": ").concat(e.getMostSpecificCause().getMessage()));
            return new ResponseEntity<Map<String, Object>>(response, HttpStatus.INTERNAL_SERVER_ERROR);
        }

        response.put("message", "El usuario ha sido eliminado con exito!");
        return new ResponseEntity<Map<String, Object>>(response, HttpStatus.OK);
    }

    @PostMapping("/login")
    public ResponseEntity<?> login(@RequestBody Login login) {
        Map<String, Object> response = new HashMap<>();
        User user = null;

        try {
            user = userService.findByEmailIgnoreCaseAndPassword(login.getEmail(), login.getPassword());

        } catch (DataAccessException e) {
            response.put("message", "Error al realizar el login en la base de datos");
            response.put("error", e.getMessage().concat(": ").concat(e.getMostSpecificCause().getMessage()));
            return new ResponseEntity<Map<String, Object>>(response, HttpStatus.INTERNAL_SERVER_ERROR);
        }

        if (user == null) {
            response.put("message", "Email o contraseña incorrectos");
            return new ResponseEntity<Map<String, Object>>(response, HttpStatus.NOT_FOUND);
        }

        user.setPassword(null);
        response.put("message", "El usuario ha sido logueado con exito!");
        response.put("user", user);
        response.put("token", jwtUtil.generateToken(user));
        response.put("menu", MenuFrontend.getMenu(user.getRole()));
        return new ResponseEntity<Map<String, Object>>(response, HttpStatus.CREATED);
    }

    @GetMapping("/login/renew")
    public ResponseEntity<?> verifyTokeR(@RequestHeader(value = "x-token") String token) {
        Map<String, Object> response = new HashMap<>();

        try {
            jwtUtil.verifyToken(token);

        } catch (Exception e) {
            response.put("message", "Error token invalido");
            return new ResponseEntity<Map<String, Object>>(response, HttpStatus.INTERNAL_SERVER_ERROR);
        }

        response.put("message", "Token validado con exito!");
        response.put("token", token);
        return new ResponseEntity<Map<String, Object>>(response, HttpStatus.CREATED);
    }

    @PutMapping("users/{id}/image")
    public ResponseEntity<?> updateImage(@RequestBody ImageURL urlImage, @PathVariable Long id) {
        Map<String, Object> response = new HashMap<>();
        User currentUser = userService.findById(id);
        User updatedUser = null;

        if (currentUser == null) {
            response.put("message", "Error: no se pudo editar, el usuario ID: "
                    .concat(id.toString().concat(" no existe en la base de datos")));
            return new ResponseEntity<Map<String, Object>>(response, HttpStatus.NOT_FOUND);
        }

        try {
            currentUser.setImage(urlImage.getUrl());
            updatedUser = userService.save(currentUser);

        } catch (DataAccessException e) {
            response.put("message", "Error al cambiar imagen en base de datos");
            response.put("error", e.getMessage().concat(": ").concat(e.getMostSpecificCause().getMessage()));
            return new ResponseEntity<Map<String, Object>>(response, HttpStatus.INTERNAL_SERVER_ERROR);
        }

        response.put("message", "La imagen se ha cambiado con exito!");
        response.put("user", updatedUser);
        return new ResponseEntity<Map<String, Object>>(response, HttpStatus.CREATED);
    }

    public boolean isAdmin(User user) {
        return user.getRole().equals("ROLE_ADMIN");
    }

}
