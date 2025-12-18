package com.suivi_academique.controllers;


import com.suivi_academique.dto.AffectationDTO;
import com.suivi_academique.dto.SalleDTO;
import com.suivi_academique.entities.AffectationId;
import com.suivi_academique.services.implementations.AffectationService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;

@RestController
@RequestMapping("/affectation")
public class AffectationController {

    private AffectationService affectationService;

    public AffectationController(AffectationService affectationService) {
        this.affectationService = affectationService;
    }

    @PostMapping
    public ResponseEntity<?> save(@RequestBody AffectationDTO affectationDTO) {

        try{
            return new ResponseEntity<>(affectationService.save(affectationDTO), HttpStatus.CREATED);
        }catch(Exception e){
            return new ResponseEntity<>(e.getMessage(), HttpStatus.BAD_REQUEST);
        }
    }

    @GetMapping()
    public ResponseEntity<List<AffectationDTO>> getAll() {

        try{
            return new  ResponseEntity<>(affectationService.getAll(), HttpStatus.OK);
        } catch (Exception e) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, e.getMessage());
        }
    }

    @PutMapping("/{codeCours}/{codePersonnel}")
    public ResponseEntity<?> update(@PathVariable String codeCours, @PathVariable String codePersonnel, @RequestBody AffectationDTO affectationDTO) {
        try {
            AffectationId id =  new AffectationId(codeCours, codePersonnel);
            return  new ResponseEntity<>(affectationService.update(id, affectationDTO), HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>(e.getMessage(), HttpStatus.BAD_REQUEST);
        }
    }

    @DeleteMapping("/{codeCours}/{codePersonnel}")
    public ResponseEntity<?> delete(@PathVariable String codeCours, @PathVariable String codePersonnel) {
        try {
            affectationService.delete(codeCours, codePersonnel);
            return new ResponseEntity<>(HttpStatus.OK);
        }
        catch (Exception e) {
            return new ResponseEntity<>(e.getMessage(), HttpStatus.BAD_REQUEST);
        }
    }
    @GetMapping("/{codeCours}/{codePersonnel}")
    public ResponseEntity<?> Show(@PathVariable String codeCours, @PathVariable String codePersonnel) {
        try{
            AffectationId id =  new AffectationId(codeCours, codePersonnel);
            return new ResponseEntity<>(affectationService.getById(id), HttpStatus.OK);
        }catch(Exception e){
            return new ResponseEntity<>(e.getMessage(), HttpStatus.BAD_REQUEST);
        }

    }


}
