package controller.Account;

import controller.Action;
import controller.Forward;
import entities.Bill;
import entities.User;
import services.BillService;
import services.ServiceException;
import services.UserService;
import services.impl.BillServiceImpl;
import util.FactoryException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.util.List;

public class AccountDataAction extends Action {
    @Override
    public Forward execute(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        Long id = null;
        Long toPay = null;
        Long bill_id = null;
        try {
            HttpSession session = req.getSession();
            User currentUser = (User)session.getAttribute("currentUser");
            try {
                id = Long.parseLong(req.getParameter("id"));
            } catch(NumberFormatException e) {}
            try {
                toPay = Long.parseLong(req.getParameter("toPay"));
                bill_id = Long.parseLong(req.getParameter("bill_id"));
            } catch(NumberFormatException e) {}
            BillService billService = getServiceFactory().getBillService();
            UserService userService = getServiceFactory().getUserService();
            if(toPay != null){
                if(toPay <= 0)
                    return new Forward("/account/index.html?message=account.error.lie");
                if (toPay > billService.findById(bill_id).getBill() - billService.findById(bill_id).getPaid())
                    return new Forward("/account/index.html?message=account.error.manyMoney");
                if (toPay > currentUser.getBalance())
                    return new Forward("/account/index.html?message=account.error.notMoney");
                Bill bill = billService.findById(bill_id);
                bill.setPaid(bill.getPaid() + toPay);
                billService.update(bill);
                currentUser.setBalance(currentUser.getBalance() - toPay);
                userService.save(currentUser);
                return new Forward("/account/index.html?message=account.success.paid");
            }
            if(id != null) {
                if(billService.findById(id).getBill() - billService.findById(id).getPaid() > 0)
                    return new Forward("/account/index.html?message=account.error.mustPay");
                billService.delete(id);
                return new Forward("/account/index.html?message=account.success.disable");
            }
            List<Bill> bills = billService.findByUser(currentUser);
            req.setAttribute("user", currentUser);
            req.setAttribute("bills", bills);
            return null;
        } catch (FactoryException e) {
            throw new ServletException(e);
        } catch (ServiceException e) {
            throw new ServletException(e);
        }
    }
}
