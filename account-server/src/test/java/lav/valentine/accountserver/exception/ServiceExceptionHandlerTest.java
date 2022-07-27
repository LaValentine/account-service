package lav.valentine.accountserver.exception;

import com.fasterxml.jackson.databind.ObjectMapper;
import lav.valentine.accountserver.controller.AccountController;
import lav.valentine.accountserver.dto.ResponseDto;
import lav.valentine.accountserver.exception.ext.AccountNotExistException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@ExtendWith(SpringExtension.class)
class ServiceExceptionHandlerTest {

    @MockBean
    private AccountController accountController;

    private final ObjectMapper objectMapper = new ObjectMapper();

    private MockMvc mvc;

    @BeforeEach
    void setUp() {
        mvc = MockMvcBuilders.standaloneSetup(new ServiceExceptionHandler(), accountController).build();
    }

    @Test
    void apiExceptionHandler() throws Exception {
        String message = "MESSAGE";
        ResponseDto response = new ResponseDto(message);

        when(accountController.getAmount(0)).thenThrow(new AccountNotExistException(message));

        mvc.perform(MockMvcRequestBuilders.get("/api/{id}/amount", 0))
                .andExpect(status().isBadRequest())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(content().string(objectMapper.writeValueAsString(response)));
    }
}