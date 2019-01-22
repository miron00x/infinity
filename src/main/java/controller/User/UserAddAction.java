package controller.User;

import controller.Action;
import controller.Forward;
import entities.Role;
import entities.User;
import services.ServiceException;
import services.UserLoginNotUniqueException;
import services.UserService;
import util.FactoryException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;

public class UserAddAction extends Action {
    @Override
    public Forward execute(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String login = req.getParameter("login");
        String password = req.getParameter("password");
        String name = req.getParameter("name");
        String balanceStr = req.getParameter("balance");
        if(login != null && password != null && name != null && balanceStr != null) {
            Long balance = Long.valueOf(balanceStr);
            if(balance < 0) return null;
            try {
                UserService userService = getServiceFactory().getUserService();
                User user = new User();
                user.setStatus(true);
                user.setLogin(login);
                user.setPassword(password);
                user.setName(name);
                user.setRole(Role.USER);
                user.setBalance(balance);
                userService.save(user);
                return new Forward("/user/list.html");
            } catch(FactoryException | ServiceException e) {
                if (e.getClass() == UserLoginNotUniqueException.class)
                    return new Forward("/user/add.html?message=user.add.error");
                throw new ServletException(e);
            }
        } else {
            return null;
        }
    }
}
