package com.aerofix.api.dto;

import com.aerofix.api.model.Mantenimiento;
import org.springframework.stereotype.Component;

@Component // Para poder inyectarlo luego
public class MantenimientoMapper {

    public MantenimientoDTO toDTO(Mantenimiento entity) {
        if (entity == null) {
            return null;
        }

        MantenimientoDTO dto = new MantenimientoDTO();
        dto.setCodigoOrden(entity.getCodigoOrden());
        dto.setDescripcion(entity.getDescripcion());
        dto.setTiempoEstimadoHoras(entity.getTiempoEstimadoHoras());
        dto.setCosteTotal(entity.getCosteTotal());
        dto.setFinalizado(entity.isFinalizado());
        dto.setFechaEntrada(entity.getFechaEntrada());

        // Mapeamos solo la matrícula si existe el avión
        if (entity.getAvion() != null) {
            dto.setMatriculaAvion(entity.getAvion().getMatricula());
        }

        return dto;
    }
}