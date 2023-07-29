

import data.AuthCode;
import data.DataHelper;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Test;
import page.LoginPage;

import static com.codeborne.selenide.Selenide.open;

public class AuthCodeLoginTest {
    @AfterEach
    void cleanAuthCodes() {
        AuthCode.cleanAuthCodes();
    }

    @Test
    void shouldValidLogin() {
        open("http://localhost:9999");
        var loginPage = new LoginPage();
        var authInfo = DataHelper.getAuthInfo();
        var verificationPage = loginPage.validLogin(authInfo);
        verificationPage.validVerify();
    }

    @Test
    void shouldBlockedAfterEnterThreeInvalidCodes() {
        String errorCode = "Неверно указан код! Попробуйте ещё раз.";
        String errorAttempt = "Превышено количество попыток ввода кода!";
        open("http://localhost:9999");
        var loginPage = new LoginPage();
        var authInfo = DataHelper.getAuthInfo();
        var verificationPage = loginPage.validLogin(authInfo);
        verificationPage.invalidVerify();
        verificationPage.notifyShouldHaveAndVisible(errorCode);
        verificationPage.invalidVerify();
        verificationPage.notifyShouldHaveAndVisible(errorCode);
        verificationPage.invalidVerify();
        verificationPage.notifyShouldHaveAndVisible(errorAttempt);
    }

    @Test
    void shouldLoginAfterThreeAttemptsWithoutEnterCode() {
        open("http://localhost:9999");
        var loginPage = new LoginPage();
        var authInfo = DataHelper.getAuthInfo();
        var verificationPage = loginPage.validLogin(authInfo);
        for (int i = 0; i < 3; i++) {
            //вводить что-то в поле код аутентификации необязательно
            open("http://localhost:9999");
            verificationPage = loginPage.validLogin(authInfo);
        }
        verificationPage.validVerify();
    }

    @AfterAll
    static void cleanDB() {
        AuthCode.cleanDB();
    }

}

