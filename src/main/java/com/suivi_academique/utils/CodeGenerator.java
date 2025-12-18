package com.suivi_academique.utils;

import com.suivi_academique.repositories.PersonnelRepository;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.stereotype.Component;

import java.time.LocalDate;
import java.util.concurrent.ThreadLocalRandom;

@Getter
@Setter
@AllArgsConstructor
@Component
public class CodeGenerator {

    private PersonnelRepository personnelRepository;

    public String generate(String roleString){
        String prefix  = switch (roleString){
            case "ENSEIGNANT" -> "ENS";
            case "RESPONSABLE_ACADEMIQUE" -> "RA";
            case "RESPONSABLE_PERSONNEL" -> "RP";
            default -> null;
        };

        long randomNumber = ThreadLocalRandom.current().nextInt(10000, 100000);
        int year = LocalDate.now().getYear();
        String code = prefix + year + randomNumber;
      if(personnelRepository.existsById(code))
          return generate(roleString);
      else
          return code;
      }
}
