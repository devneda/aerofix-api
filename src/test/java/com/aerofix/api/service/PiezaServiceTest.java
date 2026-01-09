package com.aerofix.api.service;

import com.aerofix.api.dto.PiezaDTO;
import com.aerofix.api.exception.PiezaNotFoundException;
import com.aerofix.api.model.Pieza;
import com.aerofix.api.repository.PiezaRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.modelmapper.ModelMapper;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class PiezaServiceTest {

    @Mock
    private PiezaRepository piezaRepository;

    @Mock
    private ModelMapper modelMapper;

    @InjectMocks
    private PiezaService piezaService;

    @Test
    void guardarPieza_Exito() {
        Pieza pieza = new Pieza();
        pieza.setNombre("Tornillo");
        PiezaDTO dto = new PiezaDTO();
        dto.setNombre("Tornillo");

        when(piezaRepository.save(any(Pieza.class))).thenReturn(pieza);
        when(modelMapper.map(any(), eq(PiezaDTO.class))).thenReturn(dto);

        PiezaDTO resultado = piezaService.guardarPieza(pieza);
        assertNotNull(resultado);
        assertEquals("Tornillo", resultado.getNombre());
    }

    @Test
    void obtenerPorId_NoExiste_LanzaExcepcion() {
        when(piezaRepository.findById(99L)).thenReturn(Optional.empty());
        assertThrows(PiezaNotFoundException.class, () -> piezaService.obtenerPorId(99L));
    }
}