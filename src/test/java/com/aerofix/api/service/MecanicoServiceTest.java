package com.aerofix.api.service;

import com.aerofix.api.dto.MecanicoDTO;
import com.aerofix.api.exception.MecanicoNotFoundException;
import com.aerofix.api.model.Mecanico;
import com.aerofix.api.repository.MecanicoRepository;
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
class MecanicoServiceTest {

    @Mock
    private MecanicoRepository mecanicoRepository;

    @Mock
    private ModelMapper modelMapper;

    @InjectMocks
    private MecanicoService mecanicoService;

    @Test
    void guardarMecanico_Exito() {
        Mecanico mec = new Mecanico();
        mec.setNombre("Luis");
        MecanicoDTO dto = new MecanicoDTO();
        dto.setNombre("Luis");

        when(mecanicoRepository.save(any(Mecanico.class))).thenReturn(mec);
        when(modelMapper.map(any(), eq(MecanicoDTO.class))).thenReturn(dto);

        MecanicoDTO result = mecanicoService.guardarMecanico(mec);
        assertNotNull(result);
        assertEquals("Luis", result.getNombre());
    }

    @Test
    void obtenerPorId_NoExiste_LanzaExcepcion() {
        when(mecanicoRepository.findById(1L)).thenReturn(Optional.empty());
        assertThrows(MecanicoNotFoundException.class, () -> mecanicoService.obtenerPorId(1L));
    }
}