package shop.coding.bank.service;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Spy;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import shop.coding.bank.domain.user.User;
import shop.coding.bank.service.UserService.JoinReqDto;
import shop.coding.bank.domain.user.UserEnum;
import shop.coding.bank.domain.user.UserRepository;

import java.time.LocalDateTime;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

// Spring 관련 Bean들이 하나도 없는 환경
@ExtendWith(MockitoExtension.class)
class UserServiceTest {

    @InjectMocks
    private UserService userService;

    @Mock
    private UserRepository userRepository;

    @Spy
    private BCryptPasswordEncoder passwordEncoder;

    @Test
    public void 회원가입_test() throws Exception {
        // Given
        JoinReqDto joinReqDto = new UserService.JoinReqDto();
        joinReqDto.setUsername("ssar");
        joinReqDto.setPassword("1234");
        joinReqDto.setEmail("ssar@nate.com");
        joinReqDto.setFullname("쌀");

        // stub 1
        when(userRepository.findByUsername(any())).thenReturn(Optional.empty());

        // stub 2
        User ssar = User.builder()
                        .id(1L)
                        .username("ssar")
                        .password("1234")
                        .email("ssar@nate.com")
                        .fullname("쌀")
                        .createdAt(LocalDateTime.now())
                        .updatedAt(LocalDateTime.now())
                        .role(UserEnum.CUSTOMER)
                        .build();
        when(userRepository.save(any())).thenReturn(ssar);

        // When
        UserService.JoinRespDto joinRespDto = userService.회원가입(joinReqDto);
        System.out.println("테스트: " + joinRespDto.toString());

        // Then
        assertThat(joinRespDto.getId()).isEqualTo(1L);
        assertThat(joinRespDto.getUsername()).isEqualTo("ssar");
    }
}
