package com.aerofix.api.config;

import com.aerofix.api.model.*;
import com.aerofix.api.repository.*;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.Arrays;
import java.util.List;

@Component
public class DataSeeder implements CommandLineRunner {

    private final AvionRepository avionRepository;
    private final PiezaRepository piezaRepository;
    private final MecanicoRepository mecanicoRepository;
    private final MantenimientoRepository mantenimientoRepository;

    public DataSeeder(AvionRepository avionRepository,
                      PiezaRepository piezaRepository,
                      MecanicoRepository mecanicoRepository,
                      MantenimientoRepository mantenimientoRepository) {
        this.avionRepository = avionRepository;
        this.piezaRepository = piezaRepository;
        this.mecanicoRepository = mecanicoRepository;
        this.mantenimientoRepository = mantenimientoRepository;
    }

    @Override
    @Transactional
    public void run(String... args) throws Exception {
        // Verificamos si ya hay datos para no duplicar al reiniciar
        if (avionRepository.count() > 0) {
            System.out.println(">>> La base de datos ya tiene datos. Omitiendo carga inicial.");
            return;
        }

        System.out.println(">>> Iniciando carga de datos de prueba...");

        // 1. CREAR AVIONES
        Avion avion1 = new Avion();
        avion1.setMatricula("EC-1234");
        avion1.setModelo("Boeing 737");
        avion1.setCapacidadPasajeros(180);
        avion1.setHorasVuelo(1200.5f);
        avion1.setEnServicio(true);
        avion1.setFechaFabricacion(LocalDate.of(2015, 5, 20));

        Avion avion2 = new Avion();
        avion2.setMatricula("EC-5678");
        avion2.setModelo("Airbus A320");
        avion2.setCapacidadPasajeros(150);
        avion2.setHorasVuelo(5000.0f);
        avion2.setEnServicio(false);
        avion2.setFechaFabricacion(LocalDate.of(2010, 8, 15));

        avionRepository.saveAll(Arrays.asList(avion1, avion2));

        // 2. CREAR MECÁNICOS
        Mecanico mec1 = new Mecanico();
        mec1.setLicenciaId("LIC-001");
        mec1.setNombre("Roberto García");
        mec1.setNivelExperiencia(5);
        mec1.setSalarioHora(35.50f);
        mec1.setDisponible(true);
        mec1.setFechaContratacion(java.sql.Date.valueOf("2018-01-10"));

        Mecanico mec2 = new Mecanico();
        mec2.setLicenciaId("LIC-002");
        mec2.setNombre("Ana Miller");
        mec2.setNivelExperiencia(3);
        mec2.setSalarioHora(28.00f);
        mec2.setDisponible(false);
        mec2.setFechaContratacion(java.sql.Date.valueOf("2021-03-15"));

        mecanicoRepository.saveAll(Arrays.asList(mec1, mec2));

        // 3. CREAR PIEZAS
        Pieza p1 = new Pieza();
        p1.setReferencia("TURB-X99");
        p1.setNombre("Álabe de Turbina");
        p1.setStock(50);
        p1.setPrecioUnitario(1200.00f);
        p1.setEsCritica(true);
        p1.setFechaUltimaRevision(LocalDate.now().minusMonths(1));

        Pieza p2 = new Pieza();
        p2.setReferencia("TORN-GEN");
        p2.setNombre("Tornillo Genérico Acero");
        p2.setStock(5000);
        p2.setPrecioUnitario(0.50f);
        p2.setEsCritica(false);
        p2.setFechaUltimaRevision(LocalDate.now().minusMonths(6));

        piezaRepository.saveAll(Arrays.asList(p1, p2));

        // 4. CREAR MANTENIMIENTOS (Relacionando todo)
        Mantenimiento mant1 = new Mantenimiento();
        mant1.setCodigoOrden("ORD-2025-001");
        mant1.setDescripcion("Revisión rutinaria de motor");
        mant1.setTiempoEstimadoHoras(8);
        mant1.setCosteTotal(2500.0f);
        mant1.setFinalizado(false);
        mant1.setFechaEntrada(LocalDateTime.now().minusDays(2));

        // Relaciones
        mant1.setAvion(avion2);
        mant1.setMecanico(mec1);
        mant1.setPiezasEmpleadas(List.of(p1, p2));

        mantenimientoRepository.save(mant1);

        System.out.println(">>> Carga de datos completada con éxito.");
    }
}