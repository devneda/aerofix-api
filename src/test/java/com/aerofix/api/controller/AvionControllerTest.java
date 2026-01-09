package com.aerofix.api.controller;

import com.aerofix.api.dto.AvionDTO;
import com.aerofix.api.exception.AvionNotFoundException;
import com.aerofix.api.model.Avion;
import com.aerofix.api.service.AvionService;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.test.context.bean.override.mockito.MockitoBean; // La nueva importación
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import java.util.List;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@WebMvcTest(AvionController.class)
class AvionControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockitoBean
    private AvionService avionService;

    @Autowired
    private ObjectMapper objectMapper;

    @Test
    void obtenerAviones_Ok() throws Exception {
        when(avionService.buscarAviones(null, null, null))
                .thenReturn(List.of(new AvionDTO()));

        mockMvc.perform(get("/api/aviones"))
                .andExpect(status().isOk());
    }

    @Test
    void obtenerPorId_NoEncontrado_404() throws Exception {
        when(avionService.obtenerPorId("EC-FAIL"))
                .thenThrow(new AvionNotFoundException("No existe"));

        mockMvc.perform(get("/api/aviones/EC-FAIL"))
                .andExpect(status().isNotFound());
    }

    @Test
    void guardarAvion_Invalido_400() throws Exception {
        Avion avionInvalido = new Avion(); // Sin matrícula obligatoria

        mockMvc.perform(post("/api/aviones")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(avionInvalido)))
                .andExpect(status().isBadRequest());
    }
}