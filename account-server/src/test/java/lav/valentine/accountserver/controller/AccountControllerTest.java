package lav.valentine.accountserver.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import lav.valentine.accountserver.dto.ResponseDto;
import lav.valentine.accountserver.service.AccountService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@ExtendWith(SpringExtension.class)
@WebMvcTest(AccountController.class)
class AccountControllerTest {

    @MockBean
    private AccountService accountService;
    @Autowired
    private AccountController accountController;

    private final ObjectMapper objectMapper = new ObjectMapper();

    private MockMvc mvc;

    @BeforeEach
    void setUp() {
        mvc = MockMvcBuilders.standaloneSetup(accountController).build();
    }

    @Test
    void getAmount() throws Exception {
        Integer id = 0;
        Long amount = 0L;

        ResponseDto response = new ResponseDto("The balance of account 0 is equal to 0");

        when(accountService.getAmount(id)).thenReturn(amount);

        mvc.perform(MockMvcRequestBuilders.get("/api/{id}/amount", id))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(content().string(objectMapper.writeValueAsString(response)));
    }

    @Test
    void addAmount() throws Exception {
        Integer id = 0;
        ResponseDto response = new ResponseDto("The balance of account 0 has been changed");

        mvc.perform(MockMvcRequestBuilders.post("/api/{id}/amount", id)
                    .param("value", "0"))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(content().string(objectMapper.writeValueAsString(response)));
    }
}