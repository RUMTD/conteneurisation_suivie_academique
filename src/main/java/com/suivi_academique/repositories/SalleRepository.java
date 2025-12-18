package com.suivi_academique.repositories;

import com.suivi_academique.entities.Salle;
import com.suivi_academique.utils.SalleStatus;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import java.util.List;
import java.util.Date;


@Repository
public interface SalleRepository extends JpaRepository<Salle, String> {

    boolean existsByContenance(int contenance);

    List<Salle> findByContenanceGreaterThanEqual(int contenance);

    List<Salle> findByCodeSalleContaining(String codeSalle);

    // Trouver par statut
    List<Salle> findByStatutSalle(SalleStatus statut);

    // Trouver les salles disponibles
    @Query("SELECT s FROM Salle s WHERE s.statutSalle = 'LIBRE'")
    List<Salle> findSallesLibre();

    // Trouver les salles occupées
    @Query("SELECT s FROM Salle s WHERE s.statutSalle = 'OCCUPE'")
    List<Salle> findSallesOccupe();

    // Trouver les salles en maintenance
    @Query("SELECT s FROM Salle s WHERE s.statutSalle = 'FERME'")
    List<Salle> findSallesFerme();

    // Rechercher dans la description
    List<Salle> findByDescSalleContainingIgnoreCase(String keyword);

    // Trouver les salles avec leurs programmations
    @Query("SELECT DISTINCT s FROM Salle s LEFT JOIN FETCH s.programmations WHERE s.codeSalle = :codeSalle")
    Salle findSalleWithProgrammations(@Param("codeSalle") String codeSalle);

    // Trouver les salles disponibles pour une période donnée
    @Query("SELECT s FROM Salle s WHERE s.statutSalle = 'DISPONIBLE' AND " +
            "s.codeSalle NOT IN (SELECT p.salle.codeSalle FROM Programmation p WHERE " +
            "(p.dateProgrammation <= :fin AND p.finProgrammation >= :debut))")
    List<Salle> findAvailableSallesForPeriod(@Param("debut") Date debut, @Param("fin") Date fin);

    // Compter les programmations par salle
    @Query("SELECT COUNT(p) FROM Programmation p WHERE p.salle.codeSalle = :codeSalle")
    long countProgrammationsBySalle(@Param("codeSalle") String codeSalle);

    // Trouver les salles sans programmation
    @Query("SELECT s FROM Salle s WHERE SIZE(s.programmations) = 0")
    List<Salle> findSallesWithoutProgrammations();

    // Trouver les salles les plus utilisées
    @Query("SELECT s FROM Salle s ORDER BY SIZE(s.programmations) DESC")
    List<Salle> findSallesOrderedByUsage();

    // Vérifier si une salle est disponible pour une période
    @Query("SELECT CASE WHEN COUNT(p) > 0 THEN false ELSE true END FROM Programmation p " +
            "WHERE p.salle.codeSalle = :codeSalle AND " +
            "(p.dateProgrammation <= :fin AND p.finProgrammation >= :debut)")
    boolean isSalleAvailableForPeriod(@Param("codeSalle") String codeSalle,
                                      @Param("debut") Date debut,
                                      @Param("fin") Date fin);


}
