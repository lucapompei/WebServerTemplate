#set($symbol_pound='#')
#set($symbol_dollar='$')
#set($symbol_escape='\')
package ${package};

import ${package}.config.security.JwtAuthenticationFilter;
import ${package}.config.security.JwtAuthorizationFilter;
import ${package}.constants.AuthConstants;
import ${package}.entities.ApplicationUser;
import ${package}.utils.JsonUtils;
import ${package}.utils.JwtUtils;
import ${package}.utils.TextUtils;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;
import org.mockito.Mockito;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.HttpStatus;
import org.springframework.mock.web.MockFilterChain;
import org.springframework.mock.web.MockHttpServletRequest;
import org.springframework.mock.web.MockHttpServletResponse;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.web.server.ResponseStatusException;

import java.nio.charset.StandardCharsets;

@SpringBootTest
@ActiveProfiles("test")
@DirtiesContext
class TestAuth {

    @MockBean
    private AuthenticationManager authenticationManager;

    private static JwtAuthenticationFilter AUTHENTICATION_FILTER;
    private static JwtAuthorizationFilter AUTHORIZATION_FILTER;
    private static String USER;
    private static String JWT;

    @BeforeEach
    void beforeEach() {
        String jwtSecret = "JWT_SECRET";
        long expTime = 1L;
        AUTHENTICATION_FILTER = new JwtAuthenticationFilter(authenticationManager, jwtSecret, expTime);
        AUTHORIZATION_FILTER = new JwtAuthorizationFilter(authenticationManager, jwtSecret);
        ApplicationUser appUser = new ApplicationUser();
        appUser.setId(1L);
        appUser.setUsername("USR");
        appUser.setPassword("PWD");
        USER = JsonUtils.toJson(appUser);
        JWT = JwtUtils.getToken(appUser.getUsername(), jwtSecret, expTime);
    }

    @Test
    void testAttemptAuthenticationWithoutUser() {
        MockHttpServletRequest request = new MockHttpServletRequest();
        MockHttpServletResponse response = new MockHttpServletResponse();
        Assertions.assertDoesNotThrow(() -> AUTHENTICATION_FILTER.attemptAuthentication(request, response));
    }

    @Test
    void testAttemptAuthenticationWithUser() {
        MockHttpServletRequest request = new MockHttpServletRequest();
        request.setContent(USER.getBytes(StandardCharsets.UTF_8));
        MockHttpServletResponse response = new MockHttpServletResponse();
        Mockito.when(authenticationManager.authenticate(Mockito.any()))
                .thenReturn(null);
        Assertions.assertDoesNotThrow(() -> AUTHENTICATION_FILTER.attemptAuthentication(request, response));
    }

    @Test
    void testAttemptAuthenticationFails() {
        MockHttpServletRequest request = new MockHttpServletRequest();
        request.setContent(USER.getBytes(StandardCharsets.UTF_8));
        MockHttpServletResponse response = new MockHttpServletResponse();
        Mockito.when(authenticationManager.authenticate(Mockito.any()))
                .thenThrow(new ResponseStatusException(HttpStatus.FORBIDDEN));
        Assertions.assertThrows(UsernameNotFoundException.class, () -> AUTHENTICATION_FILTER.attemptAuthentication(request, response));
    }

    @Test
    void testSuccessfulAuthentication() {
        MockHttpServletRequest request = new MockHttpServletRequest();
        request.addHeader(AuthConstants.AUTH_HEADER, AuthConstants.AUTH_BEARERPREFIX + JWT);
        MockHttpServletResponse response = new MockHttpServletResponse();
        MockFilterChain chain = new MockFilterChain();
        Assertions.assertDoesNotThrow(() -> AUTHORIZATION_FILTER.doFilter(request, response, chain));
    }

    @ParameterizedTest
    @ValueSource(strings = {"", AuthConstants.AUTH_HEADER, AuthConstants.AUTH_BEARERPREFIX})
    void testSuccessfulAuthenticationWithoutHeader(String prefix) {
        MockHttpServletRequest request = new MockHttpServletRequest();
        if (!TextUtils.isNullOrEmpty(prefix)) {
            request.addHeader(AuthConstants.AUTH_HEADER, prefix + JWT);
        }
        MockHttpServletResponse response = new MockHttpServletResponse();
        MockFilterChain chain = new MockFilterChain();
        Assertions.assertDoesNotThrow(() -> AUTHORIZATION_FILTER.doFilter(request, response, chain));
    }

}
