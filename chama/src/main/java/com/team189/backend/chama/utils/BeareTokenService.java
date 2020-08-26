
package com.team189.backend.chama.utils;

import java.util.Arrays;
import java.util.Base64;
import java.util.LinkedHashSet;
import java.util.Set;
import javax.annotation.PostConstruct;
import org.json.JSONException;
import org.json.JSONObject;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.env.Environment;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.client.RestClientException;
import org.springframework.web.client.RestTemplate;

/**
 *
 * @author Moha
 */
@Service
@Transactional
public class BeareTokenService {

    private static final Logger LOG = LoggerFactory.getLogger(BeareTokenService.class.getSimpleName());
    public static String token = "";
    private static final Set<String> _safaricomPrefixes = new LinkedHashSet<>();

    @Autowired
    Environment env;
    
    @PostConstruct
    public void init() {
        try {
            for (int i = 254700; i < 254730; i++) {
                _safaricomPrefixes.add(String.valueOf(i));
            }
            _safaricomPrefixes.add("254790");
            _safaricomPrefixes.add("254791");
            _safaricomPrefixes.add("254792");
            autoSetOauthBearerCode();
            LOG.info("BearerTokenService init token={}", token);
        } catch (Exception e) {
            LOG.error("Error when initializing BearerTokenService for token fetch={}", e);
        }
    }

    public static boolean isSafaricomNumber(String phoneNo) {
        return _safaricomPrefixes.contains(phoneNo.substring(0, 6));
    }

    @Scheduled(initialDelay = 3000, fixedDelay = 1000 * 60 * 30)
    public void autoSetOauthBearerCode() {
        try {
            String accessToken = generateAccessToken(env);
            token = accessToken;
            LOG.info("Refreshed token={}", token);
        } catch (Exception e) {
            LOG.error("Error fetching token ={}", e);
        }
    }

    private String generateAccessToken(Environment env) {

        String accessToken = "";
        try {
            RestTemplate restTemplate = new RestTemplate();
            String authUrl = env.getProperty("stk_bearer_auth_url");
            String customerKey = env.getProperty("mpesa_consumer_key");
            String customerSecret = env.getProperty("consumer_secret");

            String encryptByte = customerKey + ":" + customerSecret;
            // encode with padding
            String encoded = Base64.getEncoder().encodeToString(encryptByte.getBytes());
            String authorizationString = "Basic " + encoded;

            HttpHeaders headers = new HttpHeaders();
            headers.setAccept(Arrays.asList(MediaType.APPLICATION_JSON));
            headers.setContentType(MediaType.APPLICATION_FORM_URLENCODED);
            headers.add("Authorization", authorizationString);
            headers.add("Access-Control-Allow-Origin", "*");

            HttpEntity<?> requestEntity = new HttpEntity<>(headers);
            LOG.info("auth url: " + authUrl + ", request : " + requestEntity.toString());

            ResponseEntity<String> response = restTemplate.exchange(authUrl, HttpMethod.GET, requestEntity, String.class);
            JSONObject authResponse = new JSONObject(response.getBody());
            LOG.info("authJsonResponse >>> " + authResponse);

            if (authResponse.toString().toUpperCase().contains("ACCESS")) {
                accessToken = authResponse.getString("access_token");
                LOG.info("access_token : " + accessToken);
                String expires_in = authResponse.getString("expires_in");
                LOG.info("expires_in : " + expires_in);
            }
        } catch (RestClientException | JSONException e) {
            LOG.info("Encountered error in bearer authentication : " + e.getMessage());
        }
        return accessToken;
    }

}