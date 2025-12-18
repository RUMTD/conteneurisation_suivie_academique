package com.suivi_academique.controllers;


import com.suivi_academique.dto.CoursDTO;
import com.suivi_academique.dto.SalleDTO;
import com.suivi_academique.services.implementations.CoursService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/cours")
public class CoursController {

    private CoursService coursService;

    public CoursController(CoursService coursService) {
        this.coursService = coursService;
    }

    @PostMapping
    public ResponseEntity<?> save(@RequestBody CoursDTO coursDTO) {

        try{
            return new ResponseEntity<>(coursService.save(coursDTO), HttpStatus.CREATED);
        }catch(Exception e){
            return new ResponseEntity<>(e.getMessage(), HttpStatus.BAD_REQUEST);
        }
    }


    @GetMapping()
    public ResponseEntity<List<CoursDTO>> getAll() {
        return new  ResponseEntity<>(coursService.getAll(), HttpStatus.OK);

    }

    @PutMapping("/{codeCours}")
    public ResponseEntity<?> update(@PathVariable String codeCours, @RequestBody CoursDTO coursDTO) {
        try {
            return  new ResponseEntity<>(coursService.update(codeCours, coursDTO), HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>(e.getMessage(), HttpStatus.BAD_REQUEST);
        }
    }

    @DeleteMapping("/{codeCours}")
    public ResponseEntity<?> delete(@PathVariable String codeCours) {
        try {
            coursService.delete(codeCours);
            return new ResponseEntity<>(HttpStatus.OK);
        }
        catch (Exception e) {
            return new ResponseEntity<>(e.getMessage(), HttpStatus.BAD_REQUEST);
        }
    }
    @GetMapping("/{codeCours}")
    public ResponseEntity<?> get(@PathVariable String codeCours) {
        try{
            return new ResponseEntity<>(coursService.getById(codeCours), HttpStatus.OK);
        }catch(Exception e){
            return new ResponseEntity<>(e.getMessage(), HttpStatus.BAD_REQUEST);
        }

    }
}
