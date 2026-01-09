package com.aerofix.api.service;

import com.github.tomakehurst.wiremock.WireMockServer;
import com.github.tomakehurst.wiremock.client.WireMock;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import static com.github.tomakehurst.wiremock.client.WireMock.*;
import static org.junit.jupiter.api.Assertions.*;

class ProveedorServiceWiremockTest {

    private WireMockServer wireMockServer;
    private ProveedorService proveedorService;

    @BeforeEach
    void setUp() {
        // 1. Arrancamos Wiremock en un puerto dinámico (o fijo, ej: 8089)
        wireMockServer = new WireMockServer(8089);
        wireMockServer.start();

        // Configuración básica
        WireMock.configureFor("localhost", 8089);

        // 2. Inicializamos nuestro servicio real
        proveedorService = new ProveedorService();
        // Le decimos al servicio que apunte a nuestro servidor falso
        proveedorService.setBaseUrl("http://localhost:8089");
    }

    @AfterEach
    void tearDown() {
        // Apagamos el servidor después de cada test
        wireMockServer.stop();
    }

    @Test
    void consultarStock_DeberiaDevolverTrue_CuandoWiremockRespondeTrue() {
        // GIVEN: "Entrenamos" a Wiremock
        // Le decimos: "Si alguien llama a GET /api/stock/TURB-99, respóndele 'true' con estado 200"
        stubFor(get(urlEqualTo("/api/stock/TURB-99"))
                .willReturn(aResponse()
                        .withHeader("Content-Type", "text/plain")
                        .withStatus(200)
                        .withBody("true")));

        // WHEN: Ejecutamos nuestro servicio real
        boolean hayStock = proveedorService.consultarStockExterno("TURB-99");

        // THEN: Verificamos
        assertTrue(hayStock, "El servicio debería devolver true porque Wiremock devolvió true");

        // Verificamos que Wiremock recibió la petición
        verify(getRequestedFor(urlEqualTo("/api/stock/TURB-99")));
    }

    @Test
    void consultarStock_DeberiaDevolverFalse_CuandoWiremockDevuelve404() {
        // GIVEN: Simulamos que la pieza no existe en el proveedor
        stubFor(get(urlEqualTo("/api/stock/INEXISTENTE"))
                .willReturn(aResponse()
                        .withStatus(404)));

        // WHEN
        boolean hayStock = proveedorService.consultarStockExterno("INEXISTENTE");

        // THEN
        assertFalse(hayStock, "Debería devolver false si el API externo falla o da 404");
    }
}