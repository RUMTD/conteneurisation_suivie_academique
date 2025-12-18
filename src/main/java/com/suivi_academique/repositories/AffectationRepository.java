package com.suivi_academique.repositories;

import com.suivi_academique.entities.Affectation;
import com.suivi_academique.entities.AffectationId;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface AffectationRepository extends JpaRepository<Affectation, AffectationId> {
    // Trouver toutes les affectations d'un personnel
    List<Affectation> findByPersonnel_CodePersonnel(String codePersonnel);

    // Trouver toutes les affectations d'un cours
    List<Affectation> findByCours_CodeCours(String codeCours);

    // Vérifier si une affectation existe
    boolean existsByPersonnel_CodePersonnelAndCours_CodeCours(String codeCours, String codePersonnel);

    // Compter le nombre d'affectations par personnel
    @Query("SELECT COUNT(a) FROM Affectation a WHERE a.personnel.codePersonnel = :codePersonnel")
    long countAffectationsByPersonnel(@Param("codePersonnel") String codePersonnel);

    // Compter le nombre d'enseignants affectés à un cours
    @Query("SELECT COUNT(a) FROM Affectation a WHERE a.cours.codeCours = :codeCours")
    long countEnseignantsByCours(@Param("codeCours") String codeCours);

    // Trouver les cours affectés à un personnel avec informations détaillées
    @Query("SELECT a FROM Affectation a JOIN FETCH a.cours WHERE a.personnel.codePersonnel = :codePersonnel")
    List<Affectation> findAffectationsWithCoursDetails(@Param("codePersonnel") String codePersonnel);

    // Trouver les personnels affectés à un cours avec informations détaillées
    @Query("SELECT a FROM Affectation a JOIN FETCH a.personnel WHERE a.cours.codeCours = :codeCours")
    List<Affectation> findAffectationsWithPersonnelDetails(@Param("codeCours") String codeCours);

    // Supprimer toutes les affectations d'un personnel
    void deleteByPersonnel_CodePersonnel(String codePersonnel);

    // Supprimer toutes les affectations d'un cours
    void deleteByCours_CodeCours(String codeCours);

}
