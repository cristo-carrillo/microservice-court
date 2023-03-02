package com.pragma.powerup.infrastructure.Auth;

import com.pragma.powerup.infrastructure.client.IUserClient;
import com.pragma.powerup.infrastructure.client.model.AuthenticationRequest;
import com.pragma.powerup.infrastructure.client.model.AuthenticationResponse;
import com.pragma.powerup.infrastructure.security.aut.IUserDetailsMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class AuthenticationService {
    private final IUserClient iAuthClient;
    private final AuthenticationManager authenticationManager;

    private final IUserDetailsMapper userDetailsMapper;
    public AuthenticationResponse authenticate(AuthenticationRequest request) {
        authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(
                        request.getEmail(),
                        request.getPassword()
                )
        );
//        var user = optionalDetailsUser(request.getEmail()).get();

        var jwtToken = iAuthClient.authenticate(request).getBody().getToken();
        System.out.println(jwtToken);
//        revokeAllUserTokens(user);
//        saveUserToken(user, jwtToken);
        return AuthenticationResponse.builder()
                .token(jwtToken)
                .build();
    }

//    private Optional<DetailsUser> optionalDetailsUser(String username) {
//        UserAuthDto userAuthDto = iAuthClient.getUserAuth(username).getBody();
//
//        DetailsUser user = userDetailsMapper.toUserDetail(userAuthDto);
//        return Optional.of(user);
//    }
}
