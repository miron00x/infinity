package testdao;

import dao.DaoException;
import dao.mysqlIMPL.UserDaoImpl;
import entities.User;
import util.DataSource;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.List;

public class UserDaoTest {
    public static void main(String[] args) {
        UserDaoImpl userDao = new UserDaoImpl();
        Connection connection = null;
        try {
            connection = DataSource.getConnection();
            userDao.setConnection(connection);
            System.out.println("UserDao.readAll():");
            readAllTest(userDao);
            readByLoginAndPassword(userDao);
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        } catch (SQLException e) {
            e.printStackTrace();
        } catch (DaoException e) {
            e.printStackTrace();
        }
    }

    private static void readAllTest(UserDaoImpl userDao) throws DaoException {
        List<User> users = userDao.readAll();
        for(User user : users) {
            output(user);
        }
    }

    private static void readByLoginAndPassword(UserDaoImpl userDao) throws DaoException {
        User user = userDao.readByLoginAndPassword("lol1", "qwerty");
        output(user);
    }

    private static void output(User user) {
        System.out.printf("\tid=%d, login=%s, password=%s, role=%s, name=%s, balance=%d, status=%s\n", user.getId(), user.getLogin(), user.getPassword(), user.getRole().toString(), user.getName(), user.getBalance(), user.isStatus());
    }
}
