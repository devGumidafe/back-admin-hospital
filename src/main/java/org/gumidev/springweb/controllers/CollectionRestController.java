package org.gumidev.springweb.controllers;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.gumidev.springweb.entities.Doctor;
import org.gumidev.springweb.entities.Hospital;
import org.gumidev.springweb.entities.User;
import org.gumidev.springweb.services.doctor.IDoctorService;
import org.gumidev.springweb.services.hospital.IHospitalService;
import org.gumidev.springweb.services.user.IUserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@CrossOrigin(origins = "https://front-hospital-dashboard.vercel.app")
@RestController
@RequestMapping("/api")
public class CollectionRestController {

    @Autowired
    private IDoctorService doctorService;

    @Autowired
    private IHospitalService hospitalService;

    @Autowired
    private IUserService userService;

    @GetMapping("/collections/{name}")
    public ResponseEntity<?> index(@PathVariable String name) {
        List<User> users = new ArrayList<>();
        List<Doctor> doctors = new ArrayList<>();
        List<Hospital> hospitals = new ArrayList<>();

        Map<String, Object> response = new HashMap<>();

        try {
            users = userService.findAllByName(name);
            doctors = doctorService.findAllByName(name);
            hospitals = hospitalService.findAllByName(name);

        } catch (DataAccessException e) {
            response.put("message", "Error al acceder a la base de datos");
            response.put("error", e.getMessage().concat(": ").concat(e.getMostSpecificCause().getMessage()));
            return new ResponseEntity<Map<String, Object>>(response, HttpStatus.INTERNAL_SERVER_ERROR);
        }

        response.put("users", users);
        response.put("doctors", doctors);
        response.put("hospitals", hospitals);

        return new ResponseEntity<Map<String, Object>>(response, HttpStatus.CREATED);
    }
}
