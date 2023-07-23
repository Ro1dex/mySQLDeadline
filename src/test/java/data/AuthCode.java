package data;

import lombok.SneakyThrows;

import java.sql.*;

public class AuthCode {
    public static Connection conn;

    public AuthCode() {
        try {
            String url = "jdbc:mysql://localhost:3306/app";
            conn = DriverManager.getConnection(url, "app", "pass");
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
    @SneakyThrows
    public static String getCode() {
        String code = null;
        String query = "SELECT code FROM auth_codes WHERE user_id = (SELECT id FROM users WHERE login = ?)";

        try (PreparedStatement statement = conn.prepareStatement(query)) {
            statement.setString(1, DataHelper.getAuthInfo().getLogin());
            ResultSet resultSet = statement.executeQuery();
            while (resultSet.next()) {
                code = resultSet.getString("code");
            }
        } catch (SQLException e) {
            throw new RuntimeException("Ошибка при получении кода из базы данных", e);
        }
        return code;
    }

}

