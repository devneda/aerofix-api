package com.aerofix.api.dto;

import com.aerofix.api.model.Mecanico;
import org.springframework.stereotype.Component;

@Component
public class MecanicoMapper {

    public MecanicoDTO toDTO(Mecanico entity) {
        if (entity == null) {
            return null;
        }

        MecanicoDTO dto = new MecanicoDTO();

        // Asignación directa de tus campos reales
        dto.setId(entity.getId());
        dto.setLicenciaId(entity.getLicenciaId());
        dto.setNombre(entity.getNombre());
        dto.setNivelExperiencia(entity.getNivelExperiencia());
        dto.setSalarioHora(entity.getSalarioHora());
        dto.setDisponible(entity.isDisponible()); // Lombok usa 'is' para booleans
        dto.setFechaContratacion(entity.getFechaContratacion());

        // Calculamos el número de tareas (seguro y útil)
        if (entity.getMantenimientosAsignados() != null) {
            dto.setNumeroMantenimientosAsignados(entity.getMantenimientosAsignados().size());
        } else {
            dto.setNumeroMantenimientosAsignados(0);
        }

        return dto;
    }
}