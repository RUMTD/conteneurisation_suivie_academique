package com.suivie_academique.controllersTest;

import com.suivi_academique.SuiviAcademiqueApplication; // Remplacez par votre classe principale
import com.suivi_academique.dto.SalleDTO;
import com.suivi_academique.repositories.PersonnelRepository;
import com.suivi_academique.services.implementations.SalleService;
import com.suivi_academique.utils.SalleStatus;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;

import java.util.Arrays;
import java.util.List;

import static org.mockito.ArgumentMatchers.*;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@SpringBootTest(classes = SuiviAcademiqueApplication.class)
@AutoConfigureMockMvc
@DisplayName("🧪 SalleController - Tests d'intégration complets")
class SalleControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;

    @MockBean
    private SalleService salleService;

    @MockBean
    private PersonnelRepository personnelRepository;

    private SalleDTO salleDTO;
    private List<SalleDTO> sallesList;

    @BeforeEach
    void setUp() {
        // Préparation des données de test
        salleDTO = new SalleDTO();
        salleDTO.setCodeSalle("S001");
        salleDTO.setContenace(50);
        salleDTO.setDescSalle("Salle de conférence");
        salleDTO.setStatutSalle(SalleStatus.LIBRE);

        SalleDTO salle2 = new SalleDTO();
        salle2.setCodeSalle("S002");
        salle2.setContenace(30);
        salle2.setDescSalle("Salle de TD");
        salle2.setStatutSalle(SalleStatus.OCCUPE);

        sallesList = Arrays.asList(salleDTO, salle2);
    }

    // ════════════════════════════════════════════════════════════════════════════════
    // 🛡️ TESTS DE SÉCURITÉ (JWT requis)
    // ════════════════════════════════════════════════════════════════════════════════

    @Test
    @DisplayName("❌ Sans authentification → 403 Forbidden")
    void withoutAuthentication_getAll_forbidden() throws Exception {
        mockMvc.perform(get("/salle"))
                .andExpect(status().isForbidden());  // ✅ 403 au lieu de 401
    }


    // ════════════════════════════════════════════════════════════════════════════════
    // ✅ TESTS GET ALL
    // ════════════════════════════════════════════════════════════════════════════════

    @Test
    @WithMockUser(roles = "USER")
    @DisplayName("✅ GET /salle → Retourne toutes les salles (200)")
    void getAllSalles_success() throws Exception {
        when(salleService.getAll()).thenReturn(sallesList);

        mockMvc.perform(get("/salle"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.length()").value(2))
                .andExpect(jsonPath("$[0].codeSalle").value("S001"))
                .andExpect(jsonPath("$[1].codeSalle").value("S002"));
    }

    // ════════════════════════════════════════════════════════════════════════════════
    // ✅ TESTS GET BY ID
    // ════════════════════════════════════════════════════════════════════════════════

    @Test
    @WithMockUser(roles = "USER")
    @DisplayName("✅ GET /salle/{id} → Salle existante (200)")
    void getSalleById_existing_success() throws Exception {
        when(salleService.getById("S001")).thenReturn(salleDTO);

        mockMvc.perform(get("/salle/S001"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.codeSalle").value("S001"))
                .andExpect(jsonPath("$.contenace").value(50));
    }

    @Test
    @WithMockUser(roles = "USER")
    @DisplayName("❌ GET /salle/{id} → Salle inexistante (400)")
    void getSalleById_nonExisting_badRequest() throws Exception {
        when(salleService.getById("S999")).thenThrow(new RuntimeException("Salle non trouvée"));

        mockMvc.perform(get("/salle/S999"))
                .andExpect(status().isBadRequest())
                .andExpect(content().string(org.hamcrest.Matchers.containsString("Salle non trouvée")));
    }

    // ════════════════════════════════════════════════════════════════════════════════
    // ✅ TESTS POST CREATE
    // ════════════════════════════════════════════════════════════════════════════════

    @Test
    @WithMockUser(roles = "USER")
    @DisplayName("✅ POST /salle → Création réussie (201)")
    void createSalle_success() throws Exception {
        when(salleService.save(any(SalleDTO.class))).thenReturn(salleDTO);

        mockMvc.perform(post("/salle")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(salleDTO)))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.codeSalle").value("S001"))
                .andExpect(jsonPath("$.statutSalle").value("LIBRE"));
    }

    // ════════════════════════════════════════════════════════════════════════════════
    // ✅ TESTS PUT UPDATE
    // ════════════════════════════════════════════════════════════════════════════════

    @Test
    @WithMockUser(roles = "USER")
    @DisplayName("✅ PUT /salle/{id} → Mise à jour réussie (200)")
    void updateSalle_success() throws Exception {
        SalleDTO updatedSalle = new SalleDTO();
        updatedSalle.setCodeSalle("S001");
        updatedSalle.setContenace(100);
        updatedSalle.setDescSalle("Salle mise à jour");
        updatedSalle.setStatutSalle(SalleStatus.OCCUPE);

        when(salleService.update(eq("S001"), any(SalleDTO.class))).thenReturn(updatedSalle);

        mockMvc.perform(put("/salle/S001")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(updatedSalle)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.contenace").value(100))
                .andExpect(jsonPath("$.descSalle").value("Salle mise à jour"));
    }

    @Test
    @WithMockUser(roles = "USER")
    @DisplayName("❌ PUT /salle/{id} → Salle inexistante (400)")
    void updateSalle_nonExisting_badRequest() throws Exception {
        SalleDTO testSalle = new SalleDTO();
        testSalle.setCodeSalle("S999");
        testSalle.setContenace(10);

        when(salleService.update(eq("S999"), any(SalleDTO.class)))
                .thenThrow(new RuntimeException("Salle introuvable"));

        mockMvc.perform(put("/salle/S999")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(testSalle)))
                .andExpect(status().isBadRequest())
                .andExpect(content().string(org.hamcrest.Matchers.containsString("Salle introuvable")));
    }

    // ════════════════════════════════════════════════════════════════════════════════
    // ✅ TESTS DELETE
    // ════════════════════════════════════════════════════════════════════════════════

    @Test
    @WithMockUser(roles = "USER")
    @DisplayName("✅ DELETE /salle/{id} → Suppression réussie (200)")
    void deleteSalle_success() throws Exception {
        doNothing().when(salleService).delete("S001");

        mockMvc.perform(delete("/salle/S001"))
                .andExpect(status().isOk());
    }

    @Test
    @WithMockUser(roles = "USER")
    @DisplayName("❌ DELETE /salle/{id} → Salle inexistante (400)")
    void deleteSalle_nonExisting_badRequest() throws Exception {
        doThrow(new RuntimeException("Salle inexistante")).when(salleService).delete("S999");

        mockMvc.perform(delete("/salle/S999"))
                .andExpect(status().isBadRequest())
                .andExpect(content().string(org.hamcrest.Matchers.containsString("Salle inexistante")));
    }
}
