package shop.coding.bank.dto.account;

import lombok.Getter;
import lombok.Setter;
import shop.coding.bank.domain.account.Account;

public class AccountRespDto {

    @Getter
    @Setter
    public static class AccountSaveRespDto {
        private Long id;
        private Long number;
        private Long balance;

        public AccountSaveRespDto(Account account) {
            this.id = account.getId();
            this.number = account.getNumber();
            this.balance = account.getBalance();
        }
    }
}
