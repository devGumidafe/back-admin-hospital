package org.gumidev.springweb.controllers;

import org.gumidev.springweb.entities.Hospital;
import org.gumidev.springweb.model.ImageURL;
import org.gumidev.springweb.services.hospital.IHospitalService;
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
public class HospitalRestController {

    @Autowired
    private IHospitalService hospitalService;

    @Autowired
    private IUserService userService;

    @GetMapping("/hospitals")
    public List<Hospital> index() {
        return hospitalService.findAll();
    }

    @GetMapping("/hospitals/{id}")
    public Hospital show(@PathVariable Long id) {
        return hospitalService.findById(id);
    }

    @GetMapping("/hospitals/name/{name}")
    public List<Hospital> getHospitalByName(@PathVariable String name) {
        return hospitalService.findAllByName(name);
    }

    @GetMapping("/hospitals/doctor/{id}")
    public Hospital getHospitalByDoctor(@PathVariable Long id) {
        return hospitalService.findByDoctor(id);
    }

    @PostMapping("/hospitals")
    public ResponseEntity<?> create(@RequestBody String newName) {
        Hospital newHospital = null;
        Map<String, Object> response = new HashMap<>();

        try {
            newHospital = new Hospital();
            newHospital.setName(newName);
            hospitalService.save(newHospital);

        } catch (DataAccessException e) {
            response.put("message", "Error al crear el hospital");
            response.put("error", e.getMessage().concat(": ").concat(e.getMostSpecificCause().getMessage()));
            return new ResponseEntity<Map<String, Object>>(response, HttpStatus.INTERNAL_SERVER_ERROR);
        }

        response.put("message", "El hospital ha sido creado con exito!");
        response.put("user", newHospital);

        return new ResponseEntity<Map<String, Object>>(response, HttpStatus.CREATED);
    }

    @PutMapping("/hospitals/{id}")
    public Hospital update(@PathVariable Long id, @Valid @RequestBody Hospital hospital) {
        Hospital updateHospital = hospitalService.findById(id);
        updateHospital.setName(hospital.getName());
        updateHospital.setImage(hospital.getImage());

        return hospitalService.save(updateHospital);
    }

    @DeleteMapping("/hospitals/{id}")
    public void delete(@PathVariable Long id) {
        hospitalService.delete(id);
    }

    @PutMapping("hospitals/{id}/image")
    public ResponseEntity<?> updateImage(@RequestBody ImageURL urlImage, @PathVariable Long id) {
        Map<String, Object> response = new HashMap<>();
        Hospital hospital = hospitalService.findById(id);

        if (hospital == null) {
            response.put("message", "Error: no se pudo editar, el hospital ID: "
                    .concat(id.toString().concat(" no existe en la base de datos")));
            return new ResponseEntity<Map<String, Object>>(response, HttpStatus.NOT_FOUND);
        }

        try {
            hospital.setImage(urlImage.getUrl());
            hospitalService.save(hospital);

        } catch (DataAccessException e) {
            response.put("message", "Error al cambiar imagen en base de datos");
            response.put("error", e.getMessage().concat(": ").concat(e.getMostSpecificCause().getMessage()));
            return new ResponseEntity<Map<String, Object>>(response, HttpStatus.INTERNAL_SERVER_ERROR);
        }

        response.put("message", "La imagen se ha cambiado con exito!");
        response.put("hospital", hospital);
        return new ResponseEntity<Map<String, Object>>(response, HttpStatus.CREATED);
    }
}
