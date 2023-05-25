package com.nurullah.service;

import org.junit.jupiter.api.Test;

import static com.nurullah.utils.CustomerTestUtils.USERNAME;
import static org.assertj.core.api.BDDAssertions.then;
import static org.junit.jupiter.api.Assertions.*;

class JwtTokenServiceTest {

    private final JwtTokenService jwtTokenService = new JwtTokenService(
            "SmF2YVNjcmlwdC1JbmZvcm1hdGlvbi1Qcm9jZXNzb3ItdGVzdC1zdHJpbmc=");

    @Test
    public void should_return_token_and_get_claims() {

        var token = jwtTokenService.createToken(USERNAME);
        var claims = jwtTokenService.getAllClaimsFromToken(token);

        then(token).isNotNull();
        then(claims).isNotNull();
        then(claims.get("username")).isEqualTo(USERNAME);
    }

}