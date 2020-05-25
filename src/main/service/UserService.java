package src.main.service;

import src.main.dao.UserDAO;
import src.main.model.User;

import java.sql.Connection;
import java.sql.Driver;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.List;

public class UserService {
    private UserDAO dao = getUserDAO();
    private static volatile UserService instance;

    private UserService() {
    }

    public static UserService getInstance() {
        UserService result = instance;
        if (result != null) {
            return result;
        }
        synchronized (UserService.class) {
            if (instance == null) {
                instance = new UserService();
            }
            return instance;
        }
    }

    public List<User> getAllUsers(){
        return getUserDAO().getAllUser();
    }

    public User getUserByName(String name){
        return getUserDAO().getUserByName(name);
    }


    private static Connection getMysqlConnection() {
        try {
            DriverManager.registerDriver((Driver) Class.forName("com.mysql.cj.jdbc.Driver").newInstance());
            StringBuilder url = new StringBuilder();

            url.
                    append("jdbc:mysql://").        //db type
                    append("localhost:").           //host name
                    append("3306/").                //port
                    append("usersdb?").          //db name
                    append("user=root&").           //login
                    append("password=root").       //password
                    append("&serverTimezone=UTC");

            System.out.println("URL: " + url + "\n");

            return DriverManager.getConnection(url.toString());

        } catch (InstantiationException | SQLException | IllegalAccessException | ClassNotFoundException e) {
            e.printStackTrace();
            throw new IllegalStateException();
        }
    }
    private static UserDAO getUserDAO() {
        return new UserDAO(getMysqlConnection());
    }

    public boolean addUser(User user) {
        if (!dao.nameIsExist(user.getName())) {
            dao.addUser(user);
            return true;
        }
        return false;
    }

    public boolean deleteUserByName (String name){
        if (dao.nameIsExist(name)){
            dao.deleteUserByName(name);
            return true;
        }
        return false;
    }
}
