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
import shop.coding.bank.domain.transaction.Transaction;
import shop.coding.bank.domain.transaction.TransactionRepository;
import shop.coding.bank.domain.user.User;
import shop.coding.bank.domain.user.UserRepository;
import shop.coding.bank.dto.account.AccountReqDto.*;
import shop.coding.bank.dto.account.AccountRespDto.*;
import shop.coding.bank.handler.ex.CustomApiException;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertThrows;
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

    @Mock
    private TransactionRepository transactionRepository;

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

    @Test
    void 계좌삭제_test() throws Exception {
        // Given
        Long number = 1111L;
        Long userId = 2L;

        // stub
        User ssar = newMockUser(1L, "ssar", "쌀");
        Account ssarAccount = newMockAccount(1L, 1111L, 1000L, ssar);
        when(accountRepository.findByNumber(any())).thenReturn(Optional.of(ssarAccount));

        // When
        assertThrows(CustomApiException.class, () -> accountService.계좌삭제(number, userId));
    }

    // Account -> balance 변경됐는지
    // Transaction -> balance 잘 기록됐는지
    @Test
    void 계좌입금_test() throws Exception {
        // Given
        AccountDepositReqDto accountDepositReqDto = new AccountDepositReqDto();
        accountDepositReqDto.setNumber(1111L);
        accountDepositReqDto.setAmount(100L);
        accountDepositReqDto.setGubun("DEPOSIT");
        accountDepositReqDto.setTel("01022339999");

        // stub 1
        User ssar = newMockUser(1L, "ssar", "쌀");
        Account ssarAccount1 = newMockAccount(1L, 1111L, 1000L, ssar);
        when(accountRepository.findByNumber(any())).thenReturn(Optional.of(ssarAccount1));

        // stub 2
        Account ssarAccount2 = newMockAccount(1L, 1111L, 1000L, ssar);
        Transaction transaction = newMockDepositTransaction(1L, ssarAccount2);
        when(transactionRepository.save(any())).thenReturn(transaction);

        // When
        AccountDepositRespDto accountDepositRespDto = accountService.계좌입금(accountDepositReqDto);
        System.out.println("테스트 : 트랜잭션 입금계좌 잔액 : " + accountDepositRespDto.getTransaction().getDepositAccountBalance());
        System.out.println("테스트 : 계좌쪽 잔액 : " + ssarAccount1.getBalance());

        // Then
        assertThat(ssarAccount1.getBalance()).isEqualTo(1100L);
        assertThat(accountDepositRespDto.getTransaction().getDepositAccountBalance()).isEqualTo(1100L);
    }

    @Test
    void 계좌입금_test2() throws Exception {
        // Given
        AccountDepositReqDto accountDepositReqDto = new AccountDepositReqDto();
        accountDepositReqDto.setNumber(1111L);
        accountDepositReqDto.setAmount(100L);
        accountDepositReqDto.setGubun("DEPOSIT");
        accountDepositReqDto.setTel("01022339999");

        // stub 1
        User ssar = newMockUser(1L, "ssar", "쌀");
        Account ssarAccount1 = newMockAccount(1L, 1111L, 1000L, ssar);
        when(accountRepository.findByNumber(any())).thenReturn(Optional.of(ssarAccount1));

        // stub 2
        User ssar2 = newMockUser(1L, "ssar", "쌀");
        Account ssarAccount2 = newMockAccount(1L, 1111L, 1000L, ssar2);
        Transaction transaction = newMockDepositTransaction(1L, ssarAccount2);
        when(transactionRepository.save(any())).thenReturn(transaction);

        // When
        AccountDepositRespDto accountDepositRespDto = accountService.계좌입금(accountDepositReqDto);
        String responseBody = om.writeValueAsString(accountDepositRespDto);
        System.out.println("테스트 : " + responseBody);

        // Then
        assertThat(ssarAccount1.getBalance()).isEqualTo(1100L);
    }

    /*
     * 무엇을 여기서 테스트해야할지 명확히 구분 (책임 분리)
     * DTO를 만드는 책임 -> 서비스에 있지만 (서비스에서 검증 X -> Controller에서 테스트 가능)
     * DB관련된 것 -> 서비스 책임이 아니라고 봄
     * DB관련된 것을 조회했을 때, 그 값을 통해서 어떤 비즈니스 로직이 흘러가는 것이 있으면 -> stub으로 정의해서 테스트 해보면 됨
     *
     * DB스텁 (가짜로 DB만들어서 deposit 검증..0원 검증)
     */
    @Test
    public void 계좌입금_test3() throws Exception {
        // Given
        Account account = newMockAccount(1L, 1111L, 1000L, null);
        Long amount = 100L;

        // When
        if (amount <= 0L) {
            throw new CustomApiException("0원 이하의 금액을 임급할 수 없습니다");
        }
        account.deposit(100L);

        // Then
        assertThat(account.getBalance()).isEqualTo(1100L);
    }

    // 계좌 출금_테스트
    
    // 계좌 이체_테스트
    
    // 계좌목록_유저별_테스트
    
    // 계좌상세보기_테스트
}