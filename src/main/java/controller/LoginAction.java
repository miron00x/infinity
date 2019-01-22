package controller;

import entities.User;
import org.apache.log4j.Logger;
import services.ServiceException;
import services.UserLoginNotUniqueException;
import services.UserNotExistsException;
import services.UserService;
import util.FactoryException;

import javax.servlet.ServletException;
import javax.servlet.http.*;
import java.io.IOException;

public class LoginAction extends Action {
    Logger logger = Logger.getRootLogger();
    @Override
    public Forward execute(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String login = req.getParameter("login");
        String password = req.getParameter("password");
        if(login != null && password != null) {
            try {
                UserService service = getServiceFactory().getUserService();
                User user = service.findByLoginAndPassword(login, password);
                if(user != null && user.isStatus()) {
                    HttpSession session = req.getSession();
                    session.setAttribute("currentUser", user);
                    logger.info("User " + user.getLogin() + " is logged in");
                    return new Forward("/index.html");
                } else if(user != null && !user.isStatus()) {
                    return new Forward("/login.html?message=login.message.block");
                } else {
                    return new Forward("/login.html?message=login.message.incorrect.password");
                }
            } catch(FactoryException | ServiceException e) {
                if (e.getClass() == UserNotExistsException.class)
                    return new Forward("/user/add.html?message=login.message.incorrect.password");
                throw new ServletException(e);
            }
        } else {
            return null;
        }
    }
}
