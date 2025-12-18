package com.suivie_academique.servicesTest;


import com.suivi_academique.dto.SalleDTO;
import com.suivi_academique.entities.Salle;
import com.suivi_academique.mappers.SalleMapper;
import com.suivi_academique.repositories.SalleRepository;
import com.suivi_academique.services.implementations.SalleService;
import com.suivi_academique.utils.SalleStatus;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
@DisplayName("Tests unitaires complets du SalleService")
class SalleServiceTest {

    @Mock
    private SalleRepository salleRepository;

    @Mock
    private SalleMapper salleMapper;

    @InjectMocks
    private SalleService salleService;

    private SalleDTO salleDTO;
    private Salle salleEntity;

    @BeforeEach
    void setUp() {
        salleDTO = new SalleDTO();
        salleDTO.setCodeSalle("S001");
        salleDTO.setContenace(50);
        salleDTO.setDescSalle("Salle de conférence");
        salleDTO.setStatutSalle(SalleStatus.LIBRE);

        salleEntity = new Salle();
        salleEntity.setCodeSalle("S001");
        salleEntity.setContenance(50);
        salleEntity.setDescSalle("Salle de conférence");
        salleEntity.setStatutSalle(SalleStatus.LIBRE);
    }

    @Test
    @DisplayName("save - succès avec données valides")
    void save_ValidData_ReturnsSavedDTO() {
        // Given
        given(salleMapper.toEntity(salleDTO)).willReturn(salleEntity);
        given(salleRepository.save(salleEntity)).willReturn(salleEntity);
        given(salleMapper.toDTO(salleEntity)).willReturn(salleDTO);

        // When
        SalleDTO result = salleService.save(salleDTO);

        // Then
        assertThat(result).isNotNull();
        assertThat(result.getCodeSalle()).isEqualTo("S001");
        verify(salleRepository).save(salleEntity);
        verify(salleMapper).toEntity(salleDTO);
        verify(salleMapper).toDTO(salleEntity);
    }

    @Test
    @DisplayName("save - échoue avec code vide")
    void save_EmptyCode_ThrowsRuntimeException() {
        // Given
        salleDTO.setCodeSalle("");

        // When & Then
        assertThatThrownBy(() -> salleService.save(salleDTO))
                .isInstanceOf(RuntimeException.class)
                .hasMessage("Données incorret");
        verify(salleRepository, never()).save(any());
        verify(salleMapper, never()).toEntity(any());
    }

    @Test
    @DisplayName("save - échoue avec contenance < 10")
    void save_InvalidContenance_ThrowsRuntimeException() {
        // Given
        salleDTO.setContenace(5);

        // When & Then
        assertThatThrownBy(() -> salleService.save(salleDTO))
                .isInstanceOf(RuntimeException.class)
                .hasMessage("Données incorret");
        verify(salleRepository, never()).save(any());
        verify(salleMapper, never()).toEntity(any());
    }

    @Test
    @DisplayName("save - accepte tous les statuts valides")
    void save_AllValidStatuses_Accepted() {
        SalleStatus[] allStatuses = {SalleStatus.OCCUPE, SalleStatus.LIBRE, SalleStatus.FERMER};

        for (SalleStatus status : allStatuses) {
            // Given
            salleDTO.setStatutSalle(status);
            given(salleMapper.toEntity(salleDTO)).willReturn(salleEntity);
            given(salleRepository.save(salleEntity)).willReturn(salleEntity);
            given(salleMapper.toDTO(salleEntity)).willReturn(salleDTO);

            // When
            SalleDTO result = salleService.save(salleDTO);

            // Then
            assertThat(result).isNotNull();
            verify(salleRepository).save(salleEntity);
        }
    }

