package com.retrofit.test.client;

import static com.retrofit.test.util.Constants.TOKEN_PATH;

import com.retrofit.test.dto.TokenResponse;

import retrofit2.Call;
import retrofit2.http.POST;
import retrofit2.http.Query;

public interface SandboxClient {

    @POST(TOKEN_PATH)
    Call<TokenResponse> getToken(
            @Query("client_id") String clientId,
            @Query("client_secret") String clientSecret,
            @Query("code") String code,
            @Query("scope") String scope);
}
