package data;

import com.github.javafaker.Faker;
import lombok.SneakyThrows;
import org.apache.commons.dbutils.QueryRunner;
import org.apache.commons.dbutils.handlers.ScalarHandler;

import java.sql.*;

public class AuthCode {
    private static final QueryRunner runner = new QueryRunner();

    public AuthCode() {
    }
    public static Connection getConn() throws SQLException{
        return DriverManager.getConnection("jdbc:mysql://localhost:3306/app", "app", "pass");
    }
    @SneakyThrows
    public static DataHelper.VerificationCode getValidCode() {
        var conn = getConn();
        var codeSQL = "SELECT code FROM auth_codes ORDER BY created DESC LIMIT 1";
        var code = runner.query(conn, codeSQL, new ScalarHandler<String>());
        return new DataHelper.VerificationCode(code);
    }
    public static DataHelper.VerificationCode getInvalidCode(){
         var faker = new Faker();
        String code = faker.numerify("######");
        return new DataHelper.VerificationCode(code);
    }
    @SneakyThrows
    public static void cleanDB(){
        var connection =getConn();
        runner.execute(connection, "DELETE FROM auth_codes");
        runner.execute(connection, "DELETE FROM card_transactions");
        runner.execute(connection, "DELETE FROM cards");
        runner.execute(connection, "DELETE FROM users");
    }

}

