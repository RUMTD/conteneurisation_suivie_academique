package com.suivi_academique.repositories;

import com.suivi_academique.entities.Programmation;
import com.suivi_academique.utils.StatutProgrammation;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import java.util.List;
import java.util.Date;


public interface ProgrammationRepository extends JpaRepository<Programmation, Integer> {

    // Trouver par statut
    List<Programmation> findByStatutProgrammation(StatutProgrammation statut);

    // Trouver par cours
    List<Programmation> findByCours_CodeCours(String codeCours);

    // Trouver par salle
    List<Programmation> findBySalle_CodeSalle(String codeSalle);

    // Trouver par personnel programmateur
    List<Programmation> findByPersonnelProg_CodePersonnel(String codePersonnel);

    // Trouver par personnel validateur
    List<Programmation> findByPersonnelVal_CodePersonnel(String codePersonnel);


    // Vérifier les conflits de salle (même salle, période qui se chevauche)
    @Query("SELECT p FROM Programmation p WHERE p.salle.codeSalle = :codeSalle AND " +
            "((p.dateProgrammation <= :fin AND p.finProgrammation >= :debut))")
    List<Programmation> findConflictingSalleBookings(@Param("codeSalle") String codeSalle,
                                                     @Param("debut") Date debut,
                                                     @Param("fin") Date fin);


    // Trouver les programmations avec détails complets
    @Query("SELECT p FROM Programmation p " +
            "JOIN FETCH p.cours " +
            "JOIN FETCH p.salle " +
            "JOIN FETCH p.personnelProg " +
            "LEFT JOIN FETCH p.personnelVal")
    List<Programmation> findAllWithDetails();
/*
    // Trouver les programmations programmé
    @Query("SELECT p FROM Programmation p WHERE p.statutProgrammation = 'PROGRAMMER'")
    List<Programmation> findPendingProgrammations();

    // Trouver les programmations validées
    @Query("SELECT p FROM Programmation p WHERE p.statutProgrammation = com.suivi_academique.utils.StatutProgrammation.VALIDE")
    List<Programmation> findValidatedProgrammations();

    // Trouver les programmations NON VALIDE
    @Query("SELECT p FROM Programmation p WHERE p.statutProgrammation = 'NON_VALIDER'")
    List<Programmation> findRejectedProgrammations();
*/
    // Compter les programmations par salle
    long countBySalle_CodeSalle(String codeSalle);

    // Compter les programmations par cours
    long countByCours_CodeCours(String codeCours);

    // Trouver les programmations futures
    @Query("SELECT p FROM Programmation p WHERE p.dateProgrammation > :date")
    List<Programmation> findFutureProgrammations(@Param("date") Date date);

    // Trouver les programmations passées
    @Query("SELECT p FROM Programmation p WHERE p.finProgrammation < :date")
    List<Programmation> findPastProgrammations(@Param("date") Date date);

    // Trouver les programmations en cours
    @Query("SELECT p FROM Programmation p WHERE :date BETWEEN p.dateProgrammation AND p.finProgrammation")
    List<Programmation> findCurrentProgrammations(@Param("date") Date date);

    // Calculer le total d'heures par cours
    @Query("SELECT SUM(p.nbHeure) FROM Programmation p WHERE p.cours.codeCours = :codeCours")
    Integer sumHeuresByCours(@Param("codeCours") String codeCours);

    // Calculer le total d'heures par personnel
    @Query("SELECT SUM(p.nbHeure) FROM Programmation p WHERE p.personnelProg.codePersonnel = :codePersonnel")
    Integer sumHeuresByPersonnel(@Param("codePersonnel") String codePersonnel);

    // Recherche multicritères
    @Query("SELECT p FROM Programmation p WHERE " +
            "(:codeCours IS NULL OR p.cours.codeCours = :codeCours) AND " +
            "(:codeSalle IS NULL OR p.salle.codeSalle = :codeSalle) AND " +
            "(:codePersonnel IS NULL OR p.personnelProg.codePersonnel = :codePersonnel) AND " +
            "(:statut IS NULL OR p.statutProgrammation = :statut)")
    List<Programmation> searchProgrammations(@Param("codeCours") String codeCours,
                                             @Param("codeSalle") String codeSalle,
                                             @Param("codePersonnel") String codePersonnel,
                                             @Param("statut") StatutProgrammation statut);

    // Trouver les salles disponibles pour une période
    @Query("SELECT s.codeSalle FROM Salle s WHERE s.statutSalle = 'DISPONIBLE' AND " +
            "s.codeSalle NOT IN (SELECT p.salle.codeSalle FROM Programmation p WHERE " +
            "(p.dateProgrammation <= :fin AND p.finProgrammation >= :debut))")
    List<String> findAvailableSalles(@Param("debut") Date debut, @Param("fin") Date fin);
}
