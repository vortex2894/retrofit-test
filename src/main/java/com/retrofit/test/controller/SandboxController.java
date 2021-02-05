package com.retrofit.test.controller;

import static com.retrofit.test.util.Constants.INIT_PATH;
import static com.retrofit.test.util.Constants.SLASH_PATH;
import static com.retrofit.test.util.Constants.TOKEN_PATH;

import com.retrofit.test.dto.TokenResponse;
import com.retrofit.test.service.SandboxService;
import lombok.RequiredArgsConstructor;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.view.RedirectView;

@RestController(SLASH_PATH)
@RequiredArgsConstructor(onConstructor = @__(@Autowired))
public class SandboxController {

    private final SandboxService sandboxService;

    @GetMapping(INIT_PATH)
    public RedirectView getRedirectUrl(@RequestParam String state) {
        return new RedirectView(sandboxService.buildRedirectUrl(state));
    }

    @GetMapping(TOKEN_PATH)
    public ResponseEntity<TokenResponse> getToken(
            @RequestParam String state,
            @RequestParam String code) {
        return ResponseEntity.ok(
                sandboxService.getToken(state, code));
    }
}
