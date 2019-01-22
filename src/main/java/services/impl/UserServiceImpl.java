package services.impl;

import dao.DaoException;
import dao.UserDao;
import entities.User;
import services.ServiceException;
import services.UserLoginNotUniqueException;
import services.UserNotExistsException;
import services.UserService;

import java.util.List;

public class UserServiceImpl extends BaseService implements UserService {
    private UserDao userDao;

    public void setUserDao(UserDao userDao) {
        this.userDao = userDao;
    }

    @Override
    public User findById(Long id) throws ServiceException {
        try {
            return userDao.read(id);
        } catch (DaoException e) {
            throw new ServiceException(e);
        }
    }

    @Override
    public User findByLoginAndPassword(String login, String password) throws ServiceException {
        try {
            return userDao.readByLoginAndPassword(login, password);
        } catch (DaoException e) {
            throw new ServiceException(e);
        }
    }

    @Override
    public List<User> findAll() throws ServiceException {
        try {
            return userDao.readAll();
        } catch (DaoException e) {
            throw new ServiceException(e);
        }
    }

    @Override
    public void save(User user) throws ServiceException {
        try {
            getTransaction().start();
            if(user.getId() != null) {
                User storedUser = userDao.read(user.getId());
                if(storedUser != null) {
                    user.setPassword(storedUser.getPassword());
                    if(storedUser.getLogin().equals(user.getLogin()) || userDao.readByLogin(user.getLogin()) == null) {
                        userDao.update(user);
                    } else {
                        throw new UserLoginNotUniqueException(user.getLogin());
                    }
                } else {
                    throw new UserNotExistsException(user.getId());
                }
            } else {
                if(userDao.readByLogin(user.getLogin()) == null) {
                    Long id = userDao.create(user);
                    user.setId(id);
                } else {
                    throw new UserLoginNotUniqueException(user.getLogin());
                }
            }
            getTransaction().commit();
        } catch(DaoException e) {
            try { getTransaction().rollback(); } catch(ServiceException e1) {}
            throw new ServiceException(e);
        } catch(ServiceException e) {
            try { getTransaction().rollback(); } catch(ServiceException e1) {}
            throw e;
        }
    }

    @Override
    public void changePassword(Long userId, String oldPassword, String newPassword) throws ServiceException {

    }

    @Override
    public void delete(Long id) throws ServiceException {

    }

    @Override
    public void block(Long id) throws ServiceException {
        try{
            getTransaction().start();
            User user = userDao.read(id);
            if(user.isStatus()) {
                user.setStatus(false);
            } else {
                user.setStatus(true);
            }
            userDao.update(user);
            getTransaction().commit();
        } catch(DaoException e) {
            try { getTransaction().rollback(); } catch(ServiceException e1) {}
            throw new ServiceException(e);
        } catch(ServiceException e) {
            try { getTransaction().rollback(); } catch(ServiceException e1) {}
            throw e;
        }
    }
}
