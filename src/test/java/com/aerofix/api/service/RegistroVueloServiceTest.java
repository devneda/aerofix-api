package com.aerofix.api.service;

import com.aerofix.api.dto.RegistroVueloDTO;
import com.aerofix.api.exception.VueloNotFoundException;
import com.aerofix.api.model.Avion;
import com.aerofix.api.model.RegistroVuelo;
import com.aerofix.api.repository.AvionRepository;
import com.aerofix.api.repository.RegistroVueloRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.modelmapper.ModelMapper;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class RegistroVueloServiceTest {

    @Mock
    private RegistroVueloRepository registroVueloRepository;

    @Mock
    private AvionRepository avionRepository;

    @Mock
    private ModelMapper modelMapper;

    @InjectMocks
    private RegistroVueloService registroVueloService;

    @Test
    void testGuardar_Exito() {

        RegistroVuelo vueloEntrada = new RegistroVuelo();
        Avion avionMock = new Avion();
        avionMock.setMatricula("EC-123");
        vueloEntrada.setAvion(avionMock);

        RegistroVueloDTO dtoEsperado = new RegistroVueloDTO();
        dtoEsperado.setCodigoVuelo("IB-001");

        when(avionRepository.findById("EC-123")).thenReturn(Optional.of(avionMock));
        when(registroVueloRepository.save(any(RegistroVuelo.class))).thenReturn(vueloEntrada);
        when(modelMapper.map(any(), eq(RegistroVueloDTO.class))).thenReturn(dtoEsperado);

        RegistroVueloDTO resultado = registroVueloService.guardar(vueloEntrada);

        assertNotNull(resultado);
        assertEquals("IB-001", resultado.getCodigoVuelo());
        verify(avionRepository).findById("EC-123");
    }

    @Test
    void testGuardar_AvionNoExiste_LanzaExcepcion() {

        RegistroVuelo vueloEntrada = new RegistroVuelo();
        Avion avionMock = new Avion();
        avionMock.setMatricula("EC-FANTASMA");
        vueloEntrada.setAvion(avionMock);

        when(avionRepository.findById("EC-FANTASMA")).thenReturn(Optional.empty());

        assertThrows(VueloNotFoundException.class, () -> {
            registroVueloService.guardar(vueloEntrada);
        });
    }

    @Test
    void testObtenerPorId_NoExiste_LanzaExcepcion() {
        when(registroVueloRepository.findById(99L)).thenReturn(Optional.empty());

        assertThrows(VueloNotFoundException.class, () -> {
            registroVueloService.obtenerPorId(99L);
        });
    }
}