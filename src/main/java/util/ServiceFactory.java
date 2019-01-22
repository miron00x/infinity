package util;

import dao.BillDao;
import dao.ServiceDao;
import dao.UserDao;
import services.BillService;
import services.ServiceService;
import services.Transaction;
import services.UserService;

import java.sql.Connection;

public interface  ServiceFactory extends AutoCloseable {
    UserService getUserService() throws FactoryException;
    ServiceService getServiceService() throws FactoryException;
    BillService getBillService() throws  FactoryException;
    /*AccountService getAccountService() throws FactoryException;
    TransferService getTransferService() throws FactoryException;*/

    Transaction getTransaction() throws FactoryException;

    UserDao getUserDao() throws FactoryException;
    ServiceDao getServiceDao() throws FactoryException;
    BillDao getBillDao() throws FactoryException;
    /*AccountDao getAccountDao() throws FactoryException;
    TransferDao getTransferDao() throws FactoryException;*/

    Connection getConnection() throws FactoryException;
}
