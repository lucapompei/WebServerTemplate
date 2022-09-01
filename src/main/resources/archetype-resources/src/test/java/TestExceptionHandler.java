#set($symbol_pound='#')
#set($symbol_dollar='$')
#set($symbol_escape='\')
package ${package};

import ${package}.handlers.ApplicationExceptionHandler;
import ${package}.responses.ErrorResponse;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.http.HttpStatus;
import org.springframework.mock.web.MockHttpServletRequest;
import org.springframework.web.context.request.ServletWebRequest;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.server.ResponseStatusException;

/**
 * This class is used to test the exception handler
 */
class TestExceptionHandler {

    /**
     * Test a simple query to find an existing user on db
     */
    @DisplayName("Test exception handlers")
    @Test
    void testExceptionHandlers() {
        ResponseStatusException ex = new ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR);
        MockHttpServletRequest request = new MockHttpServletRequest();
        request.setRequestURI("URI");
        WebRequest webRequest = new ServletWebRequest(request);
        ApplicationExceptionHandler handler = new ApplicationExceptionHandler();
        ErrorResponse response = (ErrorResponse) handler.handleGenericException(ex, webRequest).getBody();
        Assertions.assertDoesNotThrow(() -> handler.handleGenericException(ex, webRequest));
        Assertions.assertDoesNotThrow(() -> handler.handleResponseStatusException(ex, webRequest));
        Assertions.assertNotNull(response);
        Assertions.assertNotNull(response.getError());
        Assertions.assertNotNull(response.getPath());
        Assertions.assertTrue(response.getStatus() > 0);
        Assertions.assertNotNull(response.getMessage());
        Assertions.assertNotNull(response.getTimestamp());
    }

}
