package com.suivi_academique.controllers;


import com.suivi_academique.dto.SalleDTO;
import com.suivi_academique.services.implementations.SalleService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;

@RestController
@RequestMapping("/salle")
public class SalleController {

    private SalleService salleService;
    public SalleController(SalleService salleService) {
        this.salleService = salleService;
    }



    @PostMapping
    public ResponseEntity<?> save(@RequestBody SalleDTO salleDTO) {

        try{
        return new ResponseEntity<>(salleService.save(salleDTO), HttpStatus.CREATED);
    }catch(Exception e){
        return new ResponseEntity<>(e.getMessage(), HttpStatus.BAD_REQUEST);
        }
    }

    @GetMapping()
    public ResponseEntity<List<SalleDTO>> getAll() {

        try{
        return new  ResponseEntity<>(salleService.getAll(), HttpStatus.OK);
        } catch (Exception e) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, e.getMessage());
        }
    }

    @PutMapping("/{codeSalle}")
    public ResponseEntity<?> update(@PathVariable String codeSalle, @RequestBody SalleDTO salleDTO) {
        try {
            return  new ResponseEntity<>(salleService.update(codeSalle, salleDTO), HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>(e.getMessage(), HttpStatus.BAD_REQUEST);
        }
    }

    @DeleteMapping("/{codeSalle}")
    public ResponseEntity<?> delete(@PathVariable String codeSalle) {
        try {
            salleService.delete(codeSalle);
            return new ResponseEntity<>(HttpStatus.OK);
        }
        catch (Exception e) {
            return new ResponseEntity<>(e.getMessage(), HttpStatus.BAD_REQUEST);
        }
    }
    @GetMapping("/{codeSalle}")
    public ResponseEntity<?> Show(@PathVariable String codeSalle) {
        try{
            return new ResponseEntity<>(salleService.getById(codeSalle), HttpStatus.OK);
        }catch(Exception e){
            return new ResponseEntity<>(e.getMessage(), HttpStatus.BAD_REQUEST);
        }

    }



}
