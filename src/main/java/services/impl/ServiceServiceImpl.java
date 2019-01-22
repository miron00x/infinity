package services.impl;

import dao.DaoException;
import dao.ServiceDao;
import entities.Service;
import services.ServiceException;
import services.ServiceService;

import java.util.List;

public class ServiceServiceImpl extends BaseService implements ServiceService {
    private ServiceDao serviceDao;

    public void setServiceDao(ServiceDao serviceDao){
        this.serviceDao = serviceDao;
    }

    @Override
    public Service findById(Long id) throws ServiceException {
        try {
            return serviceDao.read(id);
        } catch (DaoException e) {
            throw new ServiceException(e);
        }
    }

    @Override
    public List<Service> findAll() throws ServiceException {
        try {
            return serviceDao.readAll();
        } catch (DaoException e) {
            throw new ServiceException(e);
        }
    }

    @Override
    public void save(Service user) throws ServiceException {

    }

    @Override
    public void delete(Long id) throws ServiceException {

    }
}
