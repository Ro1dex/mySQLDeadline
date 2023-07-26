
import com.codeborne.selenide.SelenideElement;
import data.AuthCode;
import data.DataHelper;
import org.junit.jupiter.api.Test;
import page.LoginPage;

import static com.codeborne.selenide.Condition.text;
import static com.codeborne.selenide.Condition.visible;
import static com.codeborne.selenide.Selenide.$;
import static com.codeborne.selenide.Selenide.open;

//!! Не тестировать все тесты вместе !!
public class AuthCodeLoginTest {
    SelenideElement notify = $("[data-test-id=error-notification]");
    String errorCode = "Неверно указан код! Попробуйте ещё раз.";
    String errorAttempt = "Превышено количество попыток ввода кода!";
    //!!!после каждого отдельного теста удалять базу данных и перезапускать SUT!!!

    @Test
    void shouldValidLogin() {
        open("http://localhost:9999");
        var loginPage = new LoginPage();
        var authInfo = DataHelper.getAuthInfo();
        var verificationPage = loginPage.validLogin(authInfo);
        verificationPage.validVerify();
    }

    @Test
    void shouldBlockedAfterThreeInvalidCodesExpected() {
        open("http://localhost:9999");
        var loginPage = new LoginPage();
        var authInfo = DataHelper.getAuthInfo();
        var verificationPage = loginPage.validLogin(authInfo);
        verificationPage.invalidVerify();
        notify.shouldHave(text(errorCode)).shouldBe(visible);
        verificationPage.invalidVerify();
        notify.shouldHave(text(errorCode)).shouldBe(visible);
        verificationPage.invalidVerify();
        notify.shouldHave(text(errorAttempt)).shouldBe(visible);
    }

    @Test
    void shouldLoginAfterThreeAttemptsWithoutCode() {
        open("http://localhost:9999");
        var loginPage = new LoginPage();
        var authInfo = DataHelper.getAuthInfo();
        var verificationPage = loginPage.validLogin(authInfo);
        for (int i = 0; i < 3; i++) {
            //вводить что-то в поле аутентификации необязательно
            open("http://localhost:9999");
            verificationPage = loginPage.validLogin(authInfo);
        }
        verificationPage.validVerify();
    }

    // удаление базы данных
    @Test
    void shouldCleanDB() {
        AuthCode.cleanDB();
    }

}

