package shop.coding.bank.temp;

import org.junit.jupiter.api.Test;

import java.util.regex.Pattern;

// java.util.regex.Pattern
public class RegexTest {

    @Test
    public void 한글만된다_test() throws Exception {
        String value = "알리라일";
        boolean result = Pattern.matches("^[가-힣]+$", value);
        System.out.println("테스트 : " + result);
    }

    @Test
    public void 한글은안된다_test() throws Exception {
        String value = "#";
        boolean result = Pattern.matches("^[^ㄱ-ㅎ가-힣]+$", value);
        System.out.println("테스트 : " + result);
    }

    @Test
    public void 영어만된다_test() throws Exception {
        String value = "ssar";
        boolean result = Pattern.matches("^[a-zA-Z]+$", value);
        System.out.println("테스트 : " + result);
    }

    @Test 
    public void 영어는안된다_test() throws Exception {
        String value = "가22";
        boolean result = Pattern.matches("^[^a-zA-Z]+$", value);
        System.out.println("테스트 : " + result);
    }

    @Test
    public void 영어와숫자만된다_test() throws Exception {
        String value = "ssar아";
        boolean result = Pattern.matches("^[a-zA-Z0-9]+$", value);
        System.out.println("테스트 : " + result);
    }

    @Test
    public void 영어만되고_길이는최소2최대4이다_test() throws Exception {
        String value = "s";
        boolean result = Pattern.matches("^[a-zA-Z]{2,4}$", value);
        System.out.println("테스트 : " + result);
    }

    // username, email, fullname
    // username은 영문, 숫자는 되고, 최소 길이 2 ~ 20자 이내
    @Test
    public void user_username_test() throws Exception {
        String username = "ssar";
        boolean result = Pattern.matches("^[a-zA-Z0-9]{2,20}$", username);
        System.out.println("테스트 : " + result);
    }

    @Test
    public void user_fullname_test() throws Exception {
        String fullname = "쌀녕sssdfsdfsdfsdfsdfsdfsfdsdfsdf";
        boolean result = Pattern.matches("^[a-zA-Z가-힣]{1,20}$", fullname);
        System.out.println("테스트 : " + result);
    }

    @Test
    public void user_email_test() throws Exception {
        String email = "aaaaaaaaa@natess.com";
        boolean result = Pattern.matches("^[a-zA-Z0-9]{2,9}@[a-zA-Z0-9]{2,6}\\.[a-zA-Z]{2,3}$", email);
        System.out.println("테스트 : " + result);
    }

    @Test
    public void account_gubun_test1() throws Exception {
        String gubun = "DEPOSIT";
        boolean result = Pattern.matches("^(DEPOSIT)$", gubun);
        System.out.println("테스트 : " + result);
    }

    @Test
    public void account_gubun_test2() throws Exception {
        String gubun = "TRANSFER";
        boolean result = Pattern.matches("^(DEPOSIT|TRANSFER)$", gubun);
        System.out.println("테스트 : " + result);
    }

    @Test
    public void account_tel_test() throws Exception {
        String tel = "01012313232";
        boolean result = Pattern.matches("^[0-9]{3}[0-9]{4}[0-9]{4}", tel);
        System.out.println("테스트 : " + result);
    }
}
