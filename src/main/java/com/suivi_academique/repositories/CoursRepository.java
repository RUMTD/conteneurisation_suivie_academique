package com.suivi_academique.repositories;

import com.suivi_academique.entities.Cours;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.Optional;

public interface CoursRepository extends JpaRepository<Cours,String> {

    // Rechercher un cours par son label
    Optional<Cours> findByLabelCours(String labelCours);

    // Rechercher des cours contenant un mot-clé dans le label
    List<Cours> findByLabelCoursContainingIgnoreCase(String keyword);

    // Rechercher des cours par nombre de crédits
    List<Cours> findByNbCreditCours(String nbCreditCours);

    // Rechercher des cours par nombre d'heures
    List<Cours> findByNbHeureCours(String nbHeureCours);

    // Trouver les cours avec leurs programmations
    @Query("SELECT DISTINCT c FROM Cours c LEFT JOIN FETCH c.programmations WHERE c.codeCours = :codeCours")
    Optional<Cours> findCoursWithProgrammations(@Param("codeCours") String codeCours);

    // Trouver les cours sans programmation
    @Query("SELECT c FROM Cours c WHERE c.programmations IS EMPTY")
    List<Cours> findCoursWithoutProgrammations();

    // Trouver les cours avec au moins une programmation
    @Query("SELECT DISTINCT c FROM Cours c WHERE SIZE(c.programmations) > 0")
    List<Cours> findCoursWithProgrammations();

    // Compter le nombre de programmations par cours
    @Query("SELECT COUNT(p) FROM Programmation p WHERE p.cours.codeCours = :codeCours")
    long countProgrammationsByCours(@Param("codeCours") String codeCours);


    // Vérifier si un cours existe par son label
    boolean existsByLabelCours(String labelCours);

    // Trouver tous les cours triés par label
    List<Cours> findAllByOrderByLabelCoursAsc();
}

