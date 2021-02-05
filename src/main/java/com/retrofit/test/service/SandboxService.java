package com.retrofit.test.service;

import static com.retrofit.test.util.Constants.AND_KEY;
import static com.retrofit.test.util.Constants.CLIENT_ID_PARAM_KEY;
import static com.retrofit.test.util.Constants.EXTERNAL_SIGNING_PATH;
import static com.retrofit.test.util.Constants.PARAM_KEY;
import static com.retrofit.test.util.Constants.REDIRECT_PARAM_KEY;
import static com.retrofit.test.util.Constants.RESPONSE_TYPE_PARAM_KEY;
import static com.retrofit.test.util.Constants.RESPONSE_TYPE_TEST_VALUE;
import static com.retrofit.test.util.Constants.SCOPE_PARAM_KEY;
import static com.retrofit.test.util.Constants.SCOPE_TEST_VALUE;
import static com.retrofit.test.util.Constants.STATE_PARAM_KEY;
import static com.retrofit.test.util.Constants.TOKEN_PATH;

import com.retrofit.test.client.SandboxClient;
import com.retrofit.test.dto.TokenResponse;
import lombok.RequiredArgsConstructor;

import java.io.IOException;
import java.util.Map;
import java.util.stream.Collectors;

import org.apache.logging.log4j.util.Strings;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor(onConstructor = @__(@Autowired))
public class SandboxService {

    private final SandboxClient sandboxClient;

    @Value("${base.url}")
    private String baseUrl;

    @Value("${api.url}")
    private String apiUrl;

    @Value("${client.id}")
    private String clientId;

    @Value("${client.secret}")
    private String clientSecret;

    public String buildRedirectUrl(String state) {
        String concatenatedParams = concatParams(Map.of(
                STATE_PARAM_KEY, state,
                REDIRECT_PARAM_KEY, baseUrl.concat(TOKEN_PATH),
                CLIENT_ID_PARAM_KEY, clientId,
                SCOPE_PARAM_KEY, SCOPE_TEST_VALUE,
                RESPONSE_TYPE_PARAM_KEY, RESPONSE_TYPE_TEST_VALUE));
        return apiUrl.concat(EXTERNAL_SIGNING_PATH)
                .concat(concatenatedParams);
    }

    public TokenResponse getToken(String state, String code) {
        try {
            return sandboxClient.getToken(clientId, clientSecret, code, SCOPE_TEST_VALUE)
                    .execute()
                    .body()
                    .addState(state);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    private String concatParams(Map<String, String> params) {
        String result = params.entrySet().stream()
                .map(it -> it.getKey().concat(it.getValue()))
                .collect(Collectors.joining(AND_KEY));
        return Strings.isNotEmpty(result)
                ? PARAM_KEY.concat(result)
                : result;
    }
}
