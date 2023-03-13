package shop.coding.bank.config.dummy;

import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;
import shop.coding.bank.domain.user.User;
import shop.coding.bank.domain.user.UserRepository;

@Configuration
public class DummyInit extends DummyObject{

    @Profile("dev") // prod 모드에서는 실행되면 안된다.
    @Bean
    CommandLineRunner init(UserRepository userRepository) {
        return (args) -> {
          // 서버 실행시에 무조건 실행된다.
            User ssar = userRepository.save(newUser("ssar", "쌀"));
        };
    }

}
