package com.aerofix.api.dto;

import com.aerofix.api.model.Mecanico;
import org.springframework.stereotype.Component;

@Component
public class MecanicoMapper {

    public MecanicoDTO toDTO(Mecanico entity) {
        if (entity == null) return null;

        MecanicoDTO dto = new MecanicoDTO();
        dto.setLicenciaId(entity.getLicenciaId());
        dto.setNombre(entity.getNombre());
        dto.setNivelExperiencia(entity.getNivelExperiencia());
        dto.setSalarioHora(entity.getSalarioHora());
        dto.setDisponible(entity.isDisponible());
        dto.setFechaContratacion(entity.getFechaContratacion());

        if (entity.getMantenimientosAsignados() != null) {
            dto.setTotalMantenimientosAsignados(entity.getMantenimientosAsignados().size());
        } else {
            dto.setTotalMantenimientosAsignados(0);
        }

        return dto;
    }
}