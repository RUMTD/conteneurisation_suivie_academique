package com.suivi_academique.controllers;

import com.suivi_academique.dto.ProgrammationDTO;
import com.suivi_academique.services.interfaces.ProgrammationInterface; // Utilisation de l'interface
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/programmations")
@AllArgsConstructor
public class ProgrammationController {

    private final ProgrammationInterface programmationService;


    @PostMapping
    public ResponseEntity<?> save(@RequestBody ProgrammationDTO programmationDTO) {
        try {
            return new ResponseEntity<>(programmationService.save(programmationDTO), HttpStatus.CREATED);
        } catch (Exception e) {
            return new ResponseEntity<>(e.getMessage(), HttpStatus.BAD_REQUEST);
        }
    }

    @GetMapping
    public ResponseEntity<List<ProgrammationDTO>> getAll() {
        return new ResponseEntity<>(programmationService.getAll(), HttpStatus.OK);
    }

    @PutMapping("/{id}")
    public ResponseEntity<?> update(@PathVariable int id, @RequestBody ProgrammationDTO programmationDTO) {
        try {
            return new ResponseEntity<>(programmationService.update(id, programmationDTO), HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>(e.getMessage(), HttpStatus.BAD_REQUEST);
        }
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<?> delete(@PathVariable int id) {
        try {
            programmationService.delete(id);
            return new ResponseEntity<>(HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>(e.getMessage(), HttpStatus.BAD_REQUEST);
        }
    }

    @GetMapping("/{id}")
    public ResponseEntity<?> get(@PathVariable int id) {
        try {
            return new ResponseEntity<>(programmationService.getById(id), HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>(e.getMessage(), HttpStatus.BAD_REQUEST);
        }
    }
}