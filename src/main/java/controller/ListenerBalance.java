package controller;

import entities.Bill;
import org.apache.log4j.Logger;
import services.BillService;
import services.ServiceException;
import util.DataSource;
import util.FactoryException;
import util.MainServiceFactoryImpl;
import util.ServiceFactory;

import javax.servlet.ServletContext;
import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;
import java.util.List;

public class ListenerBalance implements ServletContextListener {
    private Thread t = null;
    private ServletContext context;
    Logger logger = Logger.getRootLogger();
    BillService billService;

    {
        try {
            billService = getServiceFactory().getBillService();
        } catch (FactoryException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void contextInitialized(ServletContextEvent servletContextEvent) {
        t = new Thread(){
            public void run(){
                try {
                    while(true){
                        try {
                            List<Bill> bills = billService.findAll();
                            for (Bill bill : bills){
                                bill.setBill(bill.getBill() + bill.getService().getPrice());
                                billService.update(bill);
                            }
                            logger.debug("Update bills");
                        } catch (ServiceException e) {
                            e.printStackTrace();
                        }
                        Thread.sleep(100000);
                    }
                } catch (InterruptedException e) {}
            }
        };
        t.start();
    }

    @Override
    public void contextDestroyed(ServletContextEvent servletContextEvent) {
        t.interrupt();
    }

    public ServiceFactory getServiceFactory() {
        return new MainServiceFactoryImpl();
    }
}
