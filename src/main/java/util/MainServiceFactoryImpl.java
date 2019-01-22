package util;

import dao.BillDao;
import dao.ServiceDao;
import dao.UserDao;
import dao.mysqlIMPL.BillDaoImpl;
import dao.mysqlIMPL.ServiceDaoImpl;
import dao.mysqlIMPL.UserDaoImpl;
import services.BillService;
import services.ServiceService;
import services.Transaction;
import services.UserService;
import services.impl.BillServiceImpl;
import services.impl.ServiceServiceImpl;
import services.impl.TransactionImpl;
import services.impl.UserServiceImpl;

import java.io.Closeable;
import java.sql.Connection;
import java.sql.SQLException;

public class MainServiceFactoryImpl implements ServiceFactory {
    private Connection connection;

    @Override
    public UserService getUserService() throws FactoryException {
        UserServiceImpl userService = new UserServiceImpl();
        userService.setTransaction(getTransaction());
        userService.setUserDao(getUserDao());
        return userService;
    }

    @Override
    public ServiceService getServiceService() throws FactoryException {
        ServiceServiceImpl serviceService = new ServiceServiceImpl();
        serviceService.setTransaction(getTransaction());
        serviceService.setServiceDao(getServiceDao());
        return serviceService;
    }

    @Override
    public BillService getBillService() throws FactoryException {
        BillServiceImpl billService = new BillServiceImpl();
        billService.setTransaction(getTransaction());
        billService.setBillDao(getBillDao());
        billService.setUserDao(getUserDao());
        billService.setServiceDao(getServiceDao());
        return billService;
    }

    @Override
    public Transaction getTransaction() throws FactoryException {
        TransactionImpl transaction = new TransactionImpl();
        transaction.setConnection(getConnection());
        return transaction;
    }

    @Override
    public UserDao getUserDao() throws FactoryException {
        UserDaoImpl userDao = new UserDaoImpl();
        userDao.setConnection(getConnection());
        return userDao;
    }

    @Override
    public ServiceDao getServiceDao() throws FactoryException {
        ServiceDaoImpl serviceDao = new ServiceDaoImpl();
        serviceDao.setConnection(getConnection());
        return serviceDao;
    }

    @Override
    public BillDao getBillDao() throws FactoryException {
        BillDaoImpl billDao = new BillDaoImpl();
        billDao.setConnection(getConnection());
        return billDao;
    }

    @Override
    public Connection getConnection() throws FactoryException {
        if (connection == null){
            try {
                connection = DataSource.getConnection();
                return connection;
            } catch (ClassNotFoundException e) {
                e.printStackTrace();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        } else {
            return connection;
        }
        throw new FactoryException("Ошибка при получение соединения с БД");
    }

    @Override
    public void close() throws Exception {
        try {
            if(connection != null) {
                DataSource.returnConnection(connection);
            }
        } catch(Exception e) {}
    }
}
