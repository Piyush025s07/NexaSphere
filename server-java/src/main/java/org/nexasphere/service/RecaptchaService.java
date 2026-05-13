package org.nexasphere.service;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.client.RestTemplate;

import java.util.Map;

@Service
public class RecaptchaService {

    private static final String VERIFY_URL =
            "https://www.google.com/recaptcha/api/siteverify";
    private static final double MIN_SCORE = 0.5;

    @Value("${recaptcha.secret-key}")
    private String secretKey;

    private final RestTemplate restTemplate = new RestTemplate();

    public void verify(String token, String action) {
        MultiValueMap<String, String> params = new LinkedMultiValueMap<>();
        params.add("secret", secretKey);
        params.add("response", token);

        @SuppressWarnings("unchecked")
        ResponseEntity<Map> response =
                restTemplate.postForEntity(VERIFY_URL, params, Map.class);

        Map<?, ?> body = response.getBody();
        if (body == null || !Boolean.TRUE.equals(body.get("success"))) {
            throw new RecaptchaException("reCAPTCHA verification failed");
        }

        double score = body.get("score") != null
                ? ((Number) body.get("score")).doubleValue()
                : 0.0;

        if (score < MIN_SCORE) {
            throw new RecaptchaException(
                    String.format("reCAPTCHA score too low (%.2f). Submission rejected.", score));
        }

        if (action != null && !action.equals(body.get("action"))) {
            throw new RecaptchaException("reCAPTCHA action mismatch");
        }
    }

    public static class RecaptchaException extends RuntimeException {
        public RecaptchaException(String message) {
            super(message);
        }
    }
}