package controller.Service;

import controller.Action;
import controller.Forward;
import entities.Bill;
import entities.Service;
import entities.User;
import services.BillService;
import services.ServiceException;
import services.ServiceService;
import services.UserService;
import util.FactoryException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.util.List;

public class ServiceListAction extends Action {
    @Override
    public Forward execute(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        Long id = null;
        try {
            id = Long.parseLong(req.getParameter("id"));
        } catch(NumberFormatException e) {}

        try {
            ServiceService serviceService = getServiceFactory().getServiceService();
            List<Service> services = serviceService.findAll();
            req.setAttribute("services", services);
            if(id != null) {
                HttpSession session = req.getSession();
                User currentUser = (User)session.getAttribute("currentUser");
                BillService billService = getServiceFactory().getBillService();
                Service service = serviceService.findById(id);
                List<Bill> userBills = billService.findByUser(currentUser);
                for (Bill bill : userBills){
                    if(bill.getService().getTitle().equals(service.getTitle())){
                        return new Forward("/service/list.html?message=service.error.exists");
                    }
                }
                Bill bill = new Bill();
                bill.setUser(currentUser);
                bill.setService(service);
                bill.setBill(service.getPrice());
                bill.setPaid(Long.valueOf(0));
                billService.save(bill);
                return new Forward("/service/list.html?message=service.list.success");
            }
            return null;
        } catch (FactoryException | ServiceException e) {
            throw new ServletException(e);
        }
    }
}
