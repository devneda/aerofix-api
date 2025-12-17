package com.aerofix.api.dto;

import com.aerofix.api.model.RegistroVuelo;
import org.springframework.stereotype.Component;

@Component
public class RegistroVueloMapper {

    public RegistroVueloDTO toDTO(RegistroVuelo entity) {
        if (entity == null) return null;

        RegistroVueloDTO dto = new RegistroVueloDTO();
        dto.setId(entity.getId());
        dto.setCodigoVuelo(entity.getCodigoVuelo());
        dto.setOrigenDestino(entity.getOrigenDestino());
        dto.setDistanciaKm(entity.getDistanciaKm());
        dto.setCombustibleConsumido(entity.getCombustibleConsumido());
        dto.setIncidenciasReportadas(entity.isIncidenciasReportadas());
        dto.setFechaVuelo(entity.getFechaVuelo());

        // Extraemos la matrícula del objeto avión relacionado
        if (entity.getAvion() != null) {
            dto.setMatriculaAvion(entity.getAvion().getMatricula());
        }

        return dto;
    }
}