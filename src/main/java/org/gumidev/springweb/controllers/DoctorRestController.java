package org.gumidev.springweb.controllers;

import org.gumidev.springweb.entities.Doctor;
import org.gumidev.springweb.entities.User;
import org.gumidev.springweb.model.ImageURL;
import org.gumidev.springweb.services.doctor.IDoctorService;
import org.gumidev.springweb.services.user.IUserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@CrossOrigin(origins = "http://localhost:4200")
@RestController
@RequestMapping("/api")
public class DoctorRestController {

    @Autowired
    private IDoctorService doctorService;

    @Autowired
    private IUserService userService;

    @GetMapping("/doctors")
    public List<Doctor> index() {
        return doctorService.findAll();
    }

    @GetMapping("/doctors/{id}")
    public Doctor show(@PathVariable Long id) {
        return doctorService.findById(id);
    }

    @GetMapping("/doctors/name/{name}")
    public List<Doctor> getDoctorByName(@PathVariable String name) {
        return doctorService.findAllByName(name);
    }

    @PostMapping("/doctors")
    public ResponseEntity<?> create(@RequestBody Doctor newDoctor) {
        Doctor doctor = null;
        Map<String, Object> response = new HashMap<>();

        try {
            doctor = newDoctor;
            doctorService.save(doctor);

        } catch (DataAccessException e) {
            response.put("message", "Error al crear el doctor");
            response.put("error", e.getMessage().concat(": ").concat(e.getMostSpecificCause().getMessage()));
            return new ResponseEntity<Map<String, Object>>(response, HttpStatus.INTERNAL_SERVER_ERROR);
        }

        response.put("message", "El doctor ha sido creado con exito!");
        response.put("user", doctor);

        return new ResponseEntity<Map<String, Object>>(response, HttpStatus.CREATED);
    }

    @PutMapping("/doctors/{id}")
    public Doctor update(@PathVariable Long id, @Valid @RequestBody Doctor doctor) {
        Doctor doctorUpdate = doctorService.findById(id);
        doctorUpdate.setName(doctor.getName());
        doctorUpdate.setImage(doctor.getImage());

        return doctorService.save(doctorUpdate);
    }

    @DeleteMapping("/doctors/{id}")
    public void delete(@PathVariable Long id) {
        doctorService.delete(id);
    }

    @PutMapping("doctors/{id}/image")
    public ResponseEntity<?> updateImage(@RequestBody ImageURL urlImage, @PathVariable Long id) {
        Map<String, Object> response = new HashMap<>();
        Doctor doctor = doctorService.findById(id);

        if (doctor == null) {
            response.put("message", "Error: no se pudo editar, el doctor ID: "
                    .concat(id.toString().concat(" no existe en la base de datos")));
            return new ResponseEntity<Map<String, Object>>(response, HttpStatus.NOT_FOUND);
        }

        try {
            doctor.setImage(urlImage.getUrl());
            doctorService.save(doctor);

        } catch (DataAccessException e) {
            response.put("message", "Error al cambiar imagen en base de datos");
            response.put("error", e.getMessage().concat(": ").concat(e.getMostSpecificCause().getMessage()));
            return new ResponseEntity<Map<String, Object>>(response, HttpStatus.INTERNAL_SERVER_ERROR);
        }

        response.put("message", "La imagen se ha cambiado con exito!");
        response.put("doctor", doctor);
        return new ResponseEntity<Map<String, Object>>(response, HttpStatus.CREATED);
    }
}
