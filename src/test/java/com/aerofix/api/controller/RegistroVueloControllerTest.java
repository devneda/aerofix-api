package com.aerofix.api.controller;

import com.aerofix.api.dto.RegistroVueloDTO;
import com.aerofix.api.exception.VueloNotFoundException;
import com.aerofix.api.model.RegistroVuelo;
import com.aerofix.api.service.RegistroVueloService;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.test.context.bean.override.mockito.MockitoBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import java.util.List;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@WebMvcTest(RegistroVueloController.class)
class RegistroVueloControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockitoBean
    private RegistroVueloService registroVueloService;

    @Autowired
    private ObjectMapper objectMapper;

    // CASO 1: Éxito (200 OK)
    @Test
    void obtenerTodos_DeberiaDevolverListaY200() throws Exception {
        when(registroVueloService.buscarVuelos(null, null, null))
                .thenReturn(List.of(new RegistroVueloDTO(), new RegistroVueloDTO()));

        mockMvc.perform(get("/api/vuelos"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.size()").value(2));
    }

    // CASO 2: Error 404 (Not Found)
    @Test
    void obtenerPorId_NoExiste_DeberiaDevolver404() throws Exception {
        // Simulamos que el servicio lanza la excepción
        when(registroVueloService.obtenerPorId(999L)).thenThrow(new VueloNotFoundException("No existe"));

        mockMvc.perform(get("/api/vuelos/999"))
                .andExpect(status().isNotFound()); // Verifica que sea 404
    }

    // CASO 3: Error 400 (Bad Request - Validación)
    @Test
    void guardar_DatosInvalidos_DeberiaDevolver400() throws Exception {
        RegistroVuelo vueloInvalido = new RegistroVuelo();
        // No le ponemos matrícula ni datos obligatorios, así que @Valid fallará

        mockMvc.perform(post("/api/vuelos")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(vueloInvalido)))
                .andExpect(status().isBadRequest());
    }

    // CASO 4: Éxito Crear (201 Created)
    @Test
    void guardar_Exito_DeberiaDevolver201() throws Exception {
        RegistroVueloDTO dtoSimulado = new RegistroVueloDTO();
        dtoSimulado.setCodigoVuelo("IB-TEST");

        // Simular respuesta del servicio
        when(registroVueloService.guardar(any(RegistroVuelo.class))).thenReturn(dtoSimulado);

        String jsonValido = """
            {
                "codigoVuelo": "IB-TEST",
                "origenDestino": "MAD-BCN",
                "avion": { "matricula": "EC-123" }
            }
        """;

        mockMvc.perform(post("/api/vuelos")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(jsonValido))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.codigoVuelo").value("IB-TEST"));
    }
}