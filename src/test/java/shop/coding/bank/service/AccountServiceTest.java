package shop.coding.bank.service;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Spy;
import org.mockito.junit.jupiter.MockitoExtension;
import shop.coding.bank.config.dummy.DummyObject;
import shop.coding.bank.domain.account.Account;
import shop.coding.bank.domain.account.AccountRepository;
import shop.coding.bank.domain.user.User;
import shop.coding.bank.domain.user.UserRepository;
import shop.coding.bank.service.AccountService.AccountListRespDto;
import shop.coding.bank.dto.account.AccountReqDto.AccountSaveReqDto;
import shop.coding.bank.dto.account.AccountRespDto.AccountSaveRespDto;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class AccountServiceTest extends DummyObject {

    @InjectMocks // 모든 Mock들이 InjectMocks 로 주입
    private AccountService accountService;

    @Mock
    private UserRepository userRepository;

    @Mock
    private AccountRepository accountRepository;

    @Spy // 진짜 객체를 InjectMocks 에 주입한다.
    private ObjectMapper om;

    @Test
    void 계좌등록_test() throws Exception {
        // Given
        Long userId = 1L;

        AccountSaveReqDto accountSaveReqDto = new AccountSaveReqDto();
        accountSaveReqDto.setNumber(1111L);
        accountSaveReqDto.setPassword(1234L);

        // stub - 1
        User ssar = newMockUser(userId, "ssar", "쌀");
        when(userRepository.findById(any())).thenReturn(Optional.of(ssar));

        // stub - 2
        when(accountRepository.findByNumber(any())).thenReturn(Optional.empty());

        // stub - 3
        Account ssarAccount = newMockAccount(1L, 1111L, 1000L, ssar);
        when(accountRepository.save(any())).thenReturn(ssarAccount);

        // When
        AccountSaveRespDto accountSaveRespDto = accountService.계좌등록(accountSaveReqDto, userId);
        String responseBody = om.writeValueAsString(accountSaveRespDto);
        System.out.println("테스트 : " + responseBody);

        // Then
        assertThat(accountSaveRespDto.getNumber()).isEqualTo(1111L);
    }

    @Test
    void 계좌목록보기_유저별_test() throws Exception {
        // Given
        Long userId = 1L;

        // stub - 1
        User ssar = newMockUser(1L, "ssar", "쌀");
        when(userRepository.findById(userId)).thenReturn(Optional.of(ssar));

        // stub - 2
        Account ssarAccount1 = newMockAccount(1L, 1111L, 1000L, ssar);
        Account ssarAccount2 = newMockAccount(2L, 2222L, 1000L, ssar);
        List<Account> accountList = Arrays.asList(ssarAccount1, ssarAccount2);
        when(accountRepository.findByUser_id(any())).thenReturn(accountList);

        // When
        AccountListRespDto accountListRespDto = accountService.계좌목록보기_유저별(userId);

        // Then
        assertThat(accountListRespDto.getFullname()).isEqualTo("쌀");
        assertThat(accountListRespDto.getAccounts()).hasSize(2);
    }
}