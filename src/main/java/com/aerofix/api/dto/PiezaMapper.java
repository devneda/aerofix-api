package com.aerofix.api.dto;

import com.aerofix.api.model.Pieza;
import org.springframework.stereotype.Component;

@Component
public class PiezaMapper {

    public PiezaDTO toDTO(Pieza entity) {
        if (entity == null) return null;

        PiezaDTO dto = new PiezaDTO();
        dto.setId(entity.getId());
        dto.setReferencia(entity.getReferencia());
        dto.setNombre(entity.getNombre());
        dto.setStock(entity.getStock());
        dto.setPrecioUnitario(entity.getPrecioUnitario());
        dto.setEsCritica(entity.isEsCritica());
        dto.setFechaUltimaRevision(entity.getFechaUltimaRevision());

        if (entity.getMantenimientosDondeSeUso() != null) {
            dto.setPiezasEmpleadas(entity.getMantenimientosDondeSeUso().size());
        } else {
            dto.setPiezasEmpleadas(0);
        }

        return dto;
    }
}