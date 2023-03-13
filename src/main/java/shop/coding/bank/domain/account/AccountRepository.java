package shop.coding.bank.domain.account;

import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface AccountRepository extends JpaRepository<Account, Long> {
    // select * from account where number = :number
    // checkpoint : 리팩토링 해야함
    Optional<Account> findByNumber(Long number);
}
