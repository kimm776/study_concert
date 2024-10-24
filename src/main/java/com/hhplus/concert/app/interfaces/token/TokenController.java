package com.hhplus.concert.app.interfaces.token;

import com.hhplus.concert.app.application.TokenFacade;
import com.hhplus.concert.app.interfaces.token.dto.TokenRequest;
import com.hhplus.concert.app.interfaces.token.dto.TokenResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
public class TokenController implements TokenApi {

    private final TokenFacade tokenFacade;

    public ResponseEntity<TokenResponse> issueToken(@RequestBody TokenRequest request) {
        return ResponseEntity.ok(new TokenResponse(tokenFacade.issueToken(request.getUserId())));
    }
}
