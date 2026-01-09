package com.aerofix.api.service;

import com.aerofix.api.dto.AvionDTO;
import com.aerofix.api.exception.AvionNotFoundException;
import com.aerofix.api.model.Avion;
import com.aerofix.api.repository.AvionRepository;
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
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class AvionServiceTest {

    @Mock
    private AvionRepository avionRepository;

    @Mock
    private ModelMapper modelMapper;

    @InjectMocks
    private AvionService avionService;

    @Test
    void guardarAvion_Exito() {
        Avion avion = new Avion();
        avion.setMatricula("EC-999");
        AvionDTO dto = new AvionDTO();
        dto.setMatricula("EC-999");

        when(avionRepository.save(any(Avion.class))).thenReturn(avion);
        when(modelMapper.map(any(), eq(AvionDTO.class))).thenReturn(dto);

        AvionDTO result = avionService.guardarAvion(avion);

        assertNotNull(result);
        assertEquals("EC-999", result.getMatricula());
    }

    @Test
    void obtenerPorId_NoExiste_LanzaExcepcion() {
        when(avionRepository.findById("EC-INEXISTENTE")).thenReturn(Optional.empty());

        assertThrows(AvionNotFoundException.class, () -> avionService.obtenerPorId("EC-INEXISTENTE"));
    }
}