package services;

import entities.Service;
import entities.User;

import java.util.List;

public interface ServiceService {
    Service findById(Long id) throws ServiceException;

    List<Service> findAll() throws ServiceException;

    void save(Service user) throws ServiceException;

    void delete(Long id) throws ServiceException;
}
