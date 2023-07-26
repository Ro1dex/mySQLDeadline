
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

    //!!!после каждого отдельного теста удалять базу данных и перезапускать SUT!!!

    @Test
    void shouldValidLogin() {
        open("http://localhost:9999");
        var loginPage = new LoginPage();
        var authInfo = DataHelper.getAuthInfo();
        var verificationPage = loginPage.validLogin(authInfo);
        verificationPage.validVerify();
        $("[data-test-id=dashboard]").shouldBe(visible);
    }
    // Ожидаемое поведение для блокировки входа
    @Test
    void shouldBlockedAfterThreeInvalidCodesExpected() {
        open("http://localhost:9999");
        var loginPage = new LoginPage();
        var authInfo = DataHelper.getAuthInfo();
        var verificationPage = loginPage.validLogin(authInfo);
        verificationPage.invalidVerify();
        $("[data-test-id=error-notification]").shouldHave(text("Неверно указан код! Попробуйте ещё раз.")).shouldBe(visible);
        verificationPage.invalidVerify();
        $("[data-test-id=error-notification]").shouldHave(text("Неверно указан код! Попробуйте ещё раз.")).shouldBe(visible);
        verificationPage.invalidVerify();
        $("[data-test-id=error-notification]").shouldHave(text("Превышено количество попыток ввода кода!")).shouldBe(visible);
    }
    // Актуальное поведение для блокировки входа
    @Test
    void shouldBlockedAfterThreeInvalidCodesActual() {
        open("http://localhost:9999");
        var loginPage = new LoginPage();
        var authInfo = DataHelper.getAuthInfo();
        var verificationPage = loginPage.validLogin(authInfo);
        for (int i = 0; i < 3; i++) {
            //вводить что-то в поле аутентификации необязательно
            open("http://localhost:9999");
            verificationPage = loginPage.validLogin(authInfo);
        }
        verificationPage.invalidVerify();
        $("[data-test-id=error-notification]").shouldHave(text("Превышено количество попыток ввода кода!")).shouldBe(visible);
    }
    // удаление базы данных
    @Test
    void shouldCleanDB() {
        AuthCode.cleanDB();
    }

}

