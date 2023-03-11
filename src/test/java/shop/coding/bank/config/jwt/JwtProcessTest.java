package shop.coding.bank.config.jwt;

import org.junit.jupiter.api.Test;
import shop.coding.bank.config.auth.LoginUser;
import shop.coding.bank.domain.user.User;
import shop.coding.bank.domain.user.UserEnum;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static org.junit.jupiter.api.Assertions.assertTrue;


class JwtProcessTest {

    @Test
    void create_Test() throws Exception {
        // Given
        User user = User.builder().id(1L).role(UserEnum.CUSTOMER).build();
        LoginUser loginUser = new LoginUser(user);

        // When
        String jwtToken = JwtProcess.create(loginUser);
        System.out.println("테스트 : " + jwtToken);

        // Then
        assertTrue(jwtToken.startsWith(JwtVO.TOKEN_PREFIX));
    }

    @Test
    void verify_test() throws Exception {
        // Given
        String jwkToken = "eyJ0eXAiOiJKV1QiLCJhbGciOiJIUzUxMiJ9.eyJzdWIiOiJiYW5rIiwicm9sZSI6IkNVU1RPTUVSIiwiaWQiOjEsImV4cCI6MTY3OTEzMjQ3N30.tVOnOxZgR8jMY0-x_th6Va7G9W4tbD9L8tUomkJZBi66gWbV7ZMY_vogFY1tumko_nmNO2St56-AI9VHdAmgLw";

        // When
        LoginUser loginUser = JwtProcess.verify(jwkToken);
        System.out.println("테스트 : " + loginUser.getUser().getId());
        System.out.println("테스트 : " + loginUser.getUser().getRole().name());

        // Then
        assertThat(loginUser.getUser().getId()).isEqualTo(1);
        assertThat(loginUser.getUser().getRole()).isEqualTo(UserEnum.CUSTOMER);
    }
}