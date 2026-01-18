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

        wireMockServer = new WireMockServer(8089);
        wireMockServer.start();

        WireMock.configureFor("localhost", 8089);

        proveedorService = new ProveedorService();
        proveedorService.setBaseUrl("http://localhost:8089");
    }

    @AfterEach
    void tearDown() {
        wireMockServer.stop();
    }

    @Test
    void consultarStock_DeberiaDevolverTrue_CuandoWiremockRespondeTrue() {

        stubFor(get(urlEqualTo("/api/stock/TURB-99"))
                .willReturn(aResponse()
                        .withHeader("Content-Type", "text/plain")
                        .withStatus(200)
                        .withBody("true")));

        // WHEN: Ejecutamos el servicio real
        boolean hayStock = proveedorService.consultarStockExterno("TURB-99");

        // THEN: Verificamos
        assertTrue(hayStock, "El servicio debería devolver true porque Wiremock devolvió true");

        // Verificamos que Wiremock recibió la petición
        verify(getRequestedFor(urlEqualTo("/api/stock/TURB-99")));
    }

    @Test
    void consultarStock_DeberiaDevolverFalse_CuandoWiremockDevuelve404() {

        stubFor(get(urlEqualTo("/api/stock/INEXISTENTE"))
                .willReturn(aResponse()
                        .withStatus(404)));

        // WHEN
        boolean hayStock = proveedorService.consultarStockExterno("INEXISTENTE");

        // THEN
        assertFalse(hayStock, "Debería devolver false si el API externo falla o da 404");
    }
}