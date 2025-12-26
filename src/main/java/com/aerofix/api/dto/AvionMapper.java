package com.aerofix.api.dto;

import com.aerofix.api.model.Avion;
import org.springframework.stereotype.Component;

@Component
public class AvionMapper {

    public AvionDTO toDTO(Avion entity) {
        if (entity == null) return null;

        AvionDTO dto = new AvionDTO();
        dto.setMatricula(entity.getMatricula());
        dto.setModelo(entity.getModelo());
        dto.setCapacidadPasajeros(entity.getCapacidadPasajeros());
        dto.setHorasVuelo(entity.getHorasVuelo());
        dto.setEnServicio(entity.isEnServicio());
        dto.setFechaFabricacion(entity.getFechaFabricacion());

        // Calculamos totales protegiendo contra null
        if (entity.getMantenimientos() != null) {
            dto.setTotalMantenimientos(entity.getMantenimientos().size());
        } else {
            dto.setTotalMantenimientos(0);
        }

        if (entity.getVuelos() != null) {
            dto.setTotalVuelos(entity.getVuelos().size());
        } else {
            dto.setTotalVuelos(0);
        }

        return dto;
    }
}