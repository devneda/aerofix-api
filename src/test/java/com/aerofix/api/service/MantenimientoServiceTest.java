package com.aerofix.api.service;

import com.aerofix.api.dto.MantenimientoDTO;
import com.aerofix.api.exception.MantenimientoNotFoundException;
import com.aerofix.api.model.Mantenimiento;
import com.aerofix.api.repository.MantenimientoRepository;
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
class MantenimientoServiceTest {

    @Mock
    private MantenimientoRepository mantenimientoRepository;

    @Mock
    private ModelMapper modelMapper;

    @InjectMocks
    private MantenimientoService mantenimientoService;

    @Test
    void guardarMantenimiento_Exito() {
        Mantenimiento mant = new Mantenimiento();
        mant.setCodigoOrden("ORD-2025");
        MantenimientoDTO dto = new MantenimientoDTO();
        dto.setCodigoOrden("ORD-2025");

        when(mantenimientoRepository.save(any(Mantenimiento.class))).thenReturn(mant);
        when(modelMapper.map(any(), eq(MantenimientoDTO.class))).thenReturn(dto);

        MantenimientoDTO resultado = mantenimientoService.guardarMantenimiento(mant);

        assertNotNull(resultado);
        assertEquals("ORD-2025", resultado.getCodigoOrden());
    }

    @Test
    void obtenerPorId_NoExiste_LanzaExcepcion() {
        when(mantenimientoRepository.findById("ORD-FAIL")).thenReturn(Optional.empty());

        assertThrows(MantenimientoNotFoundException.class,
                () -> mantenimientoService.obtenerPorId("ORD-FAIL"));
    }
}