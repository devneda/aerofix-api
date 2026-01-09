package com.aerofix.api.controller;

import com.aerofix.api.dto.MantenimientoDTO;
import com.aerofix.api.exception.MantenimientoNotFoundException;
import com.aerofix.api.model.Mantenimiento;
import com.aerofix.api.service.MantenimientoService;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.test.context.bean.override.mockito.MockitoBean; // Importación correcta
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import java.util.List;

import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@WebMvcTest(MantenimientoController.class)
class MantenimientoControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockitoBean
    private MantenimientoService mantenimientoService;

    @Autowired
    private ObjectMapper objectMapper;

    @Test
    void listarMantenimientos_Ok() throws Exception {
        when(mantenimientoService.buscarMantenimientos(null, null, null))
                .thenReturn(List.of(new MantenimientoDTO()));

        mockMvc.perform(get("/api/mantenimientos"))
                .andExpect(status().isOk());
    }

    @Test
    void guardar_DatosInvalidos_400() throws Exception {
        Mantenimiento mantInvalido = new Mantenimiento();
        // Se envía vacío para que salte @Valid (asumiendo que tiene @NotNull en codigoOrden o descripcion)

        mockMvc.perform(post("/api/mantenimientos")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(mantInvalido)))
                .andExpect(status().isBadRequest());
    }

    @Test
    void obtenerPorId_NoExiste_404() throws Exception {
        when(mantenimientoService.obtenerPorId("ORD-999"))
                .thenThrow(new MantenimientoNotFoundException("No existe"));

        mockMvc.perform(get("/api/mantenimientos/ORD-999"))
                .andExpect(status().isNotFound());
    }
}