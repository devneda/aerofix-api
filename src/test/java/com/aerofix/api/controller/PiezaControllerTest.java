package com.aerofix.api.controller;

import com.aerofix.api.dto.PiezaDTO;
import com.aerofix.api.exception.PiezaNotFoundException;
import com.aerofix.api.model.Pieza;
import com.aerofix.api.service.PiezaService;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.test.context.bean.override.mockito.MockitoBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import java.util.List;

import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@WebMvcTest(PiezaController.class)
class PiezaControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockitoBean
    private PiezaService piezaService;

    @Autowired
    private ObjectMapper objectMapper;

    @Test
    void obtenerPiezas_Ok() throws Exception {
        when(piezaService.buscarPiezas(null, null, null)).thenReturn(List.of(new PiezaDTO()));

        mockMvc.perform(get("/api/piezas"))
                .andExpect(status().isOk());
    }

    @Test
    void guardarPieza_Invalida_400() throws Exception {
        // Objeto vacío fallará validaciones @NotBlank de Pieza
        Pieza piezaMala = new Pieza();

        mockMvc.perform(post("/api/piezas")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(piezaMala)))
                .andExpect(status().isBadRequest());
    }

    @Test
    void obtenerPorId_NoExiste_404() throws Exception {
        when(piezaService.obtenerPorId(500L)).thenThrow(new PiezaNotFoundException("No existe"));

        mockMvc.perform(get("/api/piezas/500"))
                .andExpect(status().isNotFound());
    }
}