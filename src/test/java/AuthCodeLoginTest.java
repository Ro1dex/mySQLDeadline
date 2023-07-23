import data.DataHelper;
import org.junit.jupiter.api.Test;
import page.LoginPage;


import static com.codeborne.selenide.Selenide.open;

public class AuthCodeLoginTest {
    @Test
    void shouldTransferMoneyBetweenOwnCards() {
        open("http://localhost:9999");
        var loginPage = new LoginPage();
        var authInfo = DataHelper.getAuthInfo();
        var verificationPage = loginPage.validLogin(authInfo);
        var dashboardPage = verificationPage.validVerify();
    }

}

