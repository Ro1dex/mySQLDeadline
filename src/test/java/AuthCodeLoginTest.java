import data.DataHelper;
import org.junit.jupiter.api.Test;
import page.LoginPage;

import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;

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

/*    @Test
    void shouldTest() {
        String query = "SELECT code FROM auth_codes WHERE user_id = (SELECT id FROM users WHERE login = vasya)";

        try (
                var connection = DriverManager.getConnection("jdbc:mysql://localhost:3306/app", "app", "pass");
                var getID = connection.prepareStatement(query);
        ) {
            ResultSet resultSet = getID.executeQuery();
            while (resultSet.next()) {
                String code = resultSet.getString("code");
            }
        } catch (SQLException ex) {
            throw new RuntimeException(ex);
        }
    }*/
}

