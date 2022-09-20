package org.gumidev.springweb.controllers;

import org.gumidev.springweb.entities.Doctor;
import org.gumidev.springweb.entities.Hospital;
import org.gumidev.springweb.model.ImageURL;
import org.gumidev.springweb.services.doctor.IDoctorService;
import org.gumidev.springweb.services.hospital.IHospitalService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@CrossOrigin(origins = "https://front-hospital-dashboard.vercel.app")
@RestController
@RequestMapping("/api")
public class DoctorRestController {

    @Autowired
    private IDoctorService doctorService;

    @Autowired
    private IHospitalService hospitalService;

    @GetMapping("/doctors")
    public ResponseEntity<List<Doctor>> index() {
        List<Doctor> doctors = doctorService.findAll();
        return new ResponseEntity<List<Doctor>>(doctors, HttpStatus.OK);
    }

    @GetMapping("/doctors/{id}")
    public Doctor show(@PathVariable Long id) {
        try {
            return doctorService.findById(id);
        } catch (DataAccessException e) {
            return null;
        }
    }

    @GetMapping("/doctors/name/{name}")
    public List<Doctor> getDoctorByName(@PathVariable String name) {
        return doctorService.findAllByName(name);
    }

    @PostMapping("/doctors")
    public ResponseEntity<?> create(@RequestBody Doctor newDoctor) {
        Doctor doctor = null;
        Hospital hospital = null;
        Map<String, Object> response = new HashMap<>();

        try {
            hospital = hospitalService.findById(newDoctor.getHospital().getId());
            newDoctor.setHospital(hospital);
            doctor = doctorService.save(newDoctor);

        } catch (DataAccessException e) {
            response.put("message", "Error al crear el doctor");
            response.put("error", e.getMessage().concat(": ").concat(e.getMostSpecificCause().getMessage()));
            return new ResponseEntity<Map<String, Object>>(response, HttpStatus.INTERNAL_SERVER_ERROR);
        }

        response.put("message", "El doctor ha sido creado con exito!");
        response.put("doctor", doctor);

        return new ResponseEntity<Map<String, Object>>(response, HttpStatus.CREATED);
    }

    @PutMapping("/doctors/{id}")
    public ResponseEntity<?> update(@RequestBody Doctor doctor, @PathVariable Long id) {
        Doctor currentDoctor = doctorService.findById(id);
        Doctor updateDoctor = null;
        Map<String, Object> response = new HashMap<>();

        if (currentDoctor == null) {
            response.put("message", "Error: no se pudo editar, el Médico ID: "
                    .concat(id.toString().concat(" no existe en la base de datos")));
            return new ResponseEntity<Map<String, Object>>(response, HttpStatus.NOT_FOUND);
        }

        try {
            if (doctor.getName() != null || !doctor.getName().isEmpty())
                currentDoctor.setName(doctor.getName());
            if (doctor.getHospital() != null) {
                currentDoctor.setHospital(doctor.getHospital());
            }

            updateDoctor = doctorService.save(currentDoctor);
        } catch (DataAccessException e) {
            response.put("message", "Error al realizar el update en la base de datos");
            response.put("error", e.getMessage().concat(": ").concat(e.getMostSpecificCause().getMessage()));
            return new ResponseEntity<Map<String, Object>>(response, HttpStatus.INTERNAL_SERVER_ERROR);
        }

        response.put("message", "El Médico ha sido actualizado con exito!");
        response.put("doctor", updateDoctor);
        return new ResponseEntity<Map<String, Object>>(response, HttpStatus.CREATED);
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
