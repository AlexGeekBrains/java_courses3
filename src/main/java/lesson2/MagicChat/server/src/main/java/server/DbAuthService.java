package server;

import org.sqlite.SQLiteException;

import java.sql.*;

public class DbAuthService implements AuthService {

    @Override
    public boolean ChangeNick(String oldNickname, String newNickname) {

        try (PreparedStatement checkNick = DataSource.getConnection().prepareStatement("SELECT nickname FROM clients WHERE nickname =?;");) {
            checkNick.setString(1, newNickname);
            try (ResultSet rs = checkNick.executeQuery()) {
                if (rs.next()) {
                    return false;
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        try (PreparedStatement changeNick = DataSource.getConnection().prepareStatement("UPDATE clients SET nickname = ? WHERE nickname =?;");) {
            changeNick.setString(1, newNickname);
            changeNick.setString(2, oldNickname);
            changeNick.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return true;
    }

    @Override
    public String getNicknameByLoginAndPassword(String login, String password) {
        try (PreparedStatement psLogin = DataSource.getConnection().prepareStatement("SELECT nickname FROM clients WHERE login =? AND password = ?;")) {
            psLogin.setString(1, login);
            psLogin.setString(2, password);
            ResultSet rs = psLogin.executeQuery();
            if (rs.next()) {
                return rs.getString("nickname");
            }
            rs.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }

    @Override
    public boolean registration(String login, String password, String nickname) {
        try (PreparedStatement psRegistration = DataSource.getConnection().prepareStatement("INSERT INTO clients (login, password, nickname) VALUES (?, ?, ?);")) {
            psRegistration.setString(1, login);
            psRegistration.setString(2, password);
            psRegistration.setString(3, nickname);
            try {
                if (psRegistration.executeUpdate() == 1) {
                    return true;
                }
            } catch (SQLiteException e) {
                e.printStackTrace();
                return false;
            }
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
        return false;
    }
}


