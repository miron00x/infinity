package controller.Account;

import controller.Action;
import controller.Forward;
import entities.Bill;
import entities.User;
import services.BillService;
import services.ServiceException;
import services.UserService;
import util.FactoryException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;

public class BalanceUpAction extends Action {
    @Override
    public Forward execute(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        Long balanceUp = null;
        HttpSession session = req.getSession();
        User currentUser = (User)session.getAttribute("currentUser");
        try {
            balanceUp = Long.parseLong(req.getParameter("balanceUp"));
        } catch(NumberFormatException e) {}
        try {
            if(balanceUp != null){
                UserService userService = getServiceFactory().getUserService();
                currentUser.setBalance(currentUser.getBalance() + balanceUp);
                userService.save(currentUser);
                return new Forward("/account/index.html?message=account.balance.successUp");
            }
            req.setAttribute("user", currentUser);
            return null;
        } catch (FactoryException e) {
            throw new ServletException(e);
        } catch (ServiceException e) {
            throw new ServletException(e);
        }
    }
}
