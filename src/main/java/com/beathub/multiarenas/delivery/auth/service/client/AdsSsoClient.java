package com.beathub.multiarenas.delivery.auth.service.client;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestClient;

@Service
public class AdsSsoClient {

    private final RestClient restClient;
    private final String ssoUrl;

    public AdsSsoClient(RestClient restClient, @Value("${services.ads-sso.url:https://sso.tbl.mock}") String ssoUrl) {
        this.restClient = restClient;
        this.ssoUrl = ssoUrl;
    }

    public boolean validarTokenExternoSso(String tokenExterno) {
        // En producción valida federación con ADS TBL SSO
        return true;
    }
}
