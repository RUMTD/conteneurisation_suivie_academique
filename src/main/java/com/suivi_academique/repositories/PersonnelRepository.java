package com.suivi_academique.repositories;

import com.suivi_academique.entities.Personnel;
import com.suivi_academique.utils.RolePersonnel;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;


@Repository
public interface PersonnelRepository extends JpaRepository<Personnel,String> {

    List<Personnel> findByNomPersonnel(String nomPersonnel);
    @Query("select p from Personnel p WHERE p.nomPersonnel LIKE '%:token'")
    List<Personnel> findByName(@Param("token") String token);

    @Query(value = "SELECT count(*) from Personnel WHERE sexe_personnel =: sexe", nativeQuery = true)
    int countBySexe(@Param("sexe") char sexe);

    List<Personnel> findByRolePersonnel(RolePersonnel role);

    // Compter les personnels par rôle
    long countByRolePersonnel(RolePersonnel role);

    // Trouver les personnels avec leurs programmations
    @Query("SELECT DISTINCT p FROM Personnel p LEFT JOIN FETCH p.programmations WHERE p.codePersonnel = :codePersonnel")
    Optional<Personnel> findPersonnelWithProgrammations(@Param("codePersonnel") String codePersonnel);

    // Trouver les personnels avec leurs validations
    @Query("SELECT DISTINCT p FROM Personnel p LEFT JOIN FETCH p.validations WHERE p.codePersonnel = :codePersonnel")
    Optional<Personnel> findPersonnelWithValidations(@Param("codePersonnel") String codePersonnel);

    // Compter le nombre de programmations créées par un personnel
    @Query("SELECT COUNT(prog) FROM Programmation prog WHERE prog.personnelProg.codePersonnel = :codePersonnel")
    long countProgrammationsCreatedBy(@Param("codePersonnel") String codePersonnel);

    // Compter le nombre de validations effectuées par un personnel
    @Query("SELECT COUNT(prog) FROM Programmation prog WHERE prog.personnelVal.codePersonnel = :codePersonnel")
    long countValidationsBy(@Param("codePersonnel") String codePersonnel);

    // Trouver les enseignants (selon rôle)
    @Query("SELECT p FROM Personnel p WHERE p.rolePersonnel = 'ENSEIGNANT'")
    List<Personnel> findAllEnseignants();

    // Trouver les responsables académiques
    @Query("SELECT p FROM Personnel p WHERE p.rolePersonnel = 'RESPONSABLE_ACADEMIQUE'")
    List<Personnel> findAllResponsableAcademique();

    // Trouver les responsables du personnel
    @Query("SELECT p FROM Personnel p WHERE p.rolePersonnel = 'RESPONSABLE_PERSONNEL'")
    List<Personnel> findAllResponsablePersonnel();

    @Query("SELECT p FROM Personnel p WHERE p.rolePersonnel =: roleString")
    List<Personnel> searchByRole (String roleString);

    // Trouver les personnels sans programmation
    @Query("SELECT p FROM Personnel p WHERE SIZE(p.programmations) = 0")
    List<Personnel> findPersonnelWithoutProgrammations();

    // Tous les personnels triés par nom
    List<Personnel> findAllByOrderByNomPersonnelAsc();

    Personnel findByRolePersonnel(String rolePersonnel);

    Optional<Personnel> findByCodePersonnel(String codePersonnel);

    Optional<Personnel> findByLoginPersonnel(String loginPersonnel);

    boolean existsByLoginPersonnel(String loginPersonnel);
}
