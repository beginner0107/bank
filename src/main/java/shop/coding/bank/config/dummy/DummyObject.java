package shop.coding.bank.config.dummy;

import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import shop.coding.bank.domain.account.Account;
import shop.coding.bank.domain.transaction.Transaction;
import shop.coding.bank.domain.transaction.TransactionEnum;
import shop.coding.bank.domain.user.User;
import shop.coding.bank.domain.user.UserEnum;

import java.time.LocalDateTime;

public class DummyObject {
    protected User newUser(String username, String fullname) {
        BCryptPasswordEncoder passwordEncoder = new BCryptPasswordEncoder();
        String encPassword = passwordEncoder.encode("1234");
        return User.builder()
                .username(username)
                .password(encPassword)
                .email(username + "@nate.com")
                .fullname(fullname)
                .role(UserEnum.CUSTOMER)
                .build();
    }

    protected User newMockUser(Long id, String username, String fullname) {
        BCryptPasswordEncoder passwordEncoder = new BCryptPasswordEncoder();
        String encPassword = passwordEncoder.encode("1234");
        return User.builder()
                .id(id)
                .username(username)
                .password(encPassword)
                .email(username + "@nate.com")
                .fullname(fullname)
                .role(UserEnum.CUSTOMER)
                .createdAt(LocalDateTime.now())
                .updatedAt(LocalDateTime.now())
                .build();
    }

    protected Account newAccount(Long number, User user) {
        return Account.builder()
                .number(number)
                .password(1234L)
                .balance(1000L)
                .user(user)
                .build();
    }

    protected Account newMockAccount(Long id, Long number, Long balance, User user) {
        return Account.builder()
                .id(id)
                .number(number)
                .password(1234L)
                .balance(balance)
                .user(user)
                .createdAt(LocalDateTime.now())
                .updatedAt(LocalDateTime.now())
                .build();
    }

    protected Transaction newMockDepositTransaction(Long id, Account account){
        account.deposit(100L);
        Transaction transaction = Transaction.builder()
                .id(id)
                .withdrawAccount(null) // ?????? X -> null
                .depositAccount(account) // ????????????ID
                .depositAccountBalance(account.getBalance()) // ????????? ??? ??????
                .withdrawAccountBalance(null) // ?????? X -> null
                .amount(100L) // ????????? ??????
                .gubun(TransactionEnum.DEPOSIT) // ?????? -> DEPOSIT
                .sender("ATM")
                .receiver(account.getNumber() + "") // ???????????? ????????? ????????????
                .tel("01023234433") // ???????????? ????????? ????????????
                .createdAt(LocalDateTime.now())
                .updatedAt(LocalDateTime.now())
                .build();
        return transaction;
    }
}
