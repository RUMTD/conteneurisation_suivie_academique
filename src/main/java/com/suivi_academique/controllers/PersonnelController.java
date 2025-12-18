package com.suivi_academique.controllers;


import com.suivi_academique.dto.PersonnelDTO;
import com.suivi_academique.dto.SalleDTO;
import com.suivi_academique.services.implementations.PersonnelService;
import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;


@RestController
@RequestMapping("/personnel")
public class PersonnelController {


    private PersonnelService personnelService;

    public PersonnelController(PersonnelService personnelService) {
        this.personnelService = personnelService;
    }

    @PostMapping
    public ResponseEntity<?> save(@RequestBody PersonnelDTO personnelDTO) {

        try{
            return new ResponseEntity<>(personnelService.save(personnelDTO), HttpStatus.CREATED);
        }catch(Exception e){
            return new ResponseEntity<>(e.getMessage(), HttpStatus.BAD_REQUEST);
        }
    }

    @GetMapping()
    public ResponseEntity<List<PersonnelDTO>> getAll() {
        return new  ResponseEntity<>(personnelService.getAll(), HttpStatus.OK);

    }

    @PutMapping("/{codePersonnel}")
    public ResponseEntity<?> update(@PathVariable String codePersonnel, @RequestBody PersonnelDTO personnelDTO) {
        try {
            return  new ResponseEntity<>(personnelService.update(codePersonnel, personnelDTO), HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>(e.getMessage(), HttpStatus.BAD_REQUEST);
        }
    }

    @DeleteMapping("/{codePersonnel}")
    public ResponseEntity<?> delete(@PathVariable String codePersonnel) {
        try {
            personnelService.delete(codePersonnel);
            return new ResponseEntity<>(HttpStatus.OK);
        }
        catch (Exception e) {
            return new ResponseEntity<>(e.getMessage(), HttpStatus.BAD_REQUEST);
        }
    }
    @GetMapping("/{codePersonnel}")
    public ResponseEntity<?> get(@PathVariable String codePersonnel) {
        try{
            return new ResponseEntity<>(personnelService.getById(codePersonnel), HttpStatus.OK);
        }catch(Exception e){
            return new ResponseEntity<>(e.getMessage(), HttpStatus.BAD_REQUEST);
        }

    }
}
