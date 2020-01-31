import dao.UserDao;
import model.User;

import java.util.List;

/**
 * @author Oleg Pavlyukov
 * on 31.01.2020
 * cpabox777@gmail.com
 */
public class Main {

    public static void main(String[] args) {
        UserDao userDao = new UserDao();

        User user = new User(1L, "Oleg", 29);

        System.out.println("/////////////////////////");
        System.out.println("Add new user");

        boolean isAdded = userDao.addUser(user);
        System.out.println("Is user added? -> " + isAdded);

        System.out.println("/////////////////////////");
        System.out.println("Get all users");

        List<User> userList = userDao.getUserList();
        userList.forEach(System.out::println);

        System.out.println("/////////////////////////");
        System.out.println("Get user by id");

        Long gettingId = userList.get(0).getId();

        User resultUserById = userDao.getUserById(gettingId);
        System.out.println(resultUserById);

        System.out.println("/////////////////////////");
        System.out.println("Update user");

        Long updateId = userList.get(0).getId();
        User updateUser = userDao.getUserById(updateId);
        System.out.println("Before update -> " + updateUser);

        updateUser.setAge(30);
        updateUser.setName("Not Oleg");
        userDao.updateUser(updateUser);

        User resultUpdateUser = userDao.getUserById(updateId);
        System.out.println("After update -> " + resultUpdateUser);

        System.out.println("/////////////////////////");
        System.out.println("Delete user");

        Long deleteId = userList.get(0).getId();
        boolean isDeleted = userDao.deleteUserById(deleteId);

        System.out.println("Is user deleted? -> " + isDeleted);
    }
}
