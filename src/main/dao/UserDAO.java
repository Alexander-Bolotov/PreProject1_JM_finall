package src.main.dao;

import src.main.model.User;

import javax.tools.StandardJavaFileManager;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class UserDAO {
    private Connection connection;

    public UserDAO(Connection connection) {
        this.connection = connection;
    }

    public List<User> getAllUser() {
        List<User> userList = new ArrayList<>();
        try (PreparedStatement preparedStatement = connection.prepareStatement("SELECT * FROM users")) {
            ResultSet resultSet = preparedStatement.executeQuery();
            while (resultSet.next()) {
                userList.add(new User(resultSet.getLong("id"), resultSet.getString("name"), resultSet.getString("password")));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return userList;
    }

    public User getUserByName(String name) {
        User userResult = new User();
        try (PreparedStatement preparedStatement = connection.prepareStatement("select * from users where name= ?")) {
            preparedStatement.setString(1, name);
            ResultSet result = preparedStatement.executeQuery();
            result.next();
            userResult.setId(result.getLong("id"));
            userResult.setName(result.getString("name"));
            userResult.setPassword(result.getString("password"));

        } catch (SQLException e) {
            e.printStackTrace();
        }
        return userResult;
    }

    public boolean nameIsExist(String name) {
        try (PreparedStatement preparedStatement = connection.prepareStatement("select count(*) from usersdb.users where name= ?")){
            preparedStatement.setString(1, name);
            ResultSet result = preparedStatement.executeQuery();
            result.next();
            int rowCount = result.getInt(1);
            return rowCount > 0;
        } catch (SQLException e) {
            e.printStackTrace();
        } return false;
    }

    public void addUser(User user) {
        try (PreparedStatement preparedStatement = connection.prepareStatement("INSERT INTO users (name, password) VALUES (?, ?)")) {
            preparedStatement.setString(1, user.getName());
            preparedStatement.setString(2, user.getPassword());
            preparedStatement.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }


    public void deleteUserByName(String name) {
        try (PreparedStatement preparedStatement = connection.prepareStatement("DELETE FROM usersdb.users WHERE name= ?")) {
            preparedStatement.setString(1, name);
            preparedStatement.executeUpdate();
        } catch (SQLException ex) {
            ex.printStackTrace();
        }
    }
}