    @Test
    @DisplayName("getAll - retourne liste de salles")
    void getAll_ReturnsAllSalles() {
        // Given
        List<Salle> salles = Arrays.asList(salleEntity);
        given(salleRepository.findAll()).willReturn(salles);
        given(salleMapper.toDTO(salleEntity)).willReturn(salleDTO);

        // When
        List<SalleDTO> result = salleService.getAll();

        // Then
        assertThat(result).hasSize(1);
        assertThat(result.get(0).getCodeSalle()).isEqualTo("S001");
        verify(salleRepository).findAll();
        verify(salleMapper).toDTO(salleEntity);
    }

    @Test
    @DisplayName("getById - trouve salle existante")
    void getById_ExistingId_ReturnsSalleDTO() {
        // Given
        given(salleRepository.findById("S001")).willReturn(Optional.of(salleEntity));
        given(salleMapper.toDTO(salleEntity)).willReturn(salleDTO);

        // When
        SalleDTO result = salleService.getById("S001");

        // Then
        assertThat(result).isNotNull();
        assertThat(result.getCodeSalle()).isEqualTo("S001");
        verify(salleRepository).findById("S001");
        verify(salleMapper).toDTO(salleEntity);
    }

    @Test
    @DisplayName("getById - salle inexistante lève exception")
    void getById_NonExistingId_ThrowsRuntimeException() {
        // Given
        given(salleRepository.findById("S999")).willReturn(Optional.empty());

        // When & Then
        assertThatThrownBy(() -> salleService.getById("S999"))
                .isInstanceOf(RuntimeException.class)
                .hasMessage("Salle non trouvée");
        verify(salleMapper, never()).toDTO(any());
    }

    @Test
    @DisplayName("update - met à jour salle existante")
    void update_ExistingSalle_UpdatesAndReturnsDTO() {
        // Given
        SalleDTO updatedDTO = new SalleDTO();
        updatedDTO.setContenace(100);
        updatedDTO.setDescSalle("Salle mise à jour");
        updatedDTO.setStatutSalle(SalleStatus.OCCUPE);

        given(salleRepository.findById("S001")).willReturn(Optional.of(salleEntity));
        given(salleMapper.toDTO(salleEntity)).willReturn(updatedDTO);

        // When
        SalleDTO result = salleService.update("S001", updatedDTO);

        // Then
        assertThat(result.getContenace()).isEqualTo(100);
        assertThat(result.getDescSalle()).isEqualTo("Salle mise à jour");
        assertThat(result.getStatutSalle()).isEqualTo(SalleStatus.OCCUPE);
        verify(salleRepository, times(2)).save(salleEntity);
    }

    @Test
    @DisplayName("update - salle inexistante lève exception")
    void update_NonExistingSalle_ThrowsRuntimeException() {
        // Given
        given(salleRepository.findById("S999")).willReturn(Optional.empty());

        // When & Then
        assertThatThrownBy(() -> salleService.update("S999", salleDTO))
                .isInstanceOf(RuntimeException.class)
                .hasMessage("Salle introuvé");
        verify(salleRepository, never()).save(any());
    }

    @Test
    @DisplayName("delete - supprime salle existante")
    void delete_ExistingSalle_DeletesSuccessfully() {
        // Given
        given(salleRepository.existsById("S001")).willReturn(true);

        // When
        salleService.delete("S001");

        // Then
        verify(salleRepository).existsById("S001");
        verify(salleRepository).deleteById("S001");
    }

    @Test
    @DisplayName("delete - salle inexistante lève exception")
    void delete_NonExistingSalle_ThrowsRuntimeException() {
        // Given
        given(salleRepository.existsById("S999")).willReturn(false);

        // When & Then
        assertThatThrownBy(() -> salleService.delete("S999"))
                .isInstanceOf(RuntimeException.class)
                .hasMessage("Salle inexistante");
        verify(salleRepository, never()).deleteById(any());
    }

    @Test
    @DisplayName("findSallesOccupe - retourne null selon implémentation")
    void findSallesOccupe_ReturnsNull() {
        // When
        SalleDTO result = salleService.findSallesOccupe(SalleStatus.OCCUPE);

        // Then
        assertThat(result).isNull();
    }
}

