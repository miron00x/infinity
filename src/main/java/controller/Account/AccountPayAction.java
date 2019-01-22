package controller.Account;

import controller.Action;
import controller.Forward;
import entities.Bill;
import services.BillService;
import services.ServiceException;
import util.FactoryException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

public class AccountPayAction extends Action {
    @Override
    public Forward execute(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        Long id = null;
        try {
            id = Long.parseLong(req.getParameter("id"));
        } catch(NumberFormatException e) {}
        try {
            BillService billService = getServiceFactory().getBillService();
            Bill bill = billService.findById(id);
            req.setAttribute("bill", bill);
            return null;
        } catch (FactoryException e) {
            throw new ServletException(e);
        } catch (ServiceException e) {
            throw new ServletException(e);
        }
    }
}
