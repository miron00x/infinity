package dao.mysqlIMPL;

import dao.DaoException;
import dao.ServiceDao;
import entities.Service;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

public class ServiceDaoImpl extends BaseDaoImpl implements ServiceDao {
    @Override
    public List<Service> readAll() throws DaoException {
        String sql = "SELECT `id`, `title`, `price` FROM `services`";
        Statement statement = null;
        ResultSet resultSet = null;
        try {
            statement = getConnection().createStatement();
            resultSet = statement.executeQuery(sql);
            List<Service> services = new ArrayList<>();
            while(resultSet.next()) {
                Service service = new Service();
                service.setId(resultSet.getLong("id"));
                service.setTitle(resultSet.getString("title"));
                service.setPrice(resultSet.getLong("price"));
                services.add(service);
            }
            return services;
        } catch(SQLException e) {
            throw new DaoException(e);
        } finally {
            try{ statement.close(); } catch(Exception e) {}
            try{ resultSet.close(); } catch(Exception e) {}
        }
    }

    @Override
    public Service read(Long id) throws DaoException {
        String sql = "SELECT `title`, `price` FROM `services` WHERE `id` = ?";
        PreparedStatement statement = null;
        ResultSet resultSet = null;
        try {
            statement = getConnection().prepareStatement(sql);
            statement.setLong(1, id);
            resultSet = statement.executeQuery();
            Service service = null;
            if(resultSet.next()) {
                service = new Service();
                service.setId(id);
                service.setTitle(resultSet.getString("title"));
                service.setPrice(resultSet.getLong("price"));
            }
            return service;
        } catch(SQLException e) {
            throw new DaoException(e);
        } finally {
            try{ statement.close(); } catch(Exception e) {}
            try{ resultSet.close(); } catch(Exception e) {}
        }
    }

    @Override
    public Long create(Service service) throws DaoException {
        return null;
    }

    @Override
    public void update(Service service) throws DaoException {

    }

    @Override
    public void delete(Long id) throws DaoException {

    }
}
