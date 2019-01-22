package dao;

import entities.User;

import java.util.List;

public interface UserDao extends Dao<User, Long> {
    List<User> readAll() throws DaoException;

    User readByLogin(String login) throws DaoException;

    User readByLoginAndPassword(String login, String password) throws DaoException;
}
