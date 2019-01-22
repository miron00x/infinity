package controller.User;

import controller.Action;
import controller.Forward;
import entities.Bill;
import entities.Role;
import entities.User;
import services.BillService;
import services.ServiceException;
import services.UserService;
import util.FactoryException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class UserListAction extends Action {
    @Override
    public Forward execute(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        try {
            UserService userService = getServiceFactory().getUserService();
            List<User> users = userService.findAll();
            List<User> usersNotAdmin = new ArrayList<>();
            BillService billService = getServiceFactory().getBillService();
            List<Bill> bills = billService.findAll();
            for(User user : users){
                if (user.getRole() == Role.ADMINISTRATOR) {
                    continue;
                }
                user.setCredit(Long.valueOf(0));
                for(Bill bill : bills){
                    if (bill.getUser().getId() == user.getId())
                        user.setCredit(user.getCredit() + bill.getBill() - bill.getPaid());
                }
                usersNotAdmin.add(user);
            }
            req.setAttribute("users", usersNotAdmin);
            return null;
        } catch(FactoryException | ServiceException e) {
            throw new ServletException(e);
        }
    }
}
