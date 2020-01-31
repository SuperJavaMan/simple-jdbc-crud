package dao;

import model.User;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

/**
 * @author Oleg Pavlyukov
 * on 31.01.2020
 * cpabox777@gmail.com
 */
public class UserDao {

    private final String URL = "jdbc:mysql://localhost:3306/jdbc_test?useUnicode=true&serverTimezone=UTC&useSSL=true&verifyServerCertificate=false";
    private final String login = "root";
    private final String password = "root";
    private final String driverName = "com.mysql.cj.jdbc.Driver";

    private final String sqlReadAll = "SELECT * FROM user";
    private final String sqlGetById = "SELECT * FROM user WHERE id = ?";
    private final String sqlInsert = "INSERT INTO user (name, age) VALUES(?, ?)";
    private final String sqlUpdate = "UPDATE user SET name = ?, age = ? WHERE id = ?";
    private final String sqlDelete = "DELETE FROM user WHERE id = ?";

    private Connection getConnection() {
        Connection connection = null;

        try {
            Class.forName(driverName);
        } catch (ClassNotFoundException e) {
            System.err.println("Can't get class. No driver found");
            e.printStackTrace();
        }

        try {
            connection = DriverManager.getConnection(URL, login, password);
        } catch (SQLException e) {
            System.err.println("Can't get connection. Incorrect URL");
            e.printStackTrace();
        }
        return connection;
    }

    public List<User> getUserList() {
        List<User> userList = null;

        try(Connection connection = getConnection();
            Statement statement = connection.createStatement()) {

            ResultSet resultSet = statement.executeQuery(sqlReadAll);

            userList = new ArrayList<>();
            while (resultSet.next()) {
                User user = new User();
                user.setId(resultSet.getLong("id"));
                user.setName(resultSet.getString("name"));
                user.setAge(resultSet.getInt("age"));
                userList.add(user);
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }
        return userList;
    }

    public User getUserById(Long id) {
        User user = null;

        try(Connection connection = getConnection();
            PreparedStatement preparedStatement = connection.prepareStatement(sqlGetById)) {

            preparedStatement.setLong(1, id);

            ResultSet resultSet = preparedStatement.executeQuery();

            if (resultSet.next()) {
                user = new User(resultSet.getLong("id"),
                                resultSet.getString("name"),
                                resultSet.getInt("age"));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return user;
    }

    public boolean addUser(User user) {
        boolean isInserted = false;

        try(Connection connection = getConnection();
            PreparedStatement ps = connection.prepareStatement(sqlInsert)) {
            ps.setString(1, user.getName());
            ps.setInt(2, user.getAge());
            int rowAf = ps.executeUpdate();

            if (rowAf > 0) isInserted = true;
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return isInserted;
    }

    public boolean updateUser(User user) {
        boolean isUpdated = false;

        try(Connection connection = getConnection();
            PreparedStatement pr = connection.prepareStatement(sqlUpdate)) {

            pr.setString(1, user.getName());
            pr.setInt(2, user.getAge());
            pr.setLong(3, user.getId());

            int rowAf = pr.executeUpdate();
            if (rowAf < 0) isUpdated = true;

        } catch (SQLException e) {
            e.printStackTrace();
        }
        return isUpdated;
    }

    public boolean deleteUserById(Long id) {
        boolean isDeleted = false;

        try(Connection connection = getConnection();
            PreparedStatement ps = connection.prepareStatement(sqlDelete)) {

            ps.setLong(1, id);
            int rowAf = ps.executeUpdate();

            if (rowAf > 0) isDeleted = true;

        } catch (SQLException e) {
            e.printStackTrace();
        }
        return isDeleted;
    }
}
