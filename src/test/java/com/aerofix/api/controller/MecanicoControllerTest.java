package com.aerofix.api.controller;

import com.aerofix.api.dto.MecanicoDTO;
import com.aerofix.api.exception.MecanicoNotFoundException;
import com.aerofix.api.model.Mecanico;
import com.aerofix.api.service.MecanicoService;
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

@WebMvcTest(MecanicoController.class)
class MecanicoControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockitoBean
    private MecanicoService mecanicoService;

    @Autowired
    private ObjectMapper objectMapper;

    @Test
    void listarMecanicos_Ok() throws Exception {
        when(mecanicoService.buscarMecanicos(null, null, null)).thenReturn(List.of(new MecanicoDTO()));

        mockMvc.perform(get("/api/mecanicos"))
                .andExpect(status().isOk());
    }

    @Test
    void guardarMecanico_Invalido_400() throws Exception {
        Mecanico invalido = new Mecanico(); // Sin nombre, licencia, etc.

        mockMvc.perform(post("/api/mecanicos")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(invalido)))
                .andExpect(status().isBadRequest());
    }

    @Test
    void obtenerPorId_NoExiste_404() throws Exception {
        when(mecanicoService.obtenerPorId(99L)).thenThrow(new MecanicoNotFoundException("N/A"));

        mockMvc.perform(get("/api/mecanicos/99"))
                .andExpect(status().isNotFound());
    }
}