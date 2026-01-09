package com.aerofix.api.service;

import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;
import org.springframework.beans.factory.annotation.Value;

@Service
public class ProveedorService {

    private final RestTemplate restTemplate;

    // Inyectamos la URL base. Por defecto apunta a localhost,
    // pero en el test la sobreescribiremos para que apunte al servidor Mock.
    private String baseUrl;

    public ProveedorService() {
        this.restTemplate = new RestTemplate();
        this.baseUrl = "http://api-proveedor-real.com"; // URL ficticia
    }

    // Método para permitir cambiar la URL desde el test (Setter)
    public void setBaseUrl(String baseUrl) {
        this.baseUrl = baseUrl;
    }

    public boolean consultarStockExterno(String referencia) {
        // Hacemos una petición GET a: http://.../api/stock/{referencia}
        String url = baseUrl + "/api/stock/" + referencia;

        // Esperamos una respuesta simple true/false o un JSON.
        // Para simplificar, asumiremos que devuelve un String "true" o "false".
        try {
            String respuesta = restTemplate.getForObject(url, String.class);
            return Boolean.parseBoolean(respuesta);
        } catch (Exception e) {
            // Si falla la conexión, asumimos que no hay stock
            return false;
        }
    }
}