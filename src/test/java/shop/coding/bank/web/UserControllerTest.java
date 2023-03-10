package shop.coding.bank.web;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest.WebEnvironment;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultActions;
import shop.coding.bank.config.dummy.DummyObject;
import shop.coding.bank.domain.user.UserRepository;
import shop.coding.bank.dto.user.UserReqDto.JoinReqDto;

import static org.assertj.core.api.Assertions.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@AutoConfigureMockMvc
@SpringBootTest(webEnvironment = WebEnvironment.MOCK)
class UserControllerTest extends DummyObject {

    @Autowired
    private MockMvc mvc;
    @Autowired
    private ObjectMapper om;
    @Autowired
    private UserRepository userRepository;

    @BeforeEach
    public void setUp() {
        dataSetting();
    }


    @Test
    public void join_success_test() throws Exception {
        // Given
        JoinReqDto joinReqDto = new JoinReqDto();
        joinReqDto.setUsername("love");
        joinReqDto.setPassword("1234");
        joinReqDto.setEmail("love@nate.com");
        joinReqDto.setFullname("러브");

        String requestBody = om.writeValueAsString(joinReqDto);
        // System.out.println("테스트 : " + requestBody);

        // When
        ResultActions resultActions = mvc
                .perform(post("/api/join").content(requestBody).contentType(MediaType.APPLICATION_JSON));
        // String responseBody = resultActions.andReturn().getResponse().getContentAsString();
        // System.out.println("테스트 : " + responseBody);

        // Then
        resultActions.andExpect(status().isCreated());
    }

    @Test
    public void join_fail_test() throws Exception {
        // Given
        JoinReqDto joinReqDto = new JoinReqDto();
        joinReqDto.setUsername("ssar");
        joinReqDto.setPassword("1234");
        joinReqDto.setEmail("ssar@nate.com");
        joinReqDto.setFullname("쌀");

        String requestBody = om.writeValueAsString(joinReqDto);
        // System.out.println("테스트 : " + requestBody);

        // When
        ResultActions resultActions = mvc
                .perform(post("/api/join").content(requestBody).contentType(MediaType.APPLICATION_JSON));
        String responseBody = resultActions.andReturn().getResponse().getContentAsString();
        // System.out.println("테스트 : " + responseBody);

        // Then
        resultActions.andExpect(status().isBadRequest());
    }

    private void dataSetting() {
        userRepository.save(newUser("ssar", "쌀"));
    }
}