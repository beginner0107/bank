package shop.coding.bank.web;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.context.SpringBootTest.WebEnvironment;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.TestExecutionEvent;
import org.springframework.security.test.context.support.WithUserDetails;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.jdbc.Sql;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultActions;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.transaction.annotation.Transactional;
import shop.coding.bank.config.auth.LoginUser;
import shop.coding.bank.config.dummy.DummyObject;
import shop.coding.bank.domain.account.Account;
import shop.coding.bank.domain.account.AccountRepository;
import shop.coding.bank.domain.user.User;
import shop.coding.bank.domain.user.UserRepository;
import shop.coding.bank.dto.account.AccountReqDto.AccountSaveReqDto;
import shop.coding.bank.handler.ex.CustomApiException;

import javax.persistence.EntityManager;

import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@ActiveProfiles("test")
@Sql("classpath:db/teardown.sql")
@AutoConfigureMockMvc
@SpringBootTest(webEnvironment = WebEnvironment.MOCK)
class AccountControllerTest extends DummyObject {

    @Autowired
    private MockMvc mvc;
    @Autowired
    private ObjectMapper om;
    @Autowired
    private UserRepository userRepository;
    @Autowired
    private AccountRepository accountRepository;
    @Autowired
    private EntityManager em;

    @BeforeEach
    public void setUp() {
        User ssar = userRepository.save(newUser("ssar", "???"));
        User cos = userRepository.save(newUser("cos", "??????"));
        Account ssarAccount1 = accountRepository.save(newAccount(1111L, ssar));
        Account cosAccount1 = accountRepository.save(newAccount(2222L, cos));
        em.clear();
    }

    // jwt token -> ???????????? -> ???????????? ????????????
    // setupBefore=TEST_METHOD (setUp ????????? ???????????? ??????)
    // setupBefore = TestExecutionEvent.TEST_EXECUTION (saveAccount_test ????????? ?????? ?????? ??????)
    @WithUserDetails(value = "ssar", setupBefore = TestExecutionEvent.TEST_EXECUTION) // ???????????? username = ssar ????????? ?????? ????????? ???????????? ???????????????
    @Test
    public void saveAccount_test() throws Exception {
        // Given
        AccountSaveReqDto accountSaveReqDto = new AccountSaveReqDto();
        accountSaveReqDto.setNumber(9999L);
        accountSaveReqDto.setPassword(1234L);
        String requestBody = om.writeValueAsString(accountSaveReqDto);
        System.out.println("????????? : " + requestBody);

        // When
        ResultActions resultActions = mvc
                .perform(post("/api/s/account").content(requestBody).contentType(MediaType.APPLICATION_JSON));
        String responseBody = resultActions.andReturn().getResponse().getContentAsString();
        System.out.println("????????? : " + responseBody);

        // Then
        resultActions.andExpect(status().isCreated());
    }

    @WithUserDetails(value = "ssar", setupBefore = TestExecutionEvent.TEST_EXECUTION) // ???????????? username = ssar ????????? ?????? ????????? ???????????? ???????????????
    @Test
    public void findUserAccount_test() throws Exception {
        // Given

        // When
        ResultActions resultActions = mvc
                .perform(get("/api/s/account/login-user"));
        String responseBody = resultActions.andReturn().getResponse().getContentAsString();
        System.out.println("????????? : " + responseBody);

        // Then
        resultActions.andExpect(status().isOk());
    }

    /**
     * ????????? ????????? insert ???????????? ?????? PC??? ?????????(?????????)
     * ????????? ???????????? ????????? ????????? ?????? ?????? ????????? ????????? ???????????? ???????????? ??? ??? ?????? ?????????.
     * ?????? select??? ????????? ??????????????? PC??? ????????? 1??? ????????? ???.
     * Lazy ????????? ????????? ???????????? - PC??? ?????????
     * Lazy ????????? ??? PC ????????? ????????? ?????????.
     */
    @WithUserDetails(value = "ssar", setupBefore = TestExecutionEvent.TEST_EXECUTION)
    @Test
    void deleteAccount_test() throws Exception {
        // Given
        Long number = 1111L;

        // When
        ResultActions resultActions = mvc
                .perform(delete("/api/s/account/" + number));
        String responseBody = resultActions.andReturn().getResponse().getContentAsString();
        System.out.println("????????? : " + responseBody);

        // Then
        // Junit ??????????????? delete ????????? DB??????(DML) ?????? ???????????? ???????????? ????????????.
        assertThrows(CustomApiException.class, () -> accountRepository.findByNumber(number).orElseThrow(
                () -> new CustomApiException("????????? ?????? ??? ????????????."))
        );
    }
}