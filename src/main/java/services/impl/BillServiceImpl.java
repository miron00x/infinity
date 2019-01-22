package services.impl;

import dao.BillDao;
import dao.DaoException;
import dao.ServiceDao;
import dao.UserDao;
import entities.Bill;
import entities.Service;
import entities.User;
import services.BillService;
import services.ServiceException;
import services.UserLoginNotUniqueException;
import services.UserNotExistsException;

import java.util.ArrayList;
import java.util.List;

public class BillServiceImpl extends BaseService implements BillService {
    private BillDao billDao;
    private UserDao userDao;
    private ServiceDao serviceDao;

    public void setBillDao(BillDao billDao) {
        this.billDao = billDao;
    }
    public void setUserDao(UserDao userDao){
        this.userDao = userDao;
    }
    public void setServiceDao(ServiceDao serviceDao) {
        this.serviceDao = serviceDao;
    }

    @Override
    public Bill findById(Long id) throws ServiceException {
        try {
            Bill bill = billDao.read(id);
            Service service = serviceDao.read(bill.getService().getId());
            bill.setService(service);
            User user = userDao.read(bill.getUser().getId());
            bill.setUser(user);
            return bill;
        } catch (DaoException e) {
            throw new ServiceException(e);
        }
    }

    @Override
    public List<Bill> findByUser(User user) throws ServiceException {
        List<Bill> bills = findAll();
        List<Bill> out_bills = new ArrayList<>();
        for (Bill bill : bills){
            if (bill.getUser().getId() == user.getId()){
                out_bills.add(bill);
            }
        }
        return out_bills;
    }

    @Override
    public List<Bill> findAll() throws ServiceException {
        try {
            List<Bill> bills = billDao.readAll();
            for (Bill bill : bills){
                User user = userDao.read(bill.getUser().getId());
                bill.setUser(user);
                Service service = serviceDao.read(bill.getService().getId());
                bill.setService(service);
            }
            return bills;
        } catch (DaoException e) {
            throw new ServiceException(e);
        }
    }

    @Override
    public void save(Bill bill) throws ServiceException {
        try {
            getTransaction().start();
            Long id = billDao.create(bill);
            bill.setId(id);
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
    public void delete(Long id) throws ServiceException {
        try {
            getTransaction().start();
            billDao.delete(id);
            getTransaction().commit();
        } catch (DaoException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void update(Bill bill) throws ServiceException {
        try {
            getTransaction().start();
            billDao.update(bill);
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
